package com.voronin.library.services;

import com.voronin.library.domain.Role;
import com.voronin.library.repository.RoleRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
public class RoleServiceTest {

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    private final Role role = new Role();

    @Before
    public void init() {
        this.role.setRole("user");
        this.role.setId(UUID.randomUUID());
    }

    @Test
    public void whenGetRolesShouldReturnRoles() {
        List<Role> roles = new ArrayList<>(Lists.newArrayList(role));
        when(roleRepository.findAll()).thenReturn(roles);

        assertThat(this.roleService.getRoles(), is(roles));
    }

    @Test
    public void whenGetRoleByIdShouldReturnRole() {
        when(roleRepository.findRoleById(role.getId())).thenReturn(role);

        assertThat(this.roleService.getRoleById(role.getId()), is(role));
    }

    @Test
    public void whenGetRoleByNameShouldReturnRole() {
        when(roleRepository.findRoleByRole(role.getRole())).thenReturn(role);

        assertThat(this.roleService.findRoleByName(role.getRole()), is(role));
    }
}
