package com.happy.hipok.repository;

import com.happy.hipok.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Follow entity.
 */
public interface FollowRepository extends JpaRepository<Follow,Long> {

    @Query("SELECT follow FROM Follow follow WHERE follow.state = 'PENDING' AND follow.follower.extendedUser.user.login = ?#{principal.username} ")
    List<Follow> findPendingFollowers();

    @Query("SELECT follow FROM Follow follow WHERE follow.state = 'PENDING' AND follow.following.extendedUser.user.login = ?#{principal.username} ")
    List<Follow> findPendingFollowing();

    @Query("SELECT COUNT(follow) FROM Follow follow WHERE follow.state = 'PENDING' AND follow.follower.extendedUser.user.login = ?#{principal.username} ")
    Long countPendingFollowers();

    @Query("SELECT follow FROM Follow follow WHERE follow.following.extendedUser.user.login = ?#{principal.username} ")
    List<Follow> findFollowings();

    @Query("SELECT f FROM Follow f WHERE f.follower.id = :followerId and f.following.id = :followingId")
    Optional<Follow> findFollow(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

    @Query("SELECT follow FROM Follow follow WHERE follow.follower.id = :followingId AND follow.following.extendedUser.user.login = ?#{principal.username} ")
    Follow findFollowing(@Param("followingId") Long followingId);

    @Query("SELECT follow FROM Follow follow WHERE follow.follower.extendedUser.user.login = ?#{principal.username} AND follow.state = 'ACCEPTED' ")
    List<Follow> findAcceptedFollowers();

    @Query("SELECT follow FROM Follow follow WHERE follow.follower.id = :id AND follow.state = 'ACCEPTED' ")
    List<Follow> findAcceptedFollowers(@Param("id") Long id);

    @Query("SELECT follow FROM Follow follow WHERE follow.following.extendedUser.user.login = ?#{principal.username} AND follow.state = 'ACCEPTED' ")
    List<Follow> findAcceptedFollowings();

    @Query("SELECT follow FROM Follow follow WHERE follow.following.id = :id AND follow.state = 'ACCEPTED' ")
    List<Follow> findAcceptedFollowings(@Param("id") Long id);


}
