package com.example.demo.service;

import com.example.demo.RequestDto.EditUserInfoRequestDto;
import com.example.demo.RequestDto.SaveUserInfoRequestDto;
import com.example.demo.handlers.WebResponse;

public interface UserService {
    public WebResponse getAllUserInfo();
    public WebResponse getUserInfoById(Long id);
    public WebResponse addUserInfo(SaveUserInfoRequestDto request);
    public WebResponse deleteUser(Long id);
    public WebResponse editUserInfoByUserId(Long id, EditUserInfoRequestDto request);
}
