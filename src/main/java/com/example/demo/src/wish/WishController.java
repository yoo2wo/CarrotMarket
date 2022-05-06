package com.example.demo.src.wish;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.wish.model.DeleteWishReq;
import com.example.demo.src.wish.model.GetWishProductsRes;
import com.example.demo.src.wish.model.PostWishReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/app/wishes")
public class WishController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final WishProvider wishProvider;
    @Autowired
    private final WishService wishService;
    @Autowired
    private final JwtService jwtService;

    public WishController(WishProvider wishProvider, WishService wishService, JwtService jwtService) {
        this.wishProvider = wishProvider;
        this.wishService = wishService;
        this.jwtService = jwtService;
    }

    /**
     * 상품 관심 등록
     * [Post] /app/wishes/:productId/users/:userId
     */
    @ResponseBody
    @PostMapping("{productId}/users/{userId}")
    public BaseResponse<String> createWish(@PathVariable("productId") int productId, @PathVariable("userId") int userId) {
        try {
            PostWishReq postWishReq = new PostWishReq(productId, userId);
            wishService.createWish(postWishReq);

            String result = "관심 등록";
            return new BaseResponse<>(result);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 상품 관심 해제
     * [Delete] /app/wishes/:productId/users/:userId
     */
    @ResponseBody
    @DeleteMapping("{productId}/users/{userId}")
    public BaseResponse<String> deleteWish(@PathVariable("productId") int productId, @PathVariable("userId") int userId) {
        try {
            DeleteWishReq deleteWishReq = new DeleteWishReq(productId, userId);
            wishService.deleteWish(deleteWishReq);

            String result = "관심 해제";
            return new BaseResponse<>(result);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 유저의 관심상품 조회
     * [Get] /app/wishes/:userId
     */
    @ResponseBody
    @GetMapping("{userId}")
    public BaseResponse<List<GetWishProductsRes>> getWishProducts(@PathVariable("userId") int userId){
        try {
            List<GetWishProductsRes> getWishProductsRes = wishProvider.getWishProducts(userId);
            return new BaseResponse<>(getWishProductsRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
