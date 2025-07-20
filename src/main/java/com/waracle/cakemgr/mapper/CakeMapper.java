package com.waracle.cakemgr.mapper;


import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.model.CakeEntity;
import org.springframework.stereotype.Service;

@Service
public class CakeMapper {

        public static CakeDto toDto(CakeEntity entity) {
            CakeDto dto = new CakeDto();
            dto.setEmployeeId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setImage(entity.getImage());
            return dto;
        }

        public static CakeEntity toEntity(CakeDto dto) {
            CakeEntity entity = new CakeEntity();
            entity.setId(dto.getEmployeeId());
            entity.setTitle(dto.getTitle());
            entity.setDescription(dto.getDescription());
            entity.setImage(dto.getImage());
            return entity;
        }
}
