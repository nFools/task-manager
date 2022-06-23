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
