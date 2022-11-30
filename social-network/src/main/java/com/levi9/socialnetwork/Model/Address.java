package com.levi9.socialnetwork.Model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.levi9.socialnetwork.dto.AddressDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address", schema = "public")
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Address(AddressDTO addressDTO) {
        this.id = addressDTO.getId();
        this.country = addressDTO.getCountry();
        this.city = addressDTO.getCity();
        this.street = addressDTO.getStreet();
        this.number = addressDTO.getNumber();
    }
}
