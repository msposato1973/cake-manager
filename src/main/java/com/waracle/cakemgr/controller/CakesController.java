package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.api.*;
import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.mapper.CakeMapper;
import com.waracle.cakemgr.model.CakeEntity;
import com.waracle.cakemgr.service.CakesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.BASE)
public class CakesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CakesController.class);

    private final CakesService cakesService;

    public CakesController(CakesService cakeService) {
        this.cakesService = cakeService;
    }

    @GetMapping(ApiPaths.BASE)
    public List<CakeDto> findAll() {
        LOGGER.info("START - findAll {..}");
        List<CakeEntity> cakes = cakesService.getAllCakes();
        LOGGER.info("END - findAll {..} - Total Cakes: {}", cakes.size());
        return  buildResponse(cakesService.getAllCakes());

    }

    private List<CakeDto> buildResponse(List<CakeEntity> allCakes) {

        List<CakeDto> cakeDtos = allCakes.stream()
                .map(CakeMapper::toDto)
                .toList();

        if (cakeDtos.isEmpty()) return List.of();

        LOGGER.info("Cakes found: {}", cakeDtos.size());
        if (cakeDtos.size() == 1) return List.of(cakeDtos.get(0));

        LOGGER.info("Multiple cakes found, returning first one.");

        return  cakeDtos;
    }


    @PostMapping
    public ResponseEntity<CakeEntity> create(@RequestBody CakeEntity cake) {
        CakeEntity saved = cakesService.createCake(cake);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping(ApiPaths.CAKE_BY_ID)
    public ResponseEntity<CakeEntity> getCakeById(@PathVariable Integer id) {
        return this.cakesService.getCakeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(ApiPaths.CAKE_BY_ID)
    public ResponseEntity<CakeEntity> update(@PathVariable Integer id, @RequestBody CakeEntity cake) {
        return cakesService.updateCake(id, cake)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(ApiPaths.CAKE_BY_ID)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (cakesService.deleteCake(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /***
     *
     * @param <T>
     * @param response
     * @param customerRef
     * @return
     */
    private <T> ResponseEntity<CakeDto> buildResponse(CakeDto response, String customerRef) {

        return (response != null) ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>(new CakeDto(), HttpStatus.NOT_FOUND);
    }

    /***
     *
     * @param <T>
     * @param response
     * @return
     */
    private <T> ResponseEntity<CakeDto> buildResponse(CakeDto response) {

        return (response != null) ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>(new CakeDto(), HttpStatus.NOT_FOUND);
    }

    /***
     *
     * @param <T>
     * @param antityDTO
     * @return
     */
    private <T> ResponseEntity<?> buildResponseEntity(List<T> antityDTO) {
        if (antityDTO.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return ResponseEntity.ok(antityDTO);
    }
}
