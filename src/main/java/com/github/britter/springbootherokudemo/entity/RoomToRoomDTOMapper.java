package com.github.britter.springbootherokudemo.entity;

import com.github.britter.springbootherokudemo.util.DateTimeoutChecker;

import java.util.Date;

public class RoomToRoomDTOMapper {

    public RoomDTO map(Room room){
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setOccupied(room.getOccupied());
        final Date lastUpdateDate = room.getLastUpdateDate();
        dto.setLastUpdateDate(lastUpdateDate);
        dto.setLastOccupiedUpdateDate(room.getLastOccupiedUpdateDate());

        // change status to unknown on timeout
        if (DateTimeoutChecker.dateTimeout(lastUpdateDate)) {
            dto.setOccupied(null);
        }

        return dto;
    }
}
