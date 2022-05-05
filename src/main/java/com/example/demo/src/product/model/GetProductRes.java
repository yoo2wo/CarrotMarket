package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString //추가
public class GetProductRes {
    private long productId;
    private String imageUrl;
    private long secDiff; // 나중에 바꿔주어야할수있다.
    private long price;
    private String title;
    private String areaName;
    private long wishCount;
    private long chatCount;
}
