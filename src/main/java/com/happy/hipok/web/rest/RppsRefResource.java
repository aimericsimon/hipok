package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.RppsRef;
import com.happy.hipok.repository.RppsRefRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RppsRef.
 */
@RestController
@RequestMapping("/api")
public class RppsRefResource {

    private final Logger log = LoggerFactory.getLogger(RppsRefResource.class);

    @Inject
    private RppsRefRepository rppsRefRepository;

    /**
     * POST  /rppsRefs -> Create a new rppsRef.
     */
    @RequestMapping(value = "/rppsRefs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RppsRef> createRppsRef(@RequestBody RppsRef rppsRef) throws URISyntaxException {
        log.debug("REST request to save RppsRef : {}", rppsRef);
        if (rppsRef.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new rppsRef cannot already have an ID").body(null);
        }
        RppsRef result = rppsRefRepository.save(rppsRef);
        return ResponseEntity.created(new URI("/api/rppsRefs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rppsRef", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rppsRefs -> Updates an existing rppsRef.
     */
    @RequestMapping(value = "/rppsRefs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RppsRef> updateRppsRef(@RequestBody RppsRef rppsRef) throws URISyntaxException {
        log.debug("REST request to update RppsRef : {}", rppsRef);
        if (rppsRef.getId() == null) {
            return createRppsRef(rppsRef);
        }
        RppsRef result = rppsRefRepository.save(rppsRef);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rppsRef", rppsRef.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rppsRefs -> get all the rppsRefs.
     */
    @RequestMapping(value = "/rppsRefs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RppsRef>> getAllRppsRefs(Pageable pageable)
        throws URISyntaxException {
        Page<RppsRef> page = rppsRefRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rppsRefs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rppsRefs/:id -> get the "id" rppsRef.
     */
    @RequestMapping(value = "/rppsRefs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RppsRef> getRppsRef(@PathVariable Long id) {
        log.debug("REST request to get RppsRef : {}", id);
        return Optional.ofNullable(rppsRefRepository.findOne(id))
            .map(rppsRef -> new ResponseEntity<>(
                rppsRef,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rppsRefs/:id -> delete the "id" rppsRef.
     */
    @RequestMapping(value = "/rppsRefs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRppsRef(@PathVariable Long id) {
        log.debug("REST request to delete RppsRef : {}", id);
        rppsRefRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rppsRef", id.toString())).build();
    }
}
