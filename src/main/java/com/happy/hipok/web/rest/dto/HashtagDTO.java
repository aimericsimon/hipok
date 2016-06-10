package com.happy.hipok.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Hashtag entity.
 */
public class HashtagDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String label;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HashtagDTO hashtagDTO = (HashtagDTO) o;

        if ( ! Objects.equals(id, hashtagDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HashtagDTO{" +
            "id=" + id +
            ", label='" + label + "'" +
            '}';
    }
}
