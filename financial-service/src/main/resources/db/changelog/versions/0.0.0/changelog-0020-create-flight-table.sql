--liquibase formatted sql
--changeset cor:financial-2
CREATE TABLE IF NOT EXISTS flight (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  member_id bigint(20) NOT NULL,
  aircraft varchar(100) NOT NULL,
  line_item varchar(100) NOT NULL,
  flight_date datetime NOT NULL,
  flight_hours decimal(10,1) NOT NULL,
  amount decimal(10,2) NOT NULL,
  payment_date datetime DEFAULT NULL,
  payment decimal(10,2) DEFAULT 0,
  closed tinyint(1) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--rollback DROP TABLE
--rollback flight