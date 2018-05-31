package com.voronin.library.controllers;

import com.voronin.library.domain.User;
import com.voronin.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 07.05.2018.
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(final User user) {
        ModelAndView view = new ModelAndView();
        User u = this.userService.findUserByEmail(user.getEmail());
        if (u != null) {
            view.setViewName("login");
            view.addObject("error", true);
            return view;
        }
        view.setViewName("redirect:/");
        this.userService.regUser(user);
        return view;
    }
}
