package com.levi9.socialnetwork.Repository;

import com.levi9.socialnetwork.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("select e from Event e where e.groupId = :groupId")
    List<Event> findAllInGroup(Long groupId);
}
