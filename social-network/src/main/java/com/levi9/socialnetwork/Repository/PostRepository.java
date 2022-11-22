package com.levi9.socialnetwork.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.levi9.socialnetwork.Model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findPostById(Long id);
    
	@Query(value = "Select * from post inner join public.group on post.id_group = :groupId WHERE public.group.id = :groupId AND post.private = false", nativeQuery = true)
	List<Post> getAllPostsFromGroup(Long groupId);
    
}
