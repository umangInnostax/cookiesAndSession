package com.example.demo.service;

import java.util.List;

import com.example.demo.handlers.WebResponse;
import com.example.demo.response.GetUserInfoResponseDto;

public interface UserService {
    public WebResponse getAllUserInfo();
    public WebResponse getUserInfoById(Long id);
}
