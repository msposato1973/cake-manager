package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.BaseTest;
import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.mapper.CakeMapper;
import com.waracle.cakemgr.model.CakeEntity;
import com.waracle.cakemgr.service.CakesService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CakesControllerTest  extends BaseTest {


    @Mock
    private CakesService cakesService;

    @InjectMocks
    private CakesController cakesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {



        CakeEntity mockCake = getBuildMockCakeEntity(1, "Cake1", "Description1", "Image1");
        when(cakesService.getAllCakes()).thenReturn(List.of(mockCake));

        ResponseEntity<?> response = cakesController.findAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(cakesService, times(1)).getAllCakes();
 
    }

    @Test
    void testGetCakeById() {
        CakeEntity mockCake = new CakeEntity(1, "Cake1", "Description1", "Image1");
        when(cakesService.getCakeById(1)).thenReturn(Optional.of(mockCake));

        ResponseEntity<CakeEntity> response = cakesController.getCakeById(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cake1", response.getBody().getTitle());
        verify(cakesService, times(1)).getCakeById(1);
    }

    @Test
    void testCreate() {
        CakeDto cakeDto = getBuildMockCakeDto("Cake1", "Description1", "Image1") ;
        CakeEntity mockCake = CakeMapper.toEntity(cakeDto);
        when(cakesService.createCake(any(CakeEntity.class))).thenReturn(mockCake);

        ResponseEntity<CakeEntity> response = cakesController.create(cakeDto);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Cake1", response.getBody().getTitle());
        verify(cakesService, times(1)).createCake(any(CakeEntity.class));
    }

    @Test
    void testUpdate() {
        CakeDto cakeDto = getBuildMockCakeDto("UpdatedCake", "UpdatedDescription", "UpdatedImage");
        CakeEntity updatedCake = CakeMapper.toEntity(cakeDto);
        when(cakesService.updateCake(eq(1), any(CakeEntity.class))).thenReturn(Optional.of(updatedCake));

        ResponseEntity<CakeEntity> response = cakesController.update(1, cakeDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("UpdatedCake", response.getBody().getTitle());
        verify(cakesService, times(1)).updateCake(eq(1), any(CakeEntity.class));
    }

    @Test
    void testDelete() {
        when(cakesService.deleteCake(1)).thenReturn(true);

        ResponseEntity<?> response = cakesController.delete(1);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(cakesService, times(1)).deleteCake(1);
    }



}