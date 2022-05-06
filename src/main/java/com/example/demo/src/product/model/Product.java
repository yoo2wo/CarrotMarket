package com.example.demo.src.product.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString //추가
public class Product {
    private int userId;
    private int areaId;
    private int categoryId;
    private String title;
    private String mainText;
    private Long price;
    private String priceProposal;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
}
