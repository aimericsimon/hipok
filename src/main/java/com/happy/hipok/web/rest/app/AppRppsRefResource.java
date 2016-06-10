package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.RppsRef;
import com.happy.hipok.repository.RppsRefRepository;
import com.happy.hipok.security.AuthoritiesConstants;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RppsRef.
 */
@RestController
@RequestMapping("/app")
public class AppRppsRefResource {

    private final Logger log = LoggerFactory.getLogger(AppRppsRefResource.class);

    @Inject
    private RppsRefRepository rppsRefRepository;

    /**
     * GET  /rppsRefs -> get all the rppsRefs.
     */
    @RequestMapping(value = "/rppsRefs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<RppsRef>> getAllRppsRefs(Pageable pageable)
        throws URISyntaxException {
        Page<RppsRef> page = rppsRefRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/app/rppsRefs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
