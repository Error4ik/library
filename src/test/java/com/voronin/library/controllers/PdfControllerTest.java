package com.voronin.library.controllers;

import com.voronin.library.domain.Book;
import com.voronin.library.services.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 13.07.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PdfController.class)
public class PdfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private final Book book = new Book("name", "desc", new Timestamp(new Date().getTime()));

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenMappingBookContentByIdShouldReturnByteArray() throws Exception {
        File file = Files.createTempFile("file", "pdf").toFile();
        book.setUrl(file.getAbsolutePath());
        final UUID id = UUID.randomUUID();
        when(this.bookService.getBookById(id)).thenReturn(book);

        this.mockMvc.perform(get("/pdf/book-content/{id}", id)).andExpect(status().isOk());
    }
}
