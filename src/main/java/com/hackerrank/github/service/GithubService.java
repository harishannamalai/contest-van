package com.hackerrank.github.service;

import java.util.List;

import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.model.Repo;

public interface GithubService {

    int persistsEvent(Event event);
    
    List<Event> getAllEvents();
    
    List<Actor> getAllActors();
    
    List<Repo> getAllRepos();
    
    int updateActor(Actor actor);
    
    Actor getActor(long id);

    List<Event> getEventByActor(long actorId);

    List<Actor> getStreak();

    int deleteAll();
    
}
