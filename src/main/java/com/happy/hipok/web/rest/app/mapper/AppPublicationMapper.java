package com.happy.hipok.web.rest.app.mapper;

import com.happy.hipok.domain.*;
import com.happy.hipok.web.rest.app.dto.AppPublicationDTO;
import com.happy.hipok.web.rest.dto.HashtagDTO;
import com.happy.hipok.web.rest.mapper.HashtagMapper;
import com.happy.hipok.web.rest.mapper.ImageMapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Mapper for the entity Publication and its DTO AppPublicationDTO.
 */
@Component
public class AppPublicationMapper {

    @Inject
    private HashtagMapper hashtagMapper;

    @Inject
    private ImageMapper imageMapper;

    public AppPublicationDTO publicationToPublicationDTO(Publication publication, String imageRoot) {
        if (publication == null) {
            return null;
        }

        AppPublicationDTO appPublicationDTO = new AppPublicationDTO();

        appPublicationDTO.setSpecialtyRefId(publicationSpecialtyRefId(publication));
        appPublicationDTO.setAuthorProfileId(publicationAuthorProfileId(publication));
        appPublicationDTO.setAnatomicZoneRefId(publicationAnatomicZoneRefId(publication));
        appPublicationDTO.setId(publication.getId());
        appPublicationDTO.setDescription(publication.getDescription());
        appPublicationDTO.setProcessedDescription(publication.getProcessedDescription());
        appPublicationDTO.setLocation(publication.getLocation());
        appPublicationDTO.setVisibility(publication.getVisibility());

        if(publication.getImage() != null){
            appPublicationDTO.setImageUrl(imageMapper.getFullUrl(imageRoot, publication.getImage().getImageUrl()));
            appPublicationDTO.setImageThumbnailUrl(imageMapper.getFullUrl(imageRoot, publication.getImage().getImageThumbnailUrl()));
        }

        appPublicationDTO.setPublicationDate(publication.getPublicationDate());
        appPublicationDTO.setNbVizualisations(publication.getNbVizualisations());

        appPublicationDTO.setHashtags(hashtagSetToHashtagDTOSet(publication.getHashtags()));

        return appPublicationDTO;
    }

    public Publication publicationDTOToPublication(AppPublicationDTO appPublicationDTO) {
        if (appPublicationDTO == null) {
            return null;
        }

        Publication publication = new Publication();

        publication.setAuthorProfile(profileFromId(appPublicationDTO.getAuthorProfileId()));
        publication.setAnatomicZoneRef(anatomicZoneRefFromId(appPublicationDTO.getAnatomicZoneRefId()));
        publication.setSpecialtyRef(specialtyRefFromId(appPublicationDTO.getSpecialtyRefId()));
        publication.setId(appPublicationDTO.getId());
        publication.setDescription(appPublicationDTO.getDescription());
        publication.setProcessedDescription(appPublicationDTO.getProcessedDescription());
        publication.setLocation(appPublicationDTO.getLocation());
        publication.setVisibility(appPublicationDTO.getVisibility());

        Image image = new Image();
        image.setId(appPublicationDTO.getImageId());

        publication.setImage(image);
        publication.setPublicationDate(ZonedDateTime.now());
        publication.setNbVizualisations(appPublicationDTO.getNbVizualisations() == null ? 0 : appPublicationDTO.getNbVizualisations());

        publication.setHashtags(hashtagDTOSetToHashtagSet(appPublicationDTO.getHashtags()));

        return publication;
    }

    private Long publicationSpecialtyRefId(Publication publication) {

        if (publication == null) {
            return null;
        }
        SpecialtyRef specialtyRef = publication.getSpecialtyRef();
        if (specialtyRef == null) {
            return null;
        }
        Long id = specialtyRef.getId();
        if (id == null) {
            return null;
        }
        return id;
    }

    private Long publicationAuthorProfileId(Publication publication) {

        if (publication == null) {
            return null;
        }
        Profile authorProfile = publication.getAuthorProfile();
        if (authorProfile == null) {
            return null;
        }
        Long id = authorProfile.getId();
        if (id == null) {
            return null;
        }
        return id;
    }

    private Long publicationAnatomicZoneRefId(Publication publication) {

        if (publication == null) {
            return null;
        }
        AnatomicZoneRef anatomicZoneRef = publication.getAnatomicZoneRef();
        if (anatomicZoneRef == null) {
            return null;
        }
        Long id = anatomicZoneRef.getId();
        if (id == null) {
            return null;
        }
        return id;
    }

    private Profile profileFromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }

    private AnatomicZoneRef anatomicZoneRefFromId(Long id) {
        if (id == null) {
            return null;
        }
        AnatomicZoneRef anatomicZoneRef = new AnatomicZoneRef();
        anatomicZoneRef.setId(id);
        return anatomicZoneRef;
    }

    private SpecialtyRef specialtyRefFromId(Long id) {
        if (id == null) {
            return null;
        }
        SpecialtyRef specialtyRef = new SpecialtyRef();
        specialtyRef.setId(id);
        return specialtyRef;
    }

    protected Set<HashtagDTO> hashtagSetToHashtagDTOSet(Set<Hashtag> set) {
        if (set == null) {
            return null;
        }


        Set<HashtagDTO> set_ = new HashSet<HashtagDTO>();

        for (Hashtag hashtag : set) {
            set_.add(hashtagMapper.hashtagToHashtagDTO(hashtag));
        }

        return set_;
    }

    protected Set<Hashtag> hashtagDTOSetToHashtagSet(Set<HashtagDTO> set) {
        if (set == null) {
            return null;
        }


        Set<Hashtag> set_ = new HashSet<Hashtag>();

        for (HashtagDTO hashtagDTO : set) {
            set_.add(hashtagMapper.hashtagDTOToHashtag(hashtagDTO));
        }

        return set_;
    }
}
