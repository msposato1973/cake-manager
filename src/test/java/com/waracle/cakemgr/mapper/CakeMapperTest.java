package com.waracle.cakemgr.mapper;

import com.waracle.cakemgr.BaseTest;
import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.model.CakeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CakeMapperTest extends BaseTest {


    private CakeMapperService cakeMapperService;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        cakeMapperService = new CakeMapperService(modelMapper);
    }

    @Test
    void testToDto() {
        CakeEntity entity = new CakeEntity();
        entity.setId(1);
        entity.setTitle("Chocolate Cake");
        entity.setDescription("Delicious chocolate cake");
        entity.setImage("chocolate.jpg");

        CakeDto dto = cakeMapperService.toDto(entity);


        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getImage(), dto.getImage());
    }

    @Test
    void testToEntity() {
        CakeDto dto = new CakeDto();

        dto.setTitle("Vanilla Cake");
        dto.setDescription("Tasty vanilla cake");
        dto.setImage("vanilla.jpg");

        CakeEntity entity = cakeMapperService.toEntity(dto);

        assertEquals(dto.getTitle(), entity.getTitle());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getImage(), entity.getImage());
    }

    @Test
    void testToDtoList() {
        CakeEntity entity1 = new CakeEntity();
        entity1.setId(1);
        entity1.setTitle("Cake 1");
        entity1.setDescription("Description 1");
        entity1.setImage("image1.jpg");

        CakeEntity entity2 = new CakeEntity();
        entity2.setId(2);
        entity2.setTitle("Cake 2");
        entity2.setDescription("Description 2");
        entity2.setImage("image2.jpg");

        List<CakeEntity> entities = List.of(entity1, entity2);

        List<CakeDto> dtos = cakeMapperService.toDtoList(entities);

        assertEquals(entities.size(), dtos.size());
        assertEquals(entities.get(0).getTitle(), dtos.get(0).getTitle());
        assertEquals(entities.get(1).getTitle(), dtos.get(1).getTitle());
    }

    @Test
    void testToEntityList() {
        CakeDto dto1 = new CakeDto();

        dto1.setTitle("Cake 1");
        dto1.setDescription("Description 1");
        dto1.setImage("image1.jpg");

        CakeDto dto2 = new CakeDto();

        dto2.setTitle("Cake 2");
        dto2.setDescription("Description 2");
        dto2.setImage("image2.jpg");

        List<CakeDto> dtos = List.of(dto1, dto2);

        List<CakeEntity> entities = cakeMapperService.toEntityList(dtos);

        assertEquals(dtos.size(), entities.size());
        assertEquals(dtos.get(0).getTitle(), entities.get(0).getTitle());
        assertEquals(dtos.get(1).getTitle(), entities.get(1).getTitle());
    }

}
