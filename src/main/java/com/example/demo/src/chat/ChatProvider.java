package com.example.demo.src.chat;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.chat.model.GetChatInfoRes;
import com.example.demo.src.chat.model.GetChatReq;
import com.example.demo.src.chat.model.GetChatRes;
import com.example.demo.src.chat.model.GetChatRoomsRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ChatProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ChatDao chatDao;

    @Autowired
    public ChatProvider(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    //채팅 내용
    public List<GetChatRes> getChat(long chatRoomId, long userId) throws BaseException {
        try {
            //Todo : 채팅방이 해당 유저의 채팅방지 검증 필요
             List<GetChatRes> getChatRes = chatDao.getChat(chatRoomId, userId);
             return getChatRes;
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 채팅방 상품 정보
    public GetChatInfoRes getChatInfo(long chatRoomId) throws BaseException {
        try {
            GetChatInfoRes getChatInfoRes = chatDao.getChatInfo(chatRoomId);
            return getChatInfoRes;
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //채팅 목록 조회
    public List<GetChatRoomsRes> getChatRooms(long userId) throws BaseException {
        try {
            List<GetChatRoomsRes> getChatRoomsRes = chatDao.getChatRooms(userId);
            return getChatRoomsRes;
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
