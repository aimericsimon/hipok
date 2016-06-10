package com.happy.hipok.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the AuthEtat entity.
 */
public class AuthEtatDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idProfile;

    @NotNull
    private Long idImageAuth;

    private Boolean etat;


    private Long profileId;
    
    private Long imageAuthId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(Long idProfile) {
        this.idProfile = idProfile;
    }
    public Long getIdImageAuth() {
        return idImageAuth;
    }

    public void setIdImageAuth(Long idImageAuth) {
        this.idImageAuth = idImageAuth;
    }
    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getImageAuthId() {
        return imageAuthId;
    }

    public void setImageAuthId(Long imageAuthId) {
        this.imageAuthId = imageAuthId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthEtatDTO authEtatDTO = (AuthEtatDTO) o;

        if ( ! Objects.equals(id, authEtatDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AuthEtatDTO{" +
            "id=" + id +
            ", idProfile='" + idProfile + "'" +
            ", idImageAuth='" + idImageAuth + "'" +
            ", etat='" + etat + "'" +
            '}';
    }
}
