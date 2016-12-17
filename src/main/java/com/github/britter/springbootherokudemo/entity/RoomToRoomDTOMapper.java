package com.github.britter.springbootherokudemo.entity;

import java.util.Date;

public class RoomToRoomDTOMapper {

    final static int TIMEOUT = 70000;

    public RoomDTO map(Room room){
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setOccupied(room.getOccupied());
        dto.setLastUpdateDate(room.getLastUpdateDate());

        // change status to unknown on timeout
        if (room.getLastUpdateDate() != null && new Date().getTime() - room.getLastUpdateDate().getTime() > TIMEOUT) {
            dto.setOccupied(null);
        }

        return dto;
    }
}
