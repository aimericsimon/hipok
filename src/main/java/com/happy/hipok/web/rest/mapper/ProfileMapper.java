package com.happy.hipok.web.rest.mapper;

import com.happy.hipok.domain.*;
import com.happy.hipok.web.rest.dto.ProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Profile and its DTO ProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProfileMapper {

    @Mapping(source = "extendedUser.id", target = "extendedUserId")
    ProfileDTO profileToProfileDTO(Profile profile);

    @Mapping(target = "followers", ignore = true)
    @Mapping(target = "followings", ignore = true)
    @Mapping(target = "publications", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "reportings", ignore = true)
    @Mapping(target = "shares", ignore = true)
    @Mapping(source = "extendedUserId", target = "extendedUser")
    @Mapping(target = "receiverNotifications", ignore = true)
    @Mapping(target = "emitterNotifications", ignore = true)
    @Mapping(target = "devices", ignore = true)
    Profile profileDTOToProfile(ProfileDTO profileDTO);

    default ExtendedUser extendedUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        ExtendedUser extendedUser = new ExtendedUser();
        extendedUser.setId(id);
        return extendedUser;
    }
}
