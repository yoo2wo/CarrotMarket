package com.example.demo.src.chat;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.chat.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app")
public class ChatController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ChatProvider chatProvider;
    @Autowired
    private final ChatService chatService;
    @Autowired
    private final JwtService jwtService;

    public ChatController(ChatProvider chatProvider, ChatService chatService, JwtService jwtService) {
        this.chatProvider = chatProvider;
        this.chatService = chatService;
        this.jwtService = jwtService;
    }

    /**
     * 채팅 보내기 API
     * [Post] /app/chat
     */
    @ResponseBody
    @PostMapping("chat")
    public BaseResponse<String> createChat(@RequestBody PostChatReq postChatReq){
        try {
            //jwt 인가 부분 **************************************
            int userIdByJwt = jwtService.getUserId();
            if (postChatReq.getUserId() != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            //*************************************************

           chatService.createChat(postChatReq);
           String result = "메시지가 전송되었습니다.";
           return new BaseResponse<>(result);

        } catch (BaseException e){

            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 채팅방 생성 API
     * [Post] /app/chatrooms
     */
    @ResponseBody
    @PostMapping("chatrooms")
    public BaseResponse<PostChatRoomRes> createChatRoom(@RequestBody PostChatRoomReq postChatRoomReq){
        try {
            //jwt 인가 부분 **************************************
            int userIdByJwt = jwtService.getUserId();
            if (postChatRoomReq.getUserId() != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            //*************************************************

            PostChatRoomRes postChatRoomRes = chatService.createChatRoom(postChatRoomReq);
            return new BaseResponse<>(postChatRoomRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 채팅방 내용 조회 API
     * [Get] /app/chat/:charRoomId/:userId
     */
    @ResponseBody
    @GetMapping("chat/{chatRoomId}/{userId}")
    public BaseResponse<List<GetChatRes>> getChat(@PathVariable long chatRoomId, @PathVariable long userId){
        try {
            // 상대 프로필 이미지, 메시지, 상대 보낸 시간, 내 메시지, 내 보낸 시간
            // 상대 이름, 상대 아이디, 상대 온도,상대 상품, 상품 제목, 상품 사진, 상품 가격, 상품 상태,
            //jwt 인가 부분 **************************************
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            //*************************************************
            List<GetChatRes> getChatRes = chatProvider.getChat(chatRoomId, userId);
            return new BaseResponse<>(getChatRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 채팅방 판매자, 상품 정보 조회 API
     * [GET] /app/chat/:chatRoomId/:userId/product
     */
    @ResponseBody
    @GetMapping("chat/{chatRoomId}/{userId}/product")
    public BaseResponse<GetChatInfoRes> getChatInfo(@PathVariable long chatRoomId, @PathVariable long userId) {
        //상품 제목, 상품 사진, 상품 가격, 상품 상태
        try {
            //jwt 인가 부분 **************************************
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            //*************************************************
            GetChatInfoRes getChatInfoRes = chatProvider.getChatInfo(chatRoomId);
            return new BaseResponse<>(getChatInfoRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 채팅방 목록 조회 API
     * [Get] /app/chatrooms/:userId
     */
    @ResponseBody
    @GetMapping("chatrooms/{userId}")
    public BaseResponse<List<GetChatRoomsRes>> getChatRooms(@PathVariable long userId){
        try {
            //jwt 인가 부분 **************************************
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            //*************************************************
            List<GetChatRoomsRes> getChatRoomsRes = chatProvider.getChatRooms(userId);
            return new BaseResponse<>(getChatRoomsRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
