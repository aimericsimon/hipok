package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.*;
import com.happy.hipok.repository.CommentRepository;
import com.happy.hipok.repository.PublicationRepository;
import com.happy.hipok.service.CommentService;
import com.happy.hipok.service.ProfileService;
import com.happy.hipok.web.rest.app.dto.AppCommentDTO;
import com.happy.hipok.web.rest.app.mapper.AppCommentMapper;
import com.happy.hipok.web.rest.errors.CustomParameterizedException;
import com.happy.hipok.web.rest.util.HeaderUtil;
import com.happy.hipok.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing Comment.
 */
@RestController
@RequestMapping("/app")
public class AppCommentResource {

    private final Logger log = LoggerFactory.getLogger(AppCommentResource.class);

    @Inject
    private CommentRepository commentRepository;

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private AppCommentMapper appCommentMapper;

    @Inject
    private CommentService commentService;

    @Inject
    private ProfileService profileService;

    /**
     * POST  /comments -> Create a new comment.
     */
    @RequestMapping(value = "/comments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppCommentDTO> createComment(@Valid @RequestBody AppCommentDTO appCommentDTO) throws URISyntaxException {
        log.debug("REST request to save Comment : {}", appCommentDTO);
        if (appCommentDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "Un nouveau commentaire ne peut déjà avoir un id").body(null);
        }

        Profile currentProfile = profileService.getCurrentProfile();
        if (currentProfile == null) {
            throw new CustomParameterizedException("Pas de profil pour cet utilisateur.");
        }

        Publication publication = publicationRepository.findOne(appCommentDTO.getPublicationId());
        if (publication == null) {
            throw new CustomParameterizedException("Cette publication n'existe pas.");
        }

        Comment comment = new Comment();
        comment.setText(appCommentDTO.getText());
        comment.setCreationDate(ZonedDateTime.now());
        comment.setCommentatorProfile(currentProfile);
        comment.setPublication(publication);

        commentService.setAndPersistHashtags(comment);
        List<Mention> mentions = commentService.processDescriptionWithMentions(comment);

        Comment result = commentRepository.save(comment);

        //#15961: Ne pas recevoir d'auto-notification
        for(int i=0;i<mentions.size();i++){
            Mention mention  = mentions.get(i);

            if(mention != null  && mention.getMentionnedProfile() != null && !mention.getMentionnedProfile().getId().equals(currentProfile.getId())) {
                //Pas de notification de mention si l'utilisateur s'automentionne
                commentService.sendMentionCommentEvent(result, mention);
            }
        }

        //#15961: Ne pas recevoir d'auto-notification
        if(!publication.getAuthorProfile().getId().equals(currentProfile.getId())) {
            //Pas de notification de comentaire si l'utilisateur commente sa propre publication
            commentService.sendEvent(result);
        }

        return ResponseEntity.created(new URI("/app/comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("comment", result.getId().toString()))
            .body(appCommentMapper.commentToAppCommentDTO(result));
    }

    /**
     * GET  /comments -> get all the comments of a publication.
     */
    @RequestMapping(value = "/comments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AppCommentDTO>> getAllComments(@RequestParam(value = "pId") Long publicationId, Pageable pageable)
        throws URISyntaxException {

        checkGetParams(publicationId);

        Page<Comment> page = commentRepository.findAllByPublicationIdOrderByCreationDateDesc(publicationId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/app/comments");
        return new ResponseEntity<>(page.getContent().stream()
            .map(appCommentMapper::commentToAppCommentDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    private void checkGetParams(Long publicationId) {

        Publication publication = publicationRepository.findOne(publicationId);
        if (publication == null) {
            throw new CustomParameterizedException("Cette publication n'existe pas.");
        }

        // Check que le profil courant peut consulter les commentaires de cette publication car il y a accès
        Publication publicationWithCurrentProfile = publicationRepository.getPublicationWithCurrentProfile(publication.getId());
        if (publicationWithCurrentProfile == null) {
            throw new CustomParameterizedException("Ce profil ne peut obtenir les commentaires de cette publication car" +
                " il n'a pas accès à cette publication.");
        }
    }
}
