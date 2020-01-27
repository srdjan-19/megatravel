package com.megatravel.dto.response;

public class ResponseAddress {

	private String street;

	private String city;
	
	private String country;
	
	private int zip;
	
	private double latitude;
	
	private double longitude;

	public ResponseAddress() {
		
	}
	
	public ResponseAddress(String street, String city, String country, int zip, double lat, double lng) {
		super();
		this.street = street;
		this.city = city;
		this.country = country;
		this.zip = zip;
		this.latitude = lat;
		this.longitude = lng;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	
}
