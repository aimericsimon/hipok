package com.happy.hipok.web.rest.mapper;

import com.happy.hipok.domain.*;
import com.happy.hipok.web.rest.dto.HashtagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Hashtag and its DTO HashtagDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HashtagMapper {

    HashtagDTO hashtagToHashtagDTO(Hashtag hashtag);

    Hashtag hashtagDTOToHashtag(HashtagDTO hashtagDTO);
}
