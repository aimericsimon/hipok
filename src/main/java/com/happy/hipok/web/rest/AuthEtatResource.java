package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.AuthEtat;
import com.happy.hipok.repository.AuthEtatRepository;
import com.happy.hipok.web.rest.util.HeaderUtil;
import com.happy.hipok.web.rest.dto.AuthEtatDTO;
import com.happy.hipok.web.rest.mapper.AuthEtatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing AuthEtat.
 */
@RestController
@RequestMapping("/api")
public class AuthEtatResource {

    private final Logger log = LoggerFactory.getLogger(AuthEtatResource.class);
        
    @Inject
    private AuthEtatRepository authEtatRepository;
    
    @Inject
    private AuthEtatMapper authEtatMapper;
    
    /**
     * POST  /auth-etats : Create a new authEtat.
     *
     * @param authEtatDTO the authEtatDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new authEtatDTO, or with status 400 (Bad Request) if the authEtat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/auth-etats",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthEtatDTO> createAuthEtat(@Valid @RequestBody AuthEtatDTO authEtatDTO) throws URISyntaxException {
        log.debug("REST request to save AuthEtat : {}", authEtatDTO);
        if (authEtatDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("authEtat", "idexists", "A new authEtat cannot already have an ID")).body(null);
        }
        AuthEtat authEtat = authEtatMapper.authEtatDTOToAuthEtat(authEtatDTO);
        authEtat = authEtatRepository.save(authEtat);
        AuthEtatDTO result = authEtatMapper.authEtatToAuthEtatDTO(authEtat);
        return ResponseEntity.created(new URI("/api/auth-etats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("authEtat", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auth-etats : Updates an existing authEtat.
     *
     * @param authEtatDTO the authEtatDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated authEtatDTO,
     * or with status 400 (Bad Request) if the authEtatDTO is not valid,
     * or with status 500 (Internal Server Error) if the authEtatDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/auth-etats",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthEtatDTO> updateAuthEtat(@Valid @RequestBody AuthEtatDTO authEtatDTO) throws URISyntaxException {
        log.debug("REST request to update AuthEtat : {}", authEtatDTO);
        if (authEtatDTO.getId() == null) {
            return createAuthEtat(authEtatDTO);
        }
        AuthEtat authEtat = authEtatMapper.authEtatDTOToAuthEtat(authEtatDTO);
        authEtat = authEtatRepository.save(authEtat);
        AuthEtatDTO result = authEtatMapper.authEtatToAuthEtatDTO(authEtat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("authEtat", authEtatDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auth-etats : get all the authEtats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of authEtats in body
     */
    @RequestMapping(value = "/auth-etats",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<AuthEtatDTO> getAllAuthEtats() {
        log.debug("REST request to get all AuthEtats");
        List<AuthEtat> authEtats = authEtatRepository.findAll();
        return authEtatMapper.authEtatsToAuthEtatDTOs(authEtats);
    }

    /**
     * GET  /auth-etats/:id : get the "id" authEtat.
     *
     * @param id the id of the authEtatDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authEtatDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/auth-etats/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthEtatDTO> getAuthEtat(@PathVariable Long id) {
        log.debug("REST request to get AuthEtat : {}", id);
        AuthEtat authEtat = authEtatRepository.findOne(id);
        AuthEtatDTO authEtatDTO = authEtatMapper.authEtatToAuthEtatDTO(authEtat);
        return Optional.ofNullable(authEtatDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /auth-etats/:id : delete the "id" authEtat.
     *
     * @param id the id of the authEtatDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/auth-etats/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAuthEtat(@PathVariable Long id) {
        log.debug("REST request to delete AuthEtat : {}", id);
        authEtatRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("authEtat", id.toString())).build();
    }

}
