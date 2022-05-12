package com.example.demo.src.chat;

import com.example.demo.config.BaseException;
import com.example.demo.src.chat.model.PostChatReq;
import com.example.demo.src.chat.model.PostChatRoomReq;
import com.example.demo.src.chat.model.PostChatRoomRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.CREATE_FAIL_MESSAGE;
import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ChatService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ChatDao chatDao;
    private final ChatProvider chatProvider;

    @Autowired
    public ChatService(ChatDao chatDao, ChatProvider chatProvider) {
        this.chatDao = chatDao;
        this.chatProvider = chatProvider;
    }

    //채팅 보내기
    public void createChat(PostChatReq postChatReq) throws BaseException {
        try {
            int result = chatDao.createChat(postChatReq);
            if (result == 0){
                throw new BaseException(CREATE_FAIL_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //채팅방 생성
    public PostChatRoomRes createChatRoom(PostChatRoomReq postChatRoomReq) throws BaseException {
        try {
            // Todo : 같은 채팅방 못만들게 해야한다.

            int chatRoomId = chatDao.createChatRoom(postChatRoomReq);
            return new PostChatRoomRes(chatRoomId);
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
