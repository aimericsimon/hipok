package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.Hashtag;
import com.happy.hipok.repository.HashtagRepository;
import com.happy.hipok.web.rest.util.HeaderUtil;
import com.happy.hipok.web.rest.dto.HashtagDTO;
import com.happy.hipok.web.rest.mapper.HashtagMapper;
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
 * REST controller for managing Hashtag.
 */
@RestController
@RequestMapping("/api")
public class HashtagResource {

    private final Logger log = LoggerFactory.getLogger(HashtagResource.class);

    @Inject
    private HashtagRepository hashtagRepository;

    @Inject
    private HashtagMapper hashtagMapper;

    /**
     * POST  /hashtags -> Create a new hashtag.
     */
    @RequestMapping(value = "/hashtags",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HashtagDTO> createHashtag(@Valid @RequestBody HashtagDTO hashtagDTO) throws URISyntaxException {
        log.debug("REST request to save Hashtag : {}", hashtagDTO);
        if (hashtagDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new hashtag cannot already have an ID").body(null);
        }
        Hashtag hashtag = hashtagMapper.hashtagDTOToHashtag(hashtagDTO);
        Hashtag result = hashtagRepository.save(hashtag);
        return ResponseEntity.created(new URI("/api/hashtags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hashtag", result.getId().toString()))
            .body(hashtagMapper.hashtagToHashtagDTO(result));
    }

    /**
     * PUT  /hashtags -> Updates an existing hashtag.
     */
    @RequestMapping(value = "/hashtags",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HashtagDTO> updateHashtag(@Valid @RequestBody HashtagDTO hashtagDTO) throws URISyntaxException {
        log.debug("REST request to update Hashtag : {}", hashtagDTO);
        if (hashtagDTO.getId() == null) {
            return createHashtag(hashtagDTO);
        }
        Hashtag hashtag = hashtagMapper.hashtagDTOToHashtag(hashtagDTO);
        Hashtag result = hashtagRepository.save(hashtag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hashtag", hashtagDTO.getId().toString()))
            .body(hashtagMapper.hashtagToHashtagDTO(result));
    }

    /**
     * GET  /hashtags -> get all the hashtags.
     */
    @RequestMapping(value = "/hashtags",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<HashtagDTO> getAllHashtags() {
        log.debug("REST request to get all Hashtags");
        return hashtagRepository.findAll().stream()
            .map(hashtag -> hashtagMapper.hashtagToHashtagDTO(hashtag))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /hashtags/:id -> get the "id" hashtag.
     */
    @RequestMapping(value = "/hashtags/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HashtagDTO> getHashtag(@PathVariable Long id) {
        log.debug("REST request to get Hashtag : {}", id);
        return Optional.ofNullable(hashtagRepository.findOne(id))
            .map(hashtagMapper::hashtagToHashtagDTO)
            .map(hashtagDTO -> new ResponseEntity<>(
                hashtagDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hashtags/:id -> delete the "id" hashtag.
     */
    @RequestMapping(value = "/hashtags/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHashtag(@PathVariable Long id) {
        log.debug("REST request to delete Hashtag : {}", id);
        hashtagRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hashtag", id.toString())).build();
    }
}
