package com.happy.hipok.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TitleRef.
 */
@Entity
@Table(name = "title_ref")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TitleRef implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "titleRef")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExtendedUser> extendedUsers = new HashSet<>();

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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<ExtendedUser> getExtendedUsers() {
        return extendedUsers;
    }

    public void setExtendedUsers(Set<ExtendedUser> extendedUsers) {
        this.extendedUsers = extendedUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TitleRef titleRef = (TitleRef) o;
        return Objects.equals(id, titleRef.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TitleRef{" +
            "id=" + id +
            ", label='" + label + "'" +
            ", abbreviation='" + abbreviation + "'" +
            ", code='" + code + "'" +
            '}';
    }
}
