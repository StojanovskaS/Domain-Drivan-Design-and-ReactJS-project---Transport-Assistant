package com.demo.emt.contractpart.domain.listener;


import com.demo.emt.contractpart.domain.model.ChatMessagePojo;
import com.demo.emt.contractpart.domain.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Component
public class WebSocketEventListener {
    //socet konekcija,diskonekcija,broatcast
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        //user so se diskonektira
        if(username != null) {
            logger.info("User Disconnected : " + username);

            ChatMessagePojo chatMessagePojo = new ChatMessagePojo();
            chatMessagePojo.setType(MessageType.LEAVE);
            chatMessagePojo.setSender(username);

            messagingTemplate.convertAndSend("/topic/public", chatMessagePojo);
        }
    }
}

