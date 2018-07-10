package com.voronin.library.services;

import com.voronin.library.domain.Role;
import com.voronin.library.domain.User;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.07.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DetailServiceTest {

    @MockBean
    private UserService userService;

    @Autowired
    private DetailService detailService;

    @Test(expected = UsernameNotFoundException.class)
    public void whenUserEqualsNullShouldThrowException() {
        when(userService.findUserByEmail("test")).thenReturn(null);

        detailService.loadUserByUsername("test");
    }

    @Test
    public void whenValidUserShouldReturnUser() {
        User user = new User("test@test.ru", "password");
        Role role = new Role();
        role.setRole("user");
        user.setRoles(Lists.newArrayList(role));
        when(userService.findUserByEmail("test@test.ru")).thenReturn(user);

        UserDetails userDetails = detailService.loadUserByUsername("test@test.ru");

        assertThat(user.getEmail(), is(userDetails.getUsername()));
        assertThat(user.getPassword(), is(userDetails.getPassword()));
    }
}
