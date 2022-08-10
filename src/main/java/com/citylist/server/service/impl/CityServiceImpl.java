package com.citylist.server.service.impl;

import com.citylist.server.dto.CityDTO;
import com.citylist.server.entity.City;
import com.citylist.server.mapper.CityMapper;
import com.citylist.server.repository.CityRepository;
import com.citylist.server.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.citylist.server.config.Constants.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMapper cityMapper;

    public CityServiceImpl() {
    }

    public Page<CityDTO> getAllCities(Optional<Integer> page, Optional<Integer> size, Optional<String> name) {
        Page<City> cities;

        if (name.isPresent()) {
            cities = (Page<City>) cityRepository.findByNameContainingIgnoreCase(name.orElse(DEFAULT_CITY_NAME),
                    PageRequest.of(page.orElse(DEFAULT_PAGE_NUMBER), size.orElse(DEFAULT_PAGE_SIZE)));
        } else {
            cities = (Page<City>) cityRepository.findAll(PageRequest.of(page.orElse(DEFAULT_PAGE_NUMBER), size.orElse(DEFAULT_PAGE_SIZE)));
        }

        return cities.map(cityMapper::mapEntityToDTO);
    }

    public CityDTO updateCity(Long id, CityDTO newCityDTO) {
        if (cityRepository.findById(id).isPresent()) {
            City city = cityRepository.findById(id).get();
            city.setName(newCityDTO.getName());
            city.setPhoto(newCityDTO.getPhoto());

            City updated = cityRepository.save(city);
            return cityMapper.mapEntityToDTO(updated);
        } else {
            throw new ResponseStatusException(NOT_FOUND);
        }
    }
}
