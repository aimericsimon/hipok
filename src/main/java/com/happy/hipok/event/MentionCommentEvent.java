package com.happy.hipok.event;

import com.happy.hipok.domain.Comment;
import com.happy.hipok.domain.Mention;
import com.happy.hipok.domain.Notification;
import com.happy.hipok.domain.enumeration.NotificationType;

import java.time.ZonedDateTime;

/**
 * Event for a mention
 */
public class MentionCommentEvent extends NotificationEvent {
    private static final long serialVersionUID = 1L;

    private Mention mention;
    private Comment comment;

    //Constructor's first parameter must be source
    public MentionCommentEvent(Object source, Mention mention, Comment comment) {
        //Calling this super class constructor is necessary
        super(source);
        this.mention = mention;
        this.comment = comment;
    }

    @Override
    public Notification getNotification() {
        Notification notification = new Notification();
        notification.setCreationDate(ZonedDateTime.now());
        notification.setEmitterProfile(comment.getCommentatorProfile());
        notification.setRead(Boolean.FALSE);
        notification.setReceiverProfile(mention.getMentionnedProfile());
        notification.setType(NotificationType.MENTION_COMMENT);
        notification.setItemId(comment.getPublication().getId());
        if(comment.getPublication().getImage() != null){
            notification.setData(comment.getPublication().getImage().getImageThumbnailUrl());
        }

        return notification;
    }
}
