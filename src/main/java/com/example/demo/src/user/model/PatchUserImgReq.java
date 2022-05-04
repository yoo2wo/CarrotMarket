package com.example.demo.src.user.model;

import lombok.*;

/**
 * user의 img 변경을 위한 model
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchUserImgReq {
    private int userId;
    private String imageUrl;
}
