package com.happy.hipok.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Declaration.
 */
@Entity
@Table(name = "declaration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Declaration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 3000)
    @Column(name = "description", length = 3000)
    private String description;

    @Column(name = "declaration_date")
    private ZonedDateTime declarationDate;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "declaration_type_ref_id")
    private DeclarationTypeRef declarationTypeRef;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDeclarationDate() {
        return declarationDate;
    }

    public void setDeclarationDate(ZonedDateTime declarationDate) {
        this.declarationDate = declarationDate;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public DeclarationTypeRef getDeclarationTypeRef() {
        return declarationTypeRef;
    }

    public void setDeclarationTypeRef(DeclarationTypeRef declarationTypeRef) {
        this.declarationTypeRef = declarationTypeRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Declaration declaration = (Declaration) o;
        return Objects.equals(id, declaration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Declaration{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", declarationDate='" + declarationDate + "'" +
            '}';
    }
}
