package com.example.demo.src.category;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.category.Model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/categories")
public class CategoryController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CategoryProvider categoryProvider;
    @Autowired
    private final CategoryService categoryService;
    @Autowired
    private  final JwtService jwtService; //이건 아마 안쓸듯

    public CategoryController(CategoryProvider categoryProvider, CategoryService categoryService, JwtService jwtService) {
        this.categoryProvider = categoryProvider;
        this.categoryService = categoryService;
        this.jwtService = jwtService;
    }

    // Todo : 카테고리 생성 삭제 수정에대한 권한 체크 추가 필요

    /**
     * 카테고리 조회 API
     * [Get] /app/categories
     */
    @GetMapping("")
    public BaseResponse<List<GetCategoryRes>> getCategory(){
        try {
            List<GetCategoryRes> getCategoryRes = categoryProvider.getCategory();
            return new BaseResponse<>(getCategoryRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 카테고리 생성 API
     * [Post] /app/categories
     */
    @PostMapping("")
    public BaseResponse<PostCategoryRes> createCategory(@RequestBody PostCategoryReq postCategoryReq){
        try {
             PostCategoryRes postCategoryRes = categoryService.createCategory(postCategoryReq);
             return new BaseResponse<>(postCategoryRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 카테고리 수정 API
     * [Patch] /app/categories/:categoryId
     */
    @PatchMapping("{categoryId}")
    public BaseResponse<String> modifyCategory(@PathVariable long categoryId, @RequestBody Category category){
        try{
            PatchCategoryReq patchCategoryReq = new PatchCategoryReq(categoryId, category.getCategoryName());
            categoryService.modifyCategory(patchCategoryReq);
            String result = "카테고리가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 카테고리 삭제 API
     * [Delete] /app/categories/:categoryId
     */
    @DeleteMapping("{categoryId}")
    public BaseResponse<String> deleteCategory(@PathVariable long categoryId){
        try {
            categoryService.deleteCategory(categoryId);
            String result = "카테고리가 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
