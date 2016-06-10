package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.Application;
import com.happy.hipok.domain.ScopeItem;
import com.happy.hipok.repository.PublicationRepository;
import com.happy.hipok.web.rest.app.dto.AppScopeItemDTO;
import com.happy.hipok.web.rest.app.mapper.AppScopeItemMapper;
import com.happy.hipok.web.rest.util.PaginationUtil;
import com.happy.hipok.web.rest.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing scope items.
 */
@RestController
@RequestMapping("/app")
public class AppScopeItemResource {

    private final Logger log = LoggerFactory.getLogger(AppScopeItemResource.class);

    @Inject
    private AppScopeItemMapper appScopeItemMapper;

    @Inject
    PublicationRepository publicationRepository;

    /**
     * GET  /scopeItems -> get all the publications on the timeline of a profile.
     */
    @RequestMapping(value = "/scopeItems",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AppScopeItemDTO>> getScopeItems(Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {

        log.debug("REST request to get scope items");

        Page<ScopeItem> page = publicationRepository.findScopeItems(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/app/scopeItems");
        return new ResponseEntity<>(page.getContent().stream()
            .map(scopeItem -> appScopeItemMapper.scopeItemToScopeItemDTO(scopeItem, RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY))
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /scopeItems -> get all the publications of a profile.
     */
    @RequestMapping(value = "/profile/scopeItems",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AppScopeItemDTO>> getProfileScopeItems(Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {

        log.debug("REST request to get profile scope items");

        Page<ScopeItem> page = publicationRepository.findProfileScopeItems(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/app/profile/scopeItems");
        return new ResponseEntity<>(page.getContent().stream()
            .map(scopeItem -> appScopeItemMapper.scopeItemToScopeItemDTO(scopeItem, RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY))
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /scopeItems -> get all the publications of a profile by id.
     */
    @RequestMapping(value = "/profile/scopeItems/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AppScopeItemDTO>> getProfileScopeItems(@PathVariable Long id, Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {

        log.debug("REST request to get profile scope items for profile id: {}",id);

        Page<ScopeItem> page = publicationRepository.findProfileScopeItems(id,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/app/profile/scopeItems");
        return new ResponseEntity<>(page.getContent().stream()
            .map(scopeItem -> appScopeItemMapper.scopeItemToScopeItemDTO(scopeItem, RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY))
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /scopeItems/:id -> get the "id" scope item.
     */
    @RequestMapping(value = "/scopeItems/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppScopeItemDTO> getScopeItem(@PathVariable Long id, HttpServletRequest request) {
        log.debug("REST request to get scope item : {}", id);

        return Optional.ofNullable(publicationRepository.findScopeItem(id))
            .map(scopeItem -> appScopeItemMapper.scopeItemToScopeItemDTO(scopeItem, RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY))
            .map(scopeItemDTO -> new ResponseEntity<>(
                scopeItemDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
