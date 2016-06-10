package com.happy.hipok.web.rest.app.mapper;

import com.happy.hipok.domain.Comment;
import com.happy.hipok.web.rest.app.dto.AppCommentDTO;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity Comment and its DTO AppCommentDTO.
 */
@Component
public class AppCommentMapper {

    public AppCommentDTO commentToAppCommentDTO(Comment comment) {
        if (comment == null) {
            return null;
        }

        AppCommentDTO appCommentDTO = new AppCommentDTO();

        appCommentDTO.setId(comment.getId());
        appCommentDTO.setText(comment.getText());
        appCommentDTO.setProcessedText(comment.getProcessedText());
        appCommentDTO.setPublicationId(comment.getPublication().getId());
        appCommentDTO.setProfileId(comment.getCommentatorProfile().getId());
        appCommentDTO.setFirstName(comment.getCommentatorProfile().getExtendedUser().getUser().getFirstName());
        appCommentDTO.setLastName(comment.getCommentatorProfile().getExtendedUser().getUser().getLastName());
        appCommentDTO.setTitleAbbreviation(comment.getCommentatorProfile().getExtendedUser().getTitleRef().getAbbreviation());


        return appCommentDTO;
    }
}
