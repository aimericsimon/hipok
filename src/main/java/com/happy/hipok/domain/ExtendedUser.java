package com.happy.hipok.domain;

import com.happy.hipok.domain.enumeration.Sex;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ExtendedUser.
 */
@Entity
@Table(name = "extended_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExtendedUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Size(max = 200)
    @Column(name = "practice_location", length = 200)
    private String practiceLocation;

    @Column(name = "adeli_number")
    private String adeliNumber;

    @ManyToOne
    @JoinColumn(name = "medical_type_ref_id")
    private MedicalTypeRef medicalTypeRef;

    @OneToOne
    @JoinColumn(name = "rppsRef_id")
    private RppsRef rppsRef;

    @OneToOne(cascade = CascadeType.PERSIST)
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "title_ref_id")
    private TitleRef titleRef;

    @ManyToOne
    @JoinColumn(name = "situation_ref_id")
    private SituationRef situationRef;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getPracticeLocation() {
        return practiceLocation;
    }

    public void setPracticeLocation(String practiceLocation) {
        this.practiceLocation = practiceLocation;
    }

    public String getAdeliNumber() {
        return adeliNumber;
    }

    public void setAdeliNumber(String adeliNumber) {
        this.adeliNumber = adeliNumber;
    }

    public MedicalTypeRef getMedicalTypeRef() {
        return medicalTypeRef;
    }

    public void setMedicalTypeRef(MedicalTypeRef medicalTypeRef) {
        this.medicalTypeRef = medicalTypeRef;
    }

    public RppsRef getRppsRef() {
        return rppsRef;
    }

    public void setRppsRef(RppsRef rppsRef) {
        this.rppsRef = rppsRef;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public TitleRef getTitleRef() {
        return titleRef;
    }

    public void setTitleRef(TitleRef titleRef) {
        this.titleRef = titleRef;
    }

    public SituationRef getSituationRef() {
        return situationRef;
    }

    public void setSituationRef(SituationRef situationRef) {
        this.situationRef = situationRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExtendedUser extendedUser = (ExtendedUser) o;
        return Objects.equals(id, extendedUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExtendedUser{" +
            "id=" + id +
            ", birthDate='" + birthDate + "'" +
            ", sex='" + sex + "'" +
            ", practiceLocation='" + practiceLocation + "'" +
            ", adeliNumber='" + adeliNumber + "'" +
            '}';
    }
}
