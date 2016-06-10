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
 * A SpecialtyRef.
 */
@Entity
@Table(name = "specialty_ref")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SpecialtyRef implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "label")
    private String label;

    @OneToMany(mappedBy = "specialtyRef")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Publication> publications = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "medical_type_ref_id")
    private MedicalTypeRef medicalTypeRef;

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

    public Set<Publication> getPublications() {
        return publications;
    }

    public void setPublications(Set<Publication> publications) {
        this.publications = publications;
    }

    public MedicalTypeRef getMedicalTypeRef() {
        return medicalTypeRef;
    }

    public void setMedicalTypeRef(MedicalTypeRef medicalTypeRef) {
        this.medicalTypeRef = medicalTypeRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SpecialtyRef specialtyRef = (SpecialtyRef) o;
        return Objects.equals(id, specialtyRef.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SpecialtyRef{" +
            "id=" + id +
            ", label='" + label + "'" +
            '}';
    }
}
