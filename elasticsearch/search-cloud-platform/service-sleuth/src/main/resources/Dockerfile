#指定基础镜像，必须为第一个命令这里使用openjdk的基础镜像
FROM openjdk:11
#MAINTAINER: 维护者信息，这里是维护者是itheima
MAINTAINER  itheima
#VOLUME：用于指定持久化目录,挂载镜像
VOLUME /itheima
ADD zipkin.jar  app.jar
#RUN：构建镜像时执行的命令，这里是设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
EXPOSE 9411
#ENV：设置环境变量
ENV JAVA_OPTS=""
#egd表示收集守护进程；=后面为快速生成随机数
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
