package com.example.demo.src.wish;

import com.example.demo.src.wish.model.DeleteWishReq;
import com.example.demo.src.wish.model.GetWishProductsRes;
import com.example.demo.src.wish.model.PostWishReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class WishDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //관심 등록
    public int createWish(PostWishReq postWishReq) {
        String createWishQuery = "insert into wish (product_id, user_id) values (?,?)";
        Object[] createWishParams = new Object[]{postWishReq.getProductId(), postWishReq.getUserId()};
        return this.jdbcTemplate.update(createWishQuery, createWishParams);
    }

    //관심 해제
    public int deleteWish(DeleteWishReq deleteWishReq){
        String deleteWishQuery = "delete from wish where product_id = (?) and user_id = (?)";
        Object[] deleteWishParams = new Object[]{deleteWishReq.getProductId(), deleteWishReq.getUserId()};
        return this.jdbcTemplate.update(deleteWishQuery, deleteWishParams);
    }

    public List<GetWishProductsRes> getWishProduct(int userId) {
        //상품이미지, 제목, 동네, 가격, 관심수, 채팅수, 상품상태, 판매자 id, 상품 id
        String getWishProductQuery = "select w.user_id as app_user_id, p.product_id, p.user_id as seller_id ,p.title " +
                ", pc.product_category_name ,a.area_name ,p.product_status, p.price, wish.wish_count" +
                ",chat.chat_count,p.refresh_count, p.refreshed_at ,p.image_url_1, p.created_at " +
                "from product as p " +
                "inner join wish as w " +
                "on w.product_id = p.product_id " +
                "inner join area as a " +
                "on a.area_id = p.area_id " +
                "inner join product_category as pc " +
                "on pc.product_category_id = p.product_category_id " +
                "left outer join (select product_id, count(wish_id) as wish_count " +
                "from wish " +
                "group by product_id ) as wish " +
                "on wish.product_id = p.product_id " +
                "left outer join (select product_id, count(chat_room_id) as chat_count " +
                "from chat_room " +
                "group by product_id ) as chat " +
                "on chat.product_id=p.product_id " +
                "where w.user_id = ?";
        int getWishProductParams = userId;
        return this.jdbcTemplate.query(getWishProductQuery,
                (rs, rowNum) -> new GetWishProductsRes(
                        rs.getString("product_id"),
                        rs.getString("seller_id"),
                        rs.getString("image_url_1"),
                        rs.getString("title"),
                        rs.getString("area_name"),
                        rs.getLong("price"),
                        rs.getInt("chat_count"),
                        rs.getInt("wish_count"),
                        rs.getString("product_status")),
                getWishProductParams);
    }
}
