package com.happy.hipok.domain;

/**
 * Created by Dahwoud on 20/01/2016.
 */
public class Suggestion {

    /**
     *
     */
    private Profile profile;

    /**
     *
     */
    private Long count;

    /**
     *
     * @param profile
     * @param count
     */
    public Suggestion(Profile profile, Long count){
        this.profile = profile;
        this.count = count;
    }

    /**
     *
     * @return
     */
    public Profile getProfile() {
        return profile;
    }

    /***
     *
     * @param profile
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     *
     * @return
     */
    public Long getCount() {
        return count;
    }

    /**
     *
     * @param count
     */
    public void setCount(Long count) {
        this.count = count;
    }
}
