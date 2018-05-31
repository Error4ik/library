package com.voronin.library.services;

import com.voronin.library.domain.Role;
import com.voronin.library.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 08.05.2018.
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getRoles() {
        return this.roleRepository.findAll();
    }

    public Role getRoleById(final UUID id) {
        return this.roleRepository.findRoleById(id);
    }

    public Role findRoleByName(final String name) {
        return this.roleRepository.findRoleByRole(name);
    }
}
