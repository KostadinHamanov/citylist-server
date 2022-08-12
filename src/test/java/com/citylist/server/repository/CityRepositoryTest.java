package com.citylist.server.repository;

import com.citylist.server.entity.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Test
    void findByNameContainingIgnoreCase_shouldReturnCity_whenSearchByExactCityName() {
        Page<City> page = cityRepository.findByNameContainingIgnoreCase("sofia", Pageable.unpaged());

        List<City> result = page.get().collect(Collectors.toList());

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("Sofia");
    }

    @Test
    void findByNameContainingIgnoreCase_shouldReturnCity_whenSearchByCityNameIgnoringCase() {
        Page<City> page = cityRepository.findByNameContainingIgnoreCase("Sofia", Pageable.unpaged());

        List<City> result = page.get().collect(Collectors.toList());

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("Sofia");
    }

    @Test
    void findByNameContainingIgnoreCase_shouldReturnCity_whenSearchByNotFullCityName() {
        Page<City> page = cityRepository.findByNameContainingIgnoreCase("Sof", Pageable.unpaged());

        List<City> result = page.get().collect(Collectors.toList());

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("Sofia");
    }

    @Test
    void findByNameContainingIgnoreCase_shouldReturnPageNumberAndPageSize_whenValuesAreSet() {
        int PAGE_SIZE = 3;
        int PAGE_NUMBER = 0;

        Page<City> page = cityRepository.findByNameContainingIgnoreCase("", PageRequest.of(PAGE_NUMBER, PAGE_SIZE));

        assertEquals(PAGE_SIZE, page.getSize());
        assertEquals(PAGE_NUMBER, page.getNumber());
    }
}
