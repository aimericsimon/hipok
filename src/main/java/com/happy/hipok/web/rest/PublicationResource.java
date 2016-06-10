package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.Publication;
import com.happy.hipok.repository.PublicationRepository;
import com.happy.hipok.web.rest.util.HeaderUtil;
import com.happy.hipok.web.rest.util.PaginationUtil;
import com.happy.hipok.web.rest.dto.PublicationDTO;
import com.happy.hipok.web.rest.mapper.PublicationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing Publication.
 */
@RestController
@RequestMapping("/api")
public class PublicationResource {

    private final Logger log = LoggerFactory.getLogger(PublicationResource.class);

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private PublicationMapper publicationMapper;

    /**
     * POST  /publications -> Create a new publication.
     */
    @RequestMapping(value = "/publications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PublicationDTO> createPublication(@Valid @RequestBody PublicationDTO publicationDTO) throws URISyntaxException {
        log.debug("REST request to save Publication : {}", publicationDTO);
        if (publicationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("imageAuth", "idexists", "publication")).body(null);
        }
        Publication publication = publicationMapper.publicationDTOToPublication(publicationDTO);
        publication = publicationRepository.save(publication);
        PublicationDTO result = publicationMapper.publicationToPublicationDTO(publication);
        return ResponseEntity.created(new URI("/api/publications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("publication", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /publications -> Updates an existing publication.
     */
    @RequestMapping(value = "/publications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PublicationDTO> updatePublication(@Valid @RequestBody PublicationDTO publicationDTO) throws URISyntaxException {
        log.debug("REST request to update Publication : {}", publicationDTO);
        if (publicationDTO.getId() == null) {
            return createPublication(publicationDTO);
        }
        Publication publication = publicationMapper.publicationDTOToPublication(publicationDTO);
        publication = publicationRepository.save(publication);
        PublicationDTO result = publicationMapper.publicationToPublicationDTO(publication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("publication", publicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /publications -> get all the publications.
     */
    @RequestMapping(value = "/publications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<PublicationDTO>> getAllPublications(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Publications");
        Page<Publication> page = publicationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/publications");
        return new ResponseEntity<>(page.getContent().stream()
            .map(publicationMapper::publicationToPublicationDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /publications/:id -> get the "id" publication.
     */
    @RequestMapping(value = "/publications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PublicationDTO> getPublication(@PathVariable Long id) {
        log.debug("REST request to get Publication : {}", id);
        Publication publication = publicationRepository.findOneWithEagerRelationships(id);
        PublicationDTO publicationDTO = publicationMapper.publicationToPublicationDTO(publication);
        return Optional.ofNullable(publicationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /publications/:id -> delete the "id" publication.
     */
    @RequestMapping(value = "/publications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        log.debug("REST request to delete Publication : {}", id);
        publicationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("publication", id.toString())).build();
    }
}
