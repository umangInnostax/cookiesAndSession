package com.example.demo.handlers;

import com.example.demo.enums.ActivityStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse {
    private Object response; 
    private String message; 
    private ActivityStatus status;
    private String error;
}
