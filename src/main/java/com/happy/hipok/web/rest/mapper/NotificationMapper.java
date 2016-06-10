package com.happy.hipok.web.rest.mapper;

import com.happy.hipok.domain.*;
import com.happy.hipok.web.rest.dto.NotificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Notification and its DTO NotificationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NotificationMapper {

    @Mapping(source = "receiverProfile.id", target = "receiverProfileId")
    @Mapping(source = "emitterProfile.id", target = "emitterProfileId")
    NotificationDTO notificationToNotificationDTO(Notification notification);

    @Mapping(source = "receiverProfileId", target = "receiverProfile")
    @Mapping(source = "emitterProfileId", target = "emitterProfile")
    Notification notificationDTOToNotification(NotificationDTO notificationDTO);

    default Profile profileFromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }
}
