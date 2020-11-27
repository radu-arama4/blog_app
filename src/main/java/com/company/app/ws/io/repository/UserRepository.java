package com.company.app.ws.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.company.app.ws.io.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findById(String id);
//	UserEntity findByEncryptedPassword(String pass);
//
//	UserEntity deleteByEmail(String email);
}
