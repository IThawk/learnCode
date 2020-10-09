/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.el;

import java.beans.BeanInfo;
import java.beans.FeatureDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class BeanELResolver extends ELResolver {

    private static final int CACHE_SIZE;
    private static final String CACHE_SIZE_PROP =
        "org.apache.el.BeanELResolver.CACHE_SIZE";

    static {
        if (System.getSecurityManager() == null) {
            CACHE_SIZE = Integer.parseInt(
                    System.getProperty(CACHE_SIZE_PROP, "1000"));
        } else {
            CACHE_SIZE = AccessController.doPrivileged(
                    new PrivilegedAction<Integer>() {

                    @Override
                    public Integer run() {
                        return Integer.valueOf(
                                System.getProperty(CACHE_SIZE_PROP, "1000"));
                    }
                }).intValue();
        }
    }

    private final boolean readOnly;

    private final ConcurrentCache<String, BeanProperties> cache =
        new ConcurrentCache<>(CACHE_SIZE);

    public BeanELResolver() {
        this.readOnly = false;
    }

    public BeanELResolver(boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base == null || property == null) {
            return null;
        }

        context.setPropertyResolved(base, property);
        return this.property(context, base, property).getPropertyType();
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base == null || property == null) {
            return null;
        }

        context.setPropertyResolved(base, property);
        Method m = this.property(context, base, property).read(context);
        try {
            return m.invoke(base, (Object[]) null);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            Util.handleThrowable(cause);
            throw new ELException(Util.message(context, "propertyReadError",
                    base.getClass().getName(), property.toString()), cause);
        } catch (Exception e) {
            throw new ELException(e);
        }
    }

    @Override
    public void setValue(ELContext context, Object base, Object property,
            Object value) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base == null || property == null) {
            return;
        }

        context.setPropertyResolved(base, property);

        if (this.readOnly) {
            throw new PropertyNotWritableException(Util.message(context,
                    "resolverNotWriteable", base.getClass().getName()));
        }

        Method m = this.property(context, base, property).write(context);
        try {
            m.invoke(base, value);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            Util.handleThrowable(cause);
            throw new ELException(Util.message(context, "propertyWriteError",
                    base.getClass().getName(), property.toString()), cause);
        } catch (Exception e) {
            throw new ELException(e);
        }
    }

    /**
     * @since EL 2.2
     */
    @Override
    public Object invoke(ELContext context, Object base, Object method,
            Class<?>[] paramTypes, Object[] params) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base == null || method == null) {
            return null;
        }

        ExpressionFactory factory = ELManager.getExpressionFactory();

        String methodName = (String) factory.coerceToType(method, String.class);

        // Find the matching method
        Method matchingMethod =
                Util.findMethod(base.getClass(), methodName, paramTypes, params);

        Object[] parameters = Util.buildParameters(
                matchingMethod.getParameterTypes(), matchingMethod.isVarArgs(),
                params);

        Object result = null;
        try {
            result = matchingMethod.invoke(base, parameters);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new ELException(e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            Util.handleThrowable(cause);
            throw new ELException(cause);
        }

        context.setPropertyResolved(base, method);
        return result;
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base == null || property == null) {
            return false;
        }

        context.setPropertyResolved(base, property);
        return this.readOnly
                || this.property(context, base, property).isReadOnly();
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        if (base == null) {
            return null;
        }

        try {
            BeanInfo info = Introspector.getBeanInfo(base.getClass());
            PropertyDescriptor[] pds = info.getPropertyDescriptors();
            for (int i = 0; i < pds.length; i++) {
                pds[i].setValue(RESOLVABLE_AT_DESIGN_TIME, Boolean.TRUE);
                pds[i].setValue(TYPE, pds[i].getPropertyType());
            }
            return Arrays.asList((FeatureDescriptor[]) pds).iterator();
        } catch (IntrospectionException e) {
            //
        }

        return null;
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        if (base != null) {
            return Object.class;
        }

        return null;
    }

    static final class BeanProperties {
        private final Map<String, BeanProperty> properties;

        private final Class<?> type;

        public BeanProperties(Class<?> type) throws ELException {
            this.type = type;
            this.properties = new HashMap<>();
            try {
                BeanInfo info = Introspector.getBeanInfo(this.type);
                PropertyDescriptor[] pds = info.getPropertyDescriptors();
                for (int i = 0; i < pds.length; i++) {
                    this.properties.put(pds[i].getName(), new BeanProperty(
                            type, pds[i]));
                }
            } catch (IntrospectionException ie) {
                throw new ELException(ie);
            }
        }

        private BeanProperty get(ELContext ctx, String name) {
            BeanProperty property = this.properties.get(name);
            if (property == null) {
                throw new PropertyNotFoundException(Util.message(ctx,
                        "propertyNotFound", type.getName(), name));
            }
            return property;
        }

        public BeanProperty getBeanProperty(String name) {
            return get(null, name);
        }

        private Class<?> getType() {
            return type;
        }
    }

    static final class BeanProperty {
        private final Class<?> type;

        private final Class<?> owner;

        private final PropertyDescriptor descriptor;

        private Method read;

        private Method write;

        public BeanProperty(Class<?> owner, PropertyDescriptor descriptor) {
            this.owner = owner;
            this.descriptor = descriptor;
            this.type = descriptor.getPropertyType();
        }

        // Can't use Class<?> because API needs to match specification
        @SuppressWarnings("rawtypes")
        public Class getPropertyType() {
            return this.type;
        }

        public boolean isReadOnly() {
            return this.write == null
                && (null == (this.write = Util.getMethod(this.owner, descriptor.getWriteMethod())));
        }

        public Method getWriteMethod() {
            return write(null);
        }

        public Method getReadMethod() {
            return this.read(null);
        }

        private Method write(ELContext ctx) {
            if (this.write == null) {
                this.write = Util.getMethod(this.owner, descriptor.getWriteMethod());
                if (this.write == null) {
                    throw new PropertyNotWritableException(Util.message(ctx,
                            "propertyNotWritable", new Object[] {
                                    owner.getName(), descriptor.getName() }));
                }
            }
            return this.write;
        }

        private Method read(ELContext ctx) {
            if (this.read == null) {
                this.read = Util.getMethod(this.owner, descriptor.getReadMethod());
                if (this.read == null) {
                    throw new PropertyNotFoundException(Util.message(ctx,
                            "propertyNotReadable", new Object[] {
                                    owner.getName(), descriptor.getName() }));
                }
            }
            return this.read;
        }
    }

    private final BeanProperty property(ELContext ctx, Object base,
            Object property) {
        Class<?> type = base.getClass();
        String prop = property.toString();

        BeanProperties props = this.cache.get(type.getName());
        if (props == null || type != props.getType()) {
            props = new BeanProperties(type);
            this.cache.put(type.getName(), props);
        }

        return props.get(ctx, prop);
    }

    private static final class ConcurrentCache<K,V> {

        private final int size;
        private final Map<K,V> eden;
        private final Map<K,V> longterm;

        public ConcurrentCache(int size) {
            this.size = size;
            this.eden = new ConcurrentHashMap<>(size);
            this.longterm = new WeakHashMap<>(size);
        }

        public V get(K key) {
            V value = this.eden.get(key);
            if (value == null) {
                synchronized (longterm) {
                    value = this.longterm.get(key);
                }
                if (value != null) {
                    this.eden.put(key, value);
                }
            }
            return value;
        }

        public void put(K key, V value) {
            if (this.eden.size() >= this.size) {
                synchronized (longterm) {
                    this.longterm.putAll(this.eden);
                }
                this.eden.clear();
            }
            this.eden.put(key, value);
        }

    }
}
