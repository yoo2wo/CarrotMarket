package com.example.demo.src.product.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED) => 이걸 없애야 유연하게 사용 가능
public class PostProductReq {
    private int userId;
    private int areaId;
    private int categoryId;
    private String title;
    private String mainText;
    private float price;
    private String pricePropsal;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
}