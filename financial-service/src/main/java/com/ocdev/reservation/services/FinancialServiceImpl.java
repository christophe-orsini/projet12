package com.ocdev.reservation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocdev.financial.dao.FinancialRepository;

@Service
public class FinancialServiceImpl implements FinancialService
{
	private FinancialRepository _financialRepository;
	
	public FinancialServiceImpl(@Autowired FinancialRepository repository)
	{
		_financialRepository = repository;
	}
}
