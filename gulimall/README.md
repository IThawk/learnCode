# 项目
## 启动顺序：
### 启动 nacos：docker restart nacos  
### 启动 mysql : docker restart mysql
### 创建数据库：运行db脚本
### 启动 gulimall-monitor
### 网关启动 gulimall-gateway
### 前端启动 gulimall-admin-vue-app 
npm install 
npm run dev
### 登入启动 renren-fast

### 启动 elasticsearch 
    docker restart elasticsearch  
    docker restart kibana  
### 启动 gulimall-search

### 启动rocketMq
    docker restart rmqnamesrv  
    docker restart rmqbroker  
    docker restart rmqconsole  
### 启动redis
    docker restart redis
