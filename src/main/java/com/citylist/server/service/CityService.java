package com.citylist.server.service;

import com.citylist.server.dto.CityDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CityService {

    /**
     * Get all cities. Supports pagination and filtering by name
     * @param page - offset. Default value is used if no page is set
     * @param size - number of cities per single page. Default value is used if no size is set
     * @param name - filer by city name (case insensitive)
     * @return a list of cities and metadata about all of them
     */
    Page<CityDTO> getAllCities(Optional<Integer> page, Optional<Integer> size, Optional<String> name);

    /**
     * Updates the city info - ex. name, photo
     * @param id - id of the city
     * @param newCityDTO - new data model of the city
     * @return - updated city
     */
    CityDTO updateCity(Long id, CityDTO newCityDTO);
}
