package com.happy.hipok.repository;

import com.happy.hipok.domain.AnatomicZoneRef;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AnatomicZoneRef entity.
 */
public interface AnatomicZoneRefRepository extends JpaRepository<AnatomicZoneRef,Long> {

}
