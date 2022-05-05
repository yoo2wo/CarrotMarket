package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.product.model.GetProductRes;
import com.example.demo.src.product.model.PostProductReq;
import com.example.demo.src.product.model.PostProductRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
             System.out.println(postProductReq);
             PostProductRes postProductRes = productService.createProduct(postProductReq);
             return new BaseResponse<>(postProductRes);
         } catch (BaseException e){
             return new BaseResponse<>(e.getStatus());
         }
    }

    /**
     * 상품 조회 API
     * [Get] /app/products
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
}
