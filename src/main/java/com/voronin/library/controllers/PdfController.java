package com.voronin.library.controllers;

import com.voronin.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 01.06.2018.
 */
@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/book-content/{id}")
    public byte[] getFile(@PathVariable final UUID id) throws IOException {
        return Files.readAllBytes(Paths.get(this.bookService.getBookById(id).getUrl()));
    }
}
