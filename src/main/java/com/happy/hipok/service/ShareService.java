package com.happy.hipok.service;

import com.happy.hipok.domain.Share;
import com.happy.hipok.event.ShareEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class ShareService implements ApplicationEventPublisherAware {

    private final Logger log = LoggerFactory.getLogger(ShareService.class);

    @Inject
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void sendEvent(Share share) {
        publisher.publishEvent(new ShareEvent(this, share));
    }
}
