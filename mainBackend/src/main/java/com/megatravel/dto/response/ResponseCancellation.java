package com.megatravel.dto.response;

public class ResponseCancellation {
	
	private boolean available;
	
	private int period;

	public ResponseCancellation(boolean available, int period) {
		super();
		this.available = available;
		this.period = period;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

}
