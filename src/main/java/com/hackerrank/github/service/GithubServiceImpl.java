package com.hackerrank.github.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.transaction.Transactional;

import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.model.Repo;
import com.hackerrank.github.model.Streak;
import com.hackerrank.github.repository.ActorRepository;
import com.hackerrank.github.repository.EventRepository;
import com.hackerrank.github.repository.RepoRepository;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GithubServiceImpl implements GithubService {

    Logger logger = LogManager.getLogger(GithubServiceImpl.class);

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    RepoRepository repoRepository;

    @Transactional(rollbackOn = SQLException.class)
    public int persistsEvent(Event event) {

        int responseCode = 500;
        try {

            if (eventRepository.exists(event.getId())) {
                responseCode = 400;
            } else {
                Actor returnActor = actorRepository.save(event.getActor());
                Repo returnRepo = repoRepository.save(event.getRepo());
                Event returnEvent = eventRepository.save(event);
                if (logger.isDebugEnabled()) {
                    logger.debug("Actor after Persist" + returnActor);
                    logger.debug("Repo after Persist" + returnRepo);
                    logger.debug("Event after Persist" + returnEvent);
                }
                if (returnActor != null && returnRepo != null && returnActor != null) {
                    responseCode = 201;
                }
            }

        } catch (Exception e) {
            logger.error(e);
            responseCode = 500;
        }

        return responseCode;

    }

    public List<Event> getAllEvents() {

        if (logger.isDebugEnabled()) {
            logger.debug("Getting all Events!");
        }
        return eventRepository.findAll();

    }

    public List<Actor> getAllActors() {

        final Map<Long, Streak> streakMap = new TreeMap<>();
        final Map<Long, Actor> actorMap = new TreeMap<>();

        eventRepository.findAll().forEach(e -> {
            actorMap.put(e.getActor().getId(), e.getActor());
            if (streakMap.containsKey(e.getActor().getId())) {
                Streak s = streakMap.get(e.getActor().getId());
                s.incrementCount();
                if (e.getCreatedAt().after(s.getLatestTimeStamp())) {
                    s.setLatestTimeStamp(e.getCreatedAt());
                }
            } else {
                Streak s = new Streak();
                s.incrementCount();
                s.setId(e.getActor().getId());
                s.setLatestTimeStamp(e.getCreatedAt());
                s.setLogin(e.getActor().getLogin());
                streakMap.put(e.getActor().getId(), s);
            }
        });

        List<Streak> sortedList = new ArrayList<>(streakMap.values());
        Collections.sort(sortedList, new ActorsSorter());

        List<Actor> actors = new ArrayList<>();
        sortedList.forEach(s -> {
            actors.add(actorMap.get(s.getId()));
        });

        return actors;

    }

    public List<Repo> getAllRepos() {

        if (logger.isDebugEnabled()) {
            logger.debug("Getting all Repos!");
        }
        return repoRepository.findAll();

    }

    @Transactional()
    public int deleteAll() {
        if (logger.isDebugEnabled()) {
            logger.debug("Getting all Repos!");
        }
        try {
            eventRepository.deleteAllInBatch();
            actorRepository.deleteAllInBatch();
            repoRepository.deleteAllInBatch();
            return 200;
        } catch (Exception e) {
            logger.error(e);
            return 503;
        }

    }

    public int updateActor(Actor actor) {
        int responseCode = 0;
        if (logger.isDebugEnabled()) {
            logger.debug("Updating details of Paticular Actor" + actor);
        }

        try {
            if (actorRepository.exists(actor.getId())) {
                actorRepository.saveAndFlush(actor);
            }
            responseCode = 200;
        } catch (Exception e) {
            logger.error(e);
            responseCode = 500;
        }

        return responseCode;
    }

    public List<Event> getEventByActor(long id) {
        try {
            Actor actor = new Actor();
            actor.setId(id);
            return eventRepository.findByActor(actor);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
    }

    public Actor getActor(long id) {
        if (logger.isDebugEnabled()) {
            logger.debug("Fetching details of Paticular Actor with Id: " + id);
        }

        Actor actor = actorRepository.findOne(id);

        if (logger.isDebugEnabled()) {
            logger.debug("fetched Actor" + actor);
        }

        return actor;
    }

    public List<Actor> getStreak() {

        final Map<Long, Streak> streakMap = new TreeMap<>();
        final Map<Long, Actor> actorMap = new TreeMap<>();

        eventRepository.findAlEventsSorted().forEach(e -> {
            actorMap.put(e.getActor().getId(), e.getActor());
            if (streakMap.containsKey(e.getActor().getId())) {
                Streak s = streakMap.get(e.getActor().getId());
                s.incrementCount();
                if (e.getCreatedAt().after(s.getLatestTimeStamp())) {
                    s.setLatestTimeStamp(e.getCreatedAt());
                }
                if (s.getPreviousCommit() != null && ((e.getCreatedAt().toLocalDateTime().getDayOfYear()
                        - s.getPreviousCommit().toLocalDateTime().getDayOfYear() == 1) || (e.getCreatedAt().toLocalDateTime().getDayOfYear() - s.getPreviousCommit().toLocalDateTime().getDayOfYear() == 0))) {
                    s.incrementConsecutiveDay();
                    s.setPreviousCommit(e.getCreatedAt());
                }
            } else {
                Streak s = new Streak();
                s.incrementCount();
                s.setId(e.getActor().getId());
                s.setLatestTimeStamp(e.getCreatedAt());
                s.setLogin(e.getActor().getLogin());
                s.setPreviousCommit(e.getCreatedAt());
                streakMap.put(e.getActor().getId(), s);
            }
        });

        List<Streak> sortedList = new ArrayList<>(streakMap.values());
        Collections.sort(sortedList, new StreakSorter());

        List<Actor> actors = new ArrayList<>();
        sortedList.forEach(s -> {
            actors.add(actorMap.get(s.getId()));
        });
        return actors;
    }

}
