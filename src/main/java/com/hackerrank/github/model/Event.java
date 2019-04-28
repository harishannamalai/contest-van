package com.hackerrank.github.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.FetchMode;
import org.hibernate.annotations.Generated;

@Entity
public class Event implements Serializable {
    @Id
    private Long id;
    private String type;
    @OneToOne(fetch =  FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private Actor actor;
    @OneToOne(fetch =  FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private Repo repo;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("created_at")
    private Timestamp createdAt;

    public Event() {
    }

    public Event(Long id, String type, Actor actor, Repo repo, Timestamp createdAt) {
        this.id = id;
        this.type = type;
        this.actor = actor;
        this.repo = repo;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Repo getRepo() {
        return repo;
    }

    public void setRepo(Repo repo) {
        this.repo = repo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", type='" + getType() + "'" +
            ", actor='" + getActor() + "'" +
            ", repo='" + getRepo() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
    
}
