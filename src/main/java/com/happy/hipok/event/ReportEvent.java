package com.happy.hipok.event;

import com.happy.hipok.domain.Notification;
import com.happy.hipok.domain.Profile;
import com.happy.hipok.domain.Publication;
import com.happy.hipok.domain.enumeration.NotificationType;

import java.time.ZonedDateTime;

/**
 * Created by Dahwoud on 06/01/2016.
 */
public class ReportEvent extends NotificationEvent {

    private static final long serialVersionUID = 1L;

    private Publication publication;
    private Profile reporter;

    //Constructor's first parameter must be source
    public ReportEvent(Object source, Publication publication, Profile reporter) {
        //Calling this super class constructor is necessary
        super(source);
        this.reporter = reporter;
        this.publication = publication;
    }

    @Override
    public Notification getNotification() {
        Notification notification = new Notification();
        notification.setCreationDate(ZonedDateTime.now());
        notification.setEmitterProfile(reporter);
        notification.setRead(Boolean.FALSE);
        notification.setReceiverProfile(publication.getAuthorProfile());
        notification.setType(NotificationType.REPORT);
        notification.setItemId(publication.getId());

        if(publication.getImage() != null){
            notification.setData(publication.getImage().getImageThumbnailUrl());
        }

        return notification;
    }

}
