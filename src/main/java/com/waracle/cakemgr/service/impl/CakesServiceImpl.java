package com.waracle.cakemgr.service.impl;

import com.waracle.cakemgr.model.CakeEntity;
import com.waracle.cakemgr.service.CakesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CakesServiceImpl implements CakesService {

    @Override
    public List<CakeEntity> getAllCakes() {
        return null;
    }

    @Override
    public Optional<CakeEntity> getCakeById(Integer id) {
        return Optional.empty();
    }

    @Override
    public CakeEntity saveCake(CakeEntity cake) {
        return null;
    }

    @Override
    public void deleteCake(Integer id) {

    }
}
