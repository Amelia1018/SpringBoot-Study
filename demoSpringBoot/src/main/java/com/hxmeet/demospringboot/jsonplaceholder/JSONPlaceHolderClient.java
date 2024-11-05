package com.hxmeet.demospringboot.jsonplaceholder;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value="jsonplaceholder",url="http://jsonplaceholder.typicode.com/")
public interface JSONPlaceHolderClient {

    @GetMapping("posts")
    List<Post> getPosts();

    @GetMapping("posts/{postId}")
    Post getPost(@RequestParam("postId") Integer postId);
}