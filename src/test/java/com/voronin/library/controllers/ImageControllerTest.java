package com.voronin.library.controllers;

import com.voronin.library.domain.Image;
import com.voronin.library.repository.*;
import com.voronin.library.services.ImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 13.07.2018.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@WebMvcTest(ImageController.class)
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @MockBean
    private Image image;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private ImageRepository imageRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private RatingRepository ratingRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenMappingImageByIdShouldReturnByteArray() throws Exception {
        final UUID id = UUID.randomUUID();
        File file = Files.createTempFile("file", ".png").toFile();
        FileInputStream fis = new FileInputStream(file);

        when(this.imageRepository.getImageById(id)).thenReturn(image);
        when(this.imageService.getImageById(id)).thenReturn(image);
        when(this.image.getUrl()).thenReturn(file.getAbsolutePath());
        whenNew(File.class).withAnyArguments().thenReturn(file);
        whenNew(FileInputStream.class).withAnyArguments().thenReturn(fis);

        this.mockMvc.perform(get("/image/{id}", id))
                .andExpect(status().isOk());

        verify(imageService, times(1)).getImageById(id);
    }

}