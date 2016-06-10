package com.happy.hipok.domain;

import com.happy.hipok.service.BeginEndPosition;

import java.util.Optional;

/**
 * Created by gni on 14/12/15.
 */
public class Mention {

    private Optional<BeginEndPosition> beginEndPosition;

    private Profile mentionnedProfile;


    public Optional<BeginEndPosition> getBeginEndPosition() {
        return beginEndPosition;
    }

    public void setBeginEndPosition(Optional<BeginEndPosition> beginEndPosition) {
        this.beginEndPosition = beginEndPosition;
    }

    public Profile getMentionnedProfile() {
        return mentionnedProfile;
    }

    public void setMentionnedProfile(Profile mentionnedProfile) {
        this.mentionnedProfile = mentionnedProfile;
    }
}
