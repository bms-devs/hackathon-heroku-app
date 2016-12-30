package com.github.britter.springbootherokudemo.websocket;

import com.github.britter.springbootherokudemo.entity.*;
import com.github.britter.springbootherokudemo.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.messaging.simp.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class WebSocketService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomToRoomDTOMapper mapper;

    @Autowired
    private SimpMessagingTemplate webSocket;

    public void sendWebsocketNotificationToAllRegisteredClients() {
        List<RoomDTO> allRooms = roomRepository.findAll()
                .stream().map(mapper::map)
                .collect(Collectors.toList());
        webSocket.convertAndSend("/statusUpdate/update", allRooms);
    }
}
