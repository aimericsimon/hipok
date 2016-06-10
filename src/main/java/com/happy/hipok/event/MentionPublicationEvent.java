package com.happy.hipok.event;

import com.happy.hipok.domain.Mention;
import com.happy.hipok.domain.Notification;
import com.happy.hipok.domain.Publication;
import com.happy.hipok.domain.enumeration.NotificationType;

import java.time.ZonedDateTime;

/**
 * Event for a mention
 */
public class MentionPublicationEvent extends NotificationEvent {
    private static final long serialVersionUID = 1L;

    private Mention mention;
    private Publication publication;

    //Constructor's first parameter must be source
    public MentionPublicationEvent(Object source, Mention mention, Publication publication) {
        //Calling this super class constructor is necessary
        super(source);
        this.mention = mention;
        this.publication = publication;
    }

    @Override
    public Notification getNotification() {
        Notification notification = new Notification();
        notification.setCreationDate(ZonedDateTime.now());
        notification.setEmitterProfile(publication.getAuthorProfile());
        notification.setRead(Boolean.FALSE);
        notification.setReceiverProfile(mention.getMentionnedProfile());
        notification.setType(NotificationType.MENTION_PUBLICATION);
        notification.setItemId(publication.getId());

        if(publication.getImage() != null){
            notification.setData(publication.getImage().getImageThumbnailUrl());
        }

        return notification;
    }
}
