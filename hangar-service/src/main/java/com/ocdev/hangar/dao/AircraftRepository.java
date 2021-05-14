package com.ocdev.hangar.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ocdev.hangar.entities.Aircraft;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long>
{
	public Optional<Aircraft> findByRegistration(String registration);
}
