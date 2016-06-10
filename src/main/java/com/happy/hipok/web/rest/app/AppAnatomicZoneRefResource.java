package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.AnatomicZoneRef;
import com.happy.hipok.repository.AnatomicZoneRefRepository;
import com.happy.hipok.security.AuthoritiesConstants;
import com.happy.hipok.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AnatomicZoneRef.
 */
@RestController
@RequestMapping("/app")
public class AppAnatomicZoneRefResource {

    private final Logger log = LoggerFactory.getLogger(AppAnatomicZoneRefResource.class);

    @Inject
    private AnatomicZoneRefRepository anatomicZoneRefRepository;

    /**
     * GET  /anatomicZoneRefs -> get all the anatomicZoneRefs.
     */
    @RequestMapping(value = "/anatomicZoneRefs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AnatomicZoneRef> getAllAnatomicZoneRefs() {
        log.debug("REST request to get all AnatomicZoneRefs");
        return anatomicZoneRefRepository.findAll();
    }
}
