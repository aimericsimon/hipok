package com.happy.hipok.repository;

import com.happy.hipok.domain.TitleRef;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TitleRef entity.
 */
public interface TitleRefRepository extends JpaRepository<TitleRef,Long> {

    List<TitleRef> findAllByOrderByLabel();
}
