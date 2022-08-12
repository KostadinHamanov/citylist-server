package com.citylist.server.mapper;

import com.citylist.server.dto.CityDTO;
import com.citylist.server.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityDTO mapEntityToDTO(City entity);

    City mapDTOToEntity(CityDTO dto);
}
