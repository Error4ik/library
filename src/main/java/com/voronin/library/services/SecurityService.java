package com.voronin.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 07.05.2018.
 */
@Service
public class SecurityService {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private DetailService detailService;

    public String findLoggedUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

    public void autoLogin(final String userName, final String password) {
        UserDetails details = this.detailService.loadUserByUsername(userName);
        if (details == null) {
            throw new UsernameNotFoundException("Username or Password incorrectly");
        }

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(details, password, details.getAuthorities());
        this.manager.authenticate(token);
        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }
}
