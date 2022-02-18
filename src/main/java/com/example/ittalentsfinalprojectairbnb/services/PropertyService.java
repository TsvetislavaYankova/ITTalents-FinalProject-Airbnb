package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.model.dto.PropertyWithAddressDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.PropertyIdDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Address;
import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
import com.example.ittalentsfinalprojectairbnb.model.repositories.AddressRepository;
import com.example.ittalentsfinalprojectairbnb.model.repositories.PropertyRepository;
import com.example.ittalentsfinalprojectairbnb.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class PropertyService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

//    public AddressIdDTO addAddress(AddressDTO addressDTO) {
//        Address address = new Address();
//        address.setCity(addressDTO.getCity());
//        address.setCountry(addressDTO.getCountry());
//        address.setStreet(addressDTO.getStreet());
//        address.setZipCode(addressDTO.getZipCode());
//        address.setApartmentNumber(addressDTO.getApartmentNumber());
//
//        addressRepository.save(address);
//
//
//        AddressIdDTO dto = mapper.map(address, AddressIdDTO.class);
//        return dto;
//    }

    public PropertyIdDTO addProperty(PropertyWithAddressDTO propertyDTO, Integer id) {
        //TODO validations
        Property property = new Property();
        Address address = new Address();
        address.setCity(propertyDTO.getCity());
        address.setCountry(propertyDTO.getCountry());
        address.setStreet(propertyDTO.getStreet());
        address.setZipCode(propertyDTO.getZipCode());
        address.setApartmentNumber(propertyDTO.getApartmentNumber());
        addressRepository.save(address);
        property.setHost(userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Host not found")));
        property.setAddress(address);
        property.setPropertyType(propertyDTO.getPropertyType());
        property.setDescription(propertyDTO.getDescription());
        property.setBathrooms(propertyDTO.getBathrooms());
        property.setBedrooms(propertyDTO.getBedrooms());
        property.setPricePerNight(propertyDTO.getPricePerNight());
        property.setGuests(propertyDTO.getGuests());
        propertyRepository.save(property);
        PropertyIdDTO dto = mapper.map(property, PropertyIdDTO.class);
        return dto;
    }
}
