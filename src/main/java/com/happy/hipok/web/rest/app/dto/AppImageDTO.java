package com.happy.hipok.web.rest.app.dto;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Dahwoud on 15/03/2016.
 */
public class AppImageDTO implements Serializable{

    private Long id;

    @Lob
    @NotNull(message="{com.happy.hipok.image.nullid}")
    @Size(min = 0, max = 5000000)
    private byte[] imageBinary;

    private String imageUrl;

    private String imageThumbnailUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImageBinary() {
        return imageBinary;
    }

    public void setImageBinary(byte[] imageBinary) {
        this.imageBinary = imageBinary;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }

    public void setImageThumbnailUrl(String imageThumbnailUrl) {
        this.imageThumbnailUrl = imageThumbnailUrl;
    }
}
