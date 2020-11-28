package com.company.app.ws.service.implementation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

		UserDto returnValue = new UserDto();

		try{
			UserEntity userEntity = userRepository.findByEmail(user.getEmail());
			userEntity.setAge(user.getAge());
			userEntity.setFirstName(user.getFirstName());
			userEntity.setLastName(user.getLastName());

			BeanUtils.copyProperties(userEntity, returnValue);
		}catch (RuntimeException e){
			System.out.println(e.getMessage());
		}

        return null;
    }

    @Override
    public boolean deleteUser(UserDto user) {
        try {
            UserEntity deletedUser = userRepository.findByEmail(user.getEmail());
            if (bCryptPasswordEncoder.matches(user.getPassword(), deletedUser.getEncryptedPassword())) {
                userRepository.delete(deletedUser);
                return true;
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
