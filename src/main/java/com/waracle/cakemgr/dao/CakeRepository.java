package com.waracle.cakemgr.dao;

import com.waracle.cakemgr.model.CakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CakeRepository extends JpaRepository<CakeEntity, Integer> {
    boolean existsByTitleAndDescriptionAndImage(String title, String description, String image);
}
