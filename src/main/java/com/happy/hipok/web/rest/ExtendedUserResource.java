package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.ExtendedUser;
import com.happy.hipok.repository.ExtendedUserRepository;
import com.happy.hipok.web.rest.util.HeaderUtil;
import com.happy.hipok.web.rest.dto.ExtendedUserDTO;
import com.happy.hipok.web.rest.mapper.ExtendedUserMapper;
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
 * REST controller for managing ExtendedUser.
 */
@RestController
@RequestMapping("/api")
public class ExtendedUserResource {

    private final Logger log = LoggerFactory.getLogger(ExtendedUserResource.class);

    @Inject
    private ExtendedUserRepository extendedUserRepository;

    @Inject
    private ExtendedUserMapper extendedUserMapper;

    /**
     * POST  /extendedUsers -> Create a new extendedUser.
     */
    @RequestMapping(value = "/extendedUsers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExtendedUserDTO> createExtendedUser(@Valid @RequestBody ExtendedUserDTO extendedUserDTO) throws URISyntaxException {
        log.debug("REST request to save ExtendedUser : {}", extendedUserDTO);
        if (extendedUserDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new extendedUser cannot already have an ID").body(null);
        }
        ExtendedUser extendedUser = extendedUserMapper.extendedUserDTOToExtendedUser(extendedUserDTO);
        ExtendedUser result = extendedUserRepository.save(extendedUser);
        return ResponseEntity.created(new URI("/api/extendedUsers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("extendedUser", result.getId().toString()))
            .body(extendedUserMapper.extendedUserToExtendedUserDTO(result));
    }

    /**
     * PUT  /extendedUsers -> Updates an existing extendedUser.
     */
    @RequestMapping(value = "/extendedUsers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExtendedUserDTO> updateExtendedUser(@Valid @RequestBody ExtendedUserDTO extendedUserDTO) throws URISyntaxException {
        log.debug("REST request to update ExtendedUser : {}", extendedUserDTO);
        if (extendedUserDTO.getId() == null) {
            return createExtendedUser(extendedUserDTO);
        }
        ExtendedUser extendedUser = extendedUserMapper.extendedUserDTOToExtendedUser(extendedUserDTO);
        ExtendedUser result = extendedUserRepository.save(extendedUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("extendedUser", extendedUserDTO.getId().toString()))
            .body(extendedUserMapper.extendedUserToExtendedUserDTO(result));
    }

    /**
     * GET  /extendedUsers -> get all the extendedUsers.
     */
    @RequestMapping(value = "/extendedUsers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<ExtendedUserDTO> getAllExtendedUsers() {
        log.debug("REST request to get all ExtendedUsers");
        return extendedUserRepository.findAll().stream()
            .map(extendedUser -> extendedUserMapper.extendedUserToExtendedUserDTO(extendedUser))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /extendedUsers/:id -> get the "id" extendedUser.
     */
    @RequestMapping(value = "/extendedUsers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExtendedUserDTO> getExtendedUser(@PathVariable Long id) {
        log.debug("REST request to get ExtendedUser : {}", id);
        return Optional.ofNullable(extendedUserRepository.findOne(id))
            .map(extendedUserMapper::extendedUserToExtendedUserDTO)
            .map(extendedUserDTO -> new ResponseEntity<>(
                extendedUserDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /extendedUsers/:id -> delete the "id" extendedUser.
     */
    @RequestMapping(value = "/extendedUsers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteExtendedUser(@PathVariable Long id) {
        log.debug("REST request to delete ExtendedUser : {}", id);
        extendedUserRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("extendedUser", id.toString())).build();
    }
}
