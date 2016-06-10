package com.happy.hipok.service;

import com.happy.hipok.domain.*;
import com.happy.hipok.domain.enumeration.NotificationType;
import com.happy.hipok.event.MentionPublicationEvent;
import com.happy.hipok.repository.ImageRepository;
import com.happy.hipok.repository.NotificationRepository;
import com.happy.hipok.repository.PublicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PublicationService implements ApplicationEventPublisherAware {

    private final Logger log = LoggerFactory.getLogger(PublicationService.class);

    @Inject
    private NotificationRepository notificationRepository;

    @Inject
    private HashtagService hashtagService;

    @Inject
    private MentionService mentionService;

    @Inject
    private ProfileService profileService;

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private ApplicationEventPublisher publisher;

    @Inject
    private ImageService imageService;

    @Inject
    private ImageRepository imageRepository;

    //You must override this method; It will give you acces to ApplicationEventPublisher
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Set current profile to publication.
     * @param publication publication
     */
    public void setCurrentProfile(Publication publication) {
        publication.setAuthorProfile(profileService.getCurrentProfile());
    }

    /**
     * Detect hashtags in publication description and persist them in database.
     * @param publication publication
     */
    public void setAndPersistHashtags(Publication publication) {
        Set<Hashtag> hashtags = hashtagService.extractAndSaveHashtagsFromText(publication.getDescription());
        publication.setHashtags(hashtags);
    }

    /**
     * Update description with marks surrounding mentions.
     * @param publication publication
     */
    public List<Mention> processDescriptionWithMentions(Publication publication) {
        return mentionService.getMentionsAndSetProcessedDescription(publication);
    }

    public void sendMentionPublicationEvent(Publication result, Mention mention) {
        publisher.publishEvent(new MentionPublicationEvent(this, mention, result));
    }

    /**
     *
     *
     */
    public void deleteProfilePublications(){
        List<Publication> publications = publicationRepository.findProfilePublication();
        if(publications != null){
            for(Publication publication: publications){
                deletePublication(publication);
            }
        }
    }

    /**
     *
     * @param publication
     */
    public void deletePublication(Publication publication){
        List<Notification> notifications = notificationRepository.findAllByItemId(publication.getId());

        List<Notification> notificationsToDelete = notifications.stream()
            .filter(
                p -> p.getType() == NotificationType.COMMENT ||
                    p.getType() == NotificationType.MENTION_COMMENT ||
                    p.getType() == NotificationType.MENTION_PUBLICATION ||
                    p.getType() == NotificationType.REPORT ||
                    p.getType() == NotificationType.SHARE).collect(Collectors.toList());

        notificationRepository.delete(notificationsToDelete);

        Image image = imageRepository.findOne(publication.getImage().getId());

        publicationRepository.delete(publication.getId());

        if(image != null){
            imageService.deleteImage(image.getImageThumbnailUrl());
            imageService.deleteImage(image.getImageUrl());
            imageRepository.delete(image);
        }
    }
}
