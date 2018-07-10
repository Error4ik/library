package com.voronin.library.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 22.05.2018.
 */
@Component
public class CropTheFile {

    @Value("${target.width.from.image}")
    private int targetWidth;

    @Value("${target.image.height}")
    private int targetHeight;

    public BufferedImage crop(final MultipartFile cover) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(cover.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image == null ? null : resizeImage(image);
    }

    private BufferedImage resizeImage(final BufferedImage image) {
        final BufferedImage bufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();

        return bufferedImage;
    }
}
