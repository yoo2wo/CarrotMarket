package com.example.demo.src.wish;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.wish.model.GetWishProductsRes;
import com.example.demo.utils.JwtService;
import io.jsonwebtoken.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class WishProvider {

    private final WishDao wishDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public WishProvider(WishDao wishDao, JwtService jwtService) {
        this.wishDao = wishDao;
        this.jwtService = jwtService;
    }

    //관심 상품 조회
    public List<GetWishProductsRes> getWishProducts(int userId) throws BaseException{
        try {
            List<GetWishProductsRes> getWishProductsRes = wishDao.getWishProduct(userId);
            return getWishProductsRes;
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
