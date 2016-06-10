package com.happy.hipok.repository;

import com.happy.hipok.domain.Edito;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Edito entity.
 */
public interface EditoRepository extends JpaRepository<Edito,Long> {

}
