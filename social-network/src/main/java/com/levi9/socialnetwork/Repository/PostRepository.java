package com.levi9.socialnetwork.Repository;

import com.levi9.socialnetwork.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findPostById(Long id);
}
