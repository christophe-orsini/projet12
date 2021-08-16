package com.ocdev.airclub.services;

import java.util.List;

import com.ocdev.airclub.dto.Flight;

public interface FinancialService
{
	public List<Flight> getInvoices(String memberId, boolean paid);
	public double totalTime(List<Flight> flights);
	public double totalAmount(List<Flight> flights, boolean paid);
}
