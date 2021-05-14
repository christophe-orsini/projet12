--liquibase formatted sql
--changeset cor:hangar-3
UPDATE aircraft SET empty_weight = 538, max_weight = 757, max_fuel = 93
	WHERE id = 1;