package com.happy.hipok.web.rest.app.mapper;

import com.happy.hipok.domain.*;
import com.happy.hipok.web.rest.app.dto.AppScopeItemDTO;
import com.happy.hipok.web.rest.mapper.ImageMapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.ZonedDateTime;

/**
 * Mapper for the entity ScopeItem and its DTO AppScopeItemDTO.
 */
@Component
public class AppScopeItemMapper {

    @Inject
    private ImageMapper imageMapper;

    /**
     * Map domain to DTO
     *
     * @param scopeItem item of the scope
     * @param imageRoot image url root
     * @return DTO of scope item
     */
    public AppScopeItemDTO scopeItemToScopeItemDTO(ScopeItem scopeItem, String imageRoot) {
        if (scopeItem == null) {
            return null;
        }

        AppScopeItemDTO appScopeItemDTO = new AppScopeItemDTO();

        appScopeItemDTO.setPublicationId(getPublicationId(scopeItem));
        appScopeItemDTO.setAvatarThumbnailUrl(imageMapper.getFullUrl(imageRoot, getAvatarThumbnailUrl(scopeItem)));
        appScopeItemDTO.setTitleAbbreviation(getAbbreviation(scopeItem));
        appScopeItemDTO.setFirstName(getFirstName(scopeItem));
        appScopeItemDTO.setLastName(getLastName(scopeItem));
        appScopeItemDTO.setAuthorProfileId(getAuthorProfileId(scopeItem));
        appScopeItemDTO.setPracticeLocation(getPracticeLocation(scopeItem));
        appScopeItemDTO.setMedicalTypeRefLabel(getSpecialtyRefLabel(scopeItem));
        appScopeItemDTO.setPublicationDate(getPublicationDate(scopeItem));
        appScopeItemDTO.setImageUrl(imageMapper.getFullUrl(imageRoot, getImageUrl(scopeItem)));
        appScopeItemDTO.setImageThumbnailUrl(imageMapper.getFullUrl(imageRoot, getImageThumbnailUrl(scopeItem)));
        Integer nbVizualisations = getNbVizualisations(scopeItem);
        appScopeItemDTO.setNbVizualisations(nbVizualisations == null ? 0 : nbVizualisations);
        appScopeItemDTO.setNbComments(scopeItem.getNbComments());
        appScopeItemDTO.setNbShares(scopeItem.getNbShares());
        appScopeItemDTO.setDescription(getDescription(scopeItem));
        appScopeItemDTO.setProcessedDescription(getProcessedDescription(scopeItem));

        return appScopeItemDTO;
    }

    private Long getPublicationId(ScopeItem scopeItem) {
        if (scopeItem == null) {
            return null;
        }
        Publication publication = scopeItem.getPublication();
        if (publication == null) {
            return null;
        }
        return publication.getId();
    }

    private Long getAuthorProfileId(ScopeItem scopeItem) {
        Profile authorProfile = getAuthorProfile(scopeItem);
        if (authorProfile == null) {
            return null;
        }

        return authorProfile.getId();
    }

    private String getDescription(ScopeItem scopeItem) {
        if (scopeItem == null) {
            return null;
        }
        Publication publication = scopeItem.getPublication();
        if (publication == null) {
            return null;
        }
        return publication.getDescription();
    }

    private String getProcessedDescription(ScopeItem scopeItem) {
        if (scopeItem == null) {
            return null;
        }
        Publication publication = scopeItem.getPublication();
        if (publication == null) {
            return null;
        }
        return publication.getProcessedDescription();
    }

    private Integer getNbVizualisations(ScopeItem scopeItem) {
        if (scopeItem == null) {
            return null;
        }
        Publication publication = scopeItem.getPublication();
        if (publication == null) {
            return null;
        }
        return publication.getNbVizualisations();
    }

    private String getImageUrl(ScopeItem scopeItem) {
        if (scopeItem == null) {
            return null;
        }
        Publication publication = scopeItem.getPublication();
        if (publication == null) {
            return null;
        }

        Image image = publication.getImage();
        if(image == null)
        {
            return null;
        }

        return image.getImageUrl();
    }

    private String getImageThumbnailUrl(ScopeItem scopeItem) {
        if (scopeItem == null) {
            return null;
        }
        Publication publication = scopeItem.getPublication();
        if (publication == null) {
            return null;
        }

        Image image = publication.getImage();
        if(image == null)
        {
            return null;
        }

        return image.getImageThumbnailUrl();
    }

    private ZonedDateTime getPublicationDate(ScopeItem scopeItem) {
        if (scopeItem == null) {
            return null;
        }
        Publication publication = scopeItem.getPublication();
        if (publication == null) {
            return null;
        }
        return publication.getPublicationDate();
    }

    private String getLastName(ScopeItem scopeItem) {
        ExtendedUser extendedUser = getExtendedUser(scopeItem);
        if (extendedUser == null) {
            return null;
        }
        User user = extendedUser.getUser();
        if (user == null) {
            return null;
        }
        return user.getLastName();
    }

    private String getFirstName(ScopeItem scopeItem) {
        ExtendedUser extendedUser = getExtendedUser(scopeItem);
        if (extendedUser == null) {
            return null;
        }
        User user = extendedUser.getUser();
        if (user == null) {
            return null;
        }
        return user.getFirstName();
    }

    private String getAbbreviation(ScopeItem scopeItem) {
        ExtendedUser extendedUser = getExtendedUser(scopeItem);
        if (extendedUser == null) {
            return null;
        }
        TitleRef titleRef = extendedUser.getTitleRef();
        if (titleRef == null) {
            return null;
        }
        return titleRef.getAbbreviation();
    }

    private String getAvatarThumbnailUrl(ScopeItem scopeItem) {

        Profile authorProfile = getAuthorProfile(scopeItem);
        if (authorProfile == null) {
            return null;
        }

        return authorProfile.getAvatarThumbnailUrl();
    }

    private Profile getAuthorProfile(ScopeItem scopeItem) {
        if (scopeItem == null) {
            return null;
        }
        Publication publication = scopeItem.getPublication();
        if (publication == null) {
            return null;
        }
        return publication.getAuthorProfile();
    }

    private String getPracticeLocation(ScopeItem scopeItem) {
        ExtendedUser extendedUser = getExtendedUser(scopeItem);
        if (extendedUser == null) {
            return null;
        }
        return extendedUser.getPracticeLocation();
    }

    private String getSpecialtyRefLabel(ScopeItem scopeItem) {
        ExtendedUser extendedUser = getExtendedUser(scopeItem);
        if (extendedUser == null) {
            return null;
        }
        MedicalTypeRef medicalTypeRef = extendedUser.getMedicalTypeRef();
        if (medicalTypeRef == null) {
            return null;
        }
        return medicalTypeRef.getLabel();
    }

    private ExtendedUser getExtendedUser(ScopeItem scopeItem) {
        if (scopeItem == null) {
            return null;
        }
        Publication publication = scopeItem.getPublication();
        if (publication == null) {
            return null;
        }
        Profile authorProfile = publication.getAuthorProfile();
        if (authorProfile == null) {
            return null;
        }
        return authorProfile.getExtendedUser();
    }

}
