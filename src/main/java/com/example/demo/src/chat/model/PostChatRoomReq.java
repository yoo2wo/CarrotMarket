package com.example.demo.src.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //왜 추가해야될까
public class PostChatRoomReq {
    private long productId; //?
    private long userId;
    private long sellerId;
}
