package com.github.britter.springbootherokudemo.mapper;

import com.github.britter.springbootherokudemo.entity.db.*;
import com.github.britter.springbootherokudemo.entity.dto.*;
import com.github.britter.springbootherokudemo.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component(value = "websocketMapper")
public class RoomToRoomDTOWebsocketMapper extends RoomToRoomDTOMapper {

    @Autowired
    private MessageService messageService;

    @Override
    protected void mapStatus(Room room, RoomDTO dto, Date lastUpdateDate) {
        dto.setStatus(messageService.get("status.occupation." + String.valueOf(dto.getOccupied())));
    }
}
