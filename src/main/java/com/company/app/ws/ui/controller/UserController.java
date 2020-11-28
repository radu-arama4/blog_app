package com.company.app.ws.ui.controller;

import com.company.app.ws.exceptions.UserServiceException;
import com.company.app.ws.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.company.app.ws.service.UserService;
import com.company.app.ws.shared.dto.UserDto;
import com.company.app.ws.ui.model.request.UserDetailsRequest;
import com.company.app.ws.ui.model.response.UserDetailsResponse;


@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}",
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public UserDetailsResponse getUser(@PathVariable String id) {
        UserDetailsResponse returnedUser = new UserDetailsResponse();

        UserDto userDto = userService.getUserById(id);
        BeanUtils.copyProperties(userDto, returnedUser);

        return returnedUser;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public UserDetailsResponse createUser(@RequestBody UserDetailsRequest userDetails) throws Exception{
        UserDetailsResponse returnedUser = new UserDetailsResponse();

        if(userDetails.getEmail().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

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