package com.voronin.library.repository;

import com.voronin.library.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 18.05.2018.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
    Image getImageById(final UUID id);
}
