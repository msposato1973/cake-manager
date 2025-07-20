package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.api.*;
import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.mapper.CakeMapper;
import com.waracle.cakemgr.model.CakeEntity;
import com.waracle.cakemgr.service.CakesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin("*")
@RestController
@RequestMapping(ApiPaths.BASE)
public class CakesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CakesController.class);

    private final CakesService cakesService;

    @Autowired
    public CakesController(CakesService cakeService) {
        this.cakesService = cakeService;
    }


    @GetMapping(path="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        LOGGER.info("START - findAll {..}");
        List<CakeEntity> cakes = cakesService.getAllCakes();
        LOGGER.info("END - findAll {..} - Total Cakes: {}", cakes.size());
        List<CakeDto> listCakeDtos = buildResponse(cakes);

        return (!listCakeDtos.isEmpty()) ? buildResponseEntity(listCakeDtos)
                : new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);

    }

    private List<CakeDto> buildResponse(List<CakeEntity> allCakes) {

        List<CakeDto> cakeDtos = allCakes.stream()
                .map(CakeMapper::toDto)
                .collect(Collectors.toList());

        if (cakeDtos.isEmpty()) return List.of();

        LOGGER.info("Cakes found: {}", cakeDtos.size());
        if (cakeDtos.size() == 1) return List.of(cakeDtos.get(0));

        LOGGER.info("Multiple cakes found, returning first one.");

        return  cakeDtos;
    }



    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CakeEntity> create(@RequestBody CakeDto cake) {
        CakeEntity saved = cakesService.createCake(CakeMapper.toEntity(cake));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping(path=ApiPaths.CAKE_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CakeEntity> getCakeById(@PathVariable Integer id) {
        return this.cakesService.getCakeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path=ApiPaths.CAKE_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CakeEntity> update(@PathVariable Integer id, @RequestBody CakeDto cake) {
        return cakesService.updateCake(id, CakeMapper.toEntity(cake))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path=ApiPaths.CAKE_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
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
