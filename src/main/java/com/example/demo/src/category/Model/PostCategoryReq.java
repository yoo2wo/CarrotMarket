package com.example.demo.src.category.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //이거의 차이가 뭘까?
public class PostCategoryReq {
    private String categoryName;
}
