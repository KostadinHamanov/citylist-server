package com.citylist.server.service.impl;

import com.citylist.server.dto.CityDTO;
import com.citylist.server.entity.City;
import com.citylist.server.mapper.CityMapperImpl;
import com.citylist.server.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.citylist.server.config.Constants.DEFAULT_PAGE_NUMBER;
import static com.citylist.server.config.Constants.DEFAULT_PAGE_SIZE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceImplTest {

    @InjectMocks
    private CityServiceImpl objectUnderTest;

    @Mock
    private CityRepository cityRepositoryMock;

    @Spy
    private CityMapperImpl cityMapper;

    private CityDTO cityDTO;
    private City city;
    private Page<City> citiesPage;

    @BeforeEach
    public void setup(){
        cityDTO = new CityDTO();
        cityDTO.setId(1L);
        cityDTO.setName("Sofia");
        cityDTO.setPhoto("https://upload.wikimedia.org/");

        city = new City();
        city.setId(1L);
        city.setName("Sofia");
        city.setPhoto("https://upload.wikimedia.org/");

        citiesPage = (Page<City>) Mockito.mock(Page.class);
    }

    /**
     * getAllCities tests
     */
    @Test
    public void getAllCities_shouldUseDefaultPagination_whenNoneIsSet() {
        when(cityRepositoryMock.findByNameContainingIgnoreCase(any(), any())).thenReturn(citiesPage);

        objectUnderTest.getAllCities(Optional.empty(), Optional.empty(), Optional.of(""));

        verify(cityRepositoryMock).findByNameContainingIgnoreCase("", PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE));
        verify(cityRepositoryMock, times(0)).findAll(PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE));
    }

    @Test
    public void getAllCities_shouldCallAllCities_whenNoNameFilteringIsSet() {
        when(cityRepositoryMock.findAll(PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE))).thenReturn(citiesPage);

        objectUnderTest.getAllCities(Optional.empty(), Optional.empty(), Optional.empty());

        verify(cityRepositoryMock).findAll(PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE));
        verify(cityRepositoryMock, times(0)).findByNameContainingIgnoreCase("", PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE));
    }

    @Test
    public void getAllCities_shouldPagination_whenPaginationIsSet() {
        when(cityRepositoryMock.findByNameContainingIgnoreCase(any(), any())).thenReturn(citiesPage);
        int PAGE_SIZE = 3;
        int PAGE_NUMBER = 0;

        objectUnderTest.getAllCities(Optional.of(PAGE_NUMBER), Optional.of(PAGE_SIZE), Optional.of(""));

        verify(cityRepositoryMock).findByNameContainingIgnoreCase("", PageRequest.of(PAGE_NUMBER, PAGE_SIZE));
        verify(cityRepositoryMock, times(0)).findAll(PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE));
    }

    /**
     * updateCity tests
     */
    @Test
    public void updateCity_shouldUpdateOnlyCityName_whenNewNameIsSet() {
        cityDTO.setName("Sofia123");
        when(cityRepositoryMock.findById(anyLong())).thenReturn(Optional.of(city));

        CityDTO updatedCity = objectUnderTest.updateCity(cityDTO.getId(), cityDTO);

        assertEquals("Sofia123", updatedCity.getName());
        assertEquals(cityDTO.getPhoto(), updatedCity.getPhoto());

        verify(cityRepositoryMock, atLeastOnce()).findById(anyLong());
        verify(cityMapper, times(1)).mapEntityToDTO(any(City.class));
    }

    @Test
    public void updateCity_shouldUpdateOnlyPhoto_whenNewPhotoIsSet() {
        cityDTO.setPhoto("newPhotoURL");
        when(cityRepositoryMock.findById(anyLong())).thenReturn(Optional.of(city));

        CityDTO updatedCity = objectUnderTest.updateCity(cityDTO.getId(), cityDTO);

        assertEquals("newPhotoURL", updatedCity.getPhoto());
        assertEquals(cityDTO.getName(), updatedCity.getName());

        verify(cityRepositoryMock, atLeastOnce()).findById(anyLong());
        verify(cityMapper, times(1)).mapEntityToDTO(any(City.class));
    }

    @Test
    public void updateCity_shouldUpdateNameAndPhoto_whenBothAreSet() {
        cityDTO.setName("newCityName");
        cityDTO.setPhoto("newPhotoURL");
        when(cityRepositoryMock.findById(anyLong())).thenReturn(Optional.of(city));

        CityDTO updatedCity = objectUnderTest.updateCity(cityDTO.getId(), cityDTO);

        assertEquals("newPhotoURL", updatedCity.getPhoto());
        assertEquals("newCityName", updatedCity.getName());
    }

    @Test
    public void updateCity_shouldThrowAnException_whenNoCityFound() {
        when(cityRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> objectUnderTest.updateCity(cityDTO.getId(), cityDTO))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("NOT_FOUND");
    }
}
