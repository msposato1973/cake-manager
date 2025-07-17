package com.waracle.cakemgr.service;

import com.waracle.cakemgr.model.CakeEntity;

import java.util.List;
import java.util.Optional;

public interface CakesService {

    List<CakeEntity> getAllCakes();

    Optional<CakeEntity> getCakeById(Integer id);

    CakeEntity saveCake(CakeEntity cake);

    void deleteCake(Integer id);
}