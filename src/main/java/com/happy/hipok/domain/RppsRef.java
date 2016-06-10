package com.happy.hipok.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RppsRef.
 */
@Entity
@Table(name = "rpps_ref")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RppsRef implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private String number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RppsRef rppsRef = (RppsRef) o;
        return Objects.equals(id, rppsRef.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RppsRef{" +
            "id=" + id +
            ", number='" + number + "'" +
            '}';
    }
}
