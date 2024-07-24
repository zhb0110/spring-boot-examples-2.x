# 未执行到该文件

drop table if exists city3;

CREATE TABLE `city3`
(
    `id`      int          NOT NULL,
    `name`    varchar(255) NULL,
    `state`   varchar(255) NULL,
    `country` varchar(255) NULL,
    PRIMARY KEY (`id`)
);