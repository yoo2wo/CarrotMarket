package com.example.demo.src.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetChatInfoRes {
    private long productId;
    private String productTitle;
    private String productImg;
    private long productPrice;
    private String productStatus;
}
