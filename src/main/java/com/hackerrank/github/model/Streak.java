package com.hackerrank.github.model;

import java.sql.Timestamp;

public class Streak{
    private long id;
    private long count;
    private long consecutiveDays;
    private Timestamp previousCommit;
    private Timestamp latestTimeStamp;
    private String login;


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Timestamp getLatestTimeStamp() {
        return this.latestTimeStamp;
    }

    public void setLatestTimeStamp(Timestamp latestTimeStamp) {
        this.latestTimeStamp = latestTimeStamp;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getConsecutiveDays() {
        return this.consecutiveDays;
    }

    public void setConsecutiveDays(long consecutiveDays) {
        this.consecutiveDays = consecutiveDays;
    }

    public Timestamp getPreviousCommit(){
        return previousCommit;
    }

    public void setPreviousCommit(Timestamp previousCommit){
        this.previousCommit = previousCommit;
    }

    public void incrementCount(){
        this.count++;
    }

    public void incrementConsecutiveDay(){
        this.consecutiveDays++;
    }

}