package com.levi9.socialnetwork.Service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Address;

public interface AddressService {

	public java.util.List<Address> getAllAddresses();
	
	public ResponseEntity<Address> getAddressById(Long addressId) throws ResourceNotFoundException;
	
	public Address createAddress(Address address);
	
	public ResponseEntity<Address> updateAddress(Long addressId,@RequestBody Address addressDetails) throws ResourceNotFoundException;
	
	public Map<String, Boolean> deleteAddress(Long addressId) throws ResourceNotFoundException;
	
}
