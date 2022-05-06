package com.example.demo.src.wish.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteWishReq {
    private int productId;
    private int userId;
}
