package com.example.demo.src.wish;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.wish.model.DeleteWishReq;
import com.example.demo.src.wish.model.PostWishReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class WishService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WishDao wishDao;
    private final WishProvider wishProvider;
    private final JwtService jwtService;

    @Autowired
    public WishService(WishDao wishDao, WishProvider wishProvider, JwtService jwtService) {
        this.wishDao = wishDao;
        this.wishProvider = wishProvider;
        this.jwtService = jwtService;
    }

    //관심 등록
    public void createWish(PostWishReq postWishReq) throws BaseException {
        try {
            int result = wishDao.createWish(postWishReq);
            if (result == 0){
                throw new BaseException(CREATE_FAIL_WISH);
            }
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //관심 해제
    public void deleteWish(DeleteWishReq deleteWishReq) throws BaseException {
        try {
            int result = wishDao.deleteWish(deleteWishReq);
            if (result == 0){
                throw new BaseException(DELETE_FAIL_WISH);
            }
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
