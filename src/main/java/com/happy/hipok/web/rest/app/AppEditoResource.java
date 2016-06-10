package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.Edito;
import com.happy.hipok.repository.EditoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/app/edito")
public class AppEditoResource {

    private final Logger log = LoggerFactory.getLogger(AppTitleRefResource.class);

    @Inject
    private EditoRepository editoRepository;

    /**
     * GET  /edito -> get all the edito.
     */
    @RequestMapping(value = "",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Edito> getAllEditos() {
        log.debug("REST request to get all edito entities");
        return editoRepository.findAll();
    }

    /**
     * GET  /edito/:id -> get the "id" edito.
     */
    @RequestMapping(value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Edito> getEdito(@PathVariable Long id, HttpServletRequest request) {

        log.debug("REST request to get edito : {}", id.toString());

        Edito edito = editoRepository.findOne(id);

        return Optional.ofNullable(edito)
            .map(modelDTO -> new ResponseEntity<>(
                modelDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
