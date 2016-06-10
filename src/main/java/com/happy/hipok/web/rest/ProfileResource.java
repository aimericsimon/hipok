package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.Profile;
import com.happy.hipok.repository.ProfileRepository;
import com.happy.hipok.web.rest.dto.ProfileDTO;
import com.happy.hipok.web.rest.mapper.ProfileMapper;
import com.happy.hipok.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Profile.
 */
@RestController
@RequestMapping("/api")
public class ProfileResource {

    private final Logger log = LoggerFactory.getLogger(ProfileResource.class);

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private ProfileMapper profileMapper;

    /**
     * POST  /profiles -> Create a new profile.
     */
    @RequestMapping(value = "/profiles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfileDTO> createProfile(@Valid @RequestBody ProfileDTO profileDTO) throws URISyntaxException {
        log.debug("REST request to save Profile : {}", profileDTO);
        if (profileDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("imageAuth", "idexists", "profile")).body(null);
        }
        Profile profile = profileMapper.profileDTOToProfile(profileDTO);
        profile = profileRepository.save(profile);
        ProfileDTO result = profileMapper.profileToProfileDTO(profile);
        return ResponseEntity.created(new URI("/api/profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("profile", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profiles -> Updates an existing profile.
     */
    @RequestMapping(value = "/profiles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfileDTO> updateProfile(@Valid @RequestBody ProfileDTO profileDTO) throws URISyntaxException {
        log.debug("REST request to update Profile : {}", profileDTO);
        if (profileDTO.getId() == null) {
            return createProfile(profileDTO);
        }
        Profile profile = profileMapper.profileDTOToProfile(profileDTO);
        profile = profileRepository.save(profile);
        ProfileDTO result = profileMapper.profileToProfileDTO(profile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("profile", profileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profiles -> get all the profiles.
     */
    @RequestMapping(value = "/profiles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<ProfileDTO> getAllProfiles() {
        log.debug("REST request to get all Profiles");
        return profileRepository.findAll().stream()
            .map(profileMapper::profileToProfileDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /profiles/:id -> get the "id" profile.
     */
    @RequestMapping(value = "/profiles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long id) {
        log.debug("REST request to get Profile : {}", id);
        Profile profile = profileRepository.findOne(id);
        ProfileDTO profileDTO = profileMapper.profileToProfileDTO(profile);
        return Optional.ofNullable(profileDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /profiles/:id -> delete the "id" profile.
     */
    @RequestMapping(value = "/profiles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        log.debug("REST request to delete Profile : {}", id);
        profileRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("profile", id.toString())).build();
    }
}
