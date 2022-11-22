package com.levi9.socialnetwork.Service.impl;


import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Address;
import com.levi9.socialnetwork.Repository.AddressRepository;
import com.levi9.socialnetwork.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

	
	@Service
	public class AddressServiceImpl implements AddressService{
		
		
		@Autowired
		private AddressRepository addressRepository;
		
		public java.util.List<Address> getAllAddresses(){
			return this.addressRepository.findAll();
		}
			
		public Address getAddressById(Long addressId)
			throws ResourceNotFoundException{
			return addressRepository.findById(addressId)
					.orElseThrow(() -> new ResourceNotFoundException("Address not found for this id :: " + addressId));
		}
		

		public Address createAddress(Address address) {
			return addressRepository.save(address);
		}
		

		public ResponseEntity<Address> updateAddress(Long addressId,
				 @RequestBody Address addressDetails) throws ResourceNotFoundException {
			Address address = addressRepository.findById(addressId)
					.orElseThrow(() -> new ResourceNotFoundException("Address not found for this id :: " + addressId));
			
			address.setCountry(addressDetails.getCountry());
			address.setCity(addressDetails.getCity());
			address.setStreet(addressDetails.getStreet());
			address.setNumber(addressDetails.getNumber());
		
			final Address updatedAddress = addressRepository.save(address);
			return ResponseEntity.ok(updatedAddress);
		}
		
		public Map<String, Boolean> deleteAddress(Long addressId)
				throws ResourceNotFoundException {
			Address address = addressRepository.findById(addressId)
					.orElseThrow(() -> new ResourceNotFoundException("Address not found for this id :: " + addressId));

			addressRepository.delete(address);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return response;
		}
	}

	

