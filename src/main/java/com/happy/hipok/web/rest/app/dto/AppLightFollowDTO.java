package com.happy.hipok.web.rest.app.dto;

/**
 * Created by Dahwoud on 22/02/2016.
 */
public class AppLightFollowDTO {

    private Long id;

    private Long profileId;

    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
