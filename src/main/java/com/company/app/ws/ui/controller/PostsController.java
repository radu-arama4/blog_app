package com.company.app.ws.ui.controller;

import com.company.app.ws.service.PostService;
import com.company.app.ws.shared.dto.PostDto;
import com.company.app.ws.shared.dto.UpdatedPostDto;
import com.company.app.ws.ui.model.request.GetPostRequest;
import com.company.app.ws.ui.model.request.MakePostRequest;
import com.company.app.ws.ui.model.request.PostDetailsRequest;
import com.company.app.ws.ui.model.request.UpdatePostRequest;
import com.company.app.ws.ui.model.response.PostDetailsResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("posts")
public class PostsController {

    @Autowired
    PostService postService;

    @GetMapping
    public ArrayList<PostDetailsResponse> getPost(@RequestBody GetPostRequest getPost) {
        ArrayList<PostDetailsResponse> returnedPosts = new ArrayList<>();

        PostDto postDto = new PostDto();
        BeanUtils.copyProperties(getPost, postDto);

        ArrayList<PostDto> posts = postService.readPost(postDto);

        for (PostDto post:posts){
            PostDetailsResponse returnedPost = new PostDetailsResponse();
            BeanUtils.copyProperties(post, returnedPost);
            returnedPosts.add(returnedPost);
        }

        return returnedPosts;
    }

    @PostMapping
    public PostDetailsResponse writePost(@RequestBody MakePostRequest post) {
        PostDetailsResponse returnedPost = new PostDetailsResponse();

        PostDto postDto = new PostDto();
        BeanUtils.copyProperties(post, postDto);

        PostDto createdPost = postService.createPost(postDto);
        BeanUtils.copyProperties(createdPost, returnedPost);

        return returnedPost;
    }

    @PutMapping
    public PostDetailsResponse updatePost(@RequestBody UpdatePostRequest post) {
        PostDetailsResponse returnedPost = new PostDetailsResponse();

        UpdatedPostDto postDto = new UpdatedPostDto();
        BeanUtils.copyProperties(post, postDto);

        PostDto createdPost = postService.updatePost(postDto);
        BeanUtils.copyProperties(createdPost, returnedPost);

        return returnedPost;
    }

    @DeleteMapping
    public String deletePost(@RequestBody PostDetailsRequest post) {
        PostDto postDto = new PostDto();
        BeanUtils.copyProperties(post, postDto);
        String deletedPostTitle = postService.deletePost(postDto);

        return "Post with title " + deletedPostTitle + " deleted sucessfully";
    }

}
