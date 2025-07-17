package com.waracle.cakemgr.mapper;


import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.model.CakeEntity;
import org.springframework.stereotype.Component;

@Component
public class CakeMapper {

        public CakeDto toDto(CakeEntity entity) {
            CakeDto dto = new CakeDto();
            dto.setId(entity.getEmployeeId());
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setImage(entity.getImage());
            return dto;
        }

        public CakeEntity toEntity(CakeDto dto) {
            CakeEntity entity = new CakeEntity();
            entity.setEmployeeId(dto.getId());
            entity.setTitle(dto.getTitle());
            entity.setDescription(dto.getDescription());
            entity.setImage(dto.getImage());
            return entity;
        }
}
