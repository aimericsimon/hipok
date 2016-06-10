package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.TitleRef;
import com.happy.hipok.repository.TitleRefRepository;
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
 * REST controller for managing TitleRef.
 */
@RestController
@RequestMapping("/app")
public class AppTitleRefResource {

    private final Logger log = LoggerFactory.getLogger(AppTitleRefResource.class);

    @Inject
    private TitleRefRepository titleRefRepository;

    /**
     * GET  /titleRefs -> get all the titleRefs.
     */
    @RequestMapping(value = "/titleRefs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TitleRef> getAllTitleRefs() {
        log.debug("REST request to get all TitleRefs");
        return titleRefRepository.findAllByOrderByLabel();
    }
}
