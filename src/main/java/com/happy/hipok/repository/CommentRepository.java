package com.happy.hipok.repository;

import com.happy.hipok.domain.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Comment entity.
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select distinct comment from Comment comment left join fetch comment.hashtags")
    List<Comment> findAllWithEagerRelationships();

    @Query("select comment from Comment comment left join fetch comment.hashtags where comment.id =:id")
    Comment findOneWithEagerRelationships(@Param("id") Long id);

    Page<Comment> findAllByPublicationIdOrderByCreationDateDesc(Long publicationId, Pageable pageable);
}
