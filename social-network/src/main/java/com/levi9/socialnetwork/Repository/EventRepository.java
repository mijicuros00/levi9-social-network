package com.levi9.socialnetwork.Repository;

import com.levi9.socialnetwork.Model.Comment;
import com.levi9.socialnetwork.Model.Event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * FROM event\r\n" + "WHERE event.end_date < CURRENT_TIMESTAMP ", nativeQuery = true)
    List<Event> getAllExpiredEvents();

}
