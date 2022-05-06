package com.example.demo.src.wish.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetWishProductsRes {
    private String productId;
    private String sellerId;
    private String image;
    private String title;
    private String areaName;
    private long price;
    private int chatCount;
    private int wishCount;
    private String productStatus;
}
