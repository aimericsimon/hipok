package com.happy.hipok.event;

import com.happy.hipok.domain.Follow;
import com.happy.hipok.domain.Notification;
import com.happy.hipok.domain.enumeration.NotificationType;

import java.time.ZonedDateTime;

/**
 * Created by Dahwoud on 05/01/2016.
 */
public class AskFollowEvent extends NotificationEvent{

    private static final long serialVersionUID = 1L;

    private Follow follow;

    //Constructor's first parameter must be source
    public AskFollowEvent(Object source, Follow follow) {
        //Calling this super class constructor is necessary
        super(source);
        this.follow = follow;
    }

    public Follow getFollow() {
        return follow;
    }

    @Override
    public Notification getNotification() {
        Notification notification = new Notification();
        notification.setCreationDate(ZonedDateTime.now());
        notification.setEmitterProfile(follow.getFollowing());
        notification.setItemId(follow.getFollower().getId());
        notification.setReceiverProfile(follow.getFollower());
        notification.setRead(Boolean.FALSE);
        notification.setType(NotificationType.ASK_FOLLOW);
        notification.setData(follow.getFollower().getAvatarThumbnailUrl());
        return notification;
    }

}
