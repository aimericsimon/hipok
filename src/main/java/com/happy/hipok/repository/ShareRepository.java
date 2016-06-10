package com.happy.hipok.repository;

import com.happy.hipok.domain.Profile;
import com.happy.hipok.domain.Publication;
import com.happy.hipok.domain.Share;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Share entity.
 */
public interface ShareRepository extends JpaRepository<Share,Long> {

    Optional<Share> findOneBySharerProfileAndPublication(Profile profile, Publication publication);

    Long countByPublicationId(Long publicationId);
}
