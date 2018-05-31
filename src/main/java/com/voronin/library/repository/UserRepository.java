package com.voronin.library.repository;

import com.voronin.library.domain.User;
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
public interface UserRepository extends JpaRepository<User, UUID> {

    User findUserByEmail(final String email);

    User findUserById(final UUID id);
}
