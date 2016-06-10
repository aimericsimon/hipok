package com.happy.hipok.repository;

import com.happy.hipok.domain.DeclarationEmail;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DeclarationEmail entity.
 */
public interface DeclarationEmailRepository extends JpaRepository<DeclarationEmail,Long> {

}
