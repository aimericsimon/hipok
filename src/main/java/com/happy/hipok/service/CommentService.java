package com.happy.hipok.service;

import com.happy.hipok.domain.Comment;
import com.happy.hipok.domain.Hashtag;
import com.happy.hipok.domain.Mention;
import com.happy.hipok.domain.Publication;
import com.happy.hipok.event.CommentEvent;
import com.happy.hipok.event.MentionCommentEvent;
import com.happy.hipok.event.NotificationEvent;
import com.happy.hipok.repository.PublicationRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CommentService implements ApplicationEventPublisherAware {

    @Inject
    private HashtagService hashtagService;

    @Inject
    private MentionService mentionService;

    @Inject
    private ProfileService profileService;

    @Inject
    private ApplicationEventPublisher publisher;

    @Inject
    private PublicationRepository publicationRepository;

    //You must override this method; It will give you acces to ApplicationEventPublisher
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Set current profile to comment.
     *
     * @param comment comment
     */
    public void setCurrentProfile(Comment comment) {
        comment.setCommentatorProfile(profileService.getCurrentProfile());
    }

    /**
     * Detect hashtags in comment text and persist them in database.
     *
     * @param comment publication
     */
    public void setAndPersistHashtags(Comment comment) {
        Set<Hashtag> hashtags = hashtagService.extractAndSaveHashtagsFromText(comment.getText());
        comment.setHashtags(hashtags);
    }

    /**
     * Update comment text with marks surrounding mentions.
     *
     * @param comment publication
     */
    public List<Mention> processDescriptionWithMentions(Comment comment) {
        return mentionService.getMentionsAndSetProcessesText(comment);
    }

    public void sendEvent(Comment comment) {

        Publication publication = comment.getPublication();
        if (publication != null && publication.getAuthorProfile() == null) {
            comment.setPublication(publicationRepository.findOne(publication.getId()));
        }

        publisher.publishEvent(new CommentEvent(this, comment));
    }

    public void sendMentionCommentEvent(Comment result, Mention mention) {
        publisher.publishEvent(new MentionCommentEvent(this, mention, result));
    }
}
