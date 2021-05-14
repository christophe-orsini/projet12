--liquibase formatted sql
--changeset cor:reservation-1
CREATE TABLE IF NOT EXISTS reservation (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--rollback DROP TABLE
--rollback reservation