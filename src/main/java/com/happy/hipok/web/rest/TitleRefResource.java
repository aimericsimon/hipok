package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.TitleRef;
import com.happy.hipok.repository.TitleRefRepository;
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
 * REST controller for managing TitleRef.
 */
@RestController
@RequestMapping("/api")
public class TitleRefResource {

    private final Logger log = LoggerFactory.getLogger(TitleRefResource.class);

    @Inject
    private TitleRefRepository titleRefRepository;

    /**
     * POST  /titleRefs -> Create a new titleRef.
     */
    @RequestMapping(value = "/titleRefs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TitleRef> createTitleRef(@RequestBody TitleRef titleRef) throws URISyntaxException {
        log.debug("REST request to save TitleRef : {}", titleRef);
        if (titleRef.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new titleRef cannot already have an ID").body(null);
        }
        TitleRef result = titleRefRepository.save(titleRef);
        return ResponseEntity.created(new URI("/api/titleRefs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("titleRef", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /titleRefs -> Updates an existing titleRef.
     */
    @RequestMapping(value = "/titleRefs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TitleRef> updateTitleRef(@RequestBody TitleRef titleRef) throws URISyntaxException {
        log.debug("REST request to update TitleRef : {}", titleRef);
        if (titleRef.getId() == null) {
            return createTitleRef(titleRef);
        }
        TitleRef result = titleRefRepository.save(titleRef);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("titleRef", titleRef.getId().toString()))
            .body(result);
    }

    /**
     * GET  /titleRefs -> get all the titleRefs.
     */
    @RequestMapping(value = "/titleRefs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TitleRef> getAllTitleRefs() {
        log.debug("REST request to get all TitleRefs");
        return titleRefRepository.findAll();
    }

    /**
     * GET  /titleRefs/:id -> get the "id" titleRef.
     */
    @RequestMapping(value = "/titleRefs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TitleRef> getTitleRef(@PathVariable Long id) {
        log.debug("REST request to get TitleRef : {}", id);
        return Optional.ofNullable(titleRefRepository.findOne(id))
            .map(titleRef -> new ResponseEntity<>(
                titleRef,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /titleRefs/:id -> delete the "id" titleRef.
     */
    @RequestMapping(value = "/titleRefs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTitleRef(@PathVariable Long id) {
        log.debug("REST request to delete TitleRef : {}", id);
        titleRefRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("titleRef", id.toString())).build();
    }
}
