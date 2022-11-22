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
	
	@Query(value = "SELECT * FROM post\r\n"
			+ "INNER JOIN user_friends on post.id_user = user_friends.id_friend\r\n"
			+ "INNER JOIN public.user on public.user.id = user_friends.id_user\r\n"
			+ "WHERE public.user.id = :userId and post.id_group is null", nativeQuery = true)
	List<Post> getAllPostsFromFriends(Long userId);
    
}
