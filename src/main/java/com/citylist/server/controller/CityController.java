package com.citylist.server.controller;

import com.citylist.server.dto.CityDTO;
import com.citylist.server.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
@RequestMapping("")
public class CityController {

    @Autowired
    private CityService cityService;

    public CityController() {
    }

    /**
     * This API provides a list of all cities with a corresponding photo for each.
     * Supports pagination and searcing by city name
     * @param page - pagination param - offset. Default value is used if no page is set
     * @param size - pagination param - number of cities per single page. Default value is used if no size is set
     * @param name - request param to search by city name (case insensitive)
     * @return a list of cities and metadata about all the cities
     */
    @GetMapping("/cities")
    public ResponseEntity<Page<CityDTO>> getAllCities(@RequestParam Optional<Integer> page,
                                                      @RequestParam Optional<Integer> size,
                                                      @RequestParam Optional<String> name) {

        return ResponseEntity.ok(cityService.getAllCities(page, size, name));
    }

    /**
     *
     * @param id
     * @param city
     * @return
     */
    @PutMapping("/cities/{id}")
    public ResponseEntity<CityDTO> updateCity(@PathVariable Long id,
                                              @RequestBody CityDTO city) {

        if (id == null) {
            throw new ResponseStatusException(BAD_REQUEST);
        }

        return ResponseEntity.ok(cityService.updateCity(id, city));
    }



}
