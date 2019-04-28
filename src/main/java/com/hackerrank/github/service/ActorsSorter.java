package com.hackerrank.github.service;

import java.util.Comparator;

import com.hackerrank.github.model.Streak;

public class ActorsSorter implements Comparator<Streak>{

    @Override
    public int compare(Streak s1, Streak s2) {

        if(s1.getCount() == s2.getCount()){
            if(s1.getLatestTimeStamp().equals(s2.getLatestTimeStamp())){
                return s1.getLogin().compareToIgnoreCase(s2.getLogin());
            }else{
                if(s1.getLatestTimeStamp().before(s2.getLatestTimeStamp())){
                    return +1;
                }else{
                    return -1;
                }    
            }
        }else{
            if(s1.getCount() < s2.getCount()){
                return +1;
            }else{
                return -1;
            }
        }        
    }
}