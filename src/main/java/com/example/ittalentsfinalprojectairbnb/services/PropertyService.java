package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.BadRequestException;
import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
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
import java.util.*;

import static com.example.ittalentsfinalprojectairbnb.services.UserService.AVAILABLE_FILE_TYPES;
import static com.example.ittalentsfinalprojectairbnb.services.UserService.MAX_ALLOWED_FILE_SIZE;

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

    public PropertyResponseDTO addProperty(PropertyCreationDTO propertyDTO, Integer id) {

        propertyDTO.addressValidation();
        propertyDTO.propertyValidation();
        propertyDTO.characteristicValidation();

        Property property = new Property();
        Address address = new Address();
        Characteristic characteristic = new Characteristic();

        property.setHost(userRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Host not found")));
        if (property.getHost().getIsHost() != 1) {
            throw new BadRequestException("The provided user is NOT registered as host!");
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

        PropertyResponseDTO dto = mapper.map(property, PropertyResponseDTO.class);

        dto.additionalMapping(property);

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


    public PropertyResponseDTO editCharacteristic(EditCharacteristicDTO characteristicDTO, int id) {
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

        PropertyResponseDTO dto = mapper.map(p, PropertyResponseDTO.class);
        dto.additionalMapping(p);
        return dto;
    }

    public PropertyResponseDTO editAddress(EditAddressDTO addressDTO, int id) {
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

        PropertyResponseDTO dto = mapper.map(p, PropertyResponseDTO.class);
        dto.additionalMapping(p);
        return dto;
    }

    public PropertyResponseDTO addRating(int id) {
        double finalRating = 0;
        Property p = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found"));
        Set<Review> propertyRatings = reviewRepository.findByPropertyId(p.getId()).orElseThrow(() -> new NotFoundException("This property has not received reviews!"));
        for (Review r : propertyRatings) {
            finalRating += r.getRating();
        }
        finalRating = finalRating / propertyRatings.size();
        p.setGuestRating(finalRating);
        propertyRepository.save(p);

        PropertyResponseDTO dto = mapper.map(p, PropertyResponseDTO.class);
        dto.additionalMapping(p);
        return dto;
    }

    public List<PropertyResponseDTO> filterByCharacteristics(FilterPropertyDTO filter) {
        List<Property> allProperties = propertyRepository.findAll();
        List<PropertyResponseDTO> filteredProperties = new ArrayList<>();

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
                PropertyResponseDTO dto = mapper.map(p, PropertyResponseDTO.class);
                dto.additionalMapping(p);
                filteredProperties.add(dto);
            }
        }
        return filteredProperties;
    }


    public List<PropertyResponseDTO> filterByPrice(PropertyPriceDTO filter) {
        List<Property> allProperties = propertyRepository.findAll();
        List<PropertyResponseDTO> filteredPropertiesId = new ArrayList<>();

        for (Property p : allProperties) {
            if (p.getPricePerNight() >= filter.getLowerLimitPrice() && p.getPricePerNight() <= filter.getUpperLimitPrice()) {
                PropertyResponseDTO dto = mapper.map(p, PropertyResponseDTO.class);
                dto.additionalMapping(p);
                filteredPropertiesId.add(dto);
            }
        }
        return filteredPropertiesId;

    }

    @SneakyThrows
    public PropertyPhotoDTO uploadPhoto(int propertyId, MultipartFile file) {
        if(!AVAILABLE_FILE_TYPES.contains(file.getContentType())){
            throw new BadRequestException("Invalid file type");
        }
        if(file.getSize()>MAX_ALLOWED_FILE_SIZE){
            throw new BadRequestException("Please upload photo with max size 2MB.");
        }
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = System.nanoTime() + "." + extension;
        Files.copy(file.getInputStream(), new File("photos/properties_photos" + File.separator + fileName).toPath());
        Optional<Property> p = propertyRepository.findById(propertyId);
        PropertyPhoto photo = new PropertyPhoto();
        if (p.isPresent()) {
            photo.setPhoto_url(fileName);
            photo.setProperty(p.get());

            propertyPhotoRepository.save(photo);
        } else {
            throw new NotFoundException("Property not found! Photo upload failed!");
        }

        PropertyPhotoDTO dto = mapper.map(photo, PropertyPhotoDTO.class);
        return dto;
    }

    public void deletePhotoById(HttpServletRequest request, int id) {
        Optional<PropertyPhoto> opt = propertyPhotoRepository.findById(id);
        if (opt.isPresent()) {
            if ((int) request.getSession().getAttribute(SessionManager.USER_ID) != this.getPropertyById(opt.get().getProperty().getId()).getHost().getId()) {
                throw new UnauthorizedException("Photo could not be deleted from property which does not belong to the logged user!");
            }
            propertyPhotoRepository.delete(opt.get());
        } else {
            throw new NotFoundException("Photo not found!");
        }
    }

}