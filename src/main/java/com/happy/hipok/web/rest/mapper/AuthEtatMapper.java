package com.happy.hipok.web.rest.mapper;

import com.happy.hipok.domain.*;
import com.happy.hipok.web.rest.dto.AuthEtatDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity AuthEtat and its DTO AuthEtatDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuthEtatMapper {

    @Mapping(source = "profile.id", target = "profileId")
    @Mapping(source = "imageAuth.id", target = "imageAuthId")
    AuthEtatDTO authEtatToAuthEtatDTO(AuthEtat authEtat);

    List<AuthEtatDTO> authEtatsToAuthEtatDTOs(List<AuthEtat> authEtats);

    @Mapping(source = "profileId", target = "profile")
    @Mapping(source = "imageAuthId", target = "imageAuth")
    AuthEtat authEtatDTOToAuthEtat(AuthEtatDTO authEtatDTO);

    List<AuthEtat> authEtatDTOsToAuthEtats(List<AuthEtatDTO> authEtatDTOs);

    default Profile profileFromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }

    default ImageAuth imageAuthFromId(Long id) {
        if (id == null) {
            return null;
        }
        ImageAuth imageAuth = new ImageAuth();
        imageAuth.setId(id);
        return imageAuth;
    }
}
