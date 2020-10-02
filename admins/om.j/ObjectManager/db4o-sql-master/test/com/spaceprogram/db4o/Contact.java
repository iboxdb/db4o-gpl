package com.spaceprogram.db4o;

import java.util.Date;


/**
 * User: Travis Reeder
 * Date: May 18, 2006
 * Time: 5:48:04 PM
 */
public class Contact extends ParentClass1 {
    private Integer id;
    private String name;
    private String email;
    private int age;
    private String category;
    private double income;
	private Date birthDate;

	private Long longField;
	private Double doubleField;

	private Address address;

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Contact [" + id + "]: name=" + name + " age=" + age + " category=" + category + " pc1:" + pc1 + " pc2:" + pc2;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Long getLongField() {
		return longField;
	}

	public void setLongField(Long longField) {
		this.longField = longField;
	}

	public Double getDoubleField() {
		return doubleField;
	}

	public void setDoubleField(Double doubleField) {
		this.doubleField = doubleField;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
