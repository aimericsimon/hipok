package com.happy.hipok.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ImageAuth.
 */
@Entity
@Table(name = "image_auth")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ImageAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "image_auth_url")
    private String imageAuth_url;

    @Column(name = "image_auth_thumbnail_url")
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
        ImageAuth imageAuth = (ImageAuth) o;
        if(imageAuth.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, imageAuth.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ImageAuth{" +
            "id=" + id +
            ", imageAuth_url='" + imageAuth_url + "'" +
            ", imageAuth_thumbnail_url='" + imageAuth_thumbnail_url + "'" +
            '}';
    }
}
