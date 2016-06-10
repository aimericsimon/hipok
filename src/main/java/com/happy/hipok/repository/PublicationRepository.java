package com.happy.hipok.repository;

import com.happy.hipok.domain.Publication;
import com.happy.hipok.domain.ScopeItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Publication entity.
 */
public interface PublicationRepository extends JpaRepository<Publication,Long> {

    @Query("select distinct publication from Publication publication left join fetch publication.hashtags")
    List<Publication> findAllWithEagerRelationships();

    @Query("select publication from Publication publication left join fetch publication.hashtags where publication.id =:id")
    Publication findOneWithEagerRelationships(@Param("id") Long id);

    /**
     * Private publications of people that I don't follow
     */
    String FIND_PRIVATE_PUBLICATIONS_OF_UNFOLLOWED_PEOPLE =
        "SELECT pu.id " +
            "FROM Publication pu " +
            "WHERE pu.authorProfile.id IN (" + ProfileRepository.FIND_UNFOLLOWED_PROFILES + ") " +
            "AND pu.visibility='PRIVATE'";

    String WHERE_PRINCIPAL_AUTHOR =
        "WHERE p.authorProfile.extendedUser.user.login = ?#{principal.username} "+
            "GROUP BY p.id "+
            "ORDER BY p.publicationDate DESC ";

    String WHERE_AUTHOR_PROFILE_BY_ID  =
        "WHERE p.id NOT IN (" + FIND_PRIVATE_PUBLICATIONS_OF_UNFOLLOWED_PEOPLE + ") "+
            "AND p.authorProfile.id = :profileId "+
            "GROUP BY p.id " +
            "ORDER BY p.publicationDate DESC ";

    String WITHOUT_PRIVATE_PUBLICATIONS_OF_UNFOLLOWED_PEOPLE =
        "WHERE p.id NOT IN (" + FIND_PRIVATE_PUBLICATIONS_OF_UNFOLLOWED_PEOPLE + ") "+
            "GROUP BY p.id " +
            "ORDER BY p.publicationDate DESC ";

    String BY_PUBLICATION_ID =
        "WHERE p.id NOT IN (" + FIND_PRIVATE_PUBLICATIONS_OF_UNFOLLOWED_PEOPLE + ") " +
            "AND p.id = :publicationId " +
            "GROUP BY p.id " +
            "ORDER BY p.publicationDate DESC ";

    String FIND_SCOPE_ITEMS =
        "SELECT new com.happy.hipok.domain.ScopeItem(p, COUNT(DISTINCT c), COUNT(DISTINCT s)) " +
            "FROM Publication p " +
            "LEFT JOIN p.comments c " +
            "LEFT JOIN p.shares s ";

    String FIND_PROFILE_PUBLICATIONS =
        "SELECT p FROM Publication p WHERE p.authorProfile.extendedUser.user.login = ?#{principal.username} ";

    /**
     * Find publications that current profile is allowed to see
     */
    String FIND_PUBLICATIONS_WITH_CURRENT_PROFILE =
        "SELECT p " +
            "FROM Publication p " +
            "WHERE p.id NOT IN (" + FIND_PRIVATE_PUBLICATIONS_OF_UNFOLLOWED_PEOPLE + ") ";
    /**
     * Find publication with id if current profile has access to it
     */
    String FIND_PUBLICATION_WITH_CURRENT_PROFILE =
        FIND_PUBLICATIONS_WITH_CURRENT_PROFILE +
            "AND p.id = :publicationId";

    String FIND_PUBLICATIONS_AND_COMMENT_WITH_CURRENT_PROFILE =
        "SELECT DISTINCT p " +
            "FROM Publication p left join p.comments c " +
            "WHERE p.id NOT IN (" + FIND_PRIVATE_PUBLICATIONS_OF_UNFOLLOWED_PEOPLE + ") ";

    String QUERY_PUBLICATIONS_WITH_CURRENT_PROFILE =
        FIND_PUBLICATIONS_AND_COMMENT_WITH_CURRENT_PROFILE +
            "AND ("+
            ":query = '' "+
            "OR UPPER(p.description) LIKE CONCAT('%', :query, '%') "+
            "OR UPPER(p.authorProfile.extendedUser.user.firstName) LIKE CONCAT('%', :query, '%') "+
            "OR UPPER(p.authorProfile.extendedUser.user.lastName) LIKE CONCAT('%', :query, '%') "+
            "OR UPPER(c.text) LIKE CONCAT('%', :query, '%') "+
            "OR p.anatomicZoneRef.id IN (SELECT r.id FROM AnatomicZoneRef r WHERE UPPER(r.label) LIKE CONCAT('%', :query, '%') ) "+
            "OR p.specialtyRef.id IN (SELECT s.id FROM SpecialtyRef s WHERE UPPER(s.label) LIKE CONCAT('%', :query, '%') ) "+
            ") " +
            "AND (:anatomicZoneId IS NULL OR p.anatomicZoneRef.id = :anatomicZoneId ) " +
            "AND (:specialtyId IS NULL OR p.specialtyRef.id = :specialtyId  ) " +
            "ORDER BY p.publicationDate DESC";

    @Query(FIND_SCOPE_ITEMS+WHERE_PRINCIPAL_AUTHOR)
    Page<ScopeItem> findProfileScopeItems(Pageable pageable);

    @Query(FIND_PROFILE_PUBLICATIONS)
    List<Publication> findProfilePublication();

    @Query(FIND_SCOPE_ITEMS+WHERE_AUTHOR_PROFILE_BY_ID)
    Page<ScopeItem> findProfileScopeItems(@Param("profileId") Long profileId, Pageable pageable);

    @Query(FIND_SCOPE_ITEMS+WITHOUT_PRIVATE_PUBLICATIONS_OF_UNFOLLOWED_PEOPLE)
    Page<ScopeItem> findScopeItems(Pageable pageable);

    @Query(FIND_PUBLICATION_WITH_CURRENT_PROFILE)
    Publication getPublicationWithCurrentProfile(@Param("publicationId") Long publicationId);

    @Query(FIND_PUBLICATIONS_WITH_CURRENT_PROFILE + "ORDER BY p.publicationDate DESC")
    Page<Publication> findPublications(Pageable pageable);

    @Query(QUERY_PUBLICATIONS_WITH_CURRENT_PROFILE)
    Page<Publication> findPublicationsWithQuery(@Param("query") String query, @Param("anatomicZoneId") Long anatomicZoneId,
                                                @Param("specialtyId") Long specialtyId, Pageable pageable);

    @Query(FIND_SCOPE_ITEMS+BY_PUBLICATION_ID)
    ScopeItem findScopeItem(@Param("publicationId") Long publicationId);
}
