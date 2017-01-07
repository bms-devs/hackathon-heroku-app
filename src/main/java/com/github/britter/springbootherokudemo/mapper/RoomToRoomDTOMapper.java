package com.github.britter.springbootherokudemo.mapper;

import com.github.britter.springbootherokudemo.entity.db.*;
import com.github.britter.springbootherokudemo.entity.dto.*;

import java.util.*;

public abstract class RoomToRoomDTOMapper {

    public RoomDTO map(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setOccupied(room.getOccupied());
        final Date lastUpdateDate = room.getLastUpdateDate();
        dto.setLastUpdateDate(lastUpdateDate == null ? null : lastUpdateDate.getTime());

        mapStatus(room, dto, lastUpdateDate);

        return dto;
    }

    protected abstract void mapStatus(Room room, RoomDTO dto, Date lastUpdateDate);
}
