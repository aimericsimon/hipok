package com.happy.hipok.web.rest.mapper;

import com.happy.hipok.domain.Publication;
import com.happy.hipok.web.rest.app.dto.AppSearchItemDTO;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Mapper for the entity ScopeItem and its DTO AppScopeItemDTO.
 */
@Component
public class SearchItemMapper {

    @Inject
    private ImageMapper imageMapper;

    /**
     * Map domain to DTO
     *
     * @param publication publication
     * @param imageRoot image url root
     * @return search item dto
     */
    public AppSearchItemDTO publicationToSearchItemDTO(Publication publication, String imageRoot) {
        if (publication == null) {
            return null;
        }

        AppSearchItemDTO appSearchItemDTO = new AppSearchItemDTO();
        appSearchItemDTO.setPublicationId(publication.getId());

        if(publication.getImage() != null) {
            appSearchItemDTO.setImageThumbnailUrl(imageMapper.getFullUrl(imageRoot, publication.getImage().getImageThumbnailUrl()));
        }

        return appSearchItemDTO;
    }
}
