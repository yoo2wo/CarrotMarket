package com.example.demo.src.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetChatRoomsRes {
    private long chatRoomId;
    private long u1Id;
    private String u1Name;
    private String u1Img;
    private String u1Area;
    private long u2Id;
    private String u2Name;
    private String u2Img;
    private String u2Area;
    private String productImg;
    private String lastMsg;
    private long lastHour;
}
