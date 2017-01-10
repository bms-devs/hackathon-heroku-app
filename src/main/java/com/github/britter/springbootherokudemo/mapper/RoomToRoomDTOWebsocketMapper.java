package com.github.britter.springbootherokudemo.mapper;

import com.github.britter.springbootherokudemo.entity.db.Room;
import com.github.britter.springbootherokudemo.entity.dto.RoomDTO;
import com.github.britter.springbootherokudemo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "websocketMapper")
public class RoomToRoomDTOWebsocketMapper extends RoomToRoomDTOMapper {

    @Autowired
    private MessageService messageService;

    @Override
    protected void mapStatus(Room room, RoomDTO dto) {
        dto.setStatus(messageService.get("status.occupation." + String.valueOf(dto.getOccupied())));
    }
}
