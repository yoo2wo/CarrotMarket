package com.example.demo.src.category;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.category.Model.PatchCategoryReq;
import com.example.demo.src.category.Model.PostCategoryReq;
import com.example.demo.src.category.Model.PostCategoryRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class CategoryService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CategoryDao categoryDao;
    private final CategoryProvider categoryProvider;
    private final JwtService jwtService;

    @Autowired
    public CategoryService(CategoryDao categoryDao, CategoryProvider categoryProvider, JwtService jwtService) {
        this.categoryDao = categoryDao;
        this.categoryProvider = categoryProvider;
        this.jwtService = jwtService;
    }

    //카테고리 생성
    public PostCategoryRes createCategory(PostCategoryReq postCategoryReq) throws BaseException {
        try {
            //Todo : validation 해야됨
            long categoryId = categoryDao.createCategory(postCategoryReq);
            return new PostCategoryRes(categoryId);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //카테고리 수정
    public void modifyCategory(PatchCategoryReq patchCategoryReq) throws BaseException {
        try {
            int result = categoryDao.modifyCategory(patchCategoryReq);
            if (result == 0)
                throw new BaseException(MODIFY_FAIL_CATEGORY);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //카테고리 삭제
    public void deleteCategory(long categoryId) throws BaseException {
        try {
            int result = categoryDao.deleteCategory(categoryId);
            if (result == 0)
                throw new BaseException(DELETE_FAIL_CATEGORY);
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
