package com.voronin.library.util;

import com.voronin.library.domain.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 22.05.2018.
 */
@Component
public class WriteFileToDisk {

    private final String fileSeparator = System.getProperty("file.separator");

    @Value("${upload.image.folder}")
    private String pathToSaveImage;

    @Value("${upload.books.folder}")
    private String pathToSaveBooks;

    @Value("${file.extension.to.save}")
    private String fileExtension;

    @Value("${default.image.name}")
    private String imageName;

    public File writeImage(final String imageName, final BufferedImage image) {
        File dir = new File(pathToSaveImage);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String path = imageName.equalsIgnoreCase(this.imageName) ?
                String.format("%s%s%s", dir, fileSeparator, this.imageName) :
                String.format("%s%s%s.%s", dir, fileSeparator, System.currentTimeMillis(), fileExtension);

        File file = new File(path);
        try {
            ImageIO.write(image, fileExtension, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public File writeBook(final MultipartFile multipartFile, final Book book) {
        File dir = new File(pathToSaveBooks + book.getGenres().iterator().next().getGenre());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir.getAbsolutePath() + fileSeparator + multipartFile.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
