package com.voronin.library.repository;

import com.voronin.library.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 07.05.2018.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findRoleById(final UUID id);
    Role findRoleByRole(final String name);
}
