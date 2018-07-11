package com.voronin.library.services;

import com.voronin.library.domain.Image;
import com.voronin.library.repository.ImageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 11.07.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageServiceTest {

    @MockBean
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    private final Image image = new Image();

    @Before
    public void init() {
        this.image.setName("image");
        this.image.setUrl("url");
        this.image.setId(UUID.randomUUID());
    }

    @Test
    public void whenSaveImageShouldReturnImage() {
        when(imageRepository.save(image)).thenReturn(image);

        assertThat(this.imageService.save(image), is(image));
    }

    @Test
    public void whenGetImageByIdShouldReturnImage() {
        when(imageRepository.getImageById(image.getId())).thenReturn(image);

        assertThat(this.imageService.getImageById(image.getId()), is(image));
    }
}
