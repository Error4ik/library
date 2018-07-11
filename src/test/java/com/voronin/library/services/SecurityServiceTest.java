package com.voronin.library.services;

import com.voronin.library.domain.Role;
import com.voronin.library.domain.User;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.07.2018.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityServiceTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private DetailService detailService;

    @MockBean
    private SecurityContext securityContext;

    @MockBean
    private Authentication authentication;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetails userDetails;

    @Autowired
    private SecurityService securityService;

    private final User user = new User("test@test.ru", "password");

    @Before
    public void init() {
        Role role = new Role();
        role.setRole("user");
        user.setRoles(Lists.newArrayList(role));
    }

    @Test
    public void whenFindLoggedUserShouldReturnUserName() {
        org.springframework.security.core.userdetails.User u =
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(), user.getPassword(), new ArrayList<>());

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(u);

        assertThat(this.securityService.findLoggedUser(), is(user.getEmail()));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void whenUserDetailsEqualsNullShouldThrowException() {
        when(userService.findUserByEmail("test@test.ru")).thenReturn(user);
        when(detailService.loadUserByUsername("test")).thenReturn(null);

        this.securityService.autoLogin("test@test.ru", "password");
    }

    @Test
    public void whenUserDetailsNotEqualsNullShouldTokenIsAuthenticate() {
        when(userService.findUserByEmail("test@test.ru")).thenReturn(user);
        when(detailService.loadUserByUsername("test@test.ru")).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(new ArrayList<>());

        SecurityService securityServiceSpy = Mockito.spy(securityService);
        securityServiceSpy.autoLogin("test@test.ru", "password");
        verify(securityServiceSpy, atLeast(1)).autoLogin("test@test.ru", "password");
    }
}
