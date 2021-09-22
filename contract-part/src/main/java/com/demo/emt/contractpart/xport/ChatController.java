package com.demo.emt.contractpart.xport;

import com.demo.emt.contractpart.domain.model.ChatMessagePojo;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
    @GetMapping("/chat")
    public String getChatPage(){
        return "catpage";
    }

    //app/chat.sendMessag-potocno
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessagePojo sendMessage(@Payload ChatMessagePojo chatMessagePojo) {
        return chatMessagePojo;
    }

    ///broatcast na user
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessagePojo addUser(@Payload ChatMessagePojo chatMessagePojo,
                                   SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessagePojo.getSender());
        return chatMessagePojo;
    }

}