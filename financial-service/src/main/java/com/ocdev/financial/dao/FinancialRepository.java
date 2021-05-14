package com.ocdev.financial.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ocdev.financial.entities.Finance;

@Repository
public interface FinancialRepository extends JpaRepository<Finance, Long>
{
	
}
