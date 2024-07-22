package com.example.demo.controllers;

import java.util.List;
import org.modelmapper.ModelMapper;
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
import com.example.demo.entity.UserEntity;
import com.example.demo.enums.ActivityStatus;
import com.example.demo.handlers.WebResponse;
import com.example.demo.repositories.UserRepository;
import com.example.demo.response.GetUserInfoResponseDto;
import com.example.demo.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
// import lombok.launch.PatchFixesHider.Val;

@Controller
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping(path = "/practiceCrud")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/users")
    @ResponseBody
    public ResponseEntity<WebResponse> getAllUserInfo(){

        WebResponse webResponse = userService.getAllUserInfo();

        switch (webResponse.getStatus()) {
            case SUCCESS:
                return new ResponseEntity<WebResponse>(webResponse, HttpStatus.CREATED);

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
    public GetUserInfoResponseDto addUserInfo(@RequestBody SaveUserInfoRequestDto practiceCrudRequestDto, @CookieValue(value = "addUserButtonClickCountBackend", defaultValue = "0") String cookieValue, HttpServletResponse responseCookie){
        try{
            UserEntity practiceEntity = modelMapper.map(practiceCrudRequestDto, UserEntity.class);
            UserEntity userEntity =  userRepository.save(practiceEntity);
            GetUserInfoResponseDto responseDto = modelMapper.map(userEntity, GetUserInfoResponseDto.class);
            
            int clickCount = Integer.parseInt(cookieValue)+1;
            Cookie updateCookie = new Cookie("addUserButtonClickCountBackend", String.valueOf(clickCount));
            responseCookie.addCookie(updateCookie);

            return responseDto;
        } catch(Exception e){
            System.out.println("Error message: "+e.getMessage());
            return null;
        }
    }

    @DeleteMapping("/deleteUserInfo/{id}")
    @ResponseBody
    public ActivityStatus deleteUser(@PathVariable Long id){
        try{
            if(userRepository.findById(id).isEmpty()){
                throw new Exception("User not found with id: "+id);
            }
            userRepository.deleteById(id);
            return ActivityStatus.SUCCESS;
        } catch(Exception e){
            System.out.println(e.getMessage());
            return ActivityStatus.FAILED;
        }
    }

    @PutMapping("/editUserInfo/{id}")
    @ResponseBody
    public ActivityStatus editUserInfoByUserId(@PathVariable Long id, @RequestBody EditUserInfoRequestDto editUserInfoRequestDto, @CookieValue(value = "editUserButtonClickCountBackend", defaultValue = "0") String cookieValue, HttpServletResponse responseCookie){
        try{
            UserEntity userNewInfo = userRepository.findById(id)
                                        .orElseThrow(()-> new Exception("User not found with id: "+id));

            userNewInfo = modelMapper.map(editUserInfoRequestDto, UserEntity.class);
            userRepository.save(userNewInfo);

            int clickCount = Integer.parseInt(cookieValue)+1;
            Cookie updateCookie = new Cookie("editUserButtonClickCountBackend", String.valueOf(clickCount));
            responseCookie.addCookie(updateCookie);

            return ActivityStatus.SUCCESS;
        } catch(Exception e){
            System.out.println("Error = " + e.getMessage());
            return ActivityStatus.ERROR;
        }
    }
}
