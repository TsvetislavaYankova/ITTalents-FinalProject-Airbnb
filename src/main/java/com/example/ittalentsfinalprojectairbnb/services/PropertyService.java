package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.controller.UserController;
import com.example.ittalentsfinalprojectairbnb.exceptions.BadRequestException;
import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.model.dto.PropertyCreationDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.PropertyIdDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Address;
import com.example.ittalentsfinalprojectairbnb.model.entities.Characteristic;
import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
import com.example.ittalentsfinalprojectairbnb.model.entities.PropertyPhoto;
import com.example.ittalentsfinalprojectairbnb.model.repositories.*;
import com.example.ittalentsfinalprojectairbnb.utils.SessionManager;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
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

        property.setHost(userRepository.findById(id).orElseThrow(() -> new NotFoundException("Host not found")));
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
        SessionManager.validateLogin(request);

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


}