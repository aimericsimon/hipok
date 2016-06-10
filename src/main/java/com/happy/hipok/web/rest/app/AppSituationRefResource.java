package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.SituationRef;
import com.happy.hipok.repository.SituationRefRepository;
import com.happy.hipok.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SituationRef.
 */
@RestController
@RequestMapping("/app")
public class AppSituationRefResource {

    private final Logger log = LoggerFactory.getLogger(AppSituationRefResource.class);

    @Inject
    private SituationRefRepository situationRefRepository;

    /**
     * GET  /situationRefs -> get all the situationRefs.
     */
    @RequestMapping(value = "/situationRefs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SituationRef> getAllSituationRefs() {
        log.debug("REST request to get all SituationRefs");
        return situationRefRepository.findAllByOrderByLabel();
    }
}
