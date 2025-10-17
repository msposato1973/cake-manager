package com.waracle.cakemgr.mapper;


import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.model.CakeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CakeMapperService {

    private ModelMapper modelMapper;


    public CakeMapperService(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public  CakeDto toDto(CakeEntity entity) {
        return modelMapper.map(entity, CakeDto.class);
    }

    public  CakeEntity toEntity(CakeDto dto) {
        return modelMapper.map(dto, CakeEntity.class);
    }

    public List<CakeDto> toDtoList(List<CakeEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    public List<CakeEntity> toEntityList(List<CakeDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .toList();
    }




}
