package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.Declaration;
import com.happy.hipok.repository.DeclarationRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Declaration.
 */
@RestController
@RequestMapping("/api")
public class DeclarationResource {

    private final Logger log = LoggerFactory.getLogger(DeclarationResource.class);

    @Inject
    private DeclarationRepository declarationRepository;

    /**
     * POST  /declarations -> Create a new declaration.
     */
    @RequestMapping(value = "/declarations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Declaration> createDeclaration(@Valid @RequestBody Declaration declaration) throws URISyntaxException {
        log.debug("REST request to save Declaration : {}", declaration);
        if (declaration.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("imageAuth", "idexists", "declaration")).body(null);
        }
        Declaration result = declarationRepository.save(declaration);
        return ResponseEntity.created(new URI("/api/declarations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("declaration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /declarations -> Updates an existing declaration.
     */
    @RequestMapping(value = "/declarations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Declaration> updateDeclaration(@Valid @RequestBody Declaration declaration) throws URISyntaxException {
        log.debug("REST request to update Declaration : {}", declaration);
        if (declaration.getId() == null) {
            return createDeclaration(declaration);
        }
        Declaration result = declarationRepository.save(declaration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("declaration", declaration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /declarations -> get all the declarations.
     */
    @RequestMapping(value = "/declarations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Declaration>> getAllDeclarations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Declarations");
        Page<Declaration> page = declarationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/declarations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /declarations/:id -> get the "id" declaration.
     */
    @RequestMapping(value = "/declarations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Declaration> getDeclaration(@PathVariable Long id) {
        log.debug("REST request to get Declaration : {}", id);
        Declaration declaration = declarationRepository.findOne(id);
        return Optional.ofNullable(declaration)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /declarations/:id -> delete the "id" declaration.
     */
    @RequestMapping(value = "/declarations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDeclaration(@PathVariable Long id) {
        log.debug("REST request to delete Declaration : {}", id);
        declarationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("declaration", id.toString())).build();
    }
}
