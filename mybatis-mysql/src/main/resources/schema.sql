drop table if exists city;

CREATE TABLE `city`
(
    `id`      int NOT NULL,
    `name`    varchar(255) NULL,
    `state`   varchar(255) NULL,
    `country` varchar(255) NULL,
    PRIMARY KEY (`id`)
);