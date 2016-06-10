package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.Edito;
import com.happy.hipok.repository.EditoRepository;
import com.happy.hipok.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Edito.
 */
@RestController
@RequestMapping("/api")
public class EditoResource {

    private final Logger log = LoggerFactory.getLogger(EditoResource.class);

    @Inject
    private EditoRepository editoRepository;

    /**
     * POST  /editos -> Create a new edito.
     */
    @RequestMapping(value = "/editos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Edito> createEdito(@Valid @RequestBody Edito edito) throws URISyntaxException {
        log.debug("REST request to save Edito : {}", edito);
        if (edito.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new edito cannot already have an ID").body(null);
        }
        Edito result = editoRepository.save(edito);
        return ResponseEntity.created(new URI("/api/editos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("edito", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /editos -> Updates an existing edito.
     */
    @RequestMapping(value = "/editos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Edito> updateEdito(@Valid @RequestBody Edito edito) throws URISyntaxException {
        log.debug("REST request to update Edito : {}", edito);
        if (edito.getId() == null) {
            return createEdito(edito);
        }
        Edito result = editoRepository.save(edito);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("edito", edito.getId().toString()))
            .body(result);
    }

    /**
     * GET  /editos -> get all the editos.
     */
    @RequestMapping(value = "/editos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Edito> getAllEditos() {
        log.debug("REST request to get all Editos");
        return editoRepository.findAll();
    }

    /**
     * GET  /editos/:id -> get the "id" edito.
     */
    @RequestMapping(value = "/editos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Edito> getEdito(@PathVariable Long id) {
        log.debug("REST request to get Edito : {}", id);
        return Optional.ofNullable(editoRepository.findOne(id))
            .map(edito -> new ResponseEntity<>(
                edito,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /editos/:id -> delete the "id" edito.
     */
    @RequestMapping(value = "/editos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEdito(@PathVariable Long id) {
        log.debug("REST request to delete Edito : {}", id);
        editoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("edito", id.toString())).build();
    }
}
