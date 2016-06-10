package com.happy.hipok.web.rest.app.dto;

import com.happy.hipok.domain.Address;
import com.happy.hipok.domain.enumeration.State;
import com.happy.hipok.validation.annotations.ValidMedicalTypeRefId;
import com.happy.hipok.validation.annotations.ValidTitleRefId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Dahwoud on 31/12/2015.
 */

public class AppProfileDTO implements Serializable {

    @NotNull(message="{com.happy.hipok.user.notnullid}")
    private Long id;

    private String avatarUrl;

    @Size(max = 1000)
    private String description;

    private String avatarThumbnailUrl;

    private LocalDate birthDate;

    private int age;

    @NotNull(message="{com.happy.hipok.firstname.nullid}")
    @Size(max = 50)
    private String firstName;

    @NotNull(message="{com.happy.hipok.lastname.nullid}")
    @Size(max = 50)
    private String lastName;

    @Size(max = 200)
    private String practiceLocation;

    @NotNull(message="{com.happy.hipok.title.nullid}")
    @ValidTitleRefId
    private long titleRefId;

    private String titleRefLabel;

    private String  titleAbbreviation;

    @NotNull(message="{com.happy.hipok.title.medicaltyperef}")
    @ValidMedicalTypeRefId
    private long medicalTypeRefId;

    private String medicalTypeRefLabel;

    private String situationRefLabel;

    private long situationRefId;

    public String situationRefCode;

    private Address address;

    private long followers;

    private long following;

    private State followState;

    private Long followId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarThumbnailUrl() {
        return avatarThumbnailUrl;
    }

    public void setAvatarThumbnailUrl(String avatarThumbnailUrl) {
        this.avatarThumbnailUrl = avatarThumbnailUrl;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPracticeLocation() {
        return practiceLocation;
    }

    public void setPracticeLocation(String practiceLocation) {
        this.practiceLocation = practiceLocation;
    }

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long nbFollower) {
        this.followers = nbFollower;
    }

    public long getFollowing() {
        return following;
    }

    public void setFollowing(long following) {
        this.following = following;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getTitleRefId() {
        return titleRefId;
    }

    public void setTitleRefId(long titleRefId) {
        this.titleRefId = titleRefId;
    }

    public String getTitleRefLabel() {
        return titleRefLabel;
    }

    public void setTitleRefLabel(String titleRefLabel) {
        this.titleRefLabel = titleRefLabel;
    }

    public long getMedicalTypeRefId() {
        return medicalTypeRefId;
    }

    public void setMedicalTypeRefId(long medicalTypeRefId) {
        this.medicalTypeRefId = medicalTypeRefId;
    }

    public String getMedicalTypeRefLabel() {
        return medicalTypeRefLabel;
    }

    public void setMedicalTypeRefLabel(String medicalTypeRefLabel) {
        this.medicalTypeRefLabel = medicalTypeRefLabel;
    }

    public String getSituationRefLabel() {
        return situationRefLabel;
    }

    public void setSituationRefLabel(String situationRefLabel) {
        this.situationRefLabel = situationRefLabel;
    }

    public long getSituationRefId() {
        return situationRefId;
    }

    public void setSituationRefId(long situationRefId) {
        this.situationRefId = situationRefId;
    }

    public State getFollowState() {
        return followState;
    }

    public void setFollowState(State followState) {
        this.followState = followState;
    }

    public Long getFollowId() {
        return followId;
    }

    public void setFollowId(Long followId) {
        this.followId = followId;
    }

    public String getSituationRefCode() {
        return situationRefCode;
    }

    public void setSituationRefCode(String situationRefCode) {
        this.situationRefCode = situationRefCode;
    }

    public String getTitleAbbreviation() {
        return titleAbbreviation;
    }

    public void setTitleAbbreviation(String titleAbbreviation) {
        this.titleAbbreviation = titleAbbreviation;
    }
}
