package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.SpecialtyRef;
import com.happy.hipok.repository.SpecialtyRefRepository;
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
 * REST controller for managing SpecialtyRef.
 */
@RestController
@RequestMapping("/api")
public class SpecialtyRefResource {

    private final Logger log = LoggerFactory.getLogger(SpecialtyRefResource.class);

    @Inject
    private SpecialtyRefRepository specialtyRefRepository;

    /**
     * POST  /specialtyRefs -> Create a new specialtyRef.
     */
    @RequestMapping(value = "/specialtyRefs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SpecialtyRef> createSpecialtyRef(@RequestBody SpecialtyRef specialtyRef) throws URISyntaxException {
        log.debug("REST request to save SpecialtyRef : {}", specialtyRef);
        if (specialtyRef.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new specialtyRef cannot already have an ID").body(null);
        }
        SpecialtyRef result = specialtyRefRepository.save(specialtyRef);
        return ResponseEntity.created(new URI("/api/specialtyRefs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("specialtyRef", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /specialtyRefs -> Updates an existing specialtyRef.
     */
    @RequestMapping(value = "/specialtyRefs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SpecialtyRef> updateSpecialtyRef(@RequestBody SpecialtyRef specialtyRef) throws URISyntaxException {
        log.debug("REST request to update SpecialtyRef : {}", specialtyRef);
        if (specialtyRef.getId() == null) {
            return createSpecialtyRef(specialtyRef);
        }
        SpecialtyRef result = specialtyRefRepository.save(specialtyRef);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("specialtyRef", specialtyRef.getId().toString()))
            .body(result);
    }

    /**
     * GET  /specialtyRefs -> get all the specialtyRefs.
     */
    @RequestMapping(value = "/specialtyRefs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SpecialtyRef> getAllSpecialtyRefs() {
        log.debug("REST request to get all SpecialtyRefs");
        return specialtyRefRepository.findAll();
    }

    /**
     * GET  /specialtyRefs/:id -> get the "id" specialtyRef.
     */
    @RequestMapping(value = "/specialtyRefs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SpecialtyRef> getSpecialtyRef(@PathVariable Long id) {
        log.debug("REST request to get SpecialtyRef : {}", id);
        return Optional.ofNullable(specialtyRefRepository.findOne(id))
            .map(specialtyRef -> new ResponseEntity<>(
                specialtyRef,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /specialtyRefs/:id -> delete the "id" specialtyRef.
     */
    @RequestMapping(value = "/specialtyRefs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSpecialtyRef(@PathVariable Long id) {
        log.debug("REST request to delete SpecialtyRef : {}", id);
        specialtyRefRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("specialtyRef", id.toString())).build();
    }
}
