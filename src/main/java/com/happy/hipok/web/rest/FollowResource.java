package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.Follow;
import com.happy.hipok.repository.FollowRepository;
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
 * REST controller for managing Follow.
 */
@RestController
@RequestMapping("/api")
public class FollowResource {

    private final Logger log = LoggerFactory.getLogger(FollowResource.class);

    @Inject
    private FollowRepository followRepository;

    /**
     * POST  /follows -> Create a new follow.
     */
    @RequestMapping(value = "/follows",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Follow> createFollow(@RequestBody Follow follow) throws URISyntaxException {
        log.debug("REST request to save Follow : {}", follow);
        if (follow.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("imageAuth", "idexists", "follow")).body(null);
        }
        Follow result = followRepository.save(follow);
        return ResponseEntity.created(new URI("/api/follows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("follow", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /follows -> Updates an existing follow.
     */
    @RequestMapping(value = "/follows",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Follow> updateFollow(@RequestBody Follow follow) throws URISyntaxException {
        log.debug("REST request to update Follow : {}", follow);
        if (follow.getId() == null) {
            return createFollow(follow);
        }
        Follow result = followRepository.save(follow);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("follow", follow.getId().toString()))
            .body(result);
    }

    /**
     * GET  /follows -> get all the follows.
     */
    @RequestMapping(value = "/follows",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Follow> getAllFollows() {
        log.debug("REST request to get all Follows");
        return followRepository.findAll();
            }

    /**
     * GET  /follows/:id -> get the "id" follow.
     */
    @RequestMapping(value = "/follows/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Follow> getFollow(@PathVariable Long id) {
        log.debug("REST request to get Follow : {}", id);
        Follow follow = followRepository.findOne(id);
        return Optional.ofNullable(follow)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /follows/:id -> delete the "id" follow.
     */
    @RequestMapping(value = "/follows/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFollow(@PathVariable Long id) {
        log.debug("REST request to delete Follow : {}", id);
        followRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("follow", id.toString())).build();
    }
}
