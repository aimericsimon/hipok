package com.happy.hipok.repository;

import com.happy.hipok.domain.AuthEtat;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AuthEtat entity.
 */
@SuppressWarnings("unused")
public interface AuthEtatRepository extends JpaRepository<AuthEtat,Long> {

}
