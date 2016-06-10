package com.happy.hipok.repository;

import com.happy.hipok.domain.DeclarationTypeRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the DeclarationTypeRef entity.
 */
public interface DeclarationTypeRefRepository extends JpaRepository<DeclarationTypeRef,Long> {

    List<DeclarationTypeRef> findAllByOrderByLabel();

}
