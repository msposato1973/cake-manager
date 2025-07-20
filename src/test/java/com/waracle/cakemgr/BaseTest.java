package com.waracle.cakemgr;

import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.model.CakeEntity;

public abstract class BaseTest {
    protected CakeEntity getBuildMockCakeEntity(Integer id, String title, String description, String image) {
        return CakeEntity.builder()
                .id(id)
                .title(title)
                .description(description)
                .image(image)
                .build();
    }

    protected CakeDto getBuildMockCakeDto(Integer id, String title, String description, String image) {
        return CakeDto.builder()
                .employeeId(id)
                .title(title)
                .description(description)
                .image(image)
                .build();
    }

    protected CakeDto getBuildMockCakeDto(String title, String description, String image) {
        return CakeDto.builder()
                .title(title)
                .description(description)
                .image(image)
                .build();
    }


}
