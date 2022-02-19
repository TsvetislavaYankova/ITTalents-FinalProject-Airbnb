package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.controller.UserController;
import com.example.ittalentsfinalprojectairbnb.exceptions.BadRequestException;
import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.model.dto.EditAddressDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.FilterPropertyDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.PropertyCreationDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.PropertyIdDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Address;
import com.example.ittalentsfinalprojectairbnb.model.entities.Characteristic;
import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
import com.example.ittalentsfinalprojectairbnb.model.entities.PropertyPhoto;
import com.example.ittalentsfinalprojectairbnb.model.repositories.*;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropertyPhotoRepository propertyPhotoRepository;


    public PropertyIdDTO addProperty(PropertyCreationDTO propertyDTO, Integer id) {

        propertyDTO.addressValidation();
        propertyDTO.propertyValidation();
        propertyDTO.characteristicValidation();

        Property property = new Property();
        Address address = new Address();
        Characteristic characteristic = new Characteristic();

        property.setHost(userRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Host not found")));
        if (property.getHost().getIsHost() != 1) {
            new BadRequestException("The provided user is NOT registered as host!");
        }

        property.setPropertyType(propertyDTO.getPropertyType());
        property.setDescription(propertyDTO.getDescription());
        property.setBathrooms(propertyDTO.getBathrooms());
        property.setBedrooms(propertyDTO.getBedrooms());
        property.setPricePerNight(propertyDTO.getPricePerNight());
        property.setGuests(propertyDTO.getGuests());

        if (propertyDTO.getPropertyPhotos() != null) {
            for (String url : propertyDTO.getPropertyPhotos()) {
                PropertyPhoto propertyPhoto = new PropertyPhoto();
                propertyPhoto.setPhoto_url(url);
                property.getImages().add(propertyPhoto);
                propertyPhotoRepository.save(propertyPhoto);
            }
        }

        characteristic.setHasBreakfast(propertyDTO.getHasBreakfast());
        characteristic.setHasFitness(propertyDTO.getHasFitness());
        characteristic.setHasAirConditioner(propertyDTO.getHasAirConditioner());
        characteristic.setHasFridge(propertyDTO.getHasFridge());
        characteristic.setHasTv(propertyDTO.getHasTv());
        characteristic.setHasWifi(propertyDTO.getHasWifi());
        characteristic.setHasBreakfast(propertyDTO.getHasBreakfast());
        characteristic.setHasWashingMachine(propertyDTO.getHasWashingMachine());
        characteristic.setHasParkingSpot(propertyDTO.getHasParkingSpot());
        characteristic.setTypeOfBed(propertyDTO.getTypeOfBed());
        characteristic.setHasKitchenFacilities(propertyDTO.getHasKitchenFacilities());

        address.setCountry(propertyDTO.getCountry());
        address.setCity(propertyDTO.getCity());
        address.setStreet(propertyDTO.getStreet());
        address.setZipCode(propertyDTO.getZipCode());
        address.setApartmentNumber(propertyDTO.getApartmentNumber());

        property.setCharacteristic(characteristic);
        property.setAddress(address);
        propertyRepository.save(property);

        PropertyIdDTO dto = mapper.map(property, PropertyIdDTO.class);
        return dto;
    }


    public Property getPropertyById(int id) {
        return propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found"));
    }

    @SneakyThrows
    public String uploadFile(PropertyIdDTO propertyID, MultipartFile file, HttpServletRequest request) {
        UserController.validateLogin(request);

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = System.nanoTime() + "." + extension;
        Files.copy(file.getInputStream(), new File("uploads" + File.separator + name).toPath());
        Property p = new Property();
        PropertyPhoto photo = new PropertyPhoto();
        photo.setPhoto_url(name);
        p.setId(propertyID.getId());
        p.getImages().add(photo);
        propertyPhotoRepository.save(photo);
        return name;
    }

    public void deletePhotoById(int id) {
        Optional<PropertyPhoto> opt = propertyPhotoRepository.findById(id);
        if (opt.isPresent()) {
            propertyPhotoRepository.delete(opt.get());
        } else {
            throw new NotFoundException("Photo not found!");
        }
    }

    public void deletePropertyById(int id) {
        Optional<Property> opt = propertyRepository.findById(id);
        if (opt.isPresent()) {
            propertyRepository.delete(opt.get());
        } else {
            throw new NotFoundException("Property not found!");
        }
    }


    public PropertyIdDTO editAddress(EditAddressDTO addressDTO, int id) {
        Property p = getPropertyById(id);
        Address address = p.getAddress();

        if (addressDTO.getCountry() != null) {
            address.setCountry(addressDTO.getCountry());
        }
        if (addressDTO.getCity() != null) {
            address.setCity(addressDTO.getCity());
        }
        if (addressDTO.getStreet() != null) {
            address.setStreet(addressDTO.getStreet());
        }
        if (addressDTO.getZipCode() != null) {
            address.setZipCode(addressDTO.getZipCode());
        }
        if ((Integer) addressDTO.getApartmentNumber() != null) {
            address.setApartmentNumber(addressDTO.getApartmentNumber());
        }
        p.setAddress(address);
        propertyRepository.save(p);

        PropertyIdDTO dto = mapper.map(p, PropertyIdDTO.class);
        return dto;
    }

    public List<PropertyIdDTO> filter(FilterPropertyDTO filter) {
        List<Property> allProperties = propertyRepository.findAll();
        List<PropertyIdDTO> filteredPropertiesId = new ArrayList<>();

        for (Property p : allProperties) {
            if (p.getAddress().getCountry().equalsIgnoreCase((filter.getCountry())) &&
                    p.getAddress().getCity().equalsIgnoreCase(filter.getCity()) &&
                    p.getBathrooms() == filter.getBathrooms() &&
                    p.getBedrooms() == filter.getBedrooms() &&
                    p.getGuests() == filter.getGuests() &&
                    p.getCharacteristic().getHasWifi() == filter.getHasWifi() &&
                    p.getCharacteristic().getHasTv() == filter.getHasTv() &&
                    p.getCharacteristic().getHasAirConditioner() == filter.getHasAirConditioner() &&
                    p.getCharacteristic().getHasFridge() == filter.getHasFridge() &&
                    p.getCharacteristic().getHasKitchenFacilities() == filter.getHasKitchenFacilities() &&
                    p.getCharacteristic().getHasBreakfast() == filter.getHasBreakfast() &&
                    p.getCharacteristic().getHasParkingSpot() == filter.getHasParkingSpot() &&
                    p.getCharacteristic().getHasFitness() == filter.getHasFitness() &&
                    p.getCharacteristic().getHasWashingMachine() == filter.getHasWashingMachine()) {
                PropertyIdDTO dto = new PropertyIdDTO();
                dto.setId(p.getId());
                filteredPropertiesId.add(dto);
            }
        }
        return filteredPropertiesId;
    }
}