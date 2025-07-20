package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.api.*;
import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.mapper.CakeMapper;
import com.waracle.cakemgr.model.CakeEntity;
import com.waracle.cakemgr.service.CakesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(ApiPaths.BASE)
@Tag(name = "Cakes", description = "Cake management APIs")
public class CakesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CakesController.class);

    private final CakesService cakesService;

    @Autowired
    public CakesController(CakesService cakeService) {
        this.cakesService = cakeService;
    }


    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all cakes", description = "Returns the list of all cakes available in the system.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of cakes found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CakeDto.class))),
            @ApiResponse(responseCode = "204", description = "No cakes available"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary = "Create a new cake", description = "Adds a new cake to the system.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cake created successfully",
                    content = @Content(schema = @Schema(implementation = CakeEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CakeEntity> create(@RequestBody CakeDto cake) {
        CakeEntity saved = cakesService.createCake(CakeMapper.toEntity(cake));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping(path = ApiPaths.CAKE_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get cake by ID", description = "Retrieve a cake by its ID.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cake found",
                    content = @Content(schema = @Schema(implementation = CakeEntity.class))),
            @ApiResponse(responseCode = "404", description = "Cake not found")
    })
    public ResponseEntity<CakeEntity> getCakeById(@PathVariable Integer id) {
        return this.cakesService.getCakeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = ApiPaths.CAKE_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update cake by ID", description = "Update an existing cake with the given ID.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cake updated successfully",
                    content = @Content(schema = @Schema(implementation = CakeEntity.class))),
            @ApiResponse(responseCode = "404", description = "Cake not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<CakeEntity> update(@PathVariable Integer id, @RequestBody CakeDto cake) {
        return cakesService.updateCake(id, CakeMapper.toEntity(cake))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = ApiPaths.CAKE_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete cake by ID", description = "Delete the cake with the specified ID.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cake deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Cake not found")
    })
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (cakesService.deleteCake(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private <T> ResponseEntity<?> buildResponseEntity(List<T> entityDTOs) {
        if (entityDTOs.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(entityDTOs);
    }
}
