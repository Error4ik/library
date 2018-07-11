package com.voronin.library.services;

import com.voronin.library.domain.Role;
import com.voronin.library.domain.User;
import com.voronin.library.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private RoleService roleService;

    @Autowired
    private UserService userService;

    private final User user = new User("test@test.ru", "password");

    @Before
    public void init() {
        Role role = new Role();
        role.setRole("user");
        user.setRoles(Lists.newArrayList(role));
    }

    @Test
    public void whenSaveUserShouldReturnUser() {
        when(userRepository.save(user)).thenReturn(user);

        assertThat(this.userService.save(user), is(user));
    }

    @Test
    public void whenFindUserByEmailShouldReturnUser() {
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);

        assertThat(this.userService.findUserByEmail(user.getEmail()), is(user));
    }

    @Test
    public void whenGetUsersShouldReturnUsers() {
        List<User> users = new ArrayList<>(Lists.newArrayList(user));
        when(userRepository.findAll()).thenReturn(users);

        assertThat(this.userService.getUsers(), is(users));
    }

    @Test
    public void whenGetUserByIdShouldReturnUser() {
        when(userRepository.findUserById(user.getId())).thenReturn(user);

        assertThat(this.userService.getUserById(user.getId()), is(user));
    }

    @Test
    public void whenRegUserShouldSaveUserAndAutologin() {
        Role role = new Role();
        role.setRole("user");
        when(roleService.findRoleByName("user")).thenReturn(role);
        UserService service = Mockito.spy(userService);
        service.regUser(user);
        verify(service, atLeast(1)).regUser(user);
    }
}
