package com.voronin.library.controllers;

import com.voronin.library.services.GenreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 13.07.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(IndexController.class)
public class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenMappingIndexShouldReturnIndexView() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status()
                        .isOk())
                .andExpect(view()
                        .name("index"));

        verify(this.genreService, times(1)).findAll();
    }
}