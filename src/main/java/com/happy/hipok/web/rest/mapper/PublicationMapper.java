package com.happy.hipok.web.rest.mapper;

import com.happy.hipok.domain.*;
import com.happy.hipok.web.rest.dto.PublicationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Publication and its DTO PublicationDTO.
 */
@Mapper(componentModel = "spring", uses = {HashtagMapper.class, })
public interface PublicationMapper {

    @Mapping(source = "authorProfile.id", target = "authorProfileId")
    @Mapping(source = "authorProfile.description", target = "authorProfileDescription")
    @Mapping(source = "anatomicZoneRef.id", target = "anatomicZoneRefId")
    @Mapping(source = "anatomicZoneRef.label", target = "anatomicZoneRefLabel")
    @Mapping(source = "specialtyRef.id", target = "specialtyRefId")
    @Mapping(source = "specialtyRef.label", target = "specialtyRefLabel")
    @Mapping(source = "image.id", target = "imageId")
    PublicationDTO publicationToPublicationDTO(Publication publication);

    @Mapping(source = "authorProfileId", target = "authorProfile")
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "reportings", ignore = true)
    @Mapping(target = "shares", ignore = true)
    @Mapping(source = "anatomicZoneRefId", target = "anatomicZoneRef")
    @Mapping(source = "specialtyRefId", target = "specialtyRef")
    @Mapping(source = "imageId", target = "image")
    Publication publicationDTOToPublication(PublicationDTO publicationDTO);

    default Profile profileFromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }

    default Hashtag hashtagFromId(Long id) {
        if (id == null) {
            return null;
        }
        Hashtag hashtag = new Hashtag();
        hashtag.setId(id);
        return hashtag;
    }

    default AnatomicZoneRef anatomicZoneRefFromId(Long id) {
        if (id == null) {
            return null;
        }
        AnatomicZoneRef anatomicZoneRef = new AnatomicZoneRef();
        anatomicZoneRef.setId(id);
        return anatomicZoneRef;
    }

    default SpecialtyRef specialtyRefFromId(Long id) {
        if (id == null) {
            return null;
        }
        SpecialtyRef specialtyRef = new SpecialtyRef();
        specialtyRef.setId(id);
        return specialtyRef;
    }

    default Image imageFromId(Long id) {
        if (id == null) {
            return null;
        }
        Image image = new Image();
        image.setId(id);
        return image;
    }
}
