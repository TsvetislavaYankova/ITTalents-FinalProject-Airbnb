package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.controller.UserController;
import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.model.dto.CreatePropertyDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.PropertyIdDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Address;
import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
import com.example.ittalentsfinalprojectairbnb.model.entities.PropertyPhoto;
import com.example.ittalentsfinalprojectairbnb.model.repositories.AddressRepository;
import com.example.ittalentsfinalprojectairbnb.model.repositories.PropertyPhotoRepository;
import com.example.ittalentsfinalprojectairbnb.model.repositories.PropertyRepository;
import com.example.ittalentsfinalprojectairbnb.model.repositories.UserRepository;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;

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
    @Autowired
    private PropertyPhotoRepository propertyPhotoRepository;


    public PropertyIdDTO addProperty(CreatePropertyDTO propertyDTO, Integer id) {
        //TODO validations
        Property property = new Property();
        Address address = new Address();
        address.setCity(propertyDTO.getCity());
        address.setCountry(propertyDTO.getCountry());
        address.setStreet(propertyDTO.getStreet());
        address.setZipCode(propertyDTO.getZipCode());
        address.setApartmentNumber(propertyDTO.getApartmentNumber());
        addressRepository.save(address);
        property.setHost(userRepository.findById(id).orElseThrow(() -> new NotFoundException("Host not found")));
        property.setAddress(address);
        property.setPropertyType(propertyDTO.getPropertyType());
        property.setDescription(propertyDTO.getDescription());
        property.setBathrooms(propertyDTO.getBathrooms());
        property.setBedrooms(propertyDTO.getBedrooms());
        property.setPricePerNight(propertyDTO.getPricePerNight());
        property.setGuests(propertyDTO.getGuests());

        for (String url : propertyDTO.getPropertyPhotos()){
            PropertyPhoto propertyPhoto = new PropertyPhoto();
            propertyPhoto.setPhoto_url(url);
            property.getImages().add(propertyPhoto);
            propertyPhotoRepository.save(propertyPhoto);
        }

        propertyRepository.save(property);
        PropertyIdDTO dto = mapper.map(property, PropertyIdDTO.class);
        return dto;
    }

    private Property getPropertyById(int id){
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
}
