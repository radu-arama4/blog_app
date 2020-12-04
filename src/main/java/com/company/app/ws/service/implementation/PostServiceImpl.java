package com.company.app.ws.service.implementation;

import com.company.app.ws.io.entity.PostEntity;
import com.company.app.ws.io.repository.PostsRepository;
import com.company.app.ws.io.repository.UserRepository;
import com.company.app.ws.shared.dto.PostDto;
import com.company.app.ws.shared.dto.UpdatedPostDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.app.ws.service.PostService;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public PostDto createPost(PostDto post) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String currentId = userRepository.findByEmail(principal.getName()).getId();

        if(postsRepository.findByTitleAndUserId(post.getTitle(), currentId)!=null){
            throw new RuntimeException("Already existing a post with this title!");
        }

        PostEntity postEntity = new PostEntity();
        BeanUtils.copyProperties(post, postEntity);

        postEntity.setUser(userRepository.findByEmail(principal.getName()));
        postEntity.setId(UUID.randomUUID().toString());

        PostEntity storedPost = postsRepository.save(postEntity);

        PostDto returnedPost = new PostDto();
        BeanUtils.copyProperties(storedPost, returnedPost);

        return returnedPost;
    }

    @Override
    public ArrayList<PostDto> readPost(PostDto post) {
        if(userRepository.findById(post.getUserId())==null){
            throw new RuntimeException("Not existing such user!");
        }

        ArrayList<PostDto> postsOfUser = new ArrayList<>();

        if(post.getTitle()==null){
            ArrayList<PostEntity> posts = postsRepository.findAllByUserId(post.getUserId());
            for (PostEntity postEntity:posts){
                PostDto postDto = new PostDto();
                BeanUtils.copyProperties(postEntity, postDto);
                postsOfUser.add(postDto);
            }
        }else{
            PostEntity foundPost = postsRepository.findByTitleAndUserId(post.getTitle(), post.getUserId());
            PostDto newPost = new PostDto();
            BeanUtils.copyProperties(foundPost, newPost);
            postsOfUser.add(newPost);
        }

        System.out.println(postsOfUser.size());

        return postsOfUser;
    }

    @Override
    public PostDto updatePost(UpdatedPostDto post) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String currentId = userRepository.findByEmail(principal.getName()).getId();

        if(postsRepository.findByTitleAndUserId(post.getTitle(), currentId)==null){
            throw new RuntimeException("Not existing such user or post!");
        }

        PostDto returnedPost = new PostDto();
        PostEntity foundPost = postsRepository.findByTitleAndUserId(post.getTitle(), currentId);

        if(post.getNewContent()!=null){
            foundPost.setContent(post.getNewContent());
        }

        if(post.getNewTitle()!=null){
            foundPost.setTitle(post.getNewTitle());
        }

        postsRepository.save(foundPost);
        BeanUtils.copyProperties(foundPost, returnedPost);

        return returnedPost;
    }

    @Override
    public String deletePost(PostDto post) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String currentId = userRepository.findByEmail(principal.getName()).getId();

        if(postsRepository.findByTitleAndUserId(post.getTitle(), currentId)==null){
            throw new RuntimeException("Not existing such user or post!");
        }
        String title;
        PostEntity deletedPost = postsRepository.findByTitleAndUserId(post.getTitle(), currentId);
        title = deletedPost.getTitle();
        postsRepository.delete(deletedPost);

        return title;
    }
}
