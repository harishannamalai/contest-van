package com.hackerrank.github.controller;

import com.hackerrank.github.error.DataNotFoundException;
import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.service.GithubService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

@RestController
public class GithubApiRestController {

    Logger logger = LogManager.getLogger(GithubApiRestController.class);

    @Autowired
    GithubService service;

    @PostMapping(path = "/events", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getEvents(@RequestBody Event event) {

        if (logger.isDebugEnabled()) {
            logger.debug("Input Event: " + event);
        }

        int responseCode = service.persistsEvent(event);

        if (logger.isDebugEnabled()) {
            logger.debug("Response received: " + responseCode);
        }

        return new ResponseEntity<>(HttpStatus.valueOf(responseCode));

    }

    @GetMapping(path = "/events/actors/{actorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getActor(@PathVariable("actorId") long actorId) {
        if (logger.isDebugEnabled()) {
            logger.debug("Fetching Actor by Id: " + actorId);
        }

        List<Event> events = service.getEventByActor(actorId);
        if(events ==  null){
            throw new DataNotFoundException("Actor with Id: " + actorId + " Not found!");
        }

        return events;

    }

    @GetMapping(path = "/actors/streak", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Actor> getStreak() {
        if (logger.isDebugEnabled()) {

        }
        logger.info("Request received to fetch Streak of Actors!");
        return service.getStreak();

    }

    @GetMapping(path = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getEvents() {
        if (logger.isDebugEnabled()) {

        }
        logger.info("Request received to fetch all Events!");
        return service.getAllEvents();
    }

    @PutMapping(path = "/actors")
    public ResponseEntity getActors(@RequestBody Actor actor) {
        if (logger.isDebugEnabled()) {

        }
        logger.info("Request received to fetch all Actors!");

        int responseCode = service.updateActor(actor);

        return new ResponseEntity<>(HttpStatus.valueOf(responseCode));
    }

    @GetMapping(path = "/actors")
    public List<Actor> getAllActors() {
        if (logger.isDebugEnabled()) {

        }
        logger.info("Request received to fetch All Actors!");

        return service.getAllActors();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity erase() {

        if (logger.isDebugEnabled()) {

        }
        logger.info("Deleteing all Entries in Database!");

        int responseCode = service.deleteAll();

        return new ResponseEntity<>(HttpStatus.valueOf(responseCode));

    }
}
