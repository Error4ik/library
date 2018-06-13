package com.voronin.library.services;

import com.voronin.library.domain.Image;
import com.voronin.library.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 18.05.2018.
 */
@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image save(final Image image) {
        return this.imageRepository.save(image);
    }

    public Image getImageById(final UUID id) {
        return this.imageRepository.getImageById(id);
    }

    public Image getImageByNameLike(final String name) {
        return this.imageRepository.getImageByNameLike(name);
    }
}
