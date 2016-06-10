package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.ImageAuth;
import com.happy.hipok.repository.ImageAuthRepository;
import com.happy.hipok.web.rest.util.HeaderUtil;
import com.happy.hipok.web.rest.dto.ImageAuthDTO;
import com.happy.hipok.web.rest.mapper.ImageAuthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing ImageAuth.
 */
@RestController
@RequestMapping("/api")
public class ImageAuthResource {

    private final Logger log = LoggerFactory.getLogger(ImageAuthResource.class);
        
    @Inject
    private ImageAuthRepository imageAuthRepository;
    
    @Inject
    private ImageAuthMapper imageAuthMapper;
    
    /**
     * POST  /image-auths : Create a new imageAuth.
     *
     * @param imageAuthDTO the imageAuthDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new imageAuthDTO, or with status 400 (Bad Request) if the imageAuth has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/image-auths",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ImageAuthDTO> createImageAuth(@RequestBody ImageAuthDTO imageAuthDTO) throws URISyntaxException {
        log.debug("REST request to save ImageAuth : {}", imageAuthDTO);
        if (imageAuthDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("imageAuth", "idexists", "A new imageAuth cannot already have an ID")).body(null);
        }
        ImageAuth imageAuth = imageAuthMapper.imageAuthDTOToImageAuth(imageAuthDTO);
        imageAuth = imageAuthRepository.save(imageAuth);
        ImageAuthDTO result = imageAuthMapper.imageAuthToImageAuthDTO(imageAuth);
        return ResponseEntity.created(new URI("/api/image-auths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("imageAuth", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /image-auths : Updates an existing imageAuth.
     *
     * @param imageAuthDTO the imageAuthDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated imageAuthDTO,
     * or with status 400 (Bad Request) if the imageAuthDTO is not valid,
     * or with status 500 (Internal Server Error) if the imageAuthDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/image-auths",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ImageAuthDTO> updateImageAuth(@RequestBody ImageAuthDTO imageAuthDTO) throws URISyntaxException {
        log.debug("REST request to update ImageAuth : {}", imageAuthDTO);
        if (imageAuthDTO.getId() == null) {
            return createImageAuth(imageAuthDTO);
        }
        ImageAuth imageAuth = imageAuthMapper.imageAuthDTOToImageAuth(imageAuthDTO);
        imageAuth = imageAuthRepository.save(imageAuth);
        ImageAuthDTO result = imageAuthMapper.imageAuthToImageAuthDTO(imageAuth);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("imageAuth", imageAuthDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /image-auths : get all the imageAuths.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of imageAuths in body
     */
    @RequestMapping(value = "/image-auths",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<ImageAuthDTO> getAllImageAuths() {
        log.debug("REST request to get all ImageAuths");
        List<ImageAuth> imageAuths = imageAuthRepository.findAll();
        return imageAuthMapper.imageAuthsToImageAuthDTOs(imageAuths);
    }

    /**
     * GET  /image-auths/:id : get the "id" imageAuth.
     *
     * @param id the id of the imageAuthDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imageAuthDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/image-auths/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ImageAuthDTO> getImageAuth(@PathVariable Long id) {
        log.debug("REST request to get ImageAuth : {}", id);
        ImageAuth imageAuth = imageAuthRepository.findOne(id);
        ImageAuthDTO imageAuthDTO = imageAuthMapper.imageAuthToImageAuthDTO(imageAuth);
        return Optional.ofNullable(imageAuthDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /image-auths/:id : delete the "id" imageAuth.
     *
     * @param id the id of the imageAuthDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/image-auths/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteImageAuth(@PathVariable Long id) {
        log.debug("REST request to delete ImageAuth : {}", id);
        imageAuthRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("imageAuth", id.toString())).build();
    }

}
