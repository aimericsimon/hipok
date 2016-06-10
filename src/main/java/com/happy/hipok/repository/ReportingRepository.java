package com.happy.hipok.repository;

import com.happy.hipok.domain.Reporting;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Reporting entity.
 */
public interface ReportingRepository extends JpaRepository<Reporting,Long> {

    Optional<Reporting> findOneByReporterProfileIdAndReportedPublicationId(Long id, Long publicationId);

    Long countByReportedPublicationId(Long publicationId);
}
