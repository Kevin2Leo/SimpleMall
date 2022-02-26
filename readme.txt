## Day1 项目启动

### 项目概述

电商。

http://115.29.141.32:8085/#/mall/show/index

前台用户系统。针对是客户、用户。你经常见到的商城其实都是前台的系统。



http://115.29.141.32:8085/admin.html#/login

后台管理系统。针对的是该商城的工作人员。主要是用来管理当前商品里面的信息。比如商品、订单、用户等信息。

1.在package.json文件所在的目录执行指令cnpm install（如果cnpm没有，那么执行npm同样可以，访问速度很慢的话，大家可以自行去百度搜索一个 npm 修改源  **npm config set registry https://registry.npm.taobao.org**）

2.npm run dev(指令内部封装了静态资源服务器，开启了一个静态资源服务器来部署前端的页面)

默认打开的是Localhost:8085对应的就是公网服务器上面的8085
