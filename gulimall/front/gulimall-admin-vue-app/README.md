# gulimall-admin-vue-app

#### 介绍
谷粒商城后台管理系统

#### 软件架构
软件架构说明


#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)


Module build failed: Error: Node Sass does not yet support your current environment: OS X 64-bit wit


node -v  10.0

node-sass  4.0

 

解决办法其实很简单，就是sass不支持当前的环境，那么在当前环境重新安装一下就好了

先卸载：（如果卸载不成功，直接找到node-sass文件夹，删除）

1. npm uninstall --save node-sass  

再安装

2. npm install --save node-sass    (cnpm install --save node-sass   )

问题就解决了


