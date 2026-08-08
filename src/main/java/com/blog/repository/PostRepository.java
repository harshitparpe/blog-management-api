package com.blog.repository;

import com.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
        SELECT p FROM Post p
        WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR p.content LIKE CONCAT('%', :keyword, '%')
        """)
    Page<Post> search(@Param("keyword") String keyword, Pageable pageable);

    Page<Post> findByCategoryId(Long categoryId, Pageable pageable);

    boolean existsByCategoryId(Long categoryId);
}
