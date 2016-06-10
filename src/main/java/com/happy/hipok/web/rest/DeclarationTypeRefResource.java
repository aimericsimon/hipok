package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.DeclarationTypeRef;
import com.happy.hipok.repository.DeclarationTypeRefRepository;
import com.happy.hipok.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing DeclarationTypeRef.
 */
@RestController
@RequestMapping("/api")
public class DeclarationTypeRefResource {

    private final Logger log = LoggerFactory.getLogger(DeclarationTypeRefResource.class);

    @Inject
    private DeclarationTypeRefRepository declarationTypeRefRepository;

    /**
     * POST  /declarationTypeRefs -> Create a new declarationTypeRef.
     */
    @RequestMapping(value = "/declarationTypeRefs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeclarationTypeRef> createDeclarationTypeRef(@RequestBody DeclarationTypeRef declarationTypeRef) throws URISyntaxException {
        log.debug("REST request to save DeclarationTypeRef : {}", declarationTypeRef);
        if (declarationTypeRef.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("imageAuth", "idexists", "declarationTypeRef")).body(null);
        }
        DeclarationTypeRef result = declarationTypeRefRepository.save(declarationTypeRef);
        return ResponseEntity.created(new URI("/api/declarationTypeRefs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("declarationTypeRef", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /declarationTypeRefs -> Updates an existing declarationTypeRef.
     */
    @RequestMapping(value = "/declarationTypeRefs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeclarationTypeRef> updateDeclarationTypeRef(@RequestBody DeclarationTypeRef declarationTypeRef) throws URISyntaxException {
        log.debug("REST request to update DeclarationTypeRef : {}", declarationTypeRef);
        if (declarationTypeRef.getId() == null) {
            return createDeclarationTypeRef(declarationTypeRef);
        }
        DeclarationTypeRef result = declarationTypeRefRepository.save(declarationTypeRef);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("declarationTypeRef", declarationTypeRef.getId().toString()))
            .body(result);
    }

    /**
     * GET  /declarationTypeRefs -> get all the declarationTypeRefs.
     */
    @RequestMapping(value = "/declarationTypeRefs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DeclarationTypeRef> getAllDeclarationTypeRefs() {
        log.debug("REST request to get all DeclarationTypeRefs");
        return declarationTypeRefRepository.findAll();
            }

    /**
     * GET  /declarationTypeRefs/:id -> get the "id" declarationTypeRef.
     */
    @RequestMapping(value = "/declarationTypeRefs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeclarationTypeRef> getDeclarationTypeRef(@PathVariable Long id) {
        log.debug("REST request to get DeclarationTypeRef : {}", id);
        DeclarationTypeRef declarationTypeRef = declarationTypeRefRepository.findOne(id);
        return Optional.ofNullable(declarationTypeRef)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /declarationTypeRefs/:id -> delete the "id" declarationTypeRef.
     */
    @RequestMapping(value = "/declarationTypeRefs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDeclarationTypeRef(@PathVariable Long id) {
        log.debug("REST request to delete DeclarationTypeRef : {}", id);
        declarationTypeRefRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("declarationTypeRef", id.toString())).build();
    }
}
