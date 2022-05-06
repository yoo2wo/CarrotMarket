package com.example.demo.src.product.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchProductReq {
   private int productId;
   private String img1;
   private String img2;
   private String img3;
   private String img4;
   private String img5;
   private int categoryId;
   private String priceProposal;
   private long price;
   private String title;
   private String mainText;
}
