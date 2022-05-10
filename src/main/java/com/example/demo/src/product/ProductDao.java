package com.example.demo.src.product;

import com.example.demo.src.product.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 상품 등록
    public int createProduct(PostProductReq postProductReq){
        String createProductQuery = "insert into product (user_id, area_id, product_category_id, title," +
                " main_text, price, price_proposal, image_url_1, image_url_2, image_url_3, image_url_4, image_url_5) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] createProductParams = new Object[]{
                postProductReq.getUserId(), postProductReq.getAreaId(), postProductReq.getCategoryId(),
                postProductReq.getTitle(), postProductReq.getMainText(), postProductReq.getPrice(), postProductReq.getPricePropsal(),
                postProductReq.getImg1(), postProductReq.getImg2(), postProductReq.getImg3(), postProductReq.getImg4(), postProductReq.getImg5()
        };
        this.jdbcTemplate.update(createProductQuery, createProductParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    //전체 상품 조회
    // Todo : 조회시 status를 확인하고 조회해야한다.
    public List<GetProductRes> getProducts() {
        // 사진1, 지역, 작성 시간, 가격, 제목, 동네, 채팅개수 좋아요 개수
        System.out.println("-------");
        String getProductsQuery = "select p.product_id, u.nickname, p.user_id as seller_id ,p.title, pc.product_category_name" +
                ",a.area_name ,p.product_status, p.price, wish.wish_count,chat.chat_count,p.refresh_count" +
                ", p.refreshed_at ,p.image_url_1, TIMESTAMPDIFF(SECOND, p.created_at, CURRENT_TIMESTAMP()) as sec_diff " +
                "from product as p " +
                "inner join user as u " +
                "on u.user_id = p.user_id " +
                "inner join area as a " +
                "on a.area_id = p.area_id " +
                "inner join product_category as pc " +
                "on pc.product_category_id = p.product_category_id " +
                "left outer join (select product_id, count(wish_id) as wish_count" +
                " from wish " +
                " group by product_id ) as wish " +
                "on wish.product_id = p.product_id " +
                "left outer join (select product_id, count(chat_room_id) as chat_count" +
                " from chat_room" +
                " group by product_id ) as chat " +
                "on chat.product_id=p.product_id";
        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductRes( //GetProductRes에 넣을때 순서 맞춰야한다.
                        rs.getLong("product_id"),
                        rs.getString("image_url_1"),
                        rs.getString("nickname"),
                        rs.getLong("sec_diff"),
                        rs.getLong("price"),
                        rs.getString("title"),
                        rs.getString("area_name"),
                        rs.getLong("wish_count"),
                        rs.getLong("chat_count"))
        );
    }

    //특정 유저가 올린 상품 목록 조회
    public List<GetProductRes> getProductsByNickname(String nickname) {
        // 사진1, 지역, 작성 시간, 가격, 제목, 동네, 채팅개수 좋아요 개수
        String getProductsByNicknameQuery = "select p.product_id, u.nickname, p.user_id as seller_id ,p.title, pc.product_category_name" +
                ",a.area_name ,p.product_status, p.price, wish.wish_count,chat.chat_count,p.refresh_count" +
                ", p.refreshed_at ,p.image_url_1, TIMESTAMPDIFF(SECOND, p.created_at, CURRENT_TIMESTAMP()) as sec_diff " +
                "from product as p " +
                "inner join user as u " +
                "on u.user_id = p.user_id " +
                "inner join area as a " +
                "on a.area_id = p.area_id " +
                "inner join product_category as pc " +
                "on pc.product_category_id = p.product_category_id " +
                "left outer join (select product_id, count(wish_id) as wish_count" +
                " from wish " +
                " group by product_id ) as wish " +
                "on wish.product_id = p.product_id " +
                "left outer join (select product_id, count(chat_room_id) as chat_count" +
                " from chat_room" +
                " group by product_id ) as chat " +
                "on chat.product_id=p.product_id " +
                "where u.nickname = ?";

        String getProductsByNicknameParams = nickname;
        return this.jdbcTemplate.query(getProductsByNicknameQuery,
                (rs, rowNum) -> new GetProductRes( //GetProductRes에 넣을때 순서 맞춰야한다.
                        rs.getLong("product_id"),
                        rs.getString("image_url_1"),
                        rs.getString("nickname"),
                        rs.getLong("sec_diff"),
                        rs.getLong("price"),
                        rs.getString("title"),
                        rs.getString("area_name"),
                        rs.getLong("wish_count"),
                        rs.getLong("chat_count")),
                getProductsByNicknameParams
        );
    }

    public List<GetProductRes> getProductsByCategoryId (long categoryId){
        String getProductsByCategoryIdQuery = "select p.product_id, u.nickname, p.user_id as seller_id ,p.title, pc.product_category_name" +
                ",a.area_name ,p.product_status, p.price, wish.wish_count,chat.chat_count,p.refresh_count" +
                ", p.refreshed_at ,p.image_url_1, TIMESTAMPDIFF(SECOND, p.created_at, CURRENT_TIMESTAMP()) as sec_diff " +
                "from product as p " +
                "inner join user as u " +
                "on u.user_id = p.user_id " +
                "inner join area as a " +
                "on a.area_id = p.area_id " +
                "inner join product_category as pc " +
                "on pc.product_category_id = p.product_category_id " +
                "left outer join (select product_id, count(wish_id) as wish_count" +
                " from wish " +
                " group by product_id ) as wish " +
                "on wish.product_id = p.product_id " +
                "left outer join (select product_id, count(chat_room_id) as chat_count" +
                " from chat_room" +
                " group by product_id ) as chat " +
                "on chat.product_id=p.product_id " +
                "where p.product_category_id = ?";
        long getProductsByCategoryIdParams = categoryId;
        return this.jdbcTemplate.query(getProductsByCategoryIdQuery,
                (rs, rowNum) -> new GetProductRes(
                        rs.getLong("product_id"),
                        rs.getString("image_url_1"),
                        rs.getString("nickname"),
                        rs.getLong("sec_diff"),
                        rs.getLong("price"),
                        rs.getString("title"),
                        rs.getString("area_name"),
                        rs.getLong("wish_count"),
                        rs.getLong("chat_count")),
                getProductsByCategoryIdParams);
    }


    // 상품 상세 조회
    public GetProductDetailRes getProduct(int productId) {
        //사진들, 유저명, 유저위치, 유저 온도, 유저 사진, title, 카테고리, 시간, 본문, 가격, 채팅수, 관심수, 조회수
        String getProductQuery = "select p.product_id , u.nickname, a.area_name, u.manner_temp, p.image_url_1, p.image_url_2, p.image_url_3, p.image_url_4, p.image_url_5 " +
                ", u.profile_img_url, pc.product_category_name,wish.wish_count, chat.chat_count " +
                ",p.title, p.main_text , p.price , p.price_proposal, p.product_status, p.refresh_count " +
                ", p.refreshed_at, p.view_count, TIMESTAMPDIFF(SECOND, p.created_at, CURRENT_TIMESTAMP()) as sec_diff " +
                "from user as u " +
                "inner join product as p " +
                "on u.user_id = p.user_id " +
                "inner join area as a  " +
                "on a.area_id  = p.area_id  " +
                "inner join product_category as pc " +
                "on pc.product_category_id  = p.product_category_id " +
                "left outer join (select product_id, count(wish_id) as wish_count " +
                "from wish " +
                "group by product_id ) as wish " +
                "on wish.product_id = p.product_id " +
                "left outer join (select product_id, count(chat_room_id) as chat_count " +
                "from chat_room " +
                "group by product_id ) as chat " +
                "on chat.product_id=p.product_id " +
                "where p.product_id =?";
        int getProductParams = productId;
        return this.jdbcTemplate.queryForObject(getProductQuery,
                (rs, rowNum) -> new GetProductDetailRes(
                        rs.getString("image_url_1"),
                        rs.getString("image_url_2"),
                        rs.getString("image_url_3"),
                        rs.getString("image_url_4"),
                        rs.getString("image_url_5"),
                        rs.getString("nickname"),
                        rs.getInt("manner_temp"),
                        rs.getString("profile_img_url"),
                        rs.getLong("sec_diff"),
                        rs.getLong("price"),
                        rs.getString("title"),
                        rs.getString("main_text"),
                        rs.getString("area_name"),
                        rs.getLong("wish_count"),
                        rs.getLong("chat_count"),
                        rs.getLong("view_count")),
                getProductParams);
    }

    //상품 수정
    //Todo : product_status도 반영해야한다.
    public int modifyProduct(PatchProductReq patchProductReq) {

        String modifyProductQuery = "update product set image_url_1 = ?," +
                "image_url_2 = ?, image_url_3 = ?, image_url_4 = ?, image_url_5 = ?, " +
                "product_category_id = ?, price_proposal = ?, price = ?, title = ?, main_text = ? " +
                "where product_id = ?";
        Object[] modifyProductParams = new Object[]{
                patchProductReq.getImg1(),
                patchProductReq.getImg2(),
                patchProductReq.getImg3(),
                patchProductReq.getImg4(),
                patchProductReq.getImg5(),
                patchProductReq.getCategoryId(),
                patchProductReq.getPriceProposal(),
                patchProductReq.getPrice(),
                patchProductReq.getTitle(),
                patchProductReq.getMainText(),
                patchProductReq.getProductId()};
        return this.jdbcTemplate.update(modifyProductQuery, modifyProductParams);
    }

    // 상품 삭제
    public int deleteProduct(DeleteProductReq deleteProductReq) {
        String deleteProductQuery = "update product set status = ? where product_id = ?";
        Object[] deleteProductParams = new Object[]{"D", deleteProductReq.getProductId()};

        return this.jdbcTemplate.update(deleteProductQuery, deleteProductParams);
    }
}
