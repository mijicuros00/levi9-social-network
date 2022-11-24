package com.levi9.socialnetwork.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.levi9.socialnetwork.Model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	
	@Query(value = "SELECT * FROM event\r\n"
			+ "WHERE event.end_date < CURRENT_TIMESTAMP ", nativeQuery = true)
	List<Event> getAllExpiredEvents();
	
	@Query(value = "SELECT * FROM event "
	        + "WHERE event.start_date - CURRENT_TIMESTAMP < interval '1 HOUR' "
	        + "AND event.start_date - CURRENT_TIMESTAMP > interval '59 MINUTES'"
	        + "AND event.start_date > CURRENT_TIMESTAMP", nativeQuery = true)
    List<Event> getEventsForNotify();
	
}
