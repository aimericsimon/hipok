package com.happy.hipok.repository;

import com.happy.hipok.domain.Profile;
import com.happy.hipok.domain.ProfileWithFollowCount;
import com.happy.hipok.domain.Suggestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 *
 * Spring Data JPA repository for the Profile entity.
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    /**
     * Profiles that I don't follow
     */
    String FIND_UNFOLLOWED_PROFILES =
        "SELECT pro.id FROM Profile pro " +
            "WHERE pro.id NOT IN " +
            "(SELECT follow.follower.id FROM Follow follow WHERE follow.following.extendedUser.user.login = ?#{principal.username} AND follow.state = 'ACCEPTED' ) " +
            "AND pro.extendedUser.user.login <> ?#{principal.username}";

    /**
     * Find first profile by tnp
     */
    String FIND_PROFILE_BY_TNP =
        "SELECT p " +
            "FROM Profile p " +
            "WHERE " +
            "(p.extendedUser.titleRef.abbreviation = :titleAbbreviation " +
            "AND UPPER(p.extendedUser.user.firstName) = :firstParam " +
            "AND UPPER(p.extendedUser.user.lastName) LIKE CONCAT('%', :secondParam, '%')) " +
            "OR (p.extendedUser.titleRef.abbreviation = :titleAbbreviation " +
            "AND UPPER(p.extendedUser.user.firstName) = CONCAT(:firstParam, ' ', :secondParam) " +
            "AND UPPER(p.extendedUser.user.lastName) LIKE CONCAT('%', :thirdParam, '%'))";
    @Query(FIND_PROFILE_BY_TNP)
    Optional<Profile> findProfileByTNP(@Param("titleAbbreviation") String titleAbbreviation,
                                       @Param("firstParam") String firstParam,
                                       @Param("secondParam") String secondParam,
                                       @Param("thirdParam") String thirdParam);

    @Query("select profile from Profile profile where profile.extendedUser.user.login = ?#{principal.username}")
    Profile getCurrentProfile();

    @Query("select profile from Profile profile where profile.id = :id")
    Profile getProfile(@Param("id") Long id);

    /**
     *
     */
    String FIND_PROFILE_WITH_COUNTS =
        "SELECT DISTINCT new com.happy.hipok.domain.ProfileWithFollowCount(profile, COUNT(DISTINCT following), COUNT(DISTINCT follower)) " +
            "FROM Profile profile " +
            "LEFT JOIN profile.followers follower WITH follower.state = 'ACCEPTED' " +
            "LEFT JOIN profile.followings following WITH following.state = 'ACCEPTED' ";

    @Query(FIND_PROFILE_WITH_COUNTS+" WHERE profile.id = :id GROUP BY profile.id")
    ProfileWithFollowCount findProfileWithFollowCount(@Param("id") Long id);

    @Query(FIND_PROFILE_WITH_COUNTS+" WHERE profile.extendedUser.user.login = ?#{principal.username} GROUP BY profile.id")
    ProfileWithFollowCount findScopeProfileWithFollowCount();

    /**
     * Find profile with follower and followed counts
     */
    String SEARCH_SUGGESTION =
        "SELECT new com.happy.hipok.domain.Suggestion(p, COUNT(DISTINCT r)) " +
            "FROM Profile p " +
            "LEFT JOIN p.followers r " +
            "WHERE "+
            "p.extendedUser.user.login <> ?#{principal.username} "+
            "AND ("+
            "  (r.id IS NOT NULL AND r.following.id IN ( SELECT follow.following.id FROM Follow follow WHERE follow.follower.extendedUser.user.login = ?#{principal.username} ) ) "+
            "  OR   "+
            "  p.extendedUser.address.city IN (select profile.extendedUser.address.city from Profile profile where profile.extendedUser.user.login = ?#{principal.username}) "+
            "  OR "+
            "  p.extendedUser.medicalTypeRef.id IN (select profile.extendedUser.medicalTypeRef.id from Profile profile where profile.extendedUser.user.login = ?#{principal.username}) "+
            ") " +
            "GROUP BY p.id ORDER BY p.id DESC";
    @Query(SEARCH_SUGGESTION)
    Page<Suggestion> findProfileBySuggestion(Pageable pageable);
}
