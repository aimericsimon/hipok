package com.happy.hipok.web.rest.mapper;

import com.happy.hipok.domain.*;
import com.happy.hipok.web.rest.dto.ImageAuthDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ImageAuth and its DTO ImageAuthDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImageAuthMapper {

    ImageAuthDTO imageAuthToImageAuthDTO(ImageAuth imageAuth);

    List<ImageAuthDTO> imageAuthsToImageAuthDTOs(List<ImageAuth> imageAuths);

    ImageAuth imageAuthDTOToImageAuth(ImageAuthDTO imageAuthDTO);

    List<ImageAuth> imageAuthDTOsToImageAuths(List<ImageAuthDTO> imageAuthDTOs);
}
