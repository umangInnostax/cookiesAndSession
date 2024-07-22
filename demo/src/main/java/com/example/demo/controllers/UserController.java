package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.repositories.UserRepository;
import com.example.demo.response.GetUserInfoResponseDto;

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
    private ModelMapper modelMapper;

    @GetMapping("/getInfo")
    @ResponseBody
    public List<GetUserInfoResponseDto> getAllUserInfo(){

        List<GetUserInfoResponseDto> responseDto = new ArrayList<GetUserInfoResponseDto>();
        List<UserEntity> requests =  (List<UserEntity>) userRepository.findAll();
        for(UserEntity request:requests){
            GetUserInfoResponseDto userResponse = modelMapper.map(request, GetUserInfoResponseDto.class);
            responseDto.add(userResponse);
        }

        return responseDto;
    }

    @GetMapping("/getInfo/{id}")
    @ResponseBody
    public GetUserInfoResponseDto getUserInfoId(@PathVariable Long id){
        try{
            UserEntity response = userRepository.findById(id)
                                                .orElseThrow(()-> new Exception("User not found with id: "+id));
                                
            GetUserInfoResponseDto responseDto = modelMapper.map(response, GetUserInfoResponseDto.class);

            return responseDto;
        } catch(Exception e){
            System.out.println("Error message: "+e.getMessage());
            return null;
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
