package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.BaseTest;
import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.mapper.CakeMapperService;
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
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CakesControllerTest  extends BaseTest {


    @Mock
    private CakesService cakesService;

    @Mock
    private CakeMapperService cakeMapperService;

    @InjectMocks
    private CakesController cakesController;

    
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        modelMapper = new ModelMapper();
        cakeMapperService = new CakeMapperService(modelMapper);

        cakesController = new CakesController(cakesService, cakeMapperService);
    }



    @Test
    void testGetCakeById() {
        CakeEntity mockCake = new CakeEntity(1, "Cake1", "Description1", "Image1");
        when(cakesService.getCakeById(1)).thenReturn(Optional.of(mockCake));

        ResponseEntity<CakeEntity> response = (ResponseEntity<CakeEntity>) cakesController.getCakeById(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cake1", response.getBody().getTitle());
        verify(cakesService, times(1)).getCakeById(1);
    }

    @Test
    void testGetCakeById_NotFound() {
        when(cakesService.getCakeById(1)).thenReturn(Optional.empty());

        ResponseEntity<CakeEntity> response = (ResponseEntity<CakeEntity>) cakesController.getCakeById(1);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(cakesService, times(1)).getCakeById(1);
    }

    @Test
    void testFindAll() {
        List<CakeEntity> mockCakes = Arrays.asList(
                new CakeEntity(1, "Cake1", "Description1", "Image1"),
                new CakeEntity(2, "Cake2", "Description2", "Image2")
        );
        when(cakesService.getAllCakes()).thenReturn(mockCakes);

        ResponseEntity<?> response = cakesController.findAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<CakeDto> cakes = (List<CakeDto>) response.getBody();
        assertNotNull(cakes);
        assertEquals(2, cakes.size());
        verify(cakesService, times(1)).getAllCakes();
    }

    @Test
    void testDelete() {
        when(cakesService.deleteCake(1)).thenReturn(true);

        ResponseEntity<?> response = cakesController.delete(1);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(cakesService, times(1)).deleteCake(1);
    }


    @Test
    void testDelete_NotFound() {
        when(cakesService.deleteCake(1)).thenReturn(false);

        ResponseEntity<?> response = cakesController.delete(1);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(cakesService, times(1)).deleteCake(1);
    }



}