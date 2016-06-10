package com.happy.hipok.web.rest.app.dto;

import java.io.Serializable;

/**
 * Created by Dahwoud on 04/01/2016.
 */
public class AppFollowDTO implements Serializable {

    private Long id;

    private Long profileId;

    private String titleAbbreviation;

    private String firstName;

    private String lastName;

    private String city;

    private String practiceLocation;

    private String avatarUrl;

    private String avatarThumbnailUrl;

    private String situationRefCode;

    private String situationRefLabel;

    private String medicalTypeRefLabel;

    private Long medicalTypeRefId;

    private AppLightFollowDTO follower;

    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppLightFollowDTO getFollower() {
        return follower;
    }

    public void setFollower(AppLightFollowDTO follower) {
        this.follower = follower;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getTitleAbbreviation() {
        return titleAbbreviation;
    }

    public void setTitleAbbreviation(String title) {
        this.titleAbbreviation = title;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarThumbnailUrl() {
        return avatarThumbnailUrl;
    }

    public void setAvatarThumbnailUrl(String avatarThumbnailUrl) {
        this.avatarThumbnailUrl = avatarThumbnailUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPracticeLocation() {
        return practiceLocation;
    }

    public void setPracticeLocation(String practiceLocation) {
        this.practiceLocation = practiceLocation;
    }

    public String getSituationRefCode() {
        return situationRefCode;
    }

    public void setSituationRefCode(String situationRefCode) {
        this.situationRefCode = situationRefCode;
    }

    public String getSituationRefLabel() {
        return situationRefLabel;
    }

    public void setSituationRefLabel(String situationRefLabel) {
        this.situationRefLabel = situationRefLabel;
    }

    public String getMedicalTypeRefLabel() {
        return medicalTypeRefLabel;
    }

    public void setMedicalTypeRefLabel(String medicalTypeRefLabel) {
        this.medicalTypeRefLabel = medicalTypeRefLabel;
    }

    public Long getMedicalTypeRefId() {
        return medicalTypeRefId;
    }

    public void setMedicalTypeRefId(Long medicalTypeRefId) {
        this.medicalTypeRefId = medicalTypeRefId;
    }
}
