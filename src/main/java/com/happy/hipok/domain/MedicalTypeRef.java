package com.happy.hipok.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.happy.hipok.domain.enumeration.MedicalSubType;

/**
 * A MedicalTypeRef.
 */
@Entity
@Table(name = "medical_type_ref")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MedicalTypeRef implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "subtype")
    private MedicalSubType subtype;

    @Column(name = "label")
    private String label;

    @OneToMany(mappedBy = "medicalTypeRef")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SpecialtyRef> specialtyRefs = new HashSet<>();

    @OneToMany(mappedBy = "medicalTypeRef")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExtendedUser> extendedUsers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MedicalSubType getSubtype() {
        return subtype;
    }

    public void setSubtype(MedicalSubType subtype) {
        this.subtype = subtype;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<SpecialtyRef> getSpecialtyRefs() {
        return specialtyRefs;
    }

    public void setSpecialtyRefs(Set<SpecialtyRef> specialtyRefs) {
        this.specialtyRefs = specialtyRefs;
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
        MedicalTypeRef medicalTypeRef = (MedicalTypeRef) o;
        return Objects.equals(id, medicalTypeRef.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MedicalTypeRef{" +
            "id=" + id +
            ", subtype='" + subtype + "'" +
            ", label='" + label + "'" +
            '}';
    }
}
