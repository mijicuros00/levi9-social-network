package com.levi9.socialnetwork.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.levi9.socialnetwork.Model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}
