package com.happy.hipok.web.rest.app.mapper;

import com.happy.hipok.domain.Image;
import com.happy.hipok.web.rest.app.dto.AppImageDTO;
import com.happy.hipok.web.rest.mapper.ImageMapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class AppImageMapper {

    @Inject
    private ImageMapper imageMapper;

    public AppImageDTO imageToImageDTO(Image image, String imageRoot) {
        if (image == null) {
            return null;
        }

        AppImageDTO imageDTO = new AppImageDTO();
        imageDTO.setId(image.getId());
        imageDTO.setImageUrl(imageMapper.getFullUrl(imageRoot, image.getImageUrl()));
        imageDTO.setImageThumbnailUrl(imageMapper.getFullUrl(imageRoot, image.getImageThumbnailUrl()));

        return imageDTO;
    }

}
