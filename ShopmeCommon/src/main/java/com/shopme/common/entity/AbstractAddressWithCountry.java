package com.shopme.common.entity1;

import com.shopme.common.entity.AbstractAddress;
import com.shopme.common.entity.Country;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractAddressWithCountry extends AbstractAddress {
	@ManyToOne
	@JoinColumn(name = "country_id")
	protected com.shopme.common.entity.Country country;
	
	public com.shopme.common.entity.Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	@Override
	public String toString() {
		String address = firstName;
		
		if (lastName != null && !lastName.isEmpty()) address += " " + lastName;
		
		if (!addressLine1.isEmpty()) address += ", " + addressLine1;
		
		if (addressLine2 != null && !addressLine2.isEmpty()) address += ", " + addressLine2;
		
		if (!city.isEmpty()) address += ", " + city;
		
		if (state != null && !state.isEmpty()) address += ", " + state;
		
		address += ", " + country.getName();
		
		if (!postalCode.isEmpty()) address += ". Postal Code: " + postalCode;
		if (!phoneNumber.isEmpty()) address += ". Phone Number: " + phoneNumber;
		
		return address;
	}	
}