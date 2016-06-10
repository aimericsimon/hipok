package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.Application;
import com.happy.hipok.domain.Mention;
import com.happy.hipok.domain.Profile;
import com.happy.hipok.domain.Publication;
import com.happy.hipok.repository.NotificationRepository;
import com.happy.hipok.repository.PublicationRepository;
import com.happy.hipok.service.ProfileService;
import com.happy.hipok.service.PublicationService;
import com.happy.hipok.web.rest.app.dto.AppPublicationDTO;
import com.happy.hipok.web.rest.app.mapper.AppPublicationMapper;
import com.happy.hipok.web.rest.errors.CustomParameterizedException;
import com.happy.hipok.web.rest.util.HeaderUtil;
import com.happy.hipok.web.rest.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing Publication.
 */
@RestController
@RequestMapping("/app")
public class AppPublicationResource {

    private final Logger log = LoggerFactory.getLogger(AppPublicationResource.class);

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private PublicationService publicationService;

    @Inject
    private ProfileService profileService;

    @Inject
    private NotificationRepository notificationRepository;

    @Inject
    private AppPublicationMapper appPublicationMapper;

    /**
     * POST  /publications -> Create a new publication.
     */
    @RequestMapping(value = "/publications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppPublicationDTO> createPublicationWithBinary(@Valid @RequestBody AppPublicationDTO appPublicationDTO, HttpServletRequest request) throws URISyntaxException {
        log.error("REST request to save Publication : "+appPublicationDTO.getDescription());

        Profile currentProfile = profileService.getCurrentProfile();
        if (currentProfile == null) {
            throw new CustomParameterizedException("Pas de profil pour cet utilisateur.");
        }

        log.error("createPublication for "+currentProfile.getId());

        Publication publication = appPublicationMapper.publicationDTOToPublication(appPublicationDTO);

        publicationService.setCurrentProfile(publication);
        publicationService.setAndPersistHashtags(publication);

        List<Mention> mentions = publicationService.processDescriptionWithMentions(publication);

        Publication result = publicationRepository.save(publication);

        log.error("persisted publication id "+result.getId().toString()+" "+result.getDescription());

        //#15961: Ne pas recevoir d'auto-notification
        for(int i=0;i<mentions.size();i++) {
            Mention mention = mentions.get(i);

            if (mention != null && mention.getMentionnedProfile() != null && !mention.getMentionnedProfile().getId().equals(currentProfile.getId())) {
                //Pas de notification de mention si l'utilisateur s'automentionne
                publicationService.sendMentionPublicationEvent(result, mention);
            }
        }

        return ResponseEntity.created(new URI("/app/publications/" + publication.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("publication", publication.getId().toString()))
            .body(appPublicationMapper.publicationToPublicationDTO(publication, RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY));
    }

    /**
     * POST  /publications -> Delete a publication.
     */
    @RequestMapping(value = "/publications/{publicationId}/delete",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public  ResponseEntity<Void> deletePublication(@PathVariable Long publicationId, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to delete Publication : {}", publicationId);

        Profile currentProfile = profileService.getCurrentProfile();
        if (currentProfile == null) {
            throw new CustomParameterizedException("Pas de profil pour cet utilisateur.");
        }

        Publication publication  = publicationRepository.findOne(publicationId);
        if(publication == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!publication.getAuthorProfile().getId().equals(currentProfile.getId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        publicationService.deletePublication(publication);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("publication",publicationId.toString())).build();
    }

    /**
     * POST  /publications/:id/visualize -> Visualize a publication.
     */
    @RequestMapping(value = "/publications/{publicationId}/visualize",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Integer> visualizePublication(@PathVariable Long publicationId) throws URISyntaxException {
        log.debug("REST request to increment visualizations of a publication : {}", publicationId);

        Publication publication = publicationRepository.findOne(publicationId);

        checkParams();
        checkVisualizeParams(publication);

        Integer nbVisualizations = 1;
        if (publication.getNbVizualisations() != null) {
            nbVisualizations = publication.getNbVizualisations() + 1;
        }
        publication.setNbVizualisations(nbVisualizations);

        Publication result = publicationRepository.save(publication);

        return ResponseEntity.created(new URI("/app/publications/" + result.getId() + "/visualize"))
            .body(result.getNbVizualisations());
    }

    private void checkParams() {

        Profile currentProfile = profileService.getCurrentProfile();
        if (currentProfile == null) {
            throw new CustomParameterizedException("Pas de profil pour cet utilisateur.");
        }
    }

    private void checkVisualizeParams(Publication publication) {

        if (publication == null) {
            throw new CustomParameterizedException("Cette publication n'existe pas.");
        }

        // Check que le profil courant peut visualiser cette publication car il y a accès
        Publication publicationWithCurrentProfile = publicationRepository.getPublicationWithCurrentProfile(publication.getId());
        if (publicationWithCurrentProfile == null) {
            throw new CustomParameterizedException("Ce profil ne peut visualiser cette publication car" +
                " il n'a pas accès à cette publication.");
        }
    }
}
