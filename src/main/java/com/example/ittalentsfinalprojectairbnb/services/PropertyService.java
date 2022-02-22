package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.BadRequestException;
import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.model.dto.*;
import com.example.ittalentsfinalprojectairbnb.model.entities.*;
import com.example.ittalentsfinalprojectairbnb.model.repositories.*;
import com.example.ittalentsfinalprojectairbnb.utils.SessionManager;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    @Autowired
    private ReviewRepository reviewRepository;


    public PropertyGetByIdDTO addProperty(PropertyCreationDTO propertyDTO, Integer id) {

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

        PropertyGetByIdDTO dto = mapper.map(property, PropertyGetByIdDTO.class);
        return dto;
    }


    public Property getPropertyById(int id) {
        return propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found"));
    }

    public void deletePropertyById(int id) {
        Optional<Property> opt = propertyRepository.findById(id);
        if (opt.isPresent()) {
            propertyRepository.delete(opt.get());
        } else {
            throw new NotFoundException("Property not found!");
        }
    }


    public PropertyGetByIdDTO editCharacteristic(EditCharacteristicDTO characteristicDTO, int id) {
        Property p = getPropertyById(id);
        Characteristic ch = p.getCharacteristic();

        if ((characteristicDTO.getHasWifi() == 0 || characteristicDTO.getHasWifi() == 1) && characteristicDTO.getHasWifi() != ch.getHasWifi()) {
            ch.setHasTv(characteristicDTO.getHasWifi());
        }
        if ((characteristicDTO.getHasTv() == 0 || characteristicDTO.getHasTv() == 1) && characteristicDTO.getHasTv() != ch.getHasTv()) {
            ch.setHasTv(characteristicDTO.getHasTv());
        }
        if ((characteristicDTO.getHasAirConditioner() == 0 || characteristicDTO.getHasAirConditioner() == 1) && characteristicDTO.getHasAirConditioner() != ch.getHasAirConditioner()) {
            ch.setHasTv(characteristicDTO.getHasAirConditioner());
        }
        if ((characteristicDTO.getHasFridge() == 0 || characteristicDTO.getHasFridge() == 1) && characteristicDTO.getHasFridge() != ch.getHasFridge()) {
            ch.setHasTv(characteristicDTO.getHasFridge());
        }
        if ((characteristicDTO.getHasKitchenFacilities() == 0 || characteristicDTO.getHasKitchenFacilities() == 1) && characteristicDTO.getHasKitchenFacilities() != ch.getHasKitchenFacilities()) {
            ch.setHasTv(characteristicDTO.getHasKitchenFacilities());
        }
        if ((characteristicDTO.getHasBreakfast() == 0 || characteristicDTO.getHasBreakfast() == 1) && characteristicDTO.getHasBreakfast() != ch.getHasBreakfast()) {
            ch.setHasTv(characteristicDTO.getHasBreakfast());
        }
        if ((characteristicDTO.getHasParkingSpot() == 0 || characteristicDTO.getHasParkingSpot() == 1) && characteristicDTO.getHasParkingSpot() != ch.getHasParkingSpot()) {
            ch.setHasTv(characteristicDTO.getHasParkingSpot());
        }
        if ((characteristicDTO.getHasFitness() == 0 || characteristicDTO.getHasFitness() == 1) && characteristicDTO.getHasFitness() != ch.getHasFitness()) {
            ch.setHasTv(characteristicDTO.getHasFitness());
        }
        if ((characteristicDTO.getHasWashingMachine() == 0 || characteristicDTO.getHasWashingMachine() == 1) && characteristicDTO.getHasWashingMachine() != ch.getHasWashingMachine()) {
            ch.setHasTv(characteristicDTO.getHasWashingMachine());
        }
        if (characteristicDTO.getTypeOfBed() != null) {
            ch.setTypeOfBed(characteristicDTO.getTypeOfBed());
        }

        p.setCharacteristic(ch);
        propertyRepository.save(p);

        PropertyGetByIdDTO dto = mapper.map(p, PropertyGetByIdDTO.class);
        return dto;
    }

    public PropertyGetByIdDTO editAddress(EditAddressDTO addressDTO, int id) {
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

        PropertyGetByIdDTO dto = mapper.map(p, PropertyGetByIdDTO.class);
        return dto;
    }

    public PropertyGetByIdDTO addRating(int id) {
        double finalRating = 0;
        Property p = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found"));
        Set<Review> propertyRatings = reviewRepository.findByPropertyId(p.getId()).orElseThrow(() -> new NotFoundException("This property has not received reviews!"));
        for (Review r : propertyRatings) {
            finalRating += r.getRating();
        }
        finalRating = finalRating / propertyRatings.size();
        p.setGuestRating(finalRating);
        propertyRepository.save(p);

        PropertyGetByIdDTO dto = mapper.map(p, PropertyGetByIdDTO.class);
        return dto;
    }

    public List<PropertyGetByIdDTO> filterByCharacteristics(FilterPropertyDTO filter) {
        List<Property> allProperties = propertyRepository.findAll();
        List<PropertyGetByIdDTO> filteredPropertiesId = new ArrayList<>();

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
                PropertyGetByIdDTO dto = new PropertyGetByIdDTO();
                dto.setId(p.getId());
                filteredPropertiesId.add(dto);
            }
        }
        return filteredPropertiesId;
    }


    public List<PropertyGetByIdDTO> filterByPrice(PropertyPriceDTO filter) {
        List<Property> allProperties = propertyRepository.findAll();
        List<PropertyGetByIdDTO> filteredPropertiesId = new ArrayList<>();

        for (Property p : allProperties) {
            if (p.getPricePerNight() >= filter.getLowerLimitPrice() && p.getPricePerNight() <= filter.getUpperLimitPrice()) {
                PropertyGetByIdDTO dto = new PropertyGetByIdDTO();
                dto.setId(p.getId());
                filteredPropertiesId.add(dto);
            }
        }
        return filteredPropertiesId;

    }

    @SneakyThrows
    public String uploadPhoto(int propertyId, MultipartFile file, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = System.nanoTime() + "." + extension;
        Files.copy(file.getInputStream(), new File("uploads" + File.separator + name).toPath());
        Property p = new Property();
        PropertyPhoto photo = new PropertyPhoto();
        photo.setPhoto_url(name);
        p.setId(propertyId);
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

}