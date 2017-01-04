package com.github.britter.springbootherokudemo.mapper;

import com.github.britter.springbootherokudemo.entity.db.*;
import com.github.britter.springbootherokudemo.entity.dto.*;
import com.github.britter.springbootherokudemo.i18n.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class RoomToRoomDTOWebsocketMapper implements RoomToRoomDTOMapper {

    @Autowired
    private Messages messages;

    public RoomDTO map(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setOccupied(room.getOccupied());
        final Date lastUpdateDate = room.getLastUpdateDate();
        dto.setLastUpdateDate(lastUpdateDate == null ? null : lastUpdateDate.getTime());
        dto.setStatus(messages.get("status.occupation." + String.valueOf(dto.getOccupied())));

        return dto;
    }
}
