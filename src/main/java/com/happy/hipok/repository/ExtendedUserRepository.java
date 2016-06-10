package com.happy.hipok.repository;

import com.happy.hipok.domain.ExtendedUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the ExtendedUser entity.
 */
public interface ExtendedUserRepository extends JpaRepository<ExtendedUser, Long> {

    Optional<ExtendedUser> findOneByRppsRefNumber(String rppsNumber);

    Optional<ExtendedUser> findOneByUserId(Long id);
}
