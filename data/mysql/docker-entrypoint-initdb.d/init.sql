CREATE DATABASE `test` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
SET character_set_client = utf8mb4;
SET character_set_results = utf8mb4;
SET character_set_connection = utf8mb4;
USE `test`;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS car;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE car (
  id VARCHAR(100) primary key,
  name VARCHAR(100) not null,
  type VARCHAR(64) not null
);

INSERT INTO car (id, name, type) VALUES (100, 'prius', 'hybrid');
