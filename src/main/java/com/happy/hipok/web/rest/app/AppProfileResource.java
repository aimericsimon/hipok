package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.Application;
import com.happy.hipok.domain.Follow;
import com.happy.hipok.domain.Profile;
import com.happy.hipok.domain.ProfileWithFollowCount;
import com.happy.hipok.domain.Suggestion;
import com.happy.hipok.repository.AddressRepository;
import com.happy.hipok.repository.FollowRepository;
import com.happy.hipok.repository.ProfileRepository;
import com.happy.hipok.service.ImageService;
import com.happy.hipok.service.ProfileService;
import com.happy.hipok.service.PublicationService;
import com.happy.hipok.web.rest.app.dto.AppLightProfileDTO;
import com.happy.hipok.web.rest.app.dto.AppProfileAvatarDTO;
import com.happy.hipok.web.rest.app.dto.AppProfileDTO;
import com.happy.hipok.web.rest.app.mapper.AppProfileMapper;
import com.happy.hipok.web.rest.errors.CustomParameterizedException;
import com.happy.hipok.web.rest.util.HeaderUtil;
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
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Profile
 */
@RestController
@RequestMapping("/app")
public class AppProfileResource {

    private final Logger log = LoggerFactory.getLogger(AppProfileResource.class);

    @Inject
    private ImageService imageService;

    @Inject
    private ProfileService profileService;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private PublicationService publicationService;

    @Inject
    private AddressRepository addressRepository;

    @Inject
    private FollowRepository followRepository;

    @Inject
    private AppProfileMapper appProfileMapper;

    /**
     * GET  /searchItems -> get the last published thumbnails
     */
    @RequestMapping(value = "/profile/search",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AppLightProfileDTO>> getSearchProfile(@RequestParam(value = "query") String query,
                                                                     Pageable pageable, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to search profile : {}", query);

        Page<Profile> profiles = profileService.search(query,pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(profiles, "/profile/search");
        return new ResponseEntity<>(profiles.getContent().stream()
            .map(profile -> appProfileMapper.profileToAppLightProfileDTO(profile, RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY))
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     *
     * @param pageable
     * @param request
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = "/profile/suggestion",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AppLightProfileDTO>> getSuggestionProfile(Pageable pageable, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to get suggestion profile");

        Page<Suggestion> suggestions = profileService.getSuggestions(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(suggestions, "/profile/suggestion");
        return new ResponseEntity<>(suggestions.getContent().stream()
            .map(profile -> appProfileMapper.suggestionToAppLightProfileDTO(profile, RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY))
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /profile/:id -> get the "id" profile.
     */
    @RequestMapping(value = "/profile/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppProfileDTO> getProfile(@PathVariable Long id, HttpServletRequest request) {

        log.debug("REST request to get profile : {}", id.toString());

        ProfileWithFollowCount profile = profileRepository.findProfileWithFollowCount(id);

        Follow follow = followRepository.findFollowing(id);

        return Optional.ofNullable(profile)
            .map(model -> appProfileMapper.profileToAppProfileDTO(profile,follow,RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY))
            .map(modelDTO -> new ResponseEntity<>(
                modelDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /profile -> get the current profile.
     */
    @RequestMapping(value = "/profile",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppProfileDTO> getScopeProfile(HttpServletRequest request) {

        log.debug("REST request to get current profile");

        ProfileWithFollowCount profile = profileRepository.findScopeProfileWithFollowCount();

        return Optional.ofNullable(profile)
            .map(model -> appProfileMapper.profileToAppProfileDTO(profile,null,RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY))
            .map(modelDTO -> new ResponseEntity<>(
                modelDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * PUT  /profile -> Updates an existing profile
     */
    @RequestMapping(value = "/profile",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> updateProfile(@Valid @RequestBody AppProfileDTO appProfileDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update Profile : {}", appProfileDTO);

        profileService.updateProfile(appProfileDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * PUT  /profile/avatar -> Updates an existing profile avatar.
     */
    @RequestMapping(value = "/profile/avatar",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppProfileAvatarDTO> updateAvatar(@Valid @RequestBody AppProfileAvatarDTO appAvatarDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update Profile Avatar : {}", appAvatarDTO);

        String[] paths = imageService.persistFullImageAndThumbnail(appAvatarDTO.getImageBinary());

        Profile profile  = profileRepository.findOne(appAvatarDTO.getId());

        if(!Optional.ofNullable(profile).isPresent()){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        profileService.setImageUrls(profile,paths);
        Profile result = profileRepository.save(profile);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("avatar", result.getId().toString()))
            .body(appProfileMapper.profileToAppProfileAvatarDTO(result,RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY));
    }

    @RequestMapping(value = "/profile/delete",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> deleteAccount() {
        log.debug("REST request to delete Profile");

        Profile currentProfile = profileService.getCurrentProfile();
        if (currentProfile == null) {
            throw new CustomParameterizedException("Pas de profil pour cet utilisateur.");
        }

        profileService.delete(currentProfile);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
