package com.happy.hipok.repository;

import com.happy.hipok.domain.ImageAuth;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ImageAuth entity.
 */
@SuppressWarnings("unused")
public interface ImageAuthRepository extends JpaRepository<ImageAuth,Long> {

}
