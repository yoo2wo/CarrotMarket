package com.example.demo.src.product;

import com.example.demo.src.product.model.GetProductRes;
import com.example.demo.src.product.model.PostProductReq;
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

    public List<GetProductRes> getProducts() {
        // 사진1, 지역, 작성 시간, 가격, 제목, 동네, 채팅개수 좋아요 개수
        System.out.println("-------");
        String getProductsQuery = "select p.product_id, p.user_id as seller_id ,p.title, pc.product_category_name" +
                ",a.area_name ,p.product_status, p.price, wish.wish_count, chat_room.chat_count, p.refresh_count" +
                ", p.refreshed_at ,p.image_url_1, TIMESTAMPDIFF(SECOND, p.created_at, CURRENT_TIMESTAMP()) as sec_diff " +
                "from product as p " +
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
                        rs.getLong("sec_diff"),
                        rs.getLong("price"),
                        rs.getString("title"),
                        rs.getString("area_name"),
                        rs.getLong("wish_count"),
                        rs.getLong("chat_count")
                )
        );
    }
    //임시
    public List<GetProductRes> getProductsByNickname(String nickname) {
        // 사진1, 지역, 작성 시간, 가격, 제목, 동네, 채팅개수 좋아요 개수
        String getProductsQuery = "select p.product_id, p.user_id as seller_id ,p.title, pc.product_category_name" +
                ",a.area_name ,p.product_status, p.price, wish.wish_count,chat.chat_count,p.refresh_count" +
                ", p.refreshed_at ,p.image_url_1, TIMESTAMPDIFF(SECOND, p.created_at, CURRENT_TIMESTAMP()) as sec_diff " +
                "from product as p " +
                "inner join area as a " +
                "on a.area_id = p.area_id " +
                "inner join product_category as pc " +
                "on pc.product_category_id = p.product_category_id " +
                "left outer join (select product_id, count(wish_id) as wish_count" +
                " from wish " +
                " group by product_id ) as wish" +
                "on wish.product_id = p.product_id " +
                "left outer join (select product_id, count(chat_room_id) as chat_count" +
                " from chat_room" +
                " group by product_id ) as chat" +
                "on chat.product_id=p.product_id";
        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductRes( //GetProductRes에 넣을때 순서 맞춰야한다.
                        rs.getLong("product_id"),
                        rs.getString("image_url_1"),
                        rs.getLong("sec_diff"),
                        rs.getLong("price"),
                        rs.getString("title"),
                        rs.getString("area_name"),
                        rs.getLong("wish_count"),
                        rs.getLong("chat_count"))
        );
    }
}
