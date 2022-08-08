package com.citylist.server.service;

import com.citylist.server.dto.CityDTO;
import com.citylist.server.entity.City;
import com.citylist.server.mapper.CityMapper;
import com.citylist.server.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.citylist.server.config.Constants.DEFAULT_PAGE_NUMBER;
import static com.citylist.server.config.Constants.DEFAULT_PAGE_SIZE;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMapper cityMapper;

    public CityService() {
    }

    public Page<CityDTO> getAllCities(Optional<Integer> page, Optional<Integer> size) {
        //TODO - Add check for negative values

        Page<City> allCities = cityRepository.findAll(PageRequest.of(page.orElse(DEFAULT_PAGE_NUMBER), size.orElse(DEFAULT_PAGE_SIZE)));

        return allCities.map(cityMapper::mapEntityToDTO);
    }
}
