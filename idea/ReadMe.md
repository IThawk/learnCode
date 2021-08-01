# idea使用技巧
## 1：添加方法模板注释：

添加步骤

![](.\images\2021-08-01_143038.jpg)

![](.\images\2021-08-01_143154.jpg)

![](.\images\2021-08-01_143251.jpg)

```
**
 * @description: $description$ 
 $params$ 
 * @return: $returns$ 
 * @author $USER$
 * @date: $date$ $time$
 */ 
```
添加参数使用：
```
groovyScript("def result=''; def params=\"${_1}\".replaceAll('[\\\\[|\\\\]|\\\\s]', '').split(',').toList(); for(i = 0; i < params.size(); i++) {result+=' * @param ' + params[i] + ((i < params.size() - 1) ? '\\n' : '')}; return result", methodParameters())

```
![](.\images\2021-08-01_143343.jpg)

生效之后：

```
/*+回车键 就可以使用方法注解
```
## 2：类注解
![](.\images\2021-08-01_143405.jpg)

![](.\images\2021-08-01_143440.jpg)

生效之后：

```
/**+回车键 就可以使用方法注解
```

## 3：多实例启动

![](.\images\2021-08-01_144149.jpg)

## 4：解决command long问题
![](.\images\2021-08-01_144523.jpg)

