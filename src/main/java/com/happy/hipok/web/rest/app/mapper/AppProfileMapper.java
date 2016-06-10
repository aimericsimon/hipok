package com.happy.hipok.web.rest.app.mapper;

import com.happy.hipok.domain.Follow;
import com.happy.hipok.domain.Profile;
import com.happy.hipok.domain.ProfileWithFollowCount;
import com.happy.hipok.domain.Suggestion;
import com.happy.hipok.repository.FollowRepository;
import com.happy.hipok.web.rest.app.dto.AppLightFollowDTO;
import com.happy.hipok.web.rest.app.dto.AppLightProfileDTO;
import com.happy.hipok.web.rest.app.dto.AppProfileAvatarDTO;
import com.happy.hipok.web.rest.app.dto.AppProfileDTO;
import com.happy.hipok.web.rest.mapper.ImageMapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by Dahwoud on 31/12/2015.
 */
@Component
public class AppProfileMapper {

    @Inject
    private ImageMapper imageMapper;

    @Inject
    private FollowRepository followRepository;

    /**
     *
     * @param profile
     * @param imageRoot
     * @return
     */
    public AppLightProfileDTO profileToAppLightProfileDTO(Profile profile, String imageRoot){
        if (profile == null) {
            return null;
        }

        List<Follow> followings = followRepository.findFollowings();

        AppLightProfileDTO appLightProfileDTO = new AppLightProfileDTO();
        appLightProfileDTO.setId(profile.getId());
        appLightProfileDTO.setAvatarThumbnailUrl(imageMapper.getFullUrl(imageRoot, profile.getAvatarThumbnailUrl()));
        appLightProfileDTO.setCity(profile.getExtendedUser().getAddress().getCity());
        appLightProfileDTO.setMedicalTypeRefLabel(profile.getExtendedUser().getMedicalTypeRef().getLabel());
        appLightProfileDTO.setTitleAbbreviation(profile.getExtendedUser().getTitleRef().getAbbreviation());
        appLightProfileDTO.setFirstName(profile.getExtendedUser().getUser().getFirstName());
        appLightProfileDTO.setLastName(profile.getExtendedUser().getUser().getLastName());

        if(followings != null){
            for(Follow following: followings){
                if(following.getFollower().getId().equals(profile.getId())){

                    AppLightFollowDTO ligthDto = new AppLightFollowDTO();
                    ligthDto.setState(following.getState().toString());
                    ligthDto.setId(following.getId());
                    ligthDto.setProfileId(following.getFollowing().getId());
                    appLightProfileDTO.setFollower(ligthDto);
                    break;
                }
            }
        }

        return appLightProfileDTO;
    }

    /**
     *
     * @param suggestion
     * @param imageRoot
     * @return
     */
    public AppLightProfileDTO suggestionToAppLightProfileDTO(Suggestion suggestion, String imageRoot){
        if (suggestion == null) {
            return null;
        }

        return this.profileToAppLightProfileDTO(suggestion.getProfile(),imageRoot);
    }

    /**
     *
     * @param profile
     * @param follow
     * @param imageRoot
     * @return
     */
    public AppProfileDTO profileToAppProfileDTO(ProfileWithFollowCount profile, Follow follow, String imageRoot){
        if (profile == null) {
            return null;
        }

        AppProfileDTO appProfileDTO = new AppProfileDTO();
        appProfileDTO.setId(profile.getProfile().getId());
        appProfileDTO.setDescription(profile.getProfile().getDescription());

        appProfileDTO.setAvatarUrl(imageMapper.getFullUrl(imageRoot, profile.getProfile().getAvatarUrl()));
        appProfileDTO.setAvatarThumbnailUrl(imageMapper.getFullUrl(imageRoot, profile.getProfile().getAvatarThumbnailUrl()));

        appProfileDTO.setBirthDate(profile.getProfile().getExtendedUser().getBirthDate());
        appProfileDTO.setFirstName(profile.getProfile().getExtendedUser().getUser().getFirstName());
        appProfileDTO.setLastName(profile.getProfile().getExtendedUser().getUser().getLastName());
        appProfileDTO.setPracticeLocation(profile.getProfile().getExtendedUser().getPracticeLocation());

        if(follow != null){
            appProfileDTO.setFollowState(follow.getState());
            appProfileDTO.setFollowId(follow.getId());
        }

        if(Optional.ofNullable(appProfileDTO.getBirthDate()).isPresent()){
            LocalDate birthDate = appProfileDTO.getBirthDate();
            LocalDate now = LocalDate.now();
            if(birthDate.getYear()<= now.getYear()){
                appProfileDTO.setAge(now.getYear() - birthDate.getYear());
            }
        }

        appProfileDTO.setAddress(profile.getProfile().getExtendedUser().getAddress());

        appProfileDTO.setFollowing(profile.getFollowing());
        appProfileDTO.setFollowers(profile.getFollowers());

        appProfileDTO.setMedicalTypeRefId(profile.getProfile().getExtendedUser().getMedicalTypeRef().getId());
        appProfileDTO.setTitleRefId(profile.getProfile().getExtendedUser().getTitleRef().getId());

        appProfileDTO.setMedicalTypeRefLabel(profile.getProfile().getExtendedUser().getMedicalTypeRef().getLabel());
        appProfileDTO.setTitleRefLabel(profile.getProfile().getExtendedUser().getTitleRef().getLabel());

        appProfileDTO.setSituationRefId(profile.getProfile().getExtendedUser().getSituationRef().getId());
        appProfileDTO.setSituationRefLabel(profile.getProfile().getExtendedUser().getSituationRef().getLabel());
        appProfileDTO.setSituationRefCode(profile.getProfile().getExtendedUser().getSituationRef().getCode());

        appProfileDTO.setTitleAbbreviation(profile.getProfile().getExtendedUser().getTitleRef().getAbbreviation());

        return appProfileDTO;
    }

    /**
     *
     * @param profile
     * @param imageRoot
     * @return
     */
    public AppProfileAvatarDTO profileToAppProfileAvatarDTO(Profile profile, String imageRoot) {
        if (profile == null) {
            return null;
        }

        AppProfileAvatarDTO avatarDTO = new AppProfileAvatarDTO();
        avatarDTO.setId(profile.getId());
        avatarDTO.setAvatarUrl(imageMapper.getFullUrl(imageRoot, profile.getAvatarUrl()));
        avatarDTO.setAvatarThumbnailUrl(imageMapper.getFullUrl(imageRoot, profile.getAvatarThumbnailUrl()));

        return avatarDTO;
    }
}


