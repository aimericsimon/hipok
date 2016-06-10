package com.happy.hipok.event;

import com.happy.hipok.domain.Notification;
import org.springframework.context.ApplicationEvent;

/**
 * Created by gni on 13/12/15.
 */
public abstract class NotificationEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public NotificationEvent(Object source) {
        super(source);
    }

    public abstract Notification getNotification();
}
