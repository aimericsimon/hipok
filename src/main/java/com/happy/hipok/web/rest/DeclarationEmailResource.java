package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.DeclarationEmail;
import com.happy.hipok.repository.DeclarationEmailRepository;
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
 * REST controller for managing DeclarationEmail.
 */
@RestController
@RequestMapping("/api")
public class DeclarationEmailResource {

    private final Logger log = LoggerFactory.getLogger(DeclarationEmailResource.class);

    @Inject
    private DeclarationEmailRepository declarationEmailRepository;

    /**
     * POST  /declarationEmails -> Create a new declarationEmail.
     */
    @RequestMapping(value = "/declarationEmails",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeclarationEmail> createDeclarationEmail(@RequestBody DeclarationEmail declarationEmail) throws URISyntaxException {
        log.debug("REST request to save DeclarationEmail : {}", declarationEmail);
        if (declarationEmail.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("imageAuth", "idexists", "declarationEmail")).body(null);
        }
        DeclarationEmail result = declarationEmailRepository.save(declarationEmail);
        return ResponseEntity.created(new URI("/api/declarationEmails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("declarationEmail", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /declarationEmails -> Updates an existing declarationEmail.
     */
    @RequestMapping(value = "/declarationEmails",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeclarationEmail> updateDeclarationEmail(@RequestBody DeclarationEmail declarationEmail) throws URISyntaxException {
        log.debug("REST request to update DeclarationEmail : {}", declarationEmail);
        if (declarationEmail.getId() == null) {
            return createDeclarationEmail(declarationEmail);
        }
        DeclarationEmail result = declarationEmailRepository.save(declarationEmail);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("declarationEmail", declarationEmail.getId().toString()))
            .body(result);
    }

    /**
     * GET  /declarationEmails -> get all the declarationEmails.
     */
    @RequestMapping(value = "/declarationEmails",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DeclarationEmail> getAllDeclarationEmails() {
        log.debug("REST request to get all DeclarationEmails");
        return declarationEmailRepository.findAll();
            }

    /**
     * GET  /declarationEmails/:id -> get the "id" declarationEmail.
     */
    @RequestMapping(value = "/declarationEmails/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeclarationEmail> getDeclarationEmail(@PathVariable Long id) {
        log.debug("REST request to get DeclarationEmail : {}", id);
        DeclarationEmail declarationEmail = declarationEmailRepository.findOne(id);
        return Optional.ofNullable(declarationEmail)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /declarationEmails/:id -> delete the "id" declarationEmail.
     */
    @RequestMapping(value = "/declarationEmails/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDeclarationEmail(@PathVariable Long id) {
        log.debug("REST request to delete DeclarationEmail : {}", id);
        declarationEmailRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("declarationEmail", id.toString())).build();
    }
}
