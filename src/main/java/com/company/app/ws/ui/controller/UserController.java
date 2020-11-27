package com.company.app.ws.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.app.ws.service.UserService;
import com.company.app.ws.shared.dto.UserDto;
import com.company.app.ws.ui.model.request.UserDetailsRequest;
import com.company.app.ws.ui.model.response.UserDetailsResponse;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public UserDetailsResponse getUser(@RequestBody UserDetailsRequest userDetails) {
        UserDetailsResponse returnedUser = new UserDetailsResponse();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.getUser(userDto.getEmail());
        BeanUtils.copyProperties(createdUser, returnedUser);

        return returnedUser;
    }

    @PostMapping
    public UserDetailsResponse createUser(@RequestBody UserDetailsRequest userDetails) {
        UserDetailsResponse returnedUser = new UserDetailsResponse();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnedUser);

        return returnedUser;
    }

    @PutMapping
    public UserDetailsResponse updateUser(@RequestBody UserDetailsRequest userDetails) {
        UserDetailsResponse returnedUser = new UserDetailsResponse();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnedUser);

        return returnedUser;
    }

    @DeleteMapping
    public String deleteUser(@RequestBody UserDetailsRequest user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);

        if (userService.deleteUser(userDto)) {
            return "User deleted successfully!";
        }

        return "An error has occurred!";
    }
}