package com.happy.hipok.domain;

/**
 *
 */
public class ProfileWithFollowCount {

    /**
     *
     */
    private Profile profile;

    /**
     *
     */
    private long followers;

    /**
     *
     */
    private long following;

    /**
     *
     * @param profile
     * @param following
     * @param followers
     */
    public ProfileWithFollowCount(Profile profile, long following, long followers){
        this.profile = profile;
        this.following = following;
        this.followers = followers;
    }

    /**
     *
     * @return
     */
    public Profile getProfile() {
        return profile;
    }

    /**
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
    public long getFollowers() {
        return followers;
    }

    /**
     *
     * @param followers
     */
    public void setFollowers(long followers) {
        this.followers = followers;
    }

    /**
     *
     * @return
     */
    public long getFollowing() {
        return following;
    }

    /**
     *
     * @param following
     */
    public void setFollowing(long following) {
        this.following = following;
    }
}
