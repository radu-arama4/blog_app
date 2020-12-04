package com.company.app.ws.service;

import com.company.app.ws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);

    UserDto getUserById(String id);

    UserDto getUser(String email);

    List<UserDto> getUsers(int page, int limit);

    UserDto updateUser(UserDto user);

    void deleteUser();
}
