package com.happy.hipok.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.Notification;
import com.happy.hipok.repository.NotificationRepository;
import com.happy.hipok.web.rest.dto.NotificationDTO;
import com.happy.hipok.web.rest.mapper.NotificationMapper;
import com.happy.hipok.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Notification.
 */
@RestController
@RequestMapping("/api")
public class NotificationResource {

    private final Logger log = LoggerFactory.getLogger(NotificationResource.class);

    @Inject
    private NotificationRepository notificationRepository;

    @Inject
    private NotificationMapper notificationMapper;

    /**
     * POST  /notifications -> Create a new notification.
     */
    @RequestMapping(value = "/notifications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) throws URISyntaxException {
        log.debug("REST request to save Notification : {}", notificationDTO);
        if (notificationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createEntityCreationAlert("notification", "A new notification cannot already have an ID")).body(null);
        }
        Notification notification = notificationMapper.notificationDTOToNotification(notificationDTO);
        notification = notificationRepository.save(notification);
        NotificationDTO result = notificationMapper.notificationToNotificationDTO(notification);
        return ResponseEntity.created(new URI("/api/notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("notification", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notifications -> Updates an existing notification.
     */
    @RequestMapping(value = "/notifications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NotificationDTO> updateNotification(@RequestBody NotificationDTO notificationDTO) throws URISyntaxException {
        log.debug("REST request to update Notification : {}", notificationDTO);
        if (notificationDTO.getId() == null) {
            return createNotification(notificationDTO);
        }
        Notification notification = notificationMapper.notificationDTOToNotification(notificationDTO);
        notification = notificationRepository.save(notification);
        NotificationDTO result = notificationMapper.notificationToNotificationDTO(notification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("notification", notificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notifications -> get all the notifications.
     */
    @RequestMapping(value = "/notifications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<NotificationDTO> getAllNotifications() {
        log.debug("REST request to get all Notifications");
        return notificationRepository.findAll().stream()
            .map(notificationMapper::notificationToNotificationDTO)
            .collect(Collectors.toCollection(LinkedList::new));
            }

    /**
     * GET  /notifications/:id -> get the "id" notification.
     */
    @RequestMapping(value = "/notifications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable Long id) {
        log.debug("REST request to get Notification : {}", id);
        Notification notification = notificationRepository.findOne(id);
        NotificationDTO notificationDTO = notificationMapper.notificationToNotificationDTO(notification);
        return Optional.ofNullable(notificationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /notifications/:id -> delete the "id" notification.
     */
    @RequestMapping(value = "/notifications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        log.debug("REST request to delete Notification : {}", id);
        notificationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("notification", id.toString())).build();
    }
}
