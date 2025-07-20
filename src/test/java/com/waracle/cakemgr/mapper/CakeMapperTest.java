package com.waracle.cakemgr.mapper;

import com.waracle.cakemgr.BaseTest;
import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.model.CakeEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CakeMapperTest extends BaseTest {

    @Test
    void testToDto() {

        CakeEntity entity = getBuildMockCakeEntity(1, "Chocolate Cake", "Delicious chocolate cake", "chocolate.jpg");
        CakeDto dto = CakeMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getEmployeeId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getImage(), dto.getImage());
    }

    @Test
    void testToEntity() {

        CakeDto dto = getBuildMockCakeDto(1, "Vanilla Cake", "Delicious vanilla cake", "vanilla.jpg");
        CakeEntity entity = CakeMapper.toEntity(dto);


        assertNotNull(entity);
        assertEquals(dto.getEmployeeId(), entity.getId());
        assertEquals(dto.getTitle(), entity.getTitle());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getImage(), entity.getImage());
    }
}