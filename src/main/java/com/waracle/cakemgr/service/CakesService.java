package com.waracle.cakemgr.service;

import com.waracle.cakemgr.model.CakeEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CakesService {

    List<CakeEntity> getAllCakes();

    Optional<CakeEntity> getCakeById(Integer id);

    CakeEntity createCake(CakeEntity cake);

    Optional<CakeEntity> updateCake(Integer id, CakeEntity newCake);

    boolean  deleteCake(Integer id);

    void saveAll(List<CakeEntity> listCakes);
}