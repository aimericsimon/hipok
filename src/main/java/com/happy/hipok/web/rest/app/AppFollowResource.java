package com.happy.hipok.web.rest.app;


import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.Application;
import com.happy.hipok.domain.Follow;
import com.happy.hipok.domain.Profile;
import com.happy.hipok.domain.enumeration.State;
import com.happy.hipok.repository.FollowRepository;
import com.happy.hipok.repository.ProfileRepository;
import com.happy.hipok.service.FollowService;
import com.happy.hipok.service.ProfileService;
import com.happy.hipok.web.rest.app.dto.AppFollowDTO;
import com.happy.hipok.web.rest.app.mapper.AppFollowMapper;
import com.happy.hipok.web.rest.errors.CustomParameterizedException;
import com.happy.hipok.web.rest.util.HeaderUtil;
import com.happy.hipok.web.rest.util.RequestUtils;
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
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/app")
public class AppFollowResource {

    private final Logger log = LoggerFactory.getLogger(AppFollowResource.class);

    @Inject
    private FollowRepository followRepository;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private ProfileService profileService;

    @Inject
    private AppFollowMapper appFollowMapper;

    @Inject
    private FollowService followService;

    @RequestMapping(value = "/follows/following/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AppFollowDTO> getFollowing(@PathVariable Long id, HttpServletRequest request){

        log.debug("REST request to get followed by profile id : {}", id);

        List<Follow> followings = followRepository.findAcceptedFollowings(id);

        return appFollowMapper.buildFollowingsResponse(followings,true,RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY );
    }

    @RequestMapping(value = "/follows/following",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AppFollowDTO> getScopeFollowing(HttpServletRequest request){
        log.debug("REST request to get followed by current profile");

        List<Follow> followings = followRepository.findAcceptedFollowings();

        return appFollowMapper.buildFollowingsResponse(followings,false,RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY );
    }

    @RequestMapping(value = "/follows/follower/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AppFollowDTO> getFollower(@PathVariable Long id, HttpServletRequest request){
        log.debug("REST request to get followed by profile id : {}", id);

        List<Follow> followers = followRepository.findAcceptedFollowers(id);

        return appFollowMapper.buildFollowersResponse(followers,true,RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY );
    }

    @RequestMapping(value = "/follows/follower",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AppFollowDTO> getScopeFollower(HttpServletRequest request){
        log.debug("REST request to get followed by profile id");

        List<Follow> followers = followRepository.findAcceptedFollowers();

        return appFollowMapper.buildFollowersResponse(followers,false,RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY );
    }

    @RequestMapping(value = "/follows/pendingFollowers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AppFollowDTO> getPendingFollowers(HttpServletRequest request){
        log.debug("REST request to get pending followers");

        List<Follow> followers = followRepository.findPendingFollowers();

        return appFollowMapper.buildFollowersResponse(followers,false,RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY );
    }

    @RequestMapping(value = "/follows/pendingFollowing",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AppFollowDTO> getPendingFollowing(HttpServletRequest request){
        log.debug("REST request to get pending following");

        List<Follow> followers = followRepository.findPendingFollowing();

        return appFollowMapper.buildFollowingsResponse(followers,false,RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY );
    }

    @RequestMapping(value = "/follows/pending/count",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Long> getPendingFollowersCount(HttpServletRequest request){
        log.debug("REST request to get pending follow");

        return Optional.ofNullable(followRepository.countPendingFollowers())
            .map(count -> new ResponseEntity<>(count, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/follows/ask/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> askToFollow(@PathVariable Long id, HttpServletRequest request){
        log.debug("REST request to ask follow for profile id : {}", id);

        Profile currentProfile = profileService.getCurrentProfile();

        if (currentProfile == null) {
            throw new CustomParameterizedException("Pas de profil pour cet utilisateur.");
        }

        Profile profileToFollow = profileRepository.findOne(id);

        if (profileToFollow == null) {
            throw new CustomParameterizedException("Pas de profil pour cet utilisateur.");
        }

        if(profileToFollow.getId().equals(currentProfile.getId())){
            throw new CustomParameterizedException("Le profil ne peut pas faire une demande d'abonnement à lui-même.");
        }

        if(followRepository.findFollow(profileToFollow.getId(),currentProfile.getId()).isPresent()){
            throw new CustomParameterizedException("Demande d'abonnement déjà faite");
        }

        Follow follow = new Follow();
        follow.setFollowing(currentProfile);
        follow.setFollower(profileToFollow);
        follow.setState(State.PENDING);
        follow.setDate(ZonedDateTime.now());

        Follow createdFollow = followRepository.save(follow);

        followService.sendAskFollowEvent(createdFollow);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert("follow", createdFollow.getId().toString())).build();
    }


    @RequestMapping(value = "/follows/accept/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> acceptToBeFollowed(@PathVariable Long id, HttpServletRequest request){
        log.debug("REST request to ask follow for follow id : {}", id);

        Profile currentProfile = profileService.getCurrentProfile();

        if (currentProfile == null) {
            throw new CustomParameterizedException("Pas de profil pour cet utilisateur.");
        }

        Follow follow = followRepository.findOne(id);

        if (follow == null) {
            throw new CustomParameterizedException("Pas de demande de suivi trouvée");
        }

        follow.setState(State.ACCEPTED);

        Follow saveFollow = followRepository.save(follow);

        followService.sendAcceptFollowEvent(saveFollow);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("follow", saveFollow.getId().toString())).build();
    }


    @RequestMapping(value = "/follows/refuse/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> refuseToBeFollowed(@PathVariable Long id, HttpServletRequest request){
        log.debug("REST request to ask follow for follow id : {}", id);

        Profile currentProfile = profileService.getCurrentProfile();

        if (currentProfile == null) {
            throw new CustomParameterizedException("Pas de profil pour cet utilisateur.");
        }

        Follow follow = followRepository.findOne(id);

        if (follow == null) {
            throw new CustomParameterizedException("Pas de demande de suivi trouvée");
        }

        followRepository.delete(id);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("follow", id.toString())).build();
    }

    @RequestMapping(value = "/follows/unfollow/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> unfollow(@PathVariable Long id, HttpServletRequest request){
        log.debug("REST request to unfollow for follow id : {}", id);

        Profile currentProfile = profileService.getCurrentProfile();

        if (currentProfile == null) {
            throw new CustomParameterizedException("Pas de profil pour cet utilisateur.");
        }

        Follow follow = followRepository.findOne(id);

        if (follow == null) {
            throw new CustomParameterizedException("Pas de demande de suivi trouvée");
        }

        followRepository.delete(id);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("follow", id.toString())).build();
    }
}
