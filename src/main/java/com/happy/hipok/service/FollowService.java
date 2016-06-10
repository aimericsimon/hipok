package com.happy.hipok.service;

import com.happy.hipok.domain.Follow;
import com.happy.hipok.event.AcceptFollowEvent;
import com.happy.hipok.event.AskFollowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class FollowService implements ApplicationEventPublisherAware {

    private final Logger log = LoggerFactory.getLogger(FollowService.class);

    @Inject
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * publication d'une notification de demande de suivi
     * @param follow
     */
    public void sendAskFollowEvent(Follow follow) {
        publisher.publishEvent(new AskFollowEvent(this, follow));
    }

    /**
     * publication d'une notification d'acceptation de demande de suivi
     * @param follow
     */
    public void sendAcceptFollowEvent(Follow follow) {
        publisher.publishEvent(new AcceptFollowEvent(this, follow));
    }
}
