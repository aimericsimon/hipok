package com.happy.hipok.web.rest.app.dto;

import com.happy.hipok.domain.Address;
import com.happy.hipok.domain.enumeration.Sex;
import com.happy.hipok.validation.annotations.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the ExtendedUser entity.
 * Pour les professeurs ou les métiers médicaux, le RPPS n'est pas obligatoire. Sinon, il l'est,
 * et on vérifie s'il n'est pas déjà pris et s'il existe en base.
 */

public class AppExtendedUserDTO {

    private Long id;

    protected LocalDate birthDate;

    @NotNull(message="{com.happy.hipok.sex.nullid}")
    protected Sex sex;

    @Size(max = 200)
    protected String practiceLocation;

    protected String adeliNumber;

    @NotNull(message="{com.happy.hipok.medicaltyperefid.nullid}")
    @ValidMedicalTypeRefId
    protected Long medicalTypeRefId;

    protected String rppsRefNumber;

    protected Address address;

    @NotNull(message="{com.happy.hipok.title.nullid}")
    @ValidTitleRefId
    protected Long titleRefId;

    @ValidSituationRefId
    protected Long situationRefId;

    @NotNull(message="{com.happy.hipok.firstname.nullid}")
    @Size(max = 50)
    protected String firstName;

    @NotNull(message="{com.happy.hipok.lastname.nullid}")
    @Size(max = 50)
    protected String lastName;

    @Size(min = 2, max = 5)
    protected String langKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
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

    public Long getMedicalTypeRefId() {
        return medicalTypeRefId;
    }

    public void setMedicalTypeRefId(Long medicalTypeRefId) {
        this.medicalTypeRefId = medicalTypeRefId;
    }
    public String getRppsRefNumber() {
        return rppsRefNumber;
    }

    public void setRppsRefNumber(String rppsRefNumber) {
        this.rppsRefNumber = rppsRefNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getTitleRefId() {
        return titleRefId;
    }

    public void setTitleRefId(Long titleRefId) {
        this.titleRefId = titleRefId;
    }

    public Long getSituationRefId() {
        return situationRefId;
    }

    public void setSituationRefId(Long situationRefId) {
        this.situationRefId = situationRefId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppExtendedUserDTO appExtendedUserDTO = (AppExtendedUserDTO) o;

        if ( ! Objects.equals(this.getId(), appExtendedUserDTO.getId())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

    @Override
    public String toString() {
        return this.getClass().getName()+"{" +
            "id=" + this.getId() +
            ", birthDate='" + birthDate + "'" +
            ", sex='" + sex + "'" +
            ", practiceLocation='" + practiceLocation + "'" +
            ", adeliNumber='" + adeliNumber + "'" +
            '}';
    }
}
