package com.github.britter.springbootherokudemo.entity;

import java.util.Date;

public class RoomToRoomDTOMapper {

    final static int TIMEOUT = 70000;

    public RoomDTO map(Room room){
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setOccupied(room.getOccupied());
        final Date lastUpdateDate = room.getLastUpdateDate();
        dto.setLastUpdateDate(lastUpdateDate);

        // change status to unknown on timeout
        if (lastUpdateDate == null || new Date().getTime() - lastUpdateDate.getTime() > TIMEOUT) {
            dto.setOccupied(null);
        }

        return dto;
    }
}
