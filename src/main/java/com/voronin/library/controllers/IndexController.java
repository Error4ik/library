package com.voronin.library.controllers;

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

    @RequestMapping("/")
    public ModelAndView getMainPage() {
        final ModelAndView view = new ModelAndView("index");
        view.addObject("genres", this.genreService.findAll());
        return view;
    }
}
