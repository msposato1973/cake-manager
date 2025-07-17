package com.waracle.cakemgr.service.impl;

import com.waracle.cakemgr.dao.CakeRepository;
import com.waracle.cakemgr.model.CakeEntity;
import com.waracle.cakemgr.service.CakesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CakesServiceImpl implements CakesService {

    private final CakeRepository cakeRepository;
    public CakesServiceImpl(CakeRepository cakeRepository) {
        this.cakeRepository = cakeRepository;
    }

    @Override
    public List<CakeEntity> getAllCakes() {
        return cakeRepository.findAll();
    }

    @Override
    public Optional<CakeEntity> getCakeById(Integer id) {
        return cakeRepository.findById(id);
    }

    @Override
    public CakeEntity createCake(CakeEntity cake) {
        return cakeRepository.save(cake);
    }

    @Override
    public Optional<CakeEntity> updateCake(Integer id, CakeEntity newCake) {
        return cakeRepository.findById(id).map(existing -> {
            existing.setTitle(newCake.getTitle());
            existing.setDescription(newCake.getDescription());
            existing.setImage(newCake.getImage());
            return cakeRepository.save(existing);
        });
    }

    @Override
    public boolean deleteCake(Integer id) {
        if (cakeRepository.existsById(id)) {
            cakeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
