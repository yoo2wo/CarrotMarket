package com.example.demo.src.chat;

import com.example.demo.src.chat.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ChatDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 채팅방 생성
    public int createChatRoom(PostChatRoomReq postChatRoomReq){
        long sellerId = getUserIdByProductId(postChatRoomReq.getProductId()); //이렇게 해도되나?
        String createChatRoomQuery = "insert into chat_room (product_id ,user_id, user_id2) values (?,?,?)";
        Object[] createChatRoomParams = new Object[]{
                postChatRoomReq.getProductId(),
                postChatRoomReq.getUserId(),
                sellerId};
        return this.jdbcTemplate.update(createChatRoomQuery, createChatRoomParams);
    }

    // 채팅 보내기
    public int createChat(PostChatReq postChatReq){
        String createChatQuery = "insert into chat_message (chat_room_id, user_id, message) values (?,?,?)";
        Object[] createChatParams = new Object[] {
                postChatReq.getChatRoomId(),
                postChatReq.getUserId(),
                postChatReq.getMessage()};
        this.jdbcTemplate.update(createChatQuery, createChatParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    //채팅방 내용 조회
    public List<GetChatRes> getChat(long chatRoomId, long userId){
        String getChatQuery ="select r.chat_room_id, u.user_id, u.nickname, u.profile_img_url, u.manner_temp ,m.chat_message_id, m.message, DATE_FORMAT(m.created_at, '%p %H : %i') as msg_time " +
                "from chat_room as r " +
                "right outer join chat_message as m " +
                "on m.chat_room_id = r.chat_room_id " +
                "left outer join user as u " +
                "on u.user_id = m.user_id " +
                "where m.chat_room_id = ? " +
                "order by m.chat_message_id asc;";
        long getChatParams = chatRoomId;
        return this.jdbcTemplate.query(getChatQuery,
                (rs, rowNum) -> new GetChatRes(
                        rs.getLong("chat_room_id"),
                        rs.getLong("user_id"),
                        rs.getString("nickname"),
                        rs.getString("profile_img_url"),
                        rs.getFloat("manner_temp"),
                        rs.getString("message"),
                        rs.getString("msg_time")),
                getChatParams);
    }

    //채팅방 상품 조회
    public GetChatInfoRes getChatInfo(long chatRoomId) {
        String getChatInfoQuery = "select r.product_id, p.title, p.image_url_1, p.price, p.product_status " +
                "from chat_room as r " +
                "left outer join product as p " +
                "on r.product_id = p.product_id " +
                "where r.chat_room_id = ?";
        long getChatInfoParams = chatRoomId;
        return this.jdbcTemplate.queryForObject(getChatInfoQuery,
                (rs, rowNum) -> new GetChatInfoRes(
                        rs.getLong("product_id"),
                        rs.getString("title"),
                        rs.getString("image_url_1"),
                        rs.getLong("price"),
                        rs.getString("product_status")),
                getChatInfoParams);
    }

    //product Id로 userId 조회
    public long getUserIdByProductId(long productId){
        String getUserIdQuery = "select user_id from product where product_id = ?";
        long getUserIdParams = productId;
        return this.jdbcTemplate.queryForObject(getUserIdQuery, long.class,getUserIdParams);
    }

    //채팅방 목록 조회
    public List<GetChatRoomsRes> getChatRooms(long userId){
        // 상대 이름, 상대 사진, 상대 동네, (마지막 메시지 날짜), 제품 첫 사진
        String getChatRoomsQuery = "select r.chat_room_id, u1.user_id as u1_id, u1.nickname as u1_name, u1.profile_img_url as u1_img, a1.area_name as u1_area,u2.user_id as u2_id, u2.nickname as u2_name,  u2.profile_img_url as u2_img, a2.area_name as u2_area, p.image_url_1 as product_img, m.message, TIMESTAMPDIFF(HOUR, m.created_at, CURRENT_TIMESTAMP()) as hour_diff " +
                "from chat_room as r " +
                "inner join product as p " +
                "on p.product_id = r.product_id " +
                "inner join user as u1 " +
                "on u1.user_id = r.user_id " +
                "inner join user as u2 " +
                "on u2.user_id = r.user_id2 " +
                "inner join area as a1 " +
                "on u1.area_id = a1.area_id " +
                "inner join area as a2 " +
                "on u2.area_id = a2.area_id " +
                "inner join (select m.chat_room_id, max(chat_message_id) as last_msg_id " +
                "from chat_message as m " +
                "group by m.chat_room_id) as lm " +
                "on lm.chat_room_id = r.chat_room_id " +
                "inner join chat_message as m " +
                "on lm.chat_room_id = m.chat_room_id " +
                "where m.chat_message_id = lm.last_msg_id AND (u1.user_id = ? OR u2.user_id = ?)";
        Object[] getChatRoomsParmas = new Object[]{userId, userId};
        return this.jdbcTemplate.query(getChatRoomsQuery,
                (rs, rowNum) -> new GetChatRoomsRes(
                        rs.getLong("chat_room_id"),
                        rs.getLong("u1_id"),
                        rs.getString("u1_name"),
                        rs.getString("u1_img"),
                        rs.getString("u1_area"),
                        rs.getLong("u2_id"),
                        rs.getString("u2_name"),
                        rs.getString("u2_img"),
                        rs.getString("u2_area"),
                        rs.getString("product_img"),
                        rs.getString("message"),
                        rs.getLong("hour_diff")),
                getChatRoomsParmas);
    }


}
