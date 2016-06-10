package com.happy.hipok.repository;

import com.happy.hipok.domain.Hashtag;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Hashtag entity.
 */
public interface HashtagRepository extends JpaRepository<Hashtag,Long> {

    Optional<Hashtag> findOneByLabel(String label);
}
