package com.happy.hipok.repository;

import com.happy.hipok.domain.Notification;
import com.happy.hipok.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Notification entity.
 */
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findAllByReceiverProfileOrderByCreationDateDesc(Profile currentProfile);

    List<Notification> findAllByReceiverProfile(Profile currentProfile);

    Long countByReceiverProfileAndRead(Profile currentProfile, Boolean read);

    List<Notification> findAllByIdInAndReceiverProfile(List<Long> ids, Profile currentProfile);

    List<Notification> findAllByItemId(Long itemId);
}
