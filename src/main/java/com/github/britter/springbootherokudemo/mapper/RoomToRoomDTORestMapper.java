package com.github.britter.springbootherokudemo.mapper;

import com.github.britter.springbootherokudemo.entity.db.*;
import com.github.britter.springbootherokudemo.entity.dto.*;
import com.github.britter.springbootherokudemo.i18n.Messages;
import com.github.britter.springbootherokudemo.model.*;
import com.github.britter.springbootherokudemo.util.DateTimeoutChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;

@Component
public class RoomToRoomDTORestMapper implements RoomToRoomDTOMapper {

    @Autowired
    private Messages massages;

    public RoomDTO map(Room room){
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setOccupied(room.getOccupied());
        final Date lastUpdateDate = room.getLastUpdateDate();
        dto.setLastUpdateDate(lastUpdateDate == null ? null : lastUpdateDate.getTime());

        // change status to unknown on timeout
        if (DateTimeoutChecker.dateRestTimeout(lastUpdateDate)) {
            dto.setOccupied(null);
        }

        Duration duration = calculateLastOccupiedUpdateDuration(room.getLastOccupiedUpdateDate());
        dto.setStatus(massages.get("status.occupation." + String.valueOf(dto.getOccupied()),
                new Object[]{duration.getHours(), duration.getMinutes(), duration.getSeconds()}));

        return dto;
    }

    private Duration calculateLastOccupiedUpdateDuration(Date lastOccupiedUpdateDate) {
        long diff = new Date().getTime() - Optional.ofNullable(lastOccupiedUpdateDate).orElse(new Date()).getTime();
        long hours = TimeUnit.MILLISECONDS.toHours(diff);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff) - (TimeUnit.MILLISECONDS.toHours(diff) * 60);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff) - (TimeUnit.MILLISECONDS.toMinutes(diff) * 60);
        return new Duration(hours, minutes, seconds);
    }
}
