## 1：安装gradle
* https://gradle.org/next-steps/?version=8.1.1&format=bin 下载安装包
* 解压安装
![ideaGradle](.\img\ideaGradle.png)
* 添加配置 init.gradle到 init.d文件夹下，使用阿里仓库下载jar包
```
allprojects{
    repositories {
        def ALIYUN_REPOSITORY_URL = 'https://maven.aliyun.com/repository/central/'
        def ALIYUN_JCENTER_URL = 'https://maven.aliyun.com/repository/public/'
        all { ArtifactRepository repo ->
            if(repo instanceof MavenArtifactRepository){
                def url = repo.url.toString()
                if (url.startsWith('https://repo1.maven.org/maven2') || url.startsWith('http://repo1.maven.org/maven2')) {
                    project.logger.lifecycle "Repository ${repo.url} replaced by $ALIYUN_REPOSITORY_URL."
                    remove repo
                }
                if (url.startsWith('https://jcenter.bintray.com/') || url.startsWith('http://jcenter.bintray.com/')) {
                    project.logger.lifecycle "Repository ${repo.url} replaced by $ALIYUN_JCENTER_URL."
                    remove repo
                }
            }
        }
        maven {
            url ALIYUN_REPOSITORY_URL
            url ALIYUN_JCENTER_URL
			url 'https://maven.aliyun.com/repository/google/'
			url 'https://maven.aliyun.com/repository/gradle-plugin/'
        }
    }
 
 
    buildscript{
        repositories {
            def ALIYUN_REPOSITORY_URL = 'https://maven.aliyun.com/repository/central/'
            def ALIYUN_JCENTER_URL = 'https://maven.aliyun.com/repository/public/'
            all { ArtifactRepository repo ->
                if(repo instanceof MavenArtifactRepository){
                    def url = repo.url.toString()
                    if (url.startsWith('https://repo1.maven.org/maven2') || url.startsWith('http://repo1.maven.org/maven2')) {
                        project.logger.lifecycle "Repository ${repo.url} replaced by $ALIYUN_REPOSITORY_URL."
                        remove repo
                    }
                    if (url.startsWith('https://jcenter.bintray.com/') || url.startsWith('http://jcenter.bintray.com/')) {
                        project.logger.lifecycle "Repository ${repo.url} replaced by $ALIYUN_JCENTER_URL."
                        remove repo
                    }
                }
            }
            maven {
                url ALIYUN_REPOSITORY_URL
                url ALIYUN_JCENTER_URL
				url 'https://maven.aliyun.com/repository/google/'
				url 'https://maven.aliyun.com/repository/gradle-plugin/'
            }
        }
    }
}
```

## 2: 资源接口 
  org.springframework.core.io.Resource
## 3：资源加载器
  org.springframework.core.io.ResourceLoader
## 4：创建bean
  org.springframework.beans.factory.BeanFactory
  org.springframework.beans.factory.support.DefaultListableBeanFactory
  org.springframework.context.support.GenericApplicationContext
  org.springframework.context.annotation.AnnotationConfigApplicationContext


注册bean的定义信息
org.springframework.beans.factory.support.DefaultListableBeanFactory.registerBeanDefinition
实现了 org.springframework.beans.factory.support.BeanDefinitionRegistry.registerBeanDefinition



org.springframework.context.support.AbstractApplicationContext.refresh
org.springframework.context.support.AbstractRefreshableApplicationContext.refreshBeanFactory
org.springframework.context.support.AbstractXmlApplicationContext.loadBeanDefinitions(org.springframework.beans.factory.support.DefaultListableBeanFactory)

##  注意：

* 1：spring 6.0 版本的war tomcat需要10版本及以上