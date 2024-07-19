package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInfoResponseDto {
    private Integer userId;
    private String name;
    private String mobileNo;
    private String address;
    private String position; 
}
