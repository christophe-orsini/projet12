--liquibase formatted sql
--changeset cor:hangar-2
INSERT INTO aircraft (id, registration, make, model, hourly_rate, total_time, next_maintenance_schedule, empty_weight, max_weight, max_pax, max_fuel,
	fuel_type, available) VALUES
(1, 'F-GCNS', 'CESSNA', 'C152', 129, 4575.1, 4670.5, 462, 980, 2, 128, '100LL', 1 );