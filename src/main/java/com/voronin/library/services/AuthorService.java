package com.voronin.library.services;

import com.voronin.library.domain.Author;
import com.voronin.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 23.05.2018.
 */
@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author save(final Author author) {
        return this.authorRepository.save(author);
    }

    public Author findAuthorByName(final String name) {
        return this.authorRepository.findByName(name);
    }

    public List<Author> getAuthors() {
        return this.authorRepository.findAll();
    }

    public Author getAuthorById(final UUID id) {
        return this.authorRepository.getById(id);
    }
}
