package com.voronin.library.services;

import com.voronin.library.domain.Role;
import com.voronin.library.domain.User;
import com.voronin.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 07.05.2018.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityService securityService;

    public User save(final User user) {
        return this.userRepository.save(user);
    }

    public User findUserByEmail(final String email) {
        return this.userRepository.findUserByEmail(email);
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User getUserById(final UUID id) {
        return this.userRepository.findUserById(id);
    }

    public void regUser(final User user) {
        final String pass = user.getPassword();
        user.setPassword(encoder.encode(pass));
        List<Role> roles = new ArrayList<>();
        roles.add(this.roleService.findRoleByName("user"));
        user.setRoles(roles);
        this.save(user);
        this.securityService.autoLogin(user.getEmail(), pass);
    }
}
