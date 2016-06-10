package com.happy.hipok.web.rest.app.dto;

/**
 * Created by Dahwoud on 18/01/2016.
 */
public class AppLightProfileDTO {

    private Long id;
    private String titleAbbreviation;
    private String firstName;
    private String lastName;
    private String city;
    private String medicalTypeRefLabel;
    private String avatarThumbnailUrl;
    private AppLightFollowDTO follower;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleAbbreviation() {
        return titleAbbreviation;
    }

    public void setTitleAbbreviation(String titleAbbreviation) {
        this.titleAbbreviation = titleAbbreviation;
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

    public String getMedicalTypeRefLabel() {
        return medicalTypeRefLabel;
    }

    public void setMedicalTypeRefLabel(String medicalTypeRefLabel) {
        this.medicalTypeRefLabel = medicalTypeRefLabel;
    }

    public String getAvatarThumbnailUrl() {
        return avatarThumbnailUrl;
    }

    public void setAvatarThumbnailUrl(String avatarThumbnailUrl) {
        this.avatarThumbnailUrl = avatarThumbnailUrl;
    }

    public AppLightFollowDTO getFollower() {return follower;}

    public void setFollower(AppLightFollowDTO follower) {this.follower = follower;}
}
