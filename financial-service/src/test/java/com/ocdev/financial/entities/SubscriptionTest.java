package com.ocdev.financial.entities;

import static org.assertj.core.api.Assertions.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

public class SubscriptionTest
{
	@Test
	void toString_returnsString()
	{
		// arrange
		Calendar date = new GregorianCalendar(2021,04,20);
		Subscription entityUnderTest = new Subscription();
		entityUnderTest.setMemberId(5);
		entityUnderTest.setPaymentDate(date.getTime());
		entityUnderTest.setAmount(150.0);
		entityUnderTest.setValidityDate(date.getTime());
		
		String expected = "Subscription [memberId=5, paymentDate=2021-05-20, amount=150.0, validityDate=2021-05-20]"; 
				
		// act
		String actual = entityUnderTest.toString();
		
		// assert
		assertThat(actual).isEqualTo(expected);
	}
}
