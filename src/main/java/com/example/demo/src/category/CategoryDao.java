package com.example.demo.src.category;

import com.example.demo.src.category.Model.GetCategoryRes;
import com.example.demo.src.category.Model.PatchCategoryReq;
import com.example.demo.src.category.Model.PostCategoryReq;
import com.example.demo.src.product.model.DeleteProductReq;
import com.example.demo.src.product.model.GetProductRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CategoryDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


//    //카테고리별 상품 조회
//    public List<GetProductRes> getProductsByCategory(int categoryId){
//        String getProductsByCategoryQuery = "";
//        int getProductsByCategoryParams = categoryId;
//
//        return this.jdbcTemplate.query(getProductsByCategoryQuery,
//                (rs, rowNum) -> new GetProductRes( //GetProductRes에 넣을때 순서 맞춰야한다.
//                        rs.getLong("product_id"),
//                        rs.getString("image_url_1"),
//                        rs.getString("nickname"),
//                        rs.getLong("sec_diff"),
//                        rs.getLong("price"),
//                        rs.getString("title"),
//                        rs.getString("area_name"),
//                        rs.getLong("wish_count"),
//                        rs.getLong("chat_count")),
//                getProductsByCategoryParams);
//    }

    //카테고리 조회
    public List<GetCategoryRes> getCategories(){
        String getCategories = "select * from product_category";
        return this.jdbcTemplate.query(getCategories,
                (rs, rowNum)-> new GetCategoryRes(
                        rs.getInt("product_category_id"),
                        rs.getString("product_category_name")
                ));
    }

    //카테고리 생성
    public long createCategory(PostCategoryReq postCategoryReq){
        String createCategoryQuery = "insert into product_category (product_category_name) values (?)";
        String createCategoryParams = postCategoryReq.getCategoryName();
        this.jdbcTemplate.update(createCategoryQuery, createCategoryParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class);
    }

    //카테고리 수정
    public int modifyCategory(PatchCategoryReq patchCategoryReq){
        String modifyCategoryQuery = "update product_category set product_category_name = ? where product_category_id = ?";
        Object[] modifyCategoryParams = new Object[]{
                patchCategoryReq.getCategoryName(),
                patchCategoryReq.getCategoryId()};
        return this.jdbcTemplate.update(modifyCategoryQuery, modifyCategoryParams);
    }

    //카테고리 삭제
    public int deleteCategory(long categoryId){
        String deleteCategoryQuery = "delete from product_category where product_category_id = (?)";
        long deleteCategoryParams = categoryId;
        return this.jdbcTemplate.update(deleteCategoryQuery,deleteCategoryParams);
    }
}