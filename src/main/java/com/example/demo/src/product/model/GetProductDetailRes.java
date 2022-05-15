package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString //추가
public class GetProductDetailRes {
    private String image_1;
    private String image_2;
    private String image_3;
    private String image_4;
    private String image_5;
    private long userId;
    private String nickname;
    private int userTemp;
    private String userImg;
    private long secDiff; // 나중에 바꿔주어야할수있다.
    private long price;
    private String title;
    private String mainText;
    private String areaName;
    private long wishCount;
    private long chatCount;
    private long viewCount;
}
