package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.MedicalTypeRef;
import com.happy.hipok.domain.enumeration.MedicalSubType;
import com.happy.hipok.repository.MedicalTypeRefRepository;
import com.happy.hipok.security.AuthoritiesConstants;
import com.happy.hipok.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MedicalTypeRef.
 */
@RestController
@RequestMapping("/app")
public class AppMedicalTypeRefResource {

    private final Logger log = LoggerFactory.getLogger(AppMedicalTypeRefResource.class);

    @Inject
    private MedicalTypeRefRepository medicalTypeRefRepository;

    /**
     * GET  /medicalTypeRefs -> get all the medicalTypeRefs.
     */
    @RequestMapping(value = "/medicalTypeRefs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MedicalTypeRef> getAllMedicalTypeRefs(@RequestParam Optional<MedicalSubType> subtype) {
        log.debug("REST request to get all MedicalTypeRefs");

        if (subtype.isPresent()) {
            return medicalTypeRefRepository.findAllBySubtypeOrderByLabel(subtype.get());
        }
        return medicalTypeRefRepository.findAllByOrderByLabel();

    }
}
