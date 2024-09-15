# distribution OS 项目开发手册

## 开发手册

**若依前后端分离开发手册**       [介绍 | RuoYi](http://doc.ruoyi.vip/ruoyi-vue/)

**若依小程序开发手册**             [介绍 | RuoYi](http://doc.ruoyi.vip/ruoyi-app/)

**springboot 开发文档** 			[Spring Boot 中文文档 (springdoc.cn)](https://springdoc.cn/spring-boot/)

**vue2开发手册**						[简介 | Vue.js (vuejs.org)](https://cn.vuejs.org/guide/introduction.html)

**redis开发文档**						[Documentation | Redis](https://redis.io/docs/)

**Redis中文开发文档**				[Redis 教程_redis教程](https://www.redis.net.cn/tutorial/3501.html)

**swaager接口开发文档**			[API Documentation & Design Tools for Teams | Swagger](https://swagger.io/)

​	



## 项目简介

### 项目名称

distribution OS

### 项目描述

分销式维修系统

### 项目目标

分销式维修系统

实现微信小程序分销式维修系统

### 主要功能

分销 维修 积分 管理 

### 技术栈

- 框架：若依 uniapp Element-UI  Uview
- 后端：Java1.8、Spring Boot2、MySQL、Redis、Kafka等
- 前端：Vue
- 工具：Git、Maven、Docker、Swagger 等
- 数据库：MySQL等
- 部署：NGINX ，Apache，

## 开发环境

### 开发工具

- 操作系统：Windows、Linux等
- 编程语言：Java1.8、JavaScript 等
- 开发工具：HBuildx、 IDEA、VSCode、Git、微信小程序等

### 数据库环境

- 数据库：MySQL、PostgreSQL、MongoDB等
- 数据库工具：Navicat、MySQL Workbench、PostgreSQL Navigator、MongoDB Compass 等

### 服务器和容器化技术

- 服务器：Apache Tomcat、Nginx、Docker、Kubernetes等
- 容器化技术：Docker、Kubernetes、Jenkins 等

### 运行前的配置

- 1、将其他文档格式转化为pdf格式需要按如下配置
- 1.1、首先在你的本地电脑或者服务器的Linux环境下安装openoffice。
- windows环境下参照：https://blog.51cto.com/gblfy/5654134 教程
- linux环境下参照：https://blog.csdn.net/github_38924695/article/details/89470960 教程
- 1.2、启动openoffice服务，在参考教程里有启动命令。
- 1.3、修改application.yml配置文件中jodconverter配置下的“office-home”路径，修改为安装的openoffice路径。
- 1.4、修改application.yml配置文件中ruoyi配置下的profile属性值。如果是window则不用修改，如果是Linux环境下运行则修改为“/home/ruoyi/uploadPath”。
- 2、如果要预览文档需要修改application.yml文件里的httpUrl属性，改为电脑本机的ip地址。


## 项目结构

```
.git  			—这是一个隐藏的文件夹，用于存储Git版本控制系统的元数据。它跟踪项目的所有更改，并允许您在需要时回滚到之前的版本。
.github                        —这个文件夹包含了与GitHub相关的文件，例如问题和拉取请求模板。
.idea                           —这个文件夹包含了与IntelliJ IDEA（一个流行的Java集成开发环境）相关的配置文件。
bin                              —这个文件夹包含了项目的可执行脚本，例如`ry.bat`（Windows批处理文件）和`ry.sh`（Unix shell脚本）。
doc                             —这个文件夹包含了项目的文档，例如API文档、用户手册等。
ruoyi-admin                —项目自带默认控制层管理模块。
RuoYi-App                 —项目小程序前端。
ruoyi-common           —项目通用组件模块。
ruoyi-framework        —项目框架模块。
ruoyi-generator         —项目代码生成器模块或组件。
ruoyi-quartz              —项目定时模块。
ruoyi-system             — 项目系统模块。
ruoyi-ui                     —项目前端UI。
sql                            —项目数据库。
.gitignore                  —这个文件包含了一个列表，列出了Git应该忽略的文件和文件夹。这些文件和文件夹不会被版本控制系统跟踪。
LICENSE                 —这个文件包含了项目的许可证信息，说明了项目的使用条款和限制。
pom.xml                   —这个文件是一个Maven项目的配置文件，用于定义项目的构建和依赖关系。
README.md           —这个文件包含了项目的自述信息，通常包括项目的简介、安装和使用说明等。
ruoyi.iml                   —这是一个与IntelliJ IDEA相关的文件，用于存储项目的模块配置信息。
ry.bat                        —这是一个Windows批处理文件，用于运行项目的脚本或命令。
ry.sh                         —这是一个Unix shell脚本，用于运行项目的脚本或命令。
```

