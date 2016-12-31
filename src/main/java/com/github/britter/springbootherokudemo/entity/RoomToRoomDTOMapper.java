package com.github.britter.springbootherokudemo.entity;

import com.github.britter.springbootherokudemo.i18n.Messages;
import com.github.britter.springbootherokudemo.util.DateTimeoutChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RoomToRoomDTOMapper {

    @Autowired
    private Messages messages;

    public RoomDTO map(Room room){
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setOccupied(room.getOccupied());
        final Date lastUpdateDate = room.getLastUpdateDate();
        dto.setLastUpdateDate(lastUpdateDate == null ? null : lastUpdateDate.getTime());

        // change status to unknown on timeout
        if (DateTimeoutChecker.dateTimeout(lastUpdateDate)) {
            dto.setOccupied(null);
        }

        dto.setStatus(messages.get("status.occupation." + String.valueOf(dto.getOccupied())));

        return dto;
    }

}
