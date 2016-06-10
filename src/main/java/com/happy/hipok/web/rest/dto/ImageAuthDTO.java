package com.happy.hipok.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the ImageAuth entity.
 */
public class ImageAuthDTO implements Serializable {

    private Long id;

    private String imageAuth_url;

    private String imageAuth_thumbnail_url;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getImageAuth_url() {
        return imageAuth_url;
    }

    public void setImageAuth_url(String imageAuth_url) {
        this.imageAuth_url = imageAuth_url;
    }
    public String getImageAuth_thumbnail_url() {
        return imageAuth_thumbnail_url;
    }

    public void setImageAuth_thumbnail_url(String imageAuth_thumbnail_url) {
        this.imageAuth_thumbnail_url = imageAuth_thumbnail_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImageAuthDTO imageAuthDTO = (ImageAuthDTO) o;

        if ( ! Objects.equals(id, imageAuthDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ImageAuthDTO{" +
            "id=" + id +
            ", imageAuth_url='" + imageAuth_url + "'" +
            ", imageAuth_thumbnail_url='" + imageAuth_thumbnail_url + "'" +
            '}';
    }
}
