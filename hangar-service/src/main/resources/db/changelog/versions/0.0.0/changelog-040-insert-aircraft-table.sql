--liquibase formatted sql
--changeset cor:hangar-4
INSERT INTO aircraft (id, registration, make, model, hourly_rate, total_time, next_maintenance_schedule, empty_weight, max_weight, max_pax, max_fuel,
	fuel_type, available) VALUES
(2, 'F-GHNY', 'CESSNA', 'C152', 129, 3927.5, 3990.9, 453, 980, 2, 128, '100LL', 0 );