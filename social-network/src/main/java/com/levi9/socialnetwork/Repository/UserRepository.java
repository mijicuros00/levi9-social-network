package com.levi9.socialnetwork.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.levi9.socialnetwork.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {

	User findByUsername(String username);

	@Query(value = "Select * from user WHERE user.id = :userId", nativeQuery = true)
	User findUsersInGroup(Long userId);
}
