package com.example.boardtest.controller;

import com.example.boardtest.model.entity.UserEntity;
import com.example.boardtest.model.post.Post;
import com.example.boardtest.model.reply.Reply;
import com.example.boardtest.model.user.*;
import com.example.boardtest.service.PostService;
import com.example.boardtest.service.ReplyService;
import com.example.boardtest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    ReplyService replyService;

    //회원가입
    @PostMapping
    public ResponseEntity<User> singup(@Valid @RequestBody UserSingUpRequestBody userSingupReqestBody){
        var user = userService.signUp(userSingupReqestBody.username(),userSingupReqestBody.password());
        return ResponseEntity.ok(user);
//        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //로그인
    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthenticationResponse> authenticate(
            @Valid @RequestBody UserLoginRequestBody requestBody) {
        var response = userService.login(requestBody.username(), requestBody.password());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    //검색
    @GetMapping
    public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String query, Authentication authentication){
        var user = userService.getUsers(query, (UserEntity) authentication.getPrincipal());
        return ResponseEntity.ok(user);
    }


    //단건 조회
    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username, Authentication authentication){
        var user = userService.getUser(username, (UserEntity) authentication.getPrincipal());
        return ResponseEntity.ok(user);
    }

    //정보수정
    @PatchMapping("/{username}")
    public ResponseEntity<User> getUserUpdate(@PathVariable String username, @RequestBody UserPatchRequestBody requestBody, Authentication authentication){
        var user = userService.updateUser(username,requestBody,(UserEntity) authentication.getPrincipal());
        return ResponseEntity.ok(user);
    }


    //특정 유저가 작성한 게시물을 모두 조회
    @GetMapping("/{username}/posts")
    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable String username,Authentication authentication){
        var posts = postService.getPostsByUsername(username,(UserEntity) authentication.getPrincipal());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }



    //follows 추가
    @PostMapping("/{username}/follows")
    public ResponseEntity<User> follow(@PathVariable String username, Authentication authentication) {
        var user = userService.follow(username, (UserEntity) authentication.getPrincipal());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //follows 취소
    @DeleteMapping("/{username}/follows")
    public ResponseEntity<User> unfollow(
            @PathVariable String username, Authentication authentication) {
        var user = userService.unFollow(username, (UserEntity) authentication.getPrincipal());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //followers목록
    @GetMapping("/{username}/followers")
    public ResponseEntity<List<Follower>> getFollowersByUser(
            @PathVariable String username, Authentication authentication) {
        var followers =
                userService.getFollowersByUsername(username, (UserEntity) authentication.getPrincipal());
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    //followings목록
    @GetMapping("/{username}/followings")
    public ResponseEntity<List<User>> getFollowingsByUser(
            @PathVariable String username, Authentication authentication) {
        var followings =
                userService.getFollowingsByUsername(username, (UserEntity) authentication.getPrincipal());
        return new ResponseEntity<>(followings, HttpStatus.OK);
    }


    //댓글목록
    @GetMapping("/{username}/replies")
    public ResponseEntity<List<Reply>> getRepliesByUser(@PathVariable String username) {
        var replies = replyService.getRepliesByUser(username);
        return new ResponseEntity<>(replies, HttpStatus.OK);
    }

    //좋아요 목록
    @GetMapping("/{username}/liked-users")
    public ResponseEntity<List<LikedUser>> getLikedUsersByUser(
            @PathVariable String username, Authentication authentication) {

        var likedUsers =
                userService.getLikedUsersByUser(username, (UserEntity) authentication.getPrincipal());

        return new ResponseEntity<>(likedUsers, HttpStatus.OK);
    }



}
