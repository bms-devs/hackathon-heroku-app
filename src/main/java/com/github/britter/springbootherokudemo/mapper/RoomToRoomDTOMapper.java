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
        dto.setLastUpdateDate(room.getLastUpdateDate() == null ? null : room.getLastUpdateDate().getTime());
        final Date lastOccupiedStatusChangeDate = room.getLastOccupiedStatusChangeDate();
        dto.setLastOccupiedStatusChangeDate(lastOccupiedStatusChangeDate == null ? null : lastOccupiedStatusChangeDate.getTime());

        mapStatus(room, dto);

        return dto;
    }

    protected abstract void mapStatus(Room room, RoomDTO dto);
}
