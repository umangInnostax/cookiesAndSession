package com.example.demo.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActivityStatus {
    SUCCESS("SUCCESS"), 
    FAILED("FAILED"),
    ERROR("ERROR");

    private final String activityStatus;

    ActivityStatus(String activityStatus){
        this.activityStatus = activityStatus;
    }

    @JsonValue
    public String getActivityStatus(){
        return activityStatus;
    }
}
