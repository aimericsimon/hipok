package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.ExtendedUser;
import com.happy.hipok.repository.ExtendedUserRepository;
import com.happy.hipok.repository.UserRepository;
import com.happy.hipok.security.SecurityUtils;
import com.happy.hipok.service.ExtendedUserService;
import com.happy.hipok.service.MailService;
import com.happy.hipok.service.ProfileService;
import com.happy.hipok.service.UserService;
import com.happy.hipok.web.rest.app.dto.AppChangePasswordDTO;
import com.happy.hipok.web.rest.app.dto.RegisterAppExtendedUserDTO;
import com.happy.hipok.web.rest.app.mapper.AppExtendedUserMapper;
import com.happy.hipok.web.rest.dto.ExtendedUservalidationMessage;
import com.happy.hipok.web.rest.dto.KeyAndPasswordDTO;
import com.happy.hipok.web.rest.dto.UserDTO;
import com.happy.hipok.web.rest.util.RequestUtils;
import com.happy.hipok.web.rest.util.ResponseEntityUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/app")
public class AppAccountResource {

    private final Logger log = LoggerFactory.getLogger(AppAccountResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private ExtendedUserRepository extendedUserRepository;

    @Inject
    private AppExtendedUserMapper appExtendedUserMapper;

    @Inject
    private UserService userService;

    @Inject
    private MailService mailService;

    @Inject
    private ExtendedUserService extendedUserService;

    @Inject
    private ProfileService profileService;

    /**
     * POST  /register -> register the user.
     */
    @RequestMapping(value = "/register",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExtendedUservalidationMessage> registerAccount(@Valid @RequestBody RegisterAppExtendedUserDTO registerAppExtendedUserDTO, HttpServletRequest request) {

        ExtendedUservalidationMessage message = extendedUserService.validateRegisterAppExtendedUserDTO(registerAppExtendedUserDTO,request);

        if(message != null)
        {
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }

        ExtendedUser extendedUser = extendedUserService.createExtendedUser(registerAppExtendedUserDTO);

        ExtendedUser createdExtendedUser = extendedUserRepository.findOne(extendedUser.getId());

        mailService.sendActivationEmail(createdExtendedUser, RequestUtils.getBaseUrl(request));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * GET  /activate -> activate the registered user.
     */
    @RequestMapping(value = "/activate",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        return Optional.ofNullable(userService.activateRegistration(key))
            .map(user -> new ResponseEntity<String>(HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /account -> update the current user information.
     */
    @RequestMapping(value = "/account",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> saveAccount(@RequestBody UserDTO userDTO) {
        return userRepository
            .findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
            .map(u -> {
                userService.updateUserInformation(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                    userDTO.getLangKey());
                return new ResponseEntity<String>(HttpStatus.OK);
            })
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET  /authenticate -> check if the user is authenticated, and return its login.
     */
    @RequestMapping(value = "/authenticate",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }



    @RequestMapping(value = "/account",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserDTO> getAccount() {
        return Optional.ofNullable(userService.getUserWithAuthorities())
            .map(user -> new ResponseEntity<>(new UserDTO(user), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /change_password -> changes the current user's password
     */
    @RequestMapping(value = "/account/change_password",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> changePassword(@RequestBody AppChangePasswordDTO appChangePasswordDTO) {
        if (!checkPasswordLength(appChangePasswordDTO.getOldPassword())) {
            return ResponseEntityUtil.getResponse("Ancien mot de passe incorrect", HttpStatus.BAD_REQUEST);
        }

        if (!checkPasswordLength(appChangePasswordDTO.getNewPassword())) {
            return ResponseEntityUtil.getResponse("Nouveau mot de passe incorrect", HttpStatus.BAD_REQUEST);
        }

        if(!userService.changePassword(appChangePasswordDTO.getOldPassword(),appChangePasswordDTO.getNewPassword())){
            return ResponseEntityUtil.getResponse("L'ancien mot de passe ne correspond pas à celui enregistré", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/account/reset_password/init",
        method = RequestMethod.POST,
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> requestPasswordReset(@RequestBody String mail, HttpServletRequest request) {
        log.debug("Mail envoyé: " + mail);
        return userService.requestPasswordReset(mail)
            .map(user -> {
                mailService.sendPasswordResetMail(user, RequestUtils.getBaseUrl(request));
                return new ResponseEntity<>("Un e-mail a été envoyé", HttpStatus.OK);
            }).orElse(new ResponseEntity<>("L'adresse e-mail n'est pas valide", HttpStatus.BAD_REQUEST));
    }

    @RequestMapping(value = "/account/reset_password/finish",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> finishPasswordReset(@RequestBody KeyAndPasswordDTO keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            return new ResponseEntity<>("Mot de passe incorrect", HttpStatus.BAD_REQUEST);
        }
        return userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
            .map(user -> new ResponseEntity<String>(HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private boolean checkPasswordLength(String password) {
        return (!StringUtils.isEmpty(password) &&
            password.length() >= UserDTO.PASSWORD_MIN_LENGTH &&
            password.length() <= UserDTO.PASSWORD_MAX_LENGTH);
    }
}
