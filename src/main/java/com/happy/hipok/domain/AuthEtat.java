package com.happy.hipok.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AuthEtat.
 */
@Entity
@Table(name = "auth_etat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuthEtat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "id_profile", nullable = false)
    private Long idProfile;

    @NotNull
    @Column(name = "id_image_auth", nullable = false)
    private Long idImageAuth;

    @Column(name = "etat")
    private Boolean etat;

    @OneToOne
    @JoinColumn(unique = true)
    private Profile profile;

    @OneToOne
    @JoinColumn(unique = true)
    private ImageAuth imageAuth;

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

    public Boolean isEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public ImageAuth getImageAuth() {
        return imageAuth;
    }

    public void setImageAuth(ImageAuth imageAuth) {
        this.imageAuth = imageAuth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthEtat authEtat = (AuthEtat) o;
        if(authEtat.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, authEtat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AuthEtat{" +
            "id=" + id +
            ", idProfile='" + idProfile + "'" +
            ", idImageAuth='" + idImageAuth + "'" +
            ", etat='" + etat + "'" +
            '}';
    }
}
