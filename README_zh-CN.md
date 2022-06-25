# Task Manager

[English](./README.md) | 简体中文

## *为什么要使用它？*

- 配置简单
- ...

## [示例](http://demo.imcaoxuan.cn/task-manager/) 

## 开始

### 要求

- 64bit OS: 支持 Linux/Unix/Mac/Windows, 推荐 Linux/Unix/Mac.

- Java 8(推荐) or later
- [Redis](https://redis.io/)
- [MySQL8](https://www.mysql.com/)

### 获取 Task Manager

#### 使用源码构建

##### 要求

- Maven 3.6.x+
- [JDK 8u201](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)

##### 构建

etc.

#### 下载

你可以从这里下载 [最新版本](https://github.com/nFools/task-manager/releases).



### 配置

默认配置文件 `application.yml`

```yaml
server:
  port: 10000
  servlet:
    # DO NOT MODIFY IT !!!
    context-path: /task-manager
    ui-path: /ui
    detail-path: /detail
spring:
  mvc:
    static-path-pattern: /static/**
  application:
    name: task-manager
  servlet:
    multipart:
      max-file-size: 4096MB
      max-request-size: 4096MB
  redis:
    host: 127.0.0.1
    port: 6379
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/task_manager?characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true
    username: task_manager
    password: task_manager@imcaoxuan.1999
mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: org.nfools.taskmanager.entity.data
  configuration:
    map-underscore-to-camel-case: true
logging:
  level:
    root: info
    org:
      nfools:
        taskmanager: debug
  file:
    path: log_task_manager/
task-manager:
  timeout: 5
  api-prefix: /api
  data-path: ${user.dir}/data/task_manager/
  data-resource: /data/task_manager/
  third-party:
    view-detail-url: http://localhost:${server.port}${server.servlet.context-path}${server.servlet.ui-path}${server.servlet.detail-path}
    analysis-url: http://localhost:${server.port}${server.servlet.context-path}${task-manager.api-prefix}/analyse

```

- `view-detail-url`:  to show your analysis results.
- `analysis-url`: to start analysis.
- `timeout`: unit: minute.

### 部署

#### 本地服务器

##### 第一步: 下载二进制包

[下载地址](https://github.com/nFools/task-manager/releases).

##### 第二步: 安装并运行 `Redis` 和 `MySQL`

etc.

##### 第三步: 创建数据库

```mysql
DROP DATABASE IF EXISTS task_manager;
CREATE DATABASE task_manager;
USE task_manager;
-- DROP USER 'task_manager'@'localhost';
CREATE USER 'task_manager'@'localhost' IDENTIFIED BY 'task_manager@imcaoxuan.1999';
GRANT ALL ON `task_manager`.* TO 'task_manager'@'localhost';

CREATE TABLE `task`
(
    `id`           bigint unsigned NOT NULL AUTO_INCREMENT,
    `create_time`  datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_time` datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `task_name`    varchar(255)    NOT NULL,
    `ok`           tinyint         NOT NULL,
    `remarks`      varchar(255)    NOT NULL DEFAULT '',
    `path`         varchar(255)    NOT NULL,
    `view_url`     varchar(255)    NOT NULL DEFAULT '',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

```

##### 第四步: 启动服务

在 **Linux/Unix/Mac** 平台, 运行以下命令来启动服务:

```shell
java -jar task-manager-0.0.1-SNAPSHOT.jar
```

如果你想让服务在后台运行:

```shell
nohup java -jar task-manager-0.0.1-SNAPSHOT.jar & echo $! > pid &
```

#### Docker

敬请期待...

#### Cloud

敬请期待...

## 使用方式

##### Create a task:

1. 输入 **任务名称**.
2. 上传需要分析的 **文件**.
3. 输入 **备注**.(可选)
4. 点击 **开始**


![image-20220625152749801](https://user-images.githubusercontent.com/53902232/175763971-e64d110d-55d9-43ed-adff-cfce63a4c358.png)





## 提示

- `默认日志位置`: `./log_task_manager/`
- `默认数据位置`: `./data/task_manager/${year}/${month}/${day}/${task.id}`
- `默认 MySQL 密码`: `task_manager@imcaoxuan.1999`

  

## 联系我

#### 电子邮箱: cx@imcaoxuan.com; cx@imcaoxuan.cn
