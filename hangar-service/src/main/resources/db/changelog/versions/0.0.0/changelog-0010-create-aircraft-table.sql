--liquibase formatted sql
--changeset cor:hangar-1
CREATE TABLE IF NOT EXISTS aircraft (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  registration VARCHAR(10) NOT NULL,
  make VARCHAR(50) NOT NULL,
  model VARCHAR(50) NOT NULL,
  hourly_rate int(4) DEFAULT NULL,
  total_time decimal(10,1) DEFAULT NULL,
  next_Maintenance_schedule decimal(10,1) DEFAULT NULL,
  empty_weight int(6) DEFAULT NULL,
  max_weight int(6) DEFAULT NULL,
  max_pax int(3) DEFAULT NULL,
  max_fuel int(6) DEFAULT NULL,
  fuel_type VARCHAR(50),
  available tinyint(1) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--rollback DROP TABLE
--rollback aircraft