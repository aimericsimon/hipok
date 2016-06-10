package com.happy.hipok.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happy.hipok.domain.RppsRef;

/**
 * Spring Data JPA repository for the RppsRef entity.
 */
public interface RppsRefRepository extends JpaRepository<RppsRef,Long> {

	Optional<RppsRef> findOneByNumber(String rppsRefNumber);

}
