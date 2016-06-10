package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.Reporting;
import com.happy.hipok.repository.ReportingRepository;
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
 * REST controller for managing Reporting.
 */
@RestController
@RequestMapping("/api")
public class ReportingResource {

    private final Logger log = LoggerFactory.getLogger(ReportingResource.class);

    @Inject
    private ReportingRepository reportingRepository;

    /**
     * POST  /reportings -> Create a new reporting.
     */
    @RequestMapping(value = "/reportings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Reporting> createReporting(@RequestBody Reporting reporting) throws URISyntaxException {
        log.debug("REST request to save Reporting : {}", reporting);
        if (reporting.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new reporting cannot already have an ID").body(null);
        }
        Reporting result = reportingRepository.save(reporting);
        return ResponseEntity.created(new URI("/api/reportings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("reporting", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reportings -> Updates an existing reporting.
     */
    @RequestMapping(value = "/reportings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Reporting> updateReporting(@RequestBody Reporting reporting) throws URISyntaxException {
        log.debug("REST request to update Reporting : {}", reporting);
        if (reporting.getId() == null) {
            return createReporting(reporting);
        }
        Reporting result = reportingRepository.save(reporting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("reporting", reporting.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reportings -> get all the reportings.
     */
    @RequestMapping(value = "/reportings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Reporting> getAllReportings() {
        log.debug("REST request to get all Reportings");
        return reportingRepository.findAll();
    }

    /**
     * GET  /reportings/:id -> get the "id" reporting.
     */
    @RequestMapping(value = "/reportings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Reporting> getReporting(@PathVariable Long id) {
        log.debug("REST request to get Reporting : {}", id);
        return Optional.ofNullable(reportingRepository.findOne(id))
            .map(reporting -> new ResponseEntity<>(
                reporting,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /reportings/:id -> delete the "id" reporting.
     */
    @RequestMapping(value = "/reportings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteReporting(@PathVariable Long id) {
        log.debug("REST request to delete Reporting : {}", id);
        reportingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("reporting", id.toString())).build();
    }
}
