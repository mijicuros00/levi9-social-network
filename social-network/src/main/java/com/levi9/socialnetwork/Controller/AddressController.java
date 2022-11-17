package com.levi9.socialnetwork.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Address;
import com.levi9.socialnetwork.Service.AddressService;
import com.levi9.socialnetwork.Service.UserService;


@RestController
@RequestMapping("/api/addresses")
public class AddressController {
	

	@Autowired
	private AddressService addressService;

	@GetMapping
	public java.util.List<Address> getAllAddresses() {
		return this.addressService.getAllAddresses();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Address> getAddressById(@PathVariable(value = "id") Long addressId)
			throws ResourceNotFoundException {
		return addressService.getAddressById(addressId);
	}

	@PostMapping
	public Address createAddress(@RequestBody Address address) {
		return addressService.createAddress(address);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Address> updateAddress(@PathVariable(value = "id") Long addressId,
			@RequestBody Address addressDetails) throws ResourceNotFoundException {
		return addressService.updateAddress(addressId, addressDetails);
	}

	@DeleteMapping("/{id}")
	public Map<String, Boolean> deleteAddress(@PathVariable(value = "id") Long addressId)
			throws ResourceNotFoundException {
		return addressService.deleteAddress(addressId);
	}
	
	
}
