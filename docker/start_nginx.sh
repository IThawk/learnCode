mkdir -p /mydata/nginx/www /mydata/nginx/logs /mydata/nginx/conf
chmod -R 777 /mydata/nginx/
vi /mydata/nginx/conf/nginx.conf
#  kencery 注释说明Nginx文件
#  时间：2016-1-19
#  学习内容，只是来自互联网，有版权问题请联系我删除。

########   Nginx的main(全局配置)文件
#指定nginx运行的用户及用户组,默认为nobody
#user  nobody;

#开启的线程数，一般跟逻辑CPU核数一致
worker_processes  1;

#定位全局错误日志文件，级别以notice显示，还有debug,info,warn,error,crit模式，debug输出最多，crir输出最少，根据实际环境而定
#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#指定进程id的存储文件位置
#pid        logs/nginx.pid;

#指定一个nginx进程打开的最多文件描述符数目，受系统进程的最大打开文件数量限制
#worker_rlimit_nofile 65535

events {
    #设置工作模式为epoll,除此之外还有select,poll,kqueue,rtsig和/dev/poll模式
    #use epoll;

    #定义每个进程的最大连接数,受系统进程的最大打开文件数量限制。
    worker_connections  1024;
}

#######Nginx的Http服务器配置,Gzip配置
http {
    #主模块指令，实现对配置文件所包含的文件的设定，可以减少主配置文件的复杂度，DNS主配置文件中的zonerfc1912,acl基本上都是用include语句。
    include       mime.types;

    #核心模块指令，智力默认设置为二进制流，也就是当文件类型未定义时使用这种方式
    default_type  application/octet-stream;

    #下面代码为日志格式的设定，main为日志格式的名称，可自行设置，后面引用
    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #引用日志main
    #access_log  logs/access.log  main;

    #设置允许客户端请求的最大的单个文件字节数
    #client_max_body_size 20M;
    #指定来自客户端请求头的headebuffer大小
    #client_header_buffer_size  32k;
    #指定连接请求试图写入缓存文件的目录路径
    #client_body_temp_path /dev/shm/client_body_temp;
    #指定客户端请求中较大的消息头的缓存最大数量和大小，目前设置为4个32KB
    #large client_header_buffers 4 32k;

    #开启高效文件传输模式
    sendfile        on;
    #开启防止网络阻塞
    #tcp_nopush     on;
    #开启防止网络阻塞
    #tcp_nodelay    on;

    #设置客户端连接保存活动的超时时间
    #keepalive_timeout  0;
    keepalive_timeout  65;

    #设置客户端请求读取超时时间
    #client_header_timeout 10;
    #设置客户端请求主体读取超时时间
    #client_body_timeout 10;
    #用于设置相应客户端的超时时间
    #send_timeout

    ####HttpGZip模块配置
    #httpGzip modules
    #开启gzip压缩
    #gzip  on;
    #设置允许压缩的页面最小字节数
    #gzip_min_length 1k;
    #申请4个单位为16K的内存作为压缩结果流缓存
    #gzip_buffers 4 16k;
    #设置识别http协议的版本，默认为1.1
    #gzip_http_version 1.1;
    #指定gzip压缩比，1-9数字越小，压缩比越小，速度越快
    #gzip_comp_level 2;
    #指定压缩的类型
    #gzip_types text/plain application/x-javascript text/css application/xml;
    #让前端的缓存服务器进过gzip压缩的页面
    #gzip_vary on;

    #########Nginx的server虚拟主机配置
    server {
        #监听端口为 80
        listen       80;

        #设置主机域名
        server_name  localhost;

        #设置访问的语言编码
        #charset koi8-r;

        #设置虚拟主机访问日志的存放路径及日志的格式为main
        #access_log  logs/host.access.log  main;

        #设置虚拟主机的基本信息
        location / {
            #设置虚拟主机的网站根目录
            root   html;

            #设置虚拟主机默认访问的网页
            index  index.html index.htm;
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }


    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}

}
docker run -d -p 80:80 --name nginx --privileged=true -v /mydata/nginx/www:/usr/share/nginx/html -v /mydata/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /mydata/nginx/logs:/var/log/nginx nginx:1.10
