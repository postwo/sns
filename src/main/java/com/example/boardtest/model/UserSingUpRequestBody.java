package com.example.boardtest.model;



//record는 dto랑 같은 기능을한다
public record UserSingUpRequestBody(
        String username,
        String password
){ }

