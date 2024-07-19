package com.example.demo.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EditUserInfoRequestDto {
    private Long userId;
    private String name;
    private String mobileNo;
    private String address;
    private String position;
}
