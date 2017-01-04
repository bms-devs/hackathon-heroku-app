package com.github.britter.springbootherokudemo.websocket;

import com.github.britter.springbootherokudemo.repository.*;
import com.github.britter.springbootherokudemo.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class ScheduledRoomStatusTimeoutTask {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private WebSocketService webSocketService;

    @Scheduled(fixedRate = 60000)
    public void checkTimeout() {
        roomRepository.findAll().forEach(r -> {
            if (r.getOccupied() != null && DateTimeoutChecker.dateWebsocketTimeout(r.getLastUpdateDate())) {
                r.setOccupied(null);
                r.setLastUpdateDate(new Date());
                roomRepository.save(r);
                webSocketService.sendStatusUpdateNotificationToAllRegisteredClients();
            }
        });
    }
}
