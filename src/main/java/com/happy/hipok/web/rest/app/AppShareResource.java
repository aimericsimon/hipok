package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.Profile;
import com.happy.hipok.domain.Publication;
import com.happy.hipok.domain.Share;
import com.happy.hipok.repository.PublicationRepository;
import com.happy.hipok.repository.ShareRepository;
import com.happy.hipok.service.ProfileService;
import com.happy.hipok.service.ShareService;
import com.happy.hipok.web.rest.errors.CustomParameterizedException;
import com.happy.hipok.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing Share.
 */
@RestController
@RequestMapping("/app")
public class AppShareResource {

    private final Logger log = LoggerFactory.getLogger(AppShareResource.class);

    @Inject
    private ShareRepository shareRepository;

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private ProfileService profileService;

    @Inject
    private ShareService shareService;

    /**
     * POST  /shares -> Create a new share.
     */
    @RequestMapping(value = "/shares",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Long> createShare(@RequestParam(value = "pId") Long publicationId) throws URISyntaxException {
        log.debug("REST request to save Share : {}", publicationId);

        Publication publication = publicationRepository.findOne(publicationId);
        Profile currentProfile = profileService.getCurrentProfile();

        if (currentProfile == null) {
            throw new CustomParameterizedException("Pas de profil pour cet utilisateur.");
        }

        if (publication == null) {
            throw new CustomParameterizedException("Cette publication n'existe pas.");
        }

        checkParams(publication, currentProfile);

        Share share = new Share();
        share.setSharerProfile(currentProfile);
        share.setPublication(publication);
        Share createdShare = shareRepository.save(share);

        //#15961: Ne pas recevoir d'auto-notification
        if(!publication.getAuthorProfile().getId().equals(currentProfile.getId())){
            //Pas de nofication de like si l'utilisateur auto like sa publication
            shareService.sendEvent(createdShare);
        }

        return ResponseEntity.created(new URI("/app/shares/" + createdShare.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("share", createdShare.getId().toString()))
            .body(shareRepository.countByPublicationId(publicationId));
    }

    /**
     * GET  /shares/nb -> get the count of shares of a publication.
     */
    @RequestMapping(value = "/shares/nb",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Long> countSharesByPublicationId(@RequestParam(value = "pId") Long publicationId) {
        log.debug("REST request to count shares by publication id");

        checkCountParams(publicationId);

        return Optional.ofNullable(shareRepository.countByPublicationId(publicationId))
            .map(count -> new ResponseEntity<>(count, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /shares/shared -> Has the current profile already shared the publication.
     */
    @RequestMapping(value = "/shares/shared",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Boolean> hasAlreadySharedPublication (@RequestParam(value = "pId") Long publicationId) {
        log.debug("REST request to count shares by publication id");

        Publication publication = publicationRepository.findOne(publicationId);
        Profile currentProfile = profileService.getCurrentProfile();

        checkCountParams(publicationId);

        Optional<Share> existingShare = shareRepository.findOneBySharerProfileAndPublication(currentProfile, publication);

        return Optional.ofNullable(shareRepository.findOneBySharerProfileAndPublication(currentProfile, publication))
            .map(existinShare -> new ResponseEntity<>(existingShare.isPresent(), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private void checkParams(Publication publication, Profile currentProfile) {
        if (currentProfile == null) {
            throw new CustomParameterizedException("Pas de profil pour cet utilisateur.");
        }

        if (publication == null) {
            throw new CustomParameterizedException("Cette publication n'existe pas.");
        }

        Optional<Share> existingShare = shareRepository.findOneBySharerProfileAndPublication(currentProfile, publication);
        if (existingShare.isPresent()) {
            throw new CustomParameterizedException("Ce profil a déjà partagé cette publication.");
        }

        // Check que le profil courant peut partager cette publication car il y a accès
        Publication publicationWithCurrentProfile = publicationRepository.getPublicationWithCurrentProfile(publication.getId());
        if (publicationWithCurrentProfile == null) {
            throw new CustomParameterizedException("Ce profil ne peut peut pas partager cette publication car" +
                " il n'a pas accès à cette publication.");
        }
    }

    private void checkCountParams(@RequestParam(value = "pId") Long publicationId) {
        Publication publication = publicationRepository.findOne(publicationId);
        if (publication == null) {
            throw new CustomParameterizedException("Cette publication n'existe pas.");
        }

        Publication publicationWithCurrentProfile = publicationRepository.getPublicationWithCurrentProfile(publication.getId());
        if (publicationWithCurrentProfile == null) {
            throw new CustomParameterizedException("Ce profil ne peut obtenir le nombre de partages de de cette publication car" +
                " il n'a pas accès à cette publication.");
        }
    }
}
