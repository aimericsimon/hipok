package com.happy.hipok.repository;

import com.happy.hipok.domain.Declaration;
import com.happy.hipok.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Declaration entity.
 */
public interface DeclarationRepository extends JpaRepository<Declaration,Long> {

    /**
     *
     * @param profile
     * @return
     */
    List<Declaration> findAllByProfile(Profile profile);

}
