package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.api.*;
import com.waracle.cakemgr.dto.CakeDto;
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


    @GetMapping(ApiPaths.CAKES)
    public ResponseEntity<CakeDto> findAll() {
        LOGGER.info("START - findAll {..}");
        return null;
    }

    @GetMapping(ApiPaths.CAKE_BY_ID)
    public ResponseEntity<CakeDto> getCakeById(@PathVariable Integer id) {
       return null;
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
     * @param antityDTO
     * @return
     */
    private <T> ResponseEntity<?> buildResponseEntity(List<T> antityDTO) {
        if (antityDTO.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return ResponseEntity.ok(antityDTO);
    }
}
