package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.Profile;
import com.happy.hipok.domain.Publication;
import com.happy.hipok.domain.Reporting;
import com.happy.hipok.repository.PublicationRepository;
import com.happy.hipok.repository.ReportingRepository;
import com.happy.hipok.security.AuthoritiesConstants;
import com.happy.hipok.service.ProfileService;
import com.happy.hipok.service.ReportingService;
import com.happy.hipok.web.rest.errors.CustomParameterizedException;
import com.happy.hipok.web.rest.util.HeaderUtil;
import com.happy.hipok.web.rest.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Reporting.
 */
@RestController
@RequestMapping("/app")
public class AppReportingResource {

    private final Logger log = LoggerFactory.getLogger(AppReportingResource.class);

    @Inject
    private ReportingRepository reportingRepository;

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private ProfileService profileService;

    @Inject
    ReportingService reportingService;

    /**
     * POST  /reportings -> Create a new reporting.
     */
    @RequestMapping(value = "/reportings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Reporting> createReportingWithPublicationId(@RequestParam(value = "pId") Long publicationId, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Reporting : {}", publicationId);

        Publication publication = publicationRepository.findOne(publicationId);
        Profile currentProfile = profileService.getCurrentProfile();

        checkParams(publication, currentProfile);

        Reporting reporting = new Reporting();
        reporting.setReporterProfile(currentProfile);
        reporting.setReportingDate(ZonedDateTime.now());
        reporting.setReportedPublication(publication);
        Reporting createdReporting = reportingRepository.save(reporting);

        boolean removedPublication = reportingService.handleReporting(RequestUtils.getBaseUrl(request), publication, currentProfile);

        return ResponseEntity.created(new URI("/app/reportings/" + createdReporting.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("reporting", createdReporting.getId().toString()))
            .headers(HeaderUtil.createPublicationDeletionAlert(removedPublication))
            .body(null);
    }

    private void checkParams(Publication publication, Profile currentProfile) {
        if (publication == null) {
            throw new CustomParameterizedException("Cette publication n'existe pas.");
        }

        if (currentProfile.equals(publication.getAuthorProfile())) {
            throw new CustomParameterizedException("Vous ne pouvez pas signaler votre propre publication.");
        }

        Optional<Reporting> existingReporting = reportingRepository.findOneByReporterProfileIdAndReportedPublicationId(currentProfile.getId(), publication.getId());
        if (existingReporting.isPresent()) {
            throw new CustomParameterizedException("Ce profil a déjà signalé cette publication.");
        }

        // Check que le profil courant peut signaler cette publication car il y a accès
        Publication publicationWithCurrentProfile = publicationRepository.getPublicationWithCurrentProfile(publication.getId());
        if(publicationWithCurrentProfile == null){
            throw new CustomParameterizedException("Ce profil ne peut signaler cette publication car" +
                " il n'a pas accès à cette publication.");
        }
    }
}
