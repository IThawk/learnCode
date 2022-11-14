docker run --name rmqconsole -d -p 8080:8080 --link rmqnamesrv:namesrv -e "JAVA_OPTS=-Drocketmq.namesrv.addr=namesrv:9876"  pangliang/rocketmq-console-ng



docker run --name rmqconsole -d -p 18080:8080 --link rmqnamesrv:namesrv -e "JAVA_OPTS=-Drocketmq.namesrv.addr=namesrv:9876"  pangliang/rocketmq-console-ng