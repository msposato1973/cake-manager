package com.waracle.cakemgr.service.impl;


import com.waracle.cakemgr.BaseTest;
import com.waracle.cakemgr.dao.CakeRepository;
import com.waracle.cakemgr.model.CakeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CakesServiceImplTest extends BaseTest {

    @Mock
    private CakeRepository cakeRepository;

    @InjectMocks
    private CakesServiceImpl cakesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCakes() {
        CakeEntity mockCake = getBuildMockCakeEntity(1, "Cake1", "Description1", "Image1");
        // Mock the repository to return a list containing the mock cake
        when(cakeRepository.findAll()).thenReturn(List.of(mockCake));
        List<CakeEntity> mockCakes = List.of(mockCake);
        when(cakeRepository.findAll()).thenReturn(mockCakes);
        List<CakeEntity> result = cakesService.getAllCakes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Cake1", result.get(0).getTitle());
        verify(cakeRepository, times(1)).findAll();
    }

    @Test
    void testGetCakeById() {
        // Mock the repository to return a cake with ID 1

        when(cakeRepository.findById(1)).thenReturn(Optional.of(getBuildMockCakeEntity(1, "Cake1", "Description1", "Image1")));

        Optional<CakeEntity> result = cakesService.getCakeById(1);

        assertTrue(result.isPresent());
        assertEquals("Cake1", result.get().getTitle());
        verify(cakeRepository, times(1)).findById(1);
    }

    @Test
    void testCreateCake() {
        CakeEntity mockCake = getBuildMockCakeEntity(1, "Cake1", "Description1", "Image1")  ;

        when(cakeRepository.save(mockCake)).thenReturn(mockCake);

        CakeEntity result = cakesService.createCake(mockCake);

        assertNotNull(result);
        assertEquals("Cake1", result.getTitle());
        verify(cakeRepository, times(1)).save(mockCake);
    }

    @Test
    void testUpdateCake() {
        CakeEntity existingCake = getBuildMockCakeEntity(1, "Cake1", "Description1", "Image1");
        CakeEntity updatedCake = getBuildMockCakeEntity(1, "UpdatedCake", "UpdatedDescription", "UpdatedImage");

        when(cakeRepository.findById(1)).thenReturn(Optional.of(existingCake));
        when(cakeRepository.save(existingCake)).thenReturn(updatedCake);

        Optional<CakeEntity> result = cakesService.updateCake(1, updatedCake);

        assertTrue(result.isPresent());
        assertEquals("UpdatedCake", result.get().getTitle());
        verify(cakeRepository, times(1)).findById(1);
        verify(cakeRepository, times(1)).save(existingCake);
    }

    @Test
    void testDeleteCake() {
        when(cakeRepository.existsById(1)).thenReturn(true);

        boolean result = cakesService.deleteCake(1);

        assertTrue(result);
        verify(cakeRepository, times(1)).existsById(1);
        verify(cakeRepository, times(1)).deleteById(1);
    }


    @Test
    void testSaveAll() {
        CakeEntity cake1 = getBuildMockCakeEntity(1, "Cake1", "Description1", "Image1");
        CakeEntity cake2 = getBuildMockCakeEntity(2, "Cake2", "Description2", "Image2");
        List<CakeEntity> cakes = List.of(cake1, cake2);

        cakesService.saveAll(cakes);

        verify(cakeRepository, times(1)).saveAll(cakes);
        verify(cakeRepository, times(1)).flush();
    }

    @Test
    void testUpdateCake_NullIdOrCake() {
        Optional<CakeEntity> result1 = cakesService.updateCake(null, getBuildMockCakeEntity(1, "Cake1", "Description1", "Image1"));
        Optional<CakeEntity> result2 = cakesService.updateCake(1, null);

        assertTrue(result1.isEmpty());
        assertTrue(result2.isEmpty());
        verify(cakeRepository, times(0)).findById(anyInt());
        verify(cakeRepository, times(0)).save(any(CakeEntity.class));
    }

    @Test
    void testUpdateCake_ExistingCake() {
        CakeEntity newCake = getBuildMockCakeEntity(1, "Cake1", "Description1", "Image1");

        when(cakeRepository.existsByTitleAndDescriptionAndImage(
                newCake.getTitle(), newCake.getDescription(), newCake.getImage()
        )).thenReturn(true);

        Optional<CakeEntity> result = cakesService.updateCake(1, newCake);

        assertTrue(result.isEmpty());
        verify(cakeRepository, times(0)).findById(anyInt());
        verify(cakeRepository, times(0)).save(any(CakeEntity.class));
    }

    @Test
    void testDeleteCake_NotFound() {
        when(cakeRepository.existsById(1)).thenReturn(false);

        boolean result = cakesService.deleteCake(1);

        assertFalse(result);
        verify(cakeRepository, times(1)).existsById(1);
        verify(cakeRepository, times(0)).deleteById(1);
    }

}
