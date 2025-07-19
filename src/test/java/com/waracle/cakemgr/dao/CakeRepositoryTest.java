package com.waracle.cakemgr.dao;

import com.waracle.cakemgr.model.CakeEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CakeRepositoryTest {

    @Mock
    private CakeRepository cakeRepository;

    @Test
    public void testExistsByTitleAndDescriptionAndImage() {
        // Arrange
        String title = "Chocolate Cake";
        String description = "Delicious chocolate cake";
        String image = "chocolate.jpg";

        Mockito.when(cakeRepository.existsByTitleAndDescriptionAndImage(title, description, image))
                .thenReturn(true);


        boolean exists = cakeRepository.existsByTitleAndDescriptionAndImage(title, description, image);


        assertThat(exists).isTrue();
        Mockito.verify(cakeRepository).existsByTitleAndDescriptionAndImage(title, description, image);
    }

    @Test
    public void testDoesNotExistByTitleAndDescriptionAndImage() {
        // Arrange
        String title = "Vanilla Cake";
        String description = "Delicious vanilla cake";
        String image = "vanilla.jpg";

        Mockito.when(cakeRepository.existsByTitleAndDescriptionAndImage(title, description, image))
                .thenReturn(false);


        boolean exists = cakeRepository.existsByTitleAndDescriptionAndImage(title, description, image);


        assertThat(exists).isFalse();
        Mockito.verify(cakeRepository).existsByTitleAndDescriptionAndImage(title, description, image);
    }
}