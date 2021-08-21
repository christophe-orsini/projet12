package com.ocdev.airclub.services;

import java.util.List;

import com.ocdev.airclub.dto.Flight;
import com.ocdev.airclub.dto.InvoicePayDto;

public interface FinancialService
{
	public List<Flight> getInvoices(String memberId, boolean paid);
	public Flight getInvoice(long id);
	public double totalTime(List<Flight> flights);
	public double totalAmount(List<Flight> flights, boolean paid);
	public Flight payInvoice(long invoiceId, InvoicePayDto invoicePayDto);
}
