package com.company.app.ws.service.implementation;

import com.company.app.ws.exceptions.UserServiceException;
import com.company.app.ws.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.company.app.ws.io.entity.UserEntity;
import com.company.app.ws.io.repository.UserRepository;
import com.company.app.ws.service.UserService;
import com.company.app.ws.shared.dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Already existing such user!");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setId(UUID.randomUUID().toString());

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUserById(String id) {
        UserDto returnValue = new UserDto();
        try {
            UserEntity userEntity = userRepository.findById(id);
            BeanUtils.copyProperties(userEntity, returnValue);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserDto returnValue = new UserDto();
        try {
            UserEntity userEntity = userRepository.findByEmail(email);
            BeanUtils.copyProperties(userEntity, returnValue);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return returnValue;
    }

    @Override
    public UserDto updateUser(UserDto user) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String currentId = userRepository.findByEmail(principal.getName()).getId();

		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findById(currentId);

		if(userEntity==null)
		    throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		userEntity.setAge(user.getAge());
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());

		UserEntity updatedUser = userRepository.save(userEntity);

		BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @Override
    public void deleteUser() {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String currentId = userRepository.findByEmail(principal.getName()).getId();

        UserEntity userEntity = userRepository.findById(currentId);

        if(userEntity==null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        userRepository.delete(userEntity);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();

        for (UserEntity userEntity:users){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }
}
