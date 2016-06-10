package com.happy.hipok.event;

import com.happy.hipok.domain.Notification;
import com.happy.hipok.domain.Share;
import com.happy.hipok.domain.enumeration.NotificationType;

import java.time.ZonedDateTime;

/**
 * Event for a share
 */
public class ShareEvent extends NotificationEvent {
    private static final long serialVersionUID = 1L;

    private Share share;

    //Constructor's first parameter must be source
    public ShareEvent(Object source, Share share) {
        //Calling this super class constructor is necessary
        super(source);
        this.share = share;
    }

    @Override
    public Notification getNotification() {
        Notification notification = new Notification();
        notification.setCreationDate(ZonedDateTime.now());
        notification.setEmitterProfile(share.getSharerProfile());
        notification.setRead(Boolean.FALSE);
        notification.setReceiverProfile(share.getPublication().getAuthorProfile());
        notification.setItemId(share.getPublication().getId());

        if(share.getPublication().getImage() != null){
            notification.setData(share.getPublication().getImage().getImageThumbnailUrl());
        }
        notification.setType(NotificationType.SHARE);

        return notification;
    }
}
