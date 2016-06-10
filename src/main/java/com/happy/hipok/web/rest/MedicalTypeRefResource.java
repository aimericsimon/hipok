package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.MedicalTypeRef;
import com.happy.hipok.repository.MedicalTypeRefRepository;
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
 * REST controller for managing MedicalTypeRef.
 */
@RestController
@RequestMapping("/api")
public class MedicalTypeRefResource {

    private final Logger log = LoggerFactory.getLogger(MedicalTypeRefResource.class);

    @Inject
    private MedicalTypeRefRepository medicalTypeRefRepository;

    /**
     * POST  /medicalTypeRefs -> Create a new medicalTypeRef.
     */
    @RequestMapping(value = "/medicalTypeRefs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MedicalTypeRef> createMedicalTypeRef(@RequestBody MedicalTypeRef medicalTypeRef) throws URISyntaxException {
        log.debug("REST request to save MedicalTypeRef : {}", medicalTypeRef);
        if (medicalTypeRef.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new medicalTypeRef cannot already have an ID").body(null);
        }
        MedicalTypeRef result = medicalTypeRefRepository.save(medicalTypeRef);
        return ResponseEntity.created(new URI("/api/medicalTypeRefs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("medicalTypeRef", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medicalTypeRefs -> Updates an existing medicalTypeRef.
     */
    @RequestMapping(value = "/medicalTypeRefs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MedicalTypeRef> updateMedicalTypeRef(@RequestBody MedicalTypeRef medicalTypeRef) throws URISyntaxException {
        log.debug("REST request to update MedicalTypeRef : {}", medicalTypeRef);
        if (medicalTypeRef.getId() == null) {
            return createMedicalTypeRef(medicalTypeRef);
        }
        MedicalTypeRef result = medicalTypeRefRepository.save(medicalTypeRef);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("medicalTypeRef", medicalTypeRef.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medicalTypeRefs -> get all the medicalTypeRefs.
     */
    @RequestMapping(value = "/medicalTypeRefs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MedicalTypeRef> getAllMedicalTypeRefs() {
        log.debug("REST request to get all MedicalTypeRefs");
        return medicalTypeRefRepository.findAll();
    }

    /**
     * GET  /medicalTypeRefs/:id -> get the "id" medicalTypeRef.
     */
    @RequestMapping(value = "/medicalTypeRefs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MedicalTypeRef> getMedicalTypeRef(@PathVariable Long id) {
        log.debug("REST request to get MedicalTypeRef : {}", id);
        return Optional.ofNullable(medicalTypeRefRepository.findOne(id))
            .map(medicalTypeRef -> new ResponseEntity<>(
                medicalTypeRef,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /medicalTypeRefs/:id -> delete the "id" medicalTypeRef.
     */
    @RequestMapping(value = "/medicalTypeRefs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMedicalTypeRef(@PathVariable Long id) {
        log.debug("REST request to delete MedicalTypeRef : {}", id);
        medicalTypeRefRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("medicalTypeRef", id.toString())).build();
    }
}
