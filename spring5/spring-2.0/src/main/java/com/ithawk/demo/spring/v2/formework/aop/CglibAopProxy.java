package com.ithawk.demo.spring.v2.formework.aop;

import com.ithawk.demo.spring.v2.formework.aop.support.AdvisedSupport;
import com.ithawk.demo.spring.v2.formework.context.ApplicationContext;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.apache.log4j.Logger;


public class CglibAopProxy implements AopProxy {

    private static Logger logger = Logger.getLogger(ApplicationContext.class);


    private AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport config) {
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(null);
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {

        try {
            Class<?> rootClass = this.advised.getTargetClass();

            Class<?> proxySuperClass = rootClass;
            if (rootClass.getName().contains("$$")) {
                proxySuperClass = rootClass.getSuperclass();
                Class<?>[] additionalInterfaces = rootClass.getInterfaces();
//                for (Class<?> additionalInterface : additionalInterfaces) {
//                    this.advised.addInterface(additionalInterface);
//                }
            }

            // Validate the class, writing log messages as necessary.
//            validateClassIfNecessary(proxySuperClass, classLoader);

            // Configure CGLIB Enhancer...
            Enhancer enhancer = createEnhancer();
            if (classLoader != null) {
                enhancer.setClassLoader(classLoader);
//                if (classLoader instanceof SmartClassLoader &&
//                        ((SmartClassLoader) classLoader).isClassReloadable(proxySuperClass)) {
//                    enhancer.setUseCache(false);
//                }
            }
            enhancer.setSuperclass(proxySuperClass);
//            enhancer.setInterfaces(AopProxyUtils.completeProxiedInterfaces(this.advised));
//            enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
//            enhancer.setStrategy(new ClassLoaderAwareGeneratorStrategy(classLoader));

            Callback[] callbacks = getCallbacks(rootClass);
            Class<?>[] types = new Class<?>[callbacks.length];
            for (int x = 0; x < types.length; x++) {
                types[x] = callbacks[x].getClass();
            }
            // fixedInterceptorMap only populated at this point, after getCallbacks call above
//            enhancer.setCallbackFilter(new ProxyCallbackFilter(
//                    this.advised.getConfigurationOnlyCopy(), this.fixedInterceptorMap, this.fixedInterceptorOffset));
            enhancer.setCallbackTypes(types);

            // Generate the proxy class and create a proxy instance.
            return createProxyClassAndInstance(enhancer, callbacks);
        } catch (IllegalArgumentException ex) {
        }
        return null;

    }


    protected Object createProxyClassAndInstance(Enhancer enhancer, Callback[] callbacks) {
        enhancer.setInterceptDuringConstruction(false);
        enhancer.setCallbacks(callbacks);
        return enhancer.create();
    }

    protected Enhancer createEnhancer() {
        return new Enhancer();
    }


    private Callback[] getCallbacks(Class<?> rootClass) {
        // Parameters used for optimization choices...
//        boolean exposeProxy = this.advised.isExposeProxy();
//        boolean isFrozen = this.advised.isFrozen();
//        boolean isStatic = this.advised.getTargetSource().isStatic();
//
//        // Choose an "aop" interceptor (used for AOP calls).
//        Callback aopInterceptor = new DynamicAdvisedInterceptor(this.advised);

        // Choose a "straight to target" interceptor. (used for calls that are
        // unadvised but can return this). May be required to expose the proxy.
//        Callback targetInterceptor = new Callback[] ;
//        if (exposeProxy) {
//            targetInterceptor = (isStatic ?
//                    new StaticUnadvisedExposedInterceptor(this.advised.getTargetSource().getTarget()) :
//                    new DynamicUnadvisedExposedInterceptor(this.advised.getTargetSource()));
//        }
//        else {
//            targetInterceptor = (isStatic ?
//                    new StaticUnadvisedInterceptor(this.advised.getTargetSource().getTarget()) :
//                    new DynamicUnadvisedInterceptor(this.advised.getTargetSource()));
//        }

        // Choose a "direct to target" dispatcher (used for
        // unadvised calls to static targets that cannot return this).
//        Callback targetDispatcher = (isStatic ?
//                new StaticDispatcher(this.advised.getTargetSource().getTarget()) : new SerializableNoOp());
//
//        Callback[] mainCallbacks = new Callback[] {
//                aopInterceptor,  // for normal advice
//                targetInterceptor,  // invoke target without considering advice, if optimized
//                new SerializableNoOp(),  // no override for methods mapped to this
//                targetDispatcher, this.advisedDispatcher,
//                new EqualsInterceptor(this.advised),
//                new HashCodeInterceptor(this.advised)
//        };

        Callback[] callbacks = new Callback[0];

        // If the target is a static one and the advice chain is frozen,
        // then we can make some optimizations by sending the AOP calls
        // direct to the target using the fixed chain for that method.
//        if (isStatic && isFrozen) {
//            Method[] methods = rootClass.getMethods();
//            Callback[] fixedCallbacks = new Callback[methods.length];
//            this.fixedInterceptorMap = new HashMap<>(methods.length);
//
//            // TODO: small memory optimization here (can skip creation for methods with no advice)
//            for (int x = 0; x < methods.length; x++) {
//                Method method = methods[x];
//                List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, rootClass);
//                fixedCallbacks[x] = new FixedChainStaticTargetInterceptor(
//                        chain, this.advised.getTargetSource().getTarget(), this.advised.getTargetClass());
//                this.fixedInterceptorMap.put(method, x);
//            }
//
//            // Now copy both the callbacks from mainCallbacks
//            // and fixedCallbacks into the callbacks array.
//            callbacks = new Callback[mainCallbacks.length + fixedCallbacks.length];
//            System.arraycopy(mainCallbacks, 0, callbacks, 0, mainCallbacks.length);
//            System.arraycopy(fixedCallbacks, 0, callbacks, mainCallbacks.length, fixedCallbacks.length);
//            this.fixedInterceptorOffset = mainCallbacks.length;
//        }
//        else{
//            callbacks = mainCallbacks;
//        }
        return callbacks;
    }

}
