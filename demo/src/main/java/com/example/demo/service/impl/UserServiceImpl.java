package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.RequestDto.EditUserInfoRequestDto;
import com.example.demo.RequestDto.SaveUserInfoRequestDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.enums.ActivityStatus;
import com.example.demo.handlers.WebResponse;
import com.example.demo.repositories.UserRepository;
import com.example.demo.response.GetUserInfoResponseDto;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WebResponse getAllUserInfo(){
        try{
            List<GetUserInfoResponseDto> responseDto = new ArrayList<GetUserInfoResponseDto>();
            List<UserEntity> requests =  (List<UserEntity>) userRepository.findAll();
            for(UserEntity request:requests){
                GetUserInfoResponseDto userResponse = modelMapper.map(request, GetUserInfoResponseDto.class);
                responseDto.add(userResponse);
            }

            WebResponse webResponse = new WebResponse();
            webResponse.setResponse(responseDto);
            webResponse.setStatus(ActivityStatus.SUCCESS);
            webResponse.setMessage("List of all values");
            webResponse.setError("No error found");

            return webResponse;
        } catch(Exception e){
            WebResponse webResponse = new WebResponse();
            webResponse.setError(e.getMessage());
            webResponse.setStatus(ActivityStatus.ERROR);
            webResponse.setMessage("Unable to return the list");
            return webResponse;
        }
    }

    @Override
    public WebResponse getUserInfoById(@PathVariable Long id){
        try{
            WebResponse webResponse = new WebResponse();

            if(userRepository.findById(id).isEmpty()){
                webResponse.setStatus(ActivityStatus.FAILED);
                webResponse.setMessage("Information of user not found");
                webResponse.setError("User not found with id : " + id);
    
                return webResponse;
            }
            UserEntity response = userRepository.findById(id)
                                                .orElseThrow(()-> new Exception("User not found with id: "+id));
                                
            GetUserInfoResponseDto responseDto = modelMapper.map(response, GetUserInfoResponseDto.class);

            webResponse.setResponse(responseDto);
            webResponse.setStatus(ActivityStatus.SUCCESS);
            webResponse.setMessage("Information of user");
            webResponse.setError("No error found");

            return webResponse;
        } catch(Exception e){
            System.out.println("Error message: "+e.getMessage());

            WebResponse webResponse = new WebResponse();

            webResponse.setStatus(ActivityStatus.ERROR);
            webResponse.setMessage("Information of user not found");
            webResponse.setError(e.getMessage());

            return webResponse;
        }
    }

    @Override
    public WebResponse addUserInfo(SaveUserInfoRequestDto request) {
        WebResponse webResponse = new WebResponse();
         try{
            if(request.getAddress()=="" || request.getMobileNo()=="" || request.getName()=="" || request.getPosition()==""){
                webResponse.setError("Incomplete information");
                webResponse.setMessage("User information can not be added");
                webResponse.setStatus(ActivityStatus.FAILED);
                return webResponse;
            }

            UserEntity practiceEntity = modelMapper.map(request, UserEntity.class);
            UserEntity userEntity =  userRepository.save(practiceEntity);
            GetUserInfoResponseDto responseDto = modelMapper.map(userEntity, GetUserInfoResponseDto.class);

            webResponse.setError("No error found");
            webResponse.setMessage("User Information is added");
            webResponse.setResponse(responseDto);
            webResponse.setStatus(ActivityStatus.SUCCESS);

            return webResponse;
        } catch(Exception e){

            webResponse.setError(e.getMessage());
            webResponse.setMessage("User Information is not added");
            webResponse.setStatus(ActivityStatus.ERROR);
            return null;
        }
    }

    @Override
    public WebResponse deleteUser(Long id) {
        WebResponse webResponse = new WebResponse();
        try{
            if(userRepository.findById(id).isEmpty()){
                webResponse.setError("User does not exist with id: "+id);
                webResponse.setMessage("Failed to delete the information");
                webResponse.setStatus(ActivityStatus.FAILED);
                return webResponse;
            }
            webResponse.setResponse(userRepository.findById(id));
            userRepository.deleteById(id);

            webResponse.setError("No error found");
            webResponse.setMessage("User information is deleted");
            webResponse.setStatus(ActivityStatus.SUCCESS);

            return webResponse;
        } catch(Exception e){
            System.out.println(e.getMessage());

            webResponse.setError(e.getMessage());
            webResponse.setMessage("Failed to delete the information");
            webResponse.setStatus(ActivityStatus.SUCCESS);

            return webResponse;
        }
    }

    @Override
    public WebResponse editUserInfoByUserId(Long id, EditUserInfoRequestDto request) {
        WebResponse webResponse = new WebResponse();
        webResponse.setResponse(request);
        try{
            if(userRepository.findById(id).isEmpty()){
                webResponse.setError("User does not exist with id: "+id);
                webResponse.setMessage("Failed to edit the information");
                webResponse.setStatus(ActivityStatus.FAILED);
                return webResponse;
            }
            
            if(request.getAddress()=="" || request.getMobileNo()=="" || request.getName()=="" || request.getPosition()==""){
                webResponse.setError("Incomplete information");
                webResponse.setMessage("Failed to edit the information");
                webResponse.setStatus(ActivityStatus.FAILED);
                return webResponse;
            }

            UserEntity userNewInfo = userRepository.findById(id)
                                            .orElseThrow(() -> new Exception("User not found"));
            userNewInfo = modelMapper.map(request, UserEntity.class);
            userRepository.save(userNewInfo);

            webResponse.setMessage("User information is edited successfully");
            webResponse.setError("No error found");
            webResponse.setStatus(ActivityStatus.SUCCESS);

            return webResponse;
        } catch(Exception e){
            System.out.println("Error = " + e.getMessage());

            webResponse.setMessage("Failed to edit the information");
            webResponse.setError(e.getMessage());
            webResponse.setStatus(ActivityStatus.ERROR);

            return webResponse;
        }
    }

       
}
