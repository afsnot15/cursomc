package com.afonso.cursomc.services;

import com.afonso.cursomc.services.exception.FileException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author afonso.gomes
 */
@Service
public class ImageService {

    public BufferedImage getJpgImageFile(MultipartFile uploadFile) {
        try {
            String ext = FilenameUtils.getExtension(uploadFile.getOriginalFilename());

            if (!"png".equals(ext.toLowerCase()) && !"jpg".equals(ext.toLowerCase())) {
                throw new FileException("Somente imagens PNG e JPG são permitidas");
            }

            BufferedImage img = ImageIO.read(uploadFile.getInputStream());
            if ("png".equals(ext.toLowerCase())) {
                img = pngToJpj(img);
            }

            return img;

        } catch (IOException ex) {
            throw new FileException("Erro ao ler arquivo");
        }
    }

    private BufferedImage pngToJpj(BufferedImage img) {
        BufferedImage jpgImage = new BufferedImage(
                img.getWidth(),
                img.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);

        return jpgImage;
    }

    public InputStream getInputStream(BufferedImage img, String extension) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, extension, os);
            return new ByteArrayInputStream(os.toByteArray());

        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
    }

    public BufferedImage cropSquare(BufferedImage sourceImg) {
        int min = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight() : sourceImg.getWidth();
        return Scalr.crop(
                sourceImg,
                (sourceImg.getWidth() / 2) - (min / 2),
                (sourceImg.getHeight() / 2) - (min / 2),
                min,
                min);
    }

    public BufferedImage resize(BufferedImage sourceImg, int size) {
        return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
    }
}
