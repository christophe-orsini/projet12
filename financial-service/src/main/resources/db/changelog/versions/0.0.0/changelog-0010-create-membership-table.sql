--liquibase formatted sql
--changeset cor:financial-1
CREATE TABLE IF NOT EXISTS membership (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  member_id bigint(20) NOT NULL,
  payment_date datetime NOT NULL,
  amount decimal(10,2) NOT NULL,
  validity_date datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--rollback DROP TABLE
--rollback membership