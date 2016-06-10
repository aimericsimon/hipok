package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.AnatomicZoneRef;
import com.happy.hipok.repository.AnatomicZoneRefRepository;
import com.happy.hipok.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing AnatomicZoneRef.
 */
@RestController
@RequestMapping("/api")
public class AnatomicZoneRefResource {

    private final Logger log = LoggerFactory.getLogger(AnatomicZoneRefResource.class);

    @Inject
    private AnatomicZoneRefRepository anatomicZoneRefRepository;

    /**
     * POST  /anatomicZoneRefs -> Create a new anatomicZoneRef.
     */
    @RequestMapping(value = "/anatomicZoneRefs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AnatomicZoneRef> createAnatomicZoneRef(@RequestBody AnatomicZoneRef anatomicZoneRef) throws URISyntaxException {
        log.debug("REST request to save AnatomicZoneRef : {}", anatomicZoneRef);
        if (anatomicZoneRef.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new anatomicZoneRef cannot already have an ID").body(null);
        }
        AnatomicZoneRef result = anatomicZoneRefRepository.save(anatomicZoneRef);
        return ResponseEntity.created(new URI("/api/anatomicZoneRefs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("anatomicZoneRef", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /anatomicZoneRefs -> Updates an existing anatomicZoneRef.
     */
    @RequestMapping(value = "/anatomicZoneRefs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AnatomicZoneRef> updateAnatomicZoneRef(@RequestBody AnatomicZoneRef anatomicZoneRef) throws URISyntaxException {
        log.debug("REST request to update AnatomicZoneRef : {}", anatomicZoneRef);
        if (anatomicZoneRef.getId() == null) {
            return createAnatomicZoneRef(anatomicZoneRef);
        }
        AnatomicZoneRef result = anatomicZoneRefRepository.save(anatomicZoneRef);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("anatomicZoneRef", anatomicZoneRef.getId().toString()))
            .body(result);
    }

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

    /**
     * GET  /anatomicZoneRefs/:id -> get the "id" anatomicZoneRef.
     */
    @RequestMapping(value = "/anatomicZoneRefs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AnatomicZoneRef> getAnatomicZoneRef(@PathVariable Long id) {
        log.debug("REST request to get AnatomicZoneRef : {}", id);
        return Optional.ofNullable(anatomicZoneRefRepository.findOne(id))
            .map(anatomicZoneRef -> new ResponseEntity<>(
                anatomicZoneRef,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /anatomicZoneRefs/:id -> delete the "id" anatomicZoneRef.
     */
    @RequestMapping(value = "/anatomicZoneRefs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAnatomicZoneRef(@PathVariable Long id) {
        log.debug("REST request to delete AnatomicZoneRef : {}", id);
        anatomicZoneRefRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("anatomicZoneRef", id.toString())).build();
    }
}
