package com.happy.hipok.event;

import com.happy.hipok.domain.Comment;
import com.happy.hipok.domain.Notification;
import com.happy.hipok.domain.enumeration.NotificationType;

import java.time.ZonedDateTime;

/**
 * Event for a comment
 */
public class CommentEvent extends NotificationEvent {
    private static final long serialVersionUID = 1L;

    private Comment comment;

    //Constructor's first parameter must be source
    public CommentEvent(Object source, Comment comment) {
        //Calling this super class constructor is necessary
        super(source);
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }

    @Override
    public Notification getNotification() {
        Notification notification = new Notification();
        notification.setCreationDate(ZonedDateTime.now());
        notification.setEmitterProfile(comment.getCommentatorProfile());
        notification.setRead(Boolean.FALSE);
        notification.setReceiverProfile(comment.getPublication().getAuthorProfile());

        if(comment.getPublication().getImage() != null){
            notification.setData(comment.getPublication().getImage().getImageThumbnailUrl());
        }
        notification.setItemId(comment.getPublication().getId());
        notification.setType(NotificationType.COMMENT);

        return notification;
    }
}
