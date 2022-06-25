# Task Manager

English | [简体中文](./README_zh-CN.md)

## [Demo](http://demo.imcaoxuan.cn/task-manager/) 

## Getting Started

### Requirements

- 64bit OS: Linux/Unix/Mac/Windows supported, Linux/Unix/Mac recommended.

- Java 8(Recommended) or later
- [Redis](https://redis.io/)
- [MySQL8](https://www.mysql.com/)

### Get Task Manager

#### Build from source

##### Requirements

- Maven 3.6.x+
- [JDK 8u201](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)

##### Build

etc.

#### Download from release

You can download the package from the [latest stable release](https://github.com/nFools/task-manager/releases).



### Configuration

Default configuration `application.yml`

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

### Deployment

#### Local Machine

##### Step 1: Download the binary package

You can download the package from the [latest stable release](https://github.com/nFools/task-manager/releases).

##### Step 2: Install and start `Redis` and `MySQL`

etc.

##### Step 3: Create the database:

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

##### Step 4: Start Server

On the **Linux/Unix/Mac** platform, run the following command to start server:

```shell
java -jar task-manager-0.0.1-SNAPSHOT.jar
```

If you want to start server in background:

```shell
nohup java -jar task-manager-0.0.1-SNAPSHOT.jar & echo $! > pid &
```

#### Docker

Coming soon...

#### Cloud

Coming soon...

## Usage

##### Create a task:

1. Input a **task name**.
2. Upload data **files**.
3. Set **remarks**.(Optional)
4. Click **Start**


![image-20220625152749801](https://user-images.githubusercontent.com/53902232/175763971-e64d110d-55d9-43ed-adff-cfce63a4c358.png)





## Tips

- `Default log location`: `./log_task_manager/`
- `Default data location`: `./data/task_manager/${year}/${month}/${day}/${task.id}`

- `Default MySQL password`: `task_manager@imcaoxuan.1999`

  

## Contacting

#### Email: cx@imcaoxuan.com; cx@imcaoxuan.cn
