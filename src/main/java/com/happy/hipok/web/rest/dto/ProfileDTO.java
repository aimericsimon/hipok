package com.happy.hipok.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Profile entity.
 */
public class ProfileDTO implements Serializable {

    private Long id;

    private String avatarUrl;


    @Size(max = 1000)
    private String description;


    private String avatarThumbnailUrl;


    private Long extendedUserId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getAvatarThumbnailUrl() {
        return avatarThumbnailUrl;
    }

    public void setAvatarThumbnailUrl(String avatarThumbnailUrl) {
        this.avatarThumbnailUrl = avatarThumbnailUrl;
    }

    public Long getExtendedUserId() {
        return extendedUserId;
    }

    public void setExtendedUserId(Long extendedUserId) {
        this.extendedUserId = extendedUserId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfileDTO profileDTO = (ProfileDTO) o;

        if ( ! Objects.equals(id, profileDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
            "id=" + id +
            ", avatarUrl='" + avatarUrl + "'" +
            ", description='" + description + "'" +
            ", avatarThumbnailUrl='" + avatarThumbnailUrl + "'" +
            '}';
    }
}
