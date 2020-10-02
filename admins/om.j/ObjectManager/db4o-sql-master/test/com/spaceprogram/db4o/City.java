package com.spaceprogram.db4o;

/**
 * User: treeder
 * Date: Nov 16, 2006
 * Time: 2:57:37 PM
 */
public class City {
	private Integer id;
	private String name;

	public City(Integer id, String name) {
		this.id = id;

		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getId() {
		return id;
	}
}
