package com.company.app.ws.service;

import com.company.app.ws.shared.dto.PostDto;
import com.company.app.ws.shared.dto.UpdatedPostDto;

import java.util.ArrayList;

public interface PostService {
    PostDto createPost(PostDto post);
    ArrayList<PostDto> readPost(PostDto post);
    PostDto updatePost(UpdatedPostDto post);
    String deletePost(PostDto post);
}
