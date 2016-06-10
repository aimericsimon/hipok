package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.Share;
import com.happy.hipok.repository.ShareRepository;
import com.happy.hipok.service.ShareService;
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
 * REST controller for managing Share.
 */
@RestController
@RequestMapping("/api")
public class ShareResource {

    private final Logger log = LoggerFactory.getLogger(ShareResource.class);

    @Inject
    private ShareRepository shareRepository;

    @Inject
    private ShareService shareService;

    /**
     * POST  /shares -> Create a new share.
     */
    @RequestMapping(value = "/shares",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Share> createShare(@RequestBody Share share) throws URISyntaxException {
        log.debug("REST request to save Share : {}", share);
        if (share.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new share cannot already have an ID").body(null);
        }
        Share result = shareRepository.save(share);
        shareService.sendEvent(result);

        return ResponseEntity.created(new URI("/api/shares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("share", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shares -> Updates an existing share.
     */
    @RequestMapping(value = "/shares",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Share> updateShare(@RequestBody Share share) throws URISyntaxException {
        log.debug("REST request to update Share : {}", share);
        if (share.getId() == null) {
            return createShare(share);
        }
        Share result = shareRepository.save(share);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("share", share.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shares -> get all the shares.
     */
    @RequestMapping(value = "/shares",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Share> getAllShares() {
        log.debug("REST request to get all Shares");
        return shareRepository.findAll();
    }

    /**
     * GET  /shares/:id -> get the "id" share.
     */
    @RequestMapping(value = "/shares/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Share> getShare(@PathVariable Long id) {
        log.debug("REST request to get Share : {}", id);
        return Optional.ofNullable(shareRepository.findOne(id))
            .map(share -> new ResponseEntity<>(
                share,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /shares/:id -> delete the "id" share.
     */
    @RequestMapping(value = "/shares/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteShare(@PathVariable Long id) {
        log.debug("REST request to delete Share : {}", id);
        shareRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("share", id.toString())).build();
    }
}
