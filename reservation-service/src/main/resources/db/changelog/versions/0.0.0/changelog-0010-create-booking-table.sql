--liquibase formatted sql
--changeset cor:reservation-1
CREATE TABLE IF NOT EXISTS booking (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  member_id bigint(20) NOT NULL,
  aircraft_id bigint(20) NOT NULL,
  description VARCHAR(150) NOT NULL,
  departure_time datetime NOT NULL,
  arrival_time datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--rollback DROP TABLE
--rollback booking