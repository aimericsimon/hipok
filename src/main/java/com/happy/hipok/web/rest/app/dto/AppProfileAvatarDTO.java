package com.happy.hipok.web.rest.app.dto;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A profile avatar DTO
 */
public class AppProfileAvatarDTO implements Serializable {

    @NotNull(message="{com.happy.hipok.user.notnullid}")
    private Long id;


    @Lob
    @NotNull(message="{com.happy.hipok.image.nullid}")
    @Size(min = 0, max = 5000000)
    private byte[] imageBinary;

    private String avatarUrl;

    private String avatarThumbnailUrl;

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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarThumbnailUrl() {
        return avatarThumbnailUrl;
    }

    public void setAvatarThumbnailUrl(String avatarThumbnailUrl) {
        this.avatarThumbnailUrl = avatarThumbnailUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppProfileAvatarDTO appAvatarDTO = (AppProfileAvatarDTO) o;

        if ( ! Objects.equals(id, appAvatarDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AppAvatarDTO{id=" + id + '}';
    }
}
