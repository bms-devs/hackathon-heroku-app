package com.github.britter.springbootherokudemo.websocket;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.stereotype.*;
import org.springframework.web.socket.messaging.*;

@Component
public class WebsocketSubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public void onApplicationEvent(SessionSubscribeEvent sessionSubscribeEvent) {
        webSocketService.sendStatusUpdateNotificationToAllRegisteredClients();
    }
}