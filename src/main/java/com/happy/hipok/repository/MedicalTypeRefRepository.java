package com.happy.hipok.repository;

import com.happy.hipok.domain.MedicalTypeRef;

import com.happy.hipok.domain.enumeration.MedicalSubType;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MedicalTypeRef entity.
 */
public interface MedicalTypeRefRepository extends JpaRepository<MedicalTypeRef,Long> {

    List<MedicalTypeRef> findAllByOrderByLabel();

    List<MedicalTypeRef> findAllBySubtypeOrderByLabel(MedicalSubType subtype);
}
