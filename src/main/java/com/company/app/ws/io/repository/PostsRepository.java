package com.company.app.ws.io.repository;

import com.company.app.ws.io.entity.PostEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PostsRepository extends CrudRepository<PostEntity, Long> {
    PostEntity findByTitleAndUserId(String title, String userId);
    ArrayList<PostEntity> findAllByUserId(String userId);

    PostEntity findByTitle(String title);
}
