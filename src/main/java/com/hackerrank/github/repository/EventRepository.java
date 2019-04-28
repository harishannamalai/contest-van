package com.hackerrank.github.repository;

import java.util.List;

import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("DELETE from Event a")
    void deleteAll();

    @Query("FROM Event e ORDER BY e.createdAt")
    List<Event> findAlEventsSorted();

    List<Event> findByActor(Actor actor);
}
