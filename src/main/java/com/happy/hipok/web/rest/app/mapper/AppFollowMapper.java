package com.happy.hipok.web.rest.app.mapper;

import com.happy.hipok.domain.Follow;
import com.happy.hipok.domain.Profile;
import com.happy.hipok.repository.FollowRepository;
import com.happy.hipok.web.rest.app.dto.AppFollowDTO;
import com.happy.hipok.web.rest.app.dto.AppLightFollowDTO;
import com.happy.hipok.web.rest.mapper.ImageMapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dahwoud on 04/01/2016.
 */
@Component
public class AppFollowMapper {

    @Inject
    private ImageMapper imageMapper;

    @Inject
    private FollowRepository followRepository;

    /**
     *
     * @param follows
     * @param imageRoot
     * @return
     */
    public List<AppFollowDTO> buildFollowingsResponse(List<Follow> follows,boolean check,  String imageRoot){

        List<AppFollowDTO> dtos = new ArrayList<>();

        List<Follow> followings = null;
        if(check)
            followings = followRepository.findFollowings();

        for(Follow follow: follows){
            AppFollowDTO dto = new AppFollowDTO();
            dto.setState(follow.getState().toString());
            dto.setId(follow.getId());
            this.setFollow(dto,follow.getFollower(), imageRoot);

            if(followings != null){
                for(Follow following: followings){
                    if(following.getFollower().getId().equals(follow.getFollower().getId())){

                        AppLightFollowDTO ligthDto = new AppLightFollowDTO();
                        ligthDto.setState(following.getState().toString());
                        ligthDto.setId(following.getId());
                        ligthDto.setProfileId(following.getFollowing().getId());
                        dto.setFollower(ligthDto);
                        break;
                    }
                }
            }

            dtos.add(dto);
        }

        return dtos;
    }

    /**
     *
     * @param follows
     * @param imageRoot
     * @return
     */
    public List<AppFollowDTO> buildFollowersResponse(List<Follow> follows,boolean check,  String imageRoot){
        List<AppFollowDTO> dtos = new ArrayList<>();

        List<Follow> followings = null;
        if(check)
            followings = followRepository.findFollowings();

        for(Follow follow: follows){
            AppFollowDTO dto = new AppFollowDTO();
            dto.setState(follow.getState().toString());
            dto.setId(follow.getId());
            this.setFollow(dto,follow.getFollowing(), imageRoot);

            if(followings != null){

                for(Follow following: followings){

                    if(following.getFollower().getId().equals(follow.getFollowing().getId())){
                        AppLightFollowDTO ligthDto = new AppLightFollowDTO();
                        ligthDto.setState(following.getState().toString());
                        ligthDto.setId(following.getId());
                        ligthDto.setProfileId(following.getFollowing().getId());
                        dto.setFollower(ligthDto);
                        break;
                    }
                }
            }

            dtos.add(dto);
        }

        return dtos;
    }

    /**
     *
     * @param dto
     * @param profile
     * @param imageRoot
     * @return
     */
    private void setFollow(AppFollowDTO dto, Profile profile, String imageRoot){
        dto.setProfileId(profile.getId());
        dto.setCity(profile.getExtendedUser().getAddress().getCity());
        dto.setPracticeLocation(profile.getExtendedUser().getPracticeLocation());
        dto.setFirstName(profile.getExtendedUser().getUser().getFirstName());
        dto.setLastName(profile.getExtendedUser().getUser().getLastName());
        dto.setTitleAbbreviation(profile.getExtendedUser().getTitleRef().getAbbreviation());

        dto.setAvatarUrl(imageMapper.getFullUrl(imageRoot, profile.getAvatarUrl()));
        dto.setAvatarThumbnailUrl(imageMapper.getFullUrl(imageRoot, profile.getAvatarThumbnailUrl()));

        dto.setSituationRefCode(profile.getExtendedUser().getSituationRef().getCode());
        dto.setSituationRefLabel(profile.getExtendedUser().getSituationRef().getLabel());

        dto.setMedicalTypeRefLabel(profile.getExtendedUser().getMedicalTypeRef().getLabel());
        dto.setMedicalTypeRefId(profile.getExtendedUser().getMedicalTypeRef().getId());
    }
}
