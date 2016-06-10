package com.happy.hipok.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.happy.hipok.domain.enumeration.Sex;

/**
 * A DTO for the ExtendedUser entity.
 */
public class ExtendedUserDTO implements Serializable {

    private Long id;

    private LocalDate birthDate;

    private Sex sex;

    @Size(max = 200)
    private String practiceLocation;

    private String adeliNumber;

    private Long medicalTypeRefId;
    private Long rppsRefId;
    private Long userId;
    private Long addressId;
    private Long titleRefId;

    private String titleRefLabel;

    private Long situationRefId;
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

    public Long getMedicalTypeRefId() {
        return medicalTypeRefId;
    }

    public void setMedicalTypeRefId(Long medicalTypeRefId) {
        this.medicalTypeRefId = medicalTypeRefId;
    }
    public Long getRppsRefId() {
        return rppsRefId;
    }

    public void setRppsRefId(Long rppsRefId) {
        this.rppsRefId = rppsRefId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
    public Long getTitleRefId() {
        return titleRefId;
    }

    public void setTitleRefId(Long titleRefId) {
        this.titleRefId = titleRefId;
    }

    public String getTitleRefLabel() {
        return titleRefLabel;
    }

    public void setTitleRefLabel(String titleRefLabel) {
        this.titleRefLabel = titleRefLabel;
    }

    public Long getSituationRefId() {
        return situationRefId;
    }

    public void setSituationRefId(Long situationRefId) {
        this.situationRefId = situationRefId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExtendedUserDTO extendedUserDTO = (ExtendedUserDTO) o;

        if ( ! Objects.equals(id, extendedUserDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExtendedUserDTO{" +
            "id=" + id +
            ", birthDate='" + birthDate + "'" +
            ", sex='" + sex + "'" +
            ", practiceLocation='" + practiceLocation + "'" +
            ", adeliNumber='" + adeliNumber + "'" +
            '}';
    }
}
