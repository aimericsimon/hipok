package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.DeclarationTypeRef;
import com.happy.hipok.repository.DeclarationTypeRefRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for managing TitleRef.
 */
@RestController
@RequestMapping("/app")
public class AppDeclarationTypeRefResource {

    private final Logger log = LoggerFactory.getLogger(AppDeclarationTypeRefResource.class);

    @Inject
    private DeclarationTypeRefRepository declarationTypeRefRepository;

    /**
     * GET  /titleRefs -> get all the declarationTypeRefs.
     */
    @RequestMapping(value = "/declarationTypeRefs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DeclarationTypeRef> getAllDeclarationTypeRefs() {
        log.debug("REST request to get all TitleRefs");
        return declarationTypeRefRepository.findAllByOrderByLabel();
    }
}
