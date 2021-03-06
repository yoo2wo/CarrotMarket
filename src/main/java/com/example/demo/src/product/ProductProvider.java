package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.product.model.GetProductDetailRes;
import com.example.demo.src.product.model.GetProductRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional
public class ProductProvider {

    private final ProductDao productDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProductProvider(ProductDao productDao, JwtService jwtService) {
        this.productDao = productDao;
        this.jwtService = jwtService;
    }

    public List<GetProductRes> getProducts() throws BaseException{
        try {
            List<GetProductRes> getProductRes = productDao.getProducts();
            return getProductRes;
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductRes> getProductsByNickname(String nickname) throws BaseException{
        try {
            //Todo : 의미적 validation : 없는 nickname에 대해서 check 쿼리를 만드는게 좋을까? 괜히 네트워크만 쓰는 것같음
            List<GetProductRes> getProductRes = productDao.getProductsByNickname(nickname);
            return getProductRes;
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //페이징
    public List<GetProductRes> getProductsPaging(int pageNum) throws BaseException{
        try {
            int size = 5; /** 페이지당 데이터 개수 */
            List<GetProductRes> getProductRes = productDao.getProductsPaging(pageNum, size);
            return getProductRes;
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductRes> getProductsByCategoryId(long categoryId) throws BaseException{
        try {
            //Todo : 의미적 validation 없는 nickname에 대해서 check 쿼리를 만드는게 좋을까? 괜히 네트워크만 쓰는 것같음
            List<GetProductRes> getProductRes = productDao.getProductsByCategoryId(categoryId);
            return getProductRes;
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetProductDetailRes getProduct(int productId) throws BaseException{
        try {
            GetProductDetailRes getProductDetailRes = productDao.getProduct(productId);
            return getProductDetailRes;
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
