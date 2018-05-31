package com.voronin.library.controllers;

import com.voronin.library.domain.Role;
import com.voronin.library.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 08.05.2018.
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/roles")
    public List<Role> getRoles() {
        return this.roleService.getRoles();
    }

    @RequestMapping("/role/{id}")
    public Role findRoleBuId(@PathVariable final UUID id) {
        return this.roleService.getRoleById(id);
    }
}
