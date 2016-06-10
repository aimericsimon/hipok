package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.SituationRef;
import com.happy.hipok.repository.SituationRefRepository;
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
 * REST controller for managing SituationRef.
 */
@RestController
@RequestMapping("/api")
public class SituationRefResource {

    private final Logger log = LoggerFactory.getLogger(SituationRefResource.class);

    @Inject
    private SituationRefRepository situationRefRepository;

    /**
     * POST  /situationRefs -> Create a new situationRef.
     */
    @RequestMapping(value = "/situationRefs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SituationRef> createSituationRef(@RequestBody SituationRef situationRef) throws URISyntaxException {
        log.debug("REST request to save SituationRef : {}", situationRef);
        if (situationRef.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new situationRef cannot already have an ID").body(null);
        }
        SituationRef result = situationRefRepository.save(situationRef);
        return ResponseEntity.created(new URI("/api/situationRefs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("situationRef", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /situationRefs -> Updates an existing situationRef.
     */
    @RequestMapping(value = "/situationRefs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SituationRef> updateSituationRef(@RequestBody SituationRef situationRef) throws URISyntaxException {
        log.debug("REST request to update SituationRef : {}", situationRef);
        if (situationRef.getId() == null) {
            return createSituationRef(situationRef);
        }
        SituationRef result = situationRefRepository.save(situationRef);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("situationRef", situationRef.getId().toString()))
            .body(result);
    }

    /**
     * GET  /situationRefs -> get all the situationRefs.
     */
    @RequestMapping(value = "/situationRefs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SituationRef> getAllSituationRefs() {
        log.debug("REST request to get all SituationRefs");
        return situationRefRepository.findAll();
    }

    /**
     * GET  /situationRefs/:id -> get the "id" situationRef.
     */
    @RequestMapping(value = "/situationRefs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SituationRef> getSituationRef(@PathVariable Long id) {
        log.debug("REST request to get SituationRef : {}", id);
        return Optional.ofNullable(situationRefRepository.findOne(id))
            .map(situationRef -> new ResponseEntity<>(
                situationRef,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /situationRefs/:id -> delete the "id" situationRef.
     */
    @RequestMapping(value = "/situationRefs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSituationRef(@PathVariable Long id) {
        log.debug("REST request to delete SituationRef : {}", id);
        situationRefRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("situationRef", id.toString())).build();
    }
}
