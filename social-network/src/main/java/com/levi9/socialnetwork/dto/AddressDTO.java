package com.levi9.socialnetwork.dto;

import com.levi9.socialnetwork.Model.Address;

public class AddressDTO {
	private Long id;
	private String country;
	private String city;
	private String street;
	private int number;

	public AddressDTO() {
	}

	public AddressDTO(Long id, String country, String city, String street, int number) {
		this.id = id;
		this.country = country;
		this.city = city;
		this.street = street;
		this.number = number;
	}

	public AddressDTO(Address address) {
		this.id = address.getId();
		this.country = address.getCountry();
		this.city = address.getCity();
		this.street = address.getStreet();
		this.number = address.getNumber();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
