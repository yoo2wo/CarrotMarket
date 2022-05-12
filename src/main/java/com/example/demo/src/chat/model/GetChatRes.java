package com.example.demo.src.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetChatRes {
    private long chatRoomId;
    private long userId;
    private String nickname;
    private String profileImg;
    private float mannerTemp;
    private String message;
    private String msgTime;
}
