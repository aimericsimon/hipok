package com.happy.hipok.repository;

import com.happy.hipok.domain.SpecialtyRef;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SpecialtyRef entity.
 */
public interface SpecialtyRefRepository extends JpaRepository<SpecialtyRef,Long> {

    List<SpecialtyRef> findAllByOrderByLabel();
}
