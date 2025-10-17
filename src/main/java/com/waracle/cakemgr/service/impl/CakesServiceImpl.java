package com.waracle.cakemgr.service.impl;

import com.waracle.cakemgr.dao.CakeRepository;
import com.waracle.cakemgr.exception.ResourceNotFoundException;
import com.waracle.cakemgr.model.CakeEntity;
import com.waracle.cakemgr.service.CakesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
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
        if (id == null || newCake == null) {
            return Optional.empty();
        }

        return !isExistsCake(newCake) ? cakeRepository.findById(id).map(existing -> {
            existing.setTitle(newCake.getTitle());
            existing.setDescription(newCake.getDescription());
            existing.setImage(newCake.getImage());
            return cakeRepository.save(existing);
        }) : Optional.empty();
    }

    @Override
    public boolean deleteCake(Integer id) {
        if (cakeRepository.existsById(id)) {
            cakeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void saveAll(List<CakeEntity> cakes) {
        if (cakes != null && !cakes.isEmpty()) {
            List<List<CakeEntity>> chunks = splitList(cakes, cakes.size());
            for (List<CakeEntity> chunk : chunks) {
                cakeRepository.saveAll(chunk);
                cakeRepository.flush(); // for immediate write if needed
            }
        }

    }


    private boolean isExistsCake(CakeEntity cake) {
        return cakeRepository.existsByTitleAndDescriptionAndImage(
                cake.getTitle(), cake.getDescription(), cake.getImage()
        );
    }

    // Utility method to split a list into chunks of size n
    private <T> List<List<T>> splitList(List<T> list, int chunkSize) {
        List<List<T>> chunks = new ArrayList<>();
        for (int i = 0; i < list.size(); i += chunkSize) {
            chunks.add(list.subList(i, Math.min(i + chunkSize, list.size())));
        }
        return chunks;
    }
}
