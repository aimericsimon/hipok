package com.happy.hipok.web.rest.app.mapper;

import com.happy.hipok.domain.*;
import com.happy.hipok.web.rest.app.dto.AppNotificationDTO;
import com.happy.hipok.web.rest.app.dto.AppPushMessageDTO;
import com.happy.hipok.web.rest.mapper.ImageMapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component
public class AppNotificationMapper {

    @Inject
    private ImageMapper imageMapper;

    /**
     *
     * @param notification
     * @param alert
     * @param unread
     * @return
     */
    public AppPushMessageDTO notificationToPushMessageDTO(Notification notification, String alert, Long unread){
        if(notification == null){
            return null;
        }
        AppPushMessageDTO dto = new AppPushMessageDTO();
        dto.setEmitterProfileId(notificationEmitterProfileId(notification));
        dto.setEmitterTitleAbbreviation(notificationEmitterTitleAbbreviation(notification));
        dto.setEmitterFirstName(notificationEmitterFirstName(notification));
        dto.setEmitterLastName(notificationEmitterLastName(notification));
        dto.setReceiverProfileId(notificationReceiverProfileId(notification));
        dto.setId(notification.getId());
        dto.setRead(notification.getRead());
        dto.setType(notification.getType());
        dto.setItemId(notification.getItemId());
        dto.setAlert(alert);
        dto.setPriority(AppPushMessageDTO.DefaultPriority);
        dto.setTitle(AppPushMessageDTO.DefaultTitle);
        dto.setVibrate(AppPushMessageDTO.DefaultVibrate);
        dto.setSound(AppPushMessageDTO.DefaultSound);
        dto.setBadge(unread);
        return dto;
    }

    public AppNotificationDTO notificationToNotificationDTO(Notification notification, String imageRoot) {
        if (notification == null) {
            return null;
        }

        AppNotificationDTO appNotificationDTO = new AppNotificationDTO();

        appNotificationDTO.setEmitterProfileId(notificationEmitterProfileId(notification));
        appNotificationDTO.setEmitterTitleAbbreviation(notificationEmitterTitleAbbreviation(notification));
        appNotificationDTO.setEmitterFirstName(notificationEmitterFirstName(notification));
        appNotificationDTO.setEmitterLastName(notificationEmitterLastName(notification));
        appNotificationDTO.setReceiverProfileId(notificationReceiverProfileId(notification));
        appNotificationDTO.setId(notification.getId());
        appNotificationDTO.setCreationDate(notification.getCreationDate());
        appNotificationDTO.setRead(notification.getRead());
        appNotificationDTO.setType(notification.getType());
        appNotificationDTO.setItemId(notification.getItemId());

        //COMMENT,SHARE,ASK_FOLLOW,ACCEPT_FOLLOW,REPORT,MENTION_COMMENT, MENTION_PUBLICATION

        switch(notification.getType()){
            case COMMENT:
            case MENTION_COMMENT:
            case MENTION_PUBLICATION:
            case SHARE:
            case REPORT:
                appNotificationDTO.setData(imageMapper.getFullUrl(imageRoot, notification.getData()));
                break;

            default:
                appNotificationDTO.setData("");
                break;
        }
        return appNotificationDTO;
    }

    /**
     *
     * @param notification
     * @return
     */
    public String getNotificationReceiverTNP(Notification notification){
        List<String> items = new ArrayList<String>();

        String val = notificationEmitterTitleAbbreviation(notification);
        if(val != null){
            items.add(val);
        }

        val = notificationEmitterFirstName(notification);
        if(val != null){
            items.add(val);
        }

        val = notificationEmitterLastName(notification);
        if(val != null){
            items.add(val);
        }

        return String.join(" ", items);
    }

    private String notificationEmitterTitleAbbreviation(Notification notification) {
        ExtendedUser extendedUser = getExtendedUser(notification);
        TitleRef titleRef = extendedUser.getTitleRef();
        if (titleRef == null) {
            return null;
        }
        return titleRef.getAbbreviation();
    }

    private String notificationEmitterFirstName(Notification notification) {
        ExtendedUser extendedUser = getExtendedUser(notification);
        User user = extendedUser.getUser();
        if (user == null) {
            return null;
        }
        return user.getFirstName();
    }

    private String notificationEmitterLastName(Notification notification) {
        ExtendedUser extendedUser = getExtendedUser(notification);
        User user = extendedUser.getUser();
        if (user == null) {
            return null;
        }
        return user.getLastName();
    }

    private ExtendedUser getExtendedUser(Notification notification) {
        if (notification == null) {
            return null;
        }
        Profile emitterProfile = notification.getEmitterProfile();
        if (emitterProfile == null) {
            return null;
        }
        ExtendedUser extendedUser = emitterProfile.getExtendedUser();
        if (extendedUser == null) {
            return null;
        }
        return extendedUser;
    }

    private Long notificationEmitterProfileId(Notification notification) {

        if (notification == null) {
            return null;
        }
        Profile emitterProfile = notification.getEmitterProfile();
        if (emitterProfile == null) {
            return null;
        }
        Long id = emitterProfile.getId();
        if (id == null) {
            return null;
        }
        return id;
    }

    private Long notificationReceiverProfileId(Notification notification) {

        if (notification == null) {
            return null;
        }
        Profile receiverProfile = notification.getReceiverProfile();
        if (receiverProfile == null) {
            return null;
        }
        Long id = receiverProfile.getId();
        if (id == null) {
            return null;
        }
        return id;
    }

}
