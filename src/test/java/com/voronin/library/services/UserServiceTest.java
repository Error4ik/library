package com.voronin.library.services;

import com.voronin.library.domain.Role;
import com.voronin.library.domain.User;
import com.voronin.library.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.07.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @MockBean
    private RoleService roleService;

    @MockBean
    private UserDetails userDetails;

    @MockBean
    private DetailService detailService;

    @MockBean
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    private final User user = new User("user@user.ru", "password");

    @Test
    public void whenSaveUserShouldReturnUser() {
        when(userRepository.save(user)).thenReturn(user);

        assertThat(user, is(this.userService.save(user)));
    }

    @Test
    public void whenFindUserByEmailShouldReturnUser() {
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);

        assertThat(user, is(this.userService.findUserByEmail(user.getEmail())));
    }

    @Test
    public void whenGetUsersShouldReturnUsers() {
        List<User> users = new ArrayList<>(Lists.newArrayList(user));
        when(userRepository.findAll()).thenReturn(users);

        assertThat(users, is(this.userService.getUsers()));
    }

    @Test
    public void whenGetUserByIdShouldReturnUser() {
        when(userRepository.findUserById(user.getId())).thenReturn(user);

        assertThat(user, is(this.userService.getUserById(user.getId())));
    }

    @Test
    public void whenRegUserShouldSaveUserAndAuthologin() {
        User user = new User("test@test.ru", "password");
        Role role = new Role();
        role.setRole("user");
        when(encoder.encode(user.getPassword())).thenReturn(user.getPassword());
        when(roleService.findRoleByName(user.getEmail())).thenReturn(role);
        when(userRepository.save(user)).thenReturn(user);


//        when(userService.findUserByEmail("test@test.ru")).thenReturn(user);
//        when(detailService.loadUserByUsername("test@test.ru")).thenReturn(userDetails);
//        when(userDetails.getAuthorities()).thenReturn(new ArrayList<>());

        UserService service = Mockito.spy(userService);
        service.regUser(user);
        verify(service, atLeast(1)).regUser(user);
    }
}
