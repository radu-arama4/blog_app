package com.company.app.ws.service;

import com.company.app.ws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);

    UserDto getUser(String email);

    UserDto updateUser(UserDto user);

    boolean deleteUser(UserDto user);
}
