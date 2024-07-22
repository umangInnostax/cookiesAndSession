package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.RequestDto.EditUserInfoRequestDto;
import com.example.demo.RequestDto.SaveUserInfoRequestDto;
import com.example.demo.handlers.WebResponse;
import com.example.demo.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping(path = "/practiceCrud")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ResponseBody
    public ResponseEntity<WebResponse> getAllUserInfo(){

        WebResponse webResponse = userService.getAllUserInfo();

        switch (webResponse.getStatus()) {
            case SUCCESS:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.OK);

            case FAILED:
            return new ResponseEntity<WebResponse>(webResponse, HttpStatus.BAD_REQUEST);

            case ERROR:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);

            default:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public ResponseEntity<WebResponse> getUserInfoById(@PathVariable Long id){
        WebResponse webResponse = userService.getUserInfoById(id);

        switch (webResponse.getStatus()) {
            case SUCCESS:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.OK);

            case FAILED:
            return new ResponseEntity<WebResponse>(webResponse, HttpStatus.NOT_FOUND);

            case ERROR:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);

            default:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addUserInfo")
    @ResponseBody
    public ResponseEntity<WebResponse> addUserInfo(@RequestBody SaveUserInfoRequestDto practiceCrudRequestDto, @CookieValue(value = "addUserButtonClickCountBackend", defaultValue = "0") String cookieValue, HttpServletResponse responseCookie){
        int clickCount = Integer.parseInt(cookieValue)+1;
        Cookie updateCookie = new Cookie("addUserButtonClickCountBackend", String.valueOf(clickCount));
        responseCookie.addCookie(updateCookie);

        WebResponse webResponse = userService.addUserInfo(practiceCrudRequestDto);
        
        switch (webResponse.getStatus()) {
            case SUCCESS:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.CREATED);

            case FAILED:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.BAD_REQUEST);

            case ERROR:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);

            default:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @DeleteMapping("/deleteUserInfo/{id}")
    @ResponseBody
    public ResponseEntity<WebResponse> deleteUser(@PathVariable Long id){
        WebResponse webResponse = userService.deleteUser(id);
        
        switch (webResponse.getStatus()) {
            case SUCCESS:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.OK);

            case FAILED:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.BAD_REQUEST);

            case ERROR:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);

            default:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editUserInfo/{id}")
    @ResponseBody
    public ResponseEntity<WebResponse> editUserInfoByUserId(@PathVariable Long id, @RequestBody EditUserInfoRequestDto editUserInfoRequestDto, @CookieValue(value = "editUserButtonClickCountBackend", defaultValue = "0") String cookieValue, HttpServletResponse responseCookie){
        int clickCount = Integer.parseInt(cookieValue)+1;
        Cookie updateCookie = new Cookie("editUserButtonClickCountBackend", String.valueOf(clickCount));
        responseCookie.addCookie(updateCookie);
        
        WebResponse webResponse = userService.deleteUser(id);
        
        switch (webResponse.getStatus()) {
            case SUCCESS:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.OK);

            case FAILED:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.BAD_REQUEST);

            case ERROR:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);

            default:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
