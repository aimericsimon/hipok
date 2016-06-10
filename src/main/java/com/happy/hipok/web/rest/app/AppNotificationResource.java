package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.Application;
import com.happy.hipok.domain.Notification;
import com.happy.hipok.repository.NotificationRepository;
import com.happy.hipok.service.ProfileService;
import com.happy.hipok.web.rest.app.dto.AppNotificationDTO;
import com.happy.hipok.web.rest.app.mapper.AppNotificationMapper;
import com.happy.hipok.web.rest.util.HeaderUtil;
import com.happy.hipok.web.rest.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Notification.
 */
@RestController
@RequestMapping("/app")
public class AppNotificationResource {

    private final Logger log = LoggerFactory.getLogger(AppNotificationResource.class);

    @Inject
    private NotificationRepository notificationRepository;

    @Inject
    private ProfileService profileService;

    @Inject
    private AppNotificationMapper appNotificationMapper;

    /**
     * GET  /notifications -> get all the notifications received by the current profile.
     */
    @RequestMapping(value = "/notifications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AppNotificationDTO> getAllNotificationsReceivedByCurrentProfile(HttpServletRequest request) {
        log.debug("REST request to get all Notifications received by the current profile");
        return notificationRepository.findAllByReceiverProfileOrderByCreationDateDesc(profileService.getCurrentProfile())
            .stream()
            .map(notification -> appNotificationMapper.notificationToNotificationDTO(notification, RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY))
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * GET  /notifications/nb -> get the count of unread notifications received by current profile.
     */
    @RequestMapping(value = "/notifications/nb",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Long> countUnreadNotificationsReceivedByCurrentProfile() {
        log.debug("REST request to count unread notifications received by current profile.");

        return Optional.ofNullable(notificationRepository.countByReceiverProfileAndRead(profileService.getCurrentProfile(), Boolean.FALSE))
            .map(count -> new ResponseEntity<>(count, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * PUT  /notifications/read -> Reads a list of notifications
     */
    @RequestMapping(value = "/notifications/read",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> readNotifications(@RequestBody(required = false) List<Long> ids) throws URISyntaxException {
        log.debug("REST request to read notifications : {}", ids);

        List<Notification> notifications = getNotifications(ids);

        notifications.stream()
            .forEach(notification -> notification.setRead(Boolean.TRUE));

        notificationRepository.save(notifications);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("notifications", ids == null ? null : ids.toString())).build();
    }

    /**
     * DELETE  /notifications -> delete the "ids" notification.
     */
    @RequestMapping(value = "/notifications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNotifications(@RequestBody(required = false) List<Long> ids) {
        log.debug("REST request to delete Notifications : {}", ids);

        List<Notification> notifications = getNotifications(ids);

        notificationRepository.delete(notifications);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("notifications", ids == null ? null : ids.toString())).build();
    }


    private List<Notification> getNotifications(@RequestBody(required = false) List<Long> ids) {
        List<Notification> notifications;
        if (ids == null) {
            notifications = notificationRepository.findAllByReceiverProfile(profileService.getCurrentProfile());
        } else {
            notifications = notificationRepository.findAllByIdInAndReceiverProfile(ids, profileService.getCurrentProfile());
        }
        return notifications;
    }
}
