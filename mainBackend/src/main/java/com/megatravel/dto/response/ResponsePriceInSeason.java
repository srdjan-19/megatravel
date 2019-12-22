package com.megatravel.dto.response;

import com.megatravel.model.Currencies;
import com.megatravel.model.Months;

public class ResponsePriceInSeason {
	
	private Months month;
	
	private double price;
	
	private Currencies currency;

	public ResponsePriceInSeason(Months month, double price, Currencies currency) {
		super();
		this.month = month;
		this.price = price;
		this.currency = currency;
	}

	public Months getMonth() {
		return month;
	}

	public void setMonth(Months month) {
		this.month = month;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Currencies getCurrency() {
		return currency;
	}

	public void setCurrency(Currencies currency) {
		this.currency = currency;
	}
	
	
	
}