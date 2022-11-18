package com.levi9.socialnetwork.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="address", schema="public")
@NoArgsConstructor
@Getter
@Setter
public class Address {

	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "number")
	private int number;

	
	public Address(Long id, String country, String city, String street, int number) {
		super();
		this.id = id;
		this.country = country;
		this.city = city;
		this.street = street;
		this.number = number;
	}


	
	
	
	
	
}
