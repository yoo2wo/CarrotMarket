package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.product.model.DeleteProductReq;
import com.example.demo.src.product.model.PatchProductReq;
import com.example.demo.src.product.model.PostProductReq;
import com.example.demo.src.product.model.PostProductRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class ProductService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductDao productDao;
    private final ProductProvider productProvider;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    @Autowired
    public ProductService(ProductDao productDao, ProductProvider productProvider, JwtService jwtService) {
        this.productDao = productDao;
        this.productProvider = productProvider;
        this.jwtService = jwtService;
    }

    //상품 생성
    public PostProductRes createProduct(PostProductReq postProductReq) throws BaseException {
        try {
            // Todo: validation 해야함
            int productId = productDao.createProduct(postProductReq);
            return new PostProductRes(productId);
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //상품 수정
    public void modifyProduct(PatchProductReq patchProductReq) throws BaseException {
        try {
            int result = productDao.modifyProduct(patchProductReq);
            if (result == 0){
                throw new BaseException(MODIFY_FAIL_PRODUCT);
            }
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //상품 삭제
    public void deleteProduct(DeleteProductReq deleteProductReq) throws BaseException{
        try {
            int result = productDao.deleteProduct(deleteProductReq);
            if (result == 0)
                throw new BaseException(DELETE_FAIL_PRODUCT);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
