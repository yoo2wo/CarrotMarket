package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.product.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/products")
public class ProductController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ProductProvider productProvider;
    @Autowired
    private final ProductService productService;
    @Autowired
    private  final JwtService jwtService;

    public ProductController(ProductProvider productProvider, ProductService productService, JwtService jwtService) {
        this.productProvider = productProvider;
        this.productService = productService;
        this.jwtService = jwtService;
    }


    /**
     * 상품 등록 API
     * [Post] /app/products
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostProductRes> createProduct(@RequestBody PostProductReq postProductReq){
         try {
             //jwt 인가 부분 **************************************
             int userIdByJwt = jwtService.getUserId();
             if (postProductReq.getUserId() != userIdByJwt)
                    return new BaseResponse<>(INVALID_USER_JWT);
             //*************************************************

             System.out.println(postProductReq);
             PostProductRes postProductRes = productService.createProduct(postProductReq);
             return new BaseResponse<>(postProductRes);
         } catch (BaseException e){
             return new BaseResponse<>(e.getStatus());
         }
    }

    /**
     * 상품 조회 API
     * [Get] /app/products?nickname=
     * 닉네임
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetProductRes>> getProducts(@RequestParam(required = false) String nickname) {
        try {
            if (nickname == null){
                List<GetProductRes> getProductRes = productProvider.getProducts();
                return new BaseResponse<>(getProductRes);
            }
            List<GetProductRes> getProductRes = productProvider.getProductsByNickname(nickname);
            return new BaseResponse<>(getProductRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
    // paging 추가
    @ResponseBody
    @GetMapping("paging")
    public BaseResponse<List<GetProductRes>> getProductsPaging(@RequestParam(required = false, defaultValue =  "1") int pageNum){
        try {
            List<GetProductRes> getProductsPaging = productProvider.getProductsPaging(pageNum);
            return new BaseResponse<>(getProductsPaging);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


    /**
     * 상품 카테고리별 조회 API
     * [Get] /app/products/:categoryId
     */
    @ResponseBody
    @GetMapping("categories/{categoryId}")
    public BaseResponse<List<GetProductRes>> getProductsByCategoryId(@PathVariable long categoryId){
        try {
            List<GetProductRes> getProductRes = productProvider.getProductsByCategoryId(categoryId);
            return new BaseResponse<>(getProductRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 상품 상세 조회 API
     * [Get] /app/products/:productId
     */
    @ResponseBody
    @GetMapping("{productId}")
    public BaseResponse<GetProductDetailRes> getProduct(@PathVariable("productId") int productId){
        try {
            GetProductDetailRes getProductDetailRes = productProvider.getProduct(productId);
            return new BaseResponse<>(getProductDetailRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 상품 수정 API
     * [Patch] /app/products/:productId/:userId
     */
    @ResponseBody
    @PatchMapping("{productId}/{userId}")
    public BaseResponse<String> modifyProduct(@PathVariable("productId") int productId, @PathVariable("userId") int userId, @RequestBody Product product) {
        try {
            //jwt 인가 부분 **************************************
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            //*************************************************

            PatchProductReq patchProductReq = new PatchProductReq(
                    productId,
                    product.getImg1(),
                    product.getImg2(),
                    product.getImg3(),
                    product.getImg4(),
                    product.getImg5(),
                    product.getCategoryId(),
                    product.getPriceProposal(),
                    product.getPrice(),
                    product.getTitle(),
                    product.getMainText());

            productService.modifyProduct(patchProductReq);
            String result = "상품이 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 상품 삭제 API
     * (실제 삭제는 아닌 status만 변경한다.)
     * [Delete] /app/products/:productId/:userId
     */
    @ResponseBody
    @DeleteMapping("{productId}/{userId}")
    public BaseResponse<String> deleteProduct(@PathVariable("productId") int productId, @PathVariable("userId") int userId){
        try {
            //jwt 인가 부분 *************************************
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            //*************************************************
            DeleteProductReq deleteProductReq = new DeleteProductReq(productId);
            productService.deleteProduct(deleteProductReq);

            String result = "상품이 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
