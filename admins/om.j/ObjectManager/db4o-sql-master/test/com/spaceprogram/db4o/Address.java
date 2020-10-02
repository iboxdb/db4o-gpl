package com.spaceprogram.db4o;

/**
 * User: treeder
 * Date: Nov 16, 2006
 * Time: 2:57:04 PM
 */
public class Address {
	private Integer id;
	private String street;
	private City city;

	public Address(Integer id, String street) {
		this.id = id;

		this.street = street;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
}
