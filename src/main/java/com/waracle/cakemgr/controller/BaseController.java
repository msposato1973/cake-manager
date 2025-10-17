package com.waracle.cakemgr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

abstract class BaseController {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Costruisce una ResponseEntity che può gestire sia un singolo oggetto che una lista.
     * - Se l'input è null → 204 No Content
     * - Se è una lista vuota → 204 No Content
     * - Se è una lista con 1 elemento → ritorna quell’elemento
     * - Se è una lista con più elementi → ritorna la lista
     * - Se è un singolo oggetto → ritorna l’oggetto
     */
    /**
     * Crea una ResponseEntity a partire da un oggetto o una lista di oggetti.
     *
     * @param data oggetto singolo o lista di oggetti
     * @return ResponseEntity con contenuto e status appropriati
     */
    protected <T> ResponseEntity<?> buildResponseEntity(Object data) {
        if (data == null) {
            LOGGER.debug("No content: data is null");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        if (data instanceof List<?> list) {
            if (list.isEmpty()) {
                LOGGER.debug("No content: list is empty");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else if (list.size() == 1) {
                LOGGER.debug("Returning single element from list");
                return ResponseEntity.ok(list.get(0));
            } else {
                LOGGER.info("Returning list with {} items", list.size());
                return ResponseEntity.ok(list);
            }
        }

        LOGGER.debug("Returning single object of type {}", data.getClass().getSimpleName());
        return ResponseEntity.ok(data);
    }


}
