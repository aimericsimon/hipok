package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.Declaration;
import com.happy.hipok.domain.DeclarationEmail;
import com.happy.hipok.domain.DeclarationTypeRef;
import com.happy.hipok.domain.Profile;
import com.happy.hipok.repository.DeclarationEmailRepository;
import com.happy.hipok.repository.DeclarationRepository;
import com.happy.hipok.repository.DeclarationTypeRefRepository;
import com.happy.hipok.repository.ProfileRepository;
import com.happy.hipok.service.MailService;
import com.happy.hipok.web.rest.app.dto.AppDeclarationDTO;
import com.happy.hipok.web.rest.util.HeaderUtil;
import com.happy.hipok.web.rest.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * REST controller for managing TitleRef.
 */
@RestController
@RequestMapping("/app")
public class AppDeclarationResource {

    private final Logger log = LoggerFactory.getLogger(AppDeclarationResource.class);

    @Inject
    private DeclarationTypeRefRepository declarationTypeRefRepository;

    @Inject
    private DeclarationRepository declarationRepository;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private DeclarationEmailRepository declarationEmailRepository;

    @Inject
    private MailService mailService;


    /**
     * GET  /titleRefs -> get all the declarationTypeRefs.
     */
    @RequestMapping(value = "/declaration",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Declaration> createDeclaration(@Valid @RequestBody AppDeclarationDTO appDeclarationDTO, HttpServletRequest request) throws URISyntaxException {

        log.debug("REST create declaration");

        Profile profile = profileRepository.getCurrentProfile();

        Declaration declaration = new Declaration();

        DeclarationTypeRef declarationTypeRef = declarationTypeRefRepository.findOne(appDeclarationDTO.getDeclarationTypeRefId());

        declaration.setDeclarationTypeRef(declarationTypeRef);
        declaration.setProfile(profile);
        declaration.setDescription(appDeclarationDTO.getDescription());
        declaration.setDeclarationDate(ZonedDateTime.now());

        Declaration createdDeclaration = declarationRepository.save(declaration);

        List<DeclarationEmail> emails = declarationEmailRepository.findAll();
        for(DeclarationEmail email: emails)
        {
            mailService.sendDeclaration(createdDeclaration,email,RequestUtils.getBaseUrl(request));
        }

        //boolean removedPublication = reportingService.handleReporting(RequestUtils.getBaseUrl(request), publication, currentProfile);

        return ResponseEntity.created(new URI("/app/declarations/" + createdDeclaration.getId().toString()))
            .headers(HeaderUtil.createEntityCreationAlert("declaration", createdDeclaration.getId().toString()))
            .body(null);
    }
}
