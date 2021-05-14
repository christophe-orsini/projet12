--liquibase formatted sql
--changeset cor:financial-1
CREATE TABLE IF NOT EXISTS financial (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--rollback DROP TABLE
--rollback financial