package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.ExtendedUser;
import com.happy.hipok.domain.Profile;
import com.happy.hipok.repository.ExtendedUserRepository;
import com.happy.hipok.service.ExtendedUserService;
import com.happy.hipok.service.ProfileService;
import com.happy.hipok.web.rest.app.dto.AppExtendedUserDTO;
import com.happy.hipok.web.rest.app.mapper.AppExtendedUserMapper;
import com.happy.hipok.web.rest.dto.ExtendedUservalidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Created by Dahwoud on 05/01/2016.
 */
@RestController
@RequestMapping("/app")
public class AppExtendedUserResource {

    private final Logger log = LoggerFactory.getLogger(AppExtendedUserResource.class);

    @Inject
    private ExtendedUserRepository extendedUserRepository;

    @Inject
    private ExtendedUserService extendedUserService;

    @Inject
    private ProfileService profileService;

    @Inject
    private AppExtendedUserMapper extendedUserMapper;

    /**
     * PUT  /account -> Updates an existing user / extendedUser.
     */
    @RequestMapping(value = "/extendedUser",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExtendedUservalidationMessage> updateExtendedUser(@Valid @RequestBody AppExtendedUserDTO appExtendedUserDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update account : {}", appExtendedUserDTO);

        ExtendedUservalidationMessage message = extendedUserService.validateAppExtendedUserDTO(appExtendedUserDTO,request);

        if(message != null)
        {
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }

        extendedUserService.updateExtendedUser(appExtendedUserDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/extendedUser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppExtendedUserDTO> getExtenderUser() {

        Profile profile = profileService.getCurrentProfile();

        ExtendedUser extendedUser = extendedUserRepository.findOne(profile.getExtendedUser().getId());

        return Optional.ofNullable(extendedUser)
            .map(user -> new ResponseEntity<>(extendedUserMapper.extendedUserToappExtendedUserDTO(extendedUser), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
