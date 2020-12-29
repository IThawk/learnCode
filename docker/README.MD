docker从容器里面拷文件到宿主机或从宿主机拷文件到docker容器里面
1、从容器里面拷文件到宿主机？

     答：在宿主机里面执行以下命令

             docker cp 容器名：要拷贝的文件在容器里面的路径       要拷贝到宿主机的相应路径 

     示例： 假设容器名为testtomcat,要从容器里面拷贝的文件路为：/usr/local/tomcat/webapps/test/js/test.js,  现在要将test.js从容器里面拷到宿主机的/opt路径下面，那么命令应该怎么写呢？

     答案：在宿主机上面执行命令

docker cp testtomcat：/usr/local/tomcat/webapps/test/js/test.js /opt
2、从宿主机拷文件到容器里面

      答：在宿主机里面执行如下命令

              docker cp 要拷贝的文件路径 容器名：要拷贝到容器里面对应的路径

       示例：假设容器名为testtomcat,现在要将宿主机/opt/test.js文件拷贝到容器里面的/usr/local/tomcat/webapps/test/js路径下面，那么命令该怎么写呢？

       答案：在宿主机上面执行如下命令     

docker cp /mydata/test elasticsearch:/usr/share/



docker内部文件没有权限的解决方法

ccagy 2019-01-05 16:51:53  11296  收藏 1
分类专栏： docker K8S 文章标签： docker
版权
第一个冒号之后一串之后 在-it的前面加上--privileged=true

 

docker run --rm -v /data/kube1.13.1.tar.gz:/data/kube1.13.1.tar.gz --privileged=true -it -w /etc/ansible fanux/sealos:v1.13.0 bash


网络配置：
[root@localhost config]# cat /etc/sysconfig/network-scripts/ifcfg-enp0s3
TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
BOOTPROTO=static
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stable-privacy
NAME=enp0s3
UUID=2ed1642b-bdc0-439e-909b-92063b67ae70
DEVICE=enp0s3
ONBOOT=yes
IPADDR=192.168.56.101
#NETMASK=255.255.255.0
#GATEWAY=192.168.56.1
#DNS1=114.114.114.114
#DNS2=8.8.8.8

[root@localhost config]# cat /etc/sysconfig/network-scripts/ifcfg-enp0s8
TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
BOOTPROTO=dhcp
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stable-privacy
NAME=enp0s3
UUID=2ed1642j-bdc0-439e-909b-92063b67ae70
DEVICE=enp0s8
ONBOOT=yes
