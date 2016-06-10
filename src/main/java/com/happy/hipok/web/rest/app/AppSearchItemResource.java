package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.Application;
import com.happy.hipok.domain.Publication;
import com.happy.hipok.repository.PublicationRepository;
import com.happy.hipok.web.rest.app.dto.AppSearchItemDTO;
import com.happy.hipok.web.rest.mapper.SearchItemMapper;
import com.happy.hipok.web.rest.util.PaginationUtil;
import com.happy.hipok.web.rest.util.RequestUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing sescopearch items.
 */
@RestController
@RequestMapping("/app")
public class AppSearchItemResource {

    @Inject
    private SearchItemMapper searchItemMapper;

    @Inject
    PublicationRepository publicationRepository;

    /**
     * GET  /searchItems -> get the last published thumbnails
     */
    @RequestMapping(value = "/searchItems",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AppSearchItemDTO>> getSearchItems(Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        Page<Publication> page = publicationRepository.findPublications(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/app/searchItems");
        return new ResponseEntity<>(page.getContent().stream()
            .map(publication -> searchItemMapper.publicationToSearchItemDTO(publication, RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY))
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /searchItems -> get the last published thumbnails
     */
    @RequestMapping(value = "/searchItems/search",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AppSearchItemDTO>> getSearchItemsWithQuery(@RequestParam(value = "q") Optional<String> query,
                                                                          @RequestParam(value = "azId") Optional<Long> anatomicZoneId,
                                                                          @RequestParam(value = "sId") Optional<Long> specialtyId,
                                                                          Pageable pageable, HttpServletRequest request) throws URISyntaxException {

        Page<Publication> page = publicationRepository.findPublicationsWithQuery(query.isPresent() ? query.get().toUpperCase() : "",
            anatomicZoneId.isPresent() ? anatomicZoneId.get() : null,
            specialtyId.isPresent() ? specialtyId.get() : null,
            pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/app/searchItems");
        return new ResponseEntity<>(page.getContent().stream()
            .map(publication -> searchItemMapper.publicationToSearchItemDTO(publication, RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY))
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }
}
