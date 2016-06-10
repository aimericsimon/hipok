package com.happy.hipok.service;

import com.happy.hipok.domain.Device;
import com.happy.hipok.domain.Notification;
import com.happy.hipok.domain.enumeration.NotificationType;
import com.happy.hipok.event.NotificationEvent;
import com.happy.hipok.repository.DeviceRepository;
import com.happy.hipok.repository.NotificationRepository;
import com.happy.hipok.web.rest.app.dto.AppPushMessageDTO;
import com.happy.hipok.web.rest.app.mapper.AppNotificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class NotificationService implements ApplicationListener<NotificationEvent> {

    private final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Inject
    private NotificationRepository notificationRepository;

    @Inject
    private DeviceRepository deviceRepository;

    @Inject
    private PushService pushService;

    @Inject
    private AppNotificationMapper appNotificationMapper;

    @Inject
    private MessageSource messageSource;

    @Override
    public void onApplicationEvent(NotificationEvent event) {

        Notification notification = event.getNotification();

        if(notification.getType() != NotificationType.ASK_FOLLOW)
            notificationRepository.save(notification);

        Long unread = notificationRepository.countByReceiverProfileAndRead(event.getNotification().getReceiverProfile(), Boolean.FALSE);

        AppPushMessageDTO message = appNotificationMapper.notificationToPushMessageDTO(notification,getLocalizedNotificationAlert(notification),unread);


        List<Device> devices = deviceRepository.getProfileDevices(notification.getReceiverProfile().getId());

        if(devices.size()>0){
            log.debug("Will send "+devices.size()+" push to "+appNotificationMapper.getNotificationReceiverTNP(notification));
            pushService.sendPush(message,devices);
        }
        else{
            log.debug("No push to send to "+appNotificationMapper.getNotificationReceiverTNP(notification));
        }
    }

    /**
     *
     * @param notification
     * @return
     */
    private String getLocalizedNotificationAlert(Notification notification){
        Locale locale = Locale.forLanguageTag(notification.getReceiverProfile().getExtendedUser().getUser().getLangKey());
        String alert = messageSource.getMessage("notification.push."+notification.getType(),  new Object [] {appNotificationMapper.getNotificationReceiverTNP(notification)}, locale);
        return alert;
    }
}
