# dubbo spi
## 使用
### 添加扩展接口 Color
```java
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI("color")
public interface Color {

    void sayMy();

    @Adaptive
    void sayHello();

}
```
### 实现类
```java
public class RedColor implements Color {


    @Override
    public void sayMy() {
        System.out.println("我是红色的呀！你喜欢吗?");
    }

    @Override
    public void sayHello() {
        System.out.println("流血的痛苦！");
    }

}

```

```java
import org.apache.dubbo.common.extension.Activate;

@Activate(value = "blue")
public class BlueColor implements Color {


    @Override
    public void sayMy() {
        System.out.println("我是蓝色的呀！你喜欢吗?");
    }

    @Override
    public void sayHello() {
        System.out.println("红红火火的对你说喜欢你！");
    }

}

```
### 配置代码
    在项目路径下 resource/META-INF.dubbo 添加扩展接口名的文件：org.apache.dubbo.demo.spi.Color
    red=org.apache.dubbo.demo.spi.RedColor
    blue=org.apache.dubbo.demo.spi.BlueColor
### 使用代码：
``` java
public class SpiUseDemo {

    public static void main(String[] args) {
        //dubbo会默认去查找META-INF的SPI定义
        //静态扩展点
        //自适应扩展点
        //激活扩展点
//        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("myprotocol");
//        System.out.println(protocol.getDefaultPort());
//
//        Compiler protocol=ExtensionLoader.getExtensionLoader(Compiler.class).getAdaptiveExtension();
//        //.AdaptiveCompiler@1a93a7ca
//        URL url = new URL("", "", 0);
//        url = url.addParameter("cache", "cache");//添加这段代码 size未11
//        List<Filter> list = ExtensionLoader.getExtensionLoader(Filter.class).getActivateExtension(url, "cache");
//        System.out.println(list.size());

        Color color = ExtensionLoader.getExtensionLoader(Color.class).getExtension("red");
        color.sayMy();
        //激活自适应扩展点  可以参考Filter.class 的实现类CacheFilter 
        URL url = new URL("", "", 0);
        url = url.addParameter("blue", "blue");//添加这段代码 size未11
        List<Color> list =  ExtensionLoader.getExtensionLoader(Color.class).getActivateExtension(url, "blue");
        System.out.println(list.size());
        for (Color color1 :list){
            color1.sayHello();
        }

        Color color2 = ExtensionLoader.getExtensionLoader(Color.class).getExtension("blue");
        color2.sayMy();
    }
}


```

## 代码分析：
### 静态扩展点： getExtensionLoader
       Color color = ExtensionLoader.getExtensionLoader(Color.class).getExtension("red");
```java
public class ExtensionLoader{

    private static final ConcurrentMap<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>(64);
    @SuppressWarnings("unchecked")
    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Extension type == null");
        }
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Extension type (" + type + ") is not an interface!");
        }
        if (!withExtensionAnnotation(type)) {
            throw new IllegalArgumentException("Extension type (" + type +
                    ") is not an extension, because it is NOT annotated with @" + SPI.class.getSimpleName() + "!");
        }
        //从加载的map获取 对应的实现类
        ExtensionLoader<T> loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        if (loader == null) {
            EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<T>(type));
            loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        }
        return loader;
    }

    private ExtensionLoader(Class<?> type) {
        this.type = type;
        objectFactory = (type == ExtensionFactory.class ? null : ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension());
    }

    @SuppressWarnings("unchecked")
    public T getAdaptiveExtension() {
        Object instance = cachedAdaptiveInstance.get();
        if (instance == null) {
            if (createAdaptiveInstanceError != null) {
                throw new IllegalStateException("Failed to create adaptive instance: " +
                        createAdaptiveInstanceError.toString(),
                        createAdaptiveInstanceError);
            }

            synchronized (cachedAdaptiveInstance) {
                instance = cachedAdaptiveInstance.get();
                if (instance == null) {
                    try {
                        instance = createAdaptiveExtension();
                        cachedAdaptiveInstance.set(instance);
                    } catch (Throwable t) {
                        createAdaptiveInstanceError = t;
                        throw new IllegalStateException("Failed to create adaptive instance: " + t.toString(), t);
                    }
                }
            }
        }

        return (T) instance;
    }

}
```
### 说明：
    SPI 的 加载机制的最终目标都走自适应扩展点的加载getAdaptiveExtension(),
    然后再loadExtensionClasses()->loadDirectory()->loadResource()->loadClass()