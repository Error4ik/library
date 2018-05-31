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

    @Value("${upload.folder}")
    private String saveToPath;

    @Value("${file.extension.to.save}")
    private String fileExtension;

    public File writeImage(final BufferedImage image) {
        File dir = new File(saveToPath + fileSeparator + "book cover");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir + fileSeparator + System.currentTimeMillis() + "." + fileExtension);
        try {
            ImageIO.write(image, fileExtension, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    public File writeBook(final MultipartFile multipartFile, final Book book) {
        File dir = new File(
                saveToPath + fileSeparator + "books" + fileSeparator +
                        book.getGenres().iterator().next().getGenre());
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
