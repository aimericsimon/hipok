package com.happy.hipok.repository;

import com.happy.hipok.domain.SituationRef;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SituationRef entity.
 */
public interface SituationRefRepository extends JpaRepository<SituationRef,Long> {

    List<SituationRef> findAllByOrderByLabel();
}
