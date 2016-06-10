package com.happy.hipok.service;

import com.happy.hipok.web.rest.errors.CustomParameterizedException;
import com.happy.hipok.web.rest.errors.TechnicalException;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.imgscalr.Scalr.*;


/**
 * Takes a byte array, transforms the image to make a thumbnail and persist both on the filesystem.
 */
@Service
@Component
public class ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    public static final int TARGET_SIZE = 200;

    private static final List<String> AUTHORIZED_MIME_TYPES = Arrays.asList("image/png", "image/jpeg");

    @Inject
    ServletContext servletContext;

    @Value("${upload.directory}")
    private String uploadDirectory;

    /**
     *
     * @param imageName
     * @return
     */
    private String getImagePath(String imageName){
        String imagePath = uploadDirectory+imageName;
        return imagePath;
    }

    /**
     *
     * @param imageName
     */
    public void deleteImage(String imageName){
        String imagePath = getImagePath(imageName);

        log.debug("Delete image "+imagePath);

        File file = new File(imagePath);
        file.delete();
    }

    /**
     * Saves image and thumbnail on the file system
     *
     * @param image Byte array of the image
     * @return array of names with full image name  and thumbnail image name
     */
    public String[] persistFullImageAndThumbnail(byte[] image) {
        try {

            String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(image));

            if ( mimeType == null || !AUTHORIZED_MIME_TYPES.contains(mimeType)) {
                throw new CustomParameterizedException("Image is not valid.");
            }

            String saveExtension = mimeType.split("/")[1];
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));

            String fileName = UUID.randomUUID().toString();

            String fullImageName = fileName + "." + saveExtension;
            writeImageToFileSystem(bufferedImage, fullImageName, saveExtension);

            BufferedImage resizedImage = resizeCroppedSquareImage(bufferedImage, TARGET_SIZE, saveExtension);
            String thumbnailName = fileName + "-sm." + saveExtension;
            writeImageToFileSystem(resizedImage,  thumbnailName, saveExtension);

            return new String[]{fullImageName, thumbnailName};
        } catch (IOException e) {
            String message = "Impossible to save image.";
            log.error(message, e);
            throw new TechnicalException(message, e);
        }
    }

    private BufferedImage resizeCroppedSquareImage(BufferedImage origImage, Integer targetSize, String saveExtension) {

        BufferedImage resizedImage = getCroppedSquareImage(origImage);

        // If square image is less than target size, we pad with transparent background if it is png, with white
        // background if it is jpeg
        int resizedImageWidth = resizedImage.getWidth();
        if (resizedImageWidth < targetSize) {
            int padding = (targetSize - resizedImageWidth) / 2;
            if ("jpeg".equals(saveExtension)) {
                resizedImage = pad(resizedImage, padding, Color.WHITE);
            } else if ("png".equals(saveExtension)) {
                resizedImage = pad(resizedImage, padding, new Color(1f, 0f, 0f, 0f));
            }
        }

        if (resizedImageWidth != targetSize) {
            resizedImage = resize(resizedImage, Scalr.Method.ULTRA_QUALITY, targetSize);
        }

        return resizedImage;
    }

    /**
     * Get a squared image. If the image was not a square, the proportions are kept but the image is cropped.
     *
     * @param origImage buffered image
     * @return square cropped image
     */
    private BufferedImage getCroppedSquareImage(BufferedImage origImage) {
        BufferedImage resizedImage = origImage;

        // Si width > height (paysage), on croppe la largeur pour obtenir la même dimension que la hauteur
        int height = origImage.getHeight();
        int width = origImage.getWidth();

        int cropSize = (int) Math.floor((width - height) / 2);

        if (width > height) {
            resizedImage = crop(origImage, cropSize, 0, height, height);
        } else if (height > width) {
            resizedImage = crop(origImage, 0, -cropSize, width, width);
        }

        return resizedImage;
    }

    /**
     * Write a buffered image on the filesystem
     *
     * @param bufferedImage buffered image
     * @param imageName name of the image
     * @throws IOException
     */
    private void writeImageToFileSystem(BufferedImage bufferedImage, String imageName, String saveExtension) throws IOException {
        String imagePath = getImagePath(imageName);

        log.debug("Save image to "+imagePath);

        FileOutputStream outputStream = new FileOutputStream(imagePath);
        ImageIO.write(bufferedImage, saveExtension, outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
