package com.voronin.library.controllers;

import com.voronin.library.services.AuthorService;
import com.voronin.library.services.BookService;
import com.voronin.library.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 07.05.2018.
 */
@RestController
public class IndexController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @RequestMapping("/")
    public ModelAndView getMainPage() {
        final ModelAndView view = new ModelAndView("index");
        view.addObject("genres", this.genreService.findAll());
        view.addObject("authors", this.authorService.getAuthors());
        return view;
    }
}
