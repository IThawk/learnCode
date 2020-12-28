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

docker cp /opt/test.js testtomcat：/usr/local/tomcat/webapps/test/js