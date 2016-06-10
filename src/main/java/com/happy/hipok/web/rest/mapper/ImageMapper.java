package com.happy.hipok.web.rest.mapper;

import com.happy.hipok.domain.Publication;
import com.happy.hipok.web.rest.app.dto.AppSearchItemDTO;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity ScopeItem and its DTO AppScopeItemDTO.
 */
@Component
public class ImageMapper {

    /**
     * Get full URL path
     *
     * @param imageRoot
     * @param imageUrl
     * @return
     */
    public String getFullUrl(String imageRoot, String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return null;
        }
        return imageRoot + imageUrl;
    }
}
