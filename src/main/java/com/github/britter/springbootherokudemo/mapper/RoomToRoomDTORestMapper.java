package com.github.britter.springbootherokudemo.mapper;

import com.github.britter.springbootherokudemo.entity.db.*;
import com.github.britter.springbootherokudemo.entity.dto.*;
import com.github.britter.springbootherokudemo.model.*;
import com.github.britter.springbootherokudemo.service.*;
import com.github.britter.springbootherokudemo.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.concurrent.*;

@Component(value = "restMapper")
public class RoomToRoomDTORestMapper extends RoomToRoomDTOMapper {

    @Autowired
    private MessageService massages;

    @Override
    protected void mapStatus(Room room, RoomDTO dto) {
        // change status to unknown on timeout
        if (DateTimeoutChecker.dateTimeout(room.getLastUpdateDate())) {
            dto.setOccupied(null);
        }

        Duration duration = calculateDurationSinceLastOccupiedStatusChange(room.getLastOccupiedStatusChangeDate());
        dto.setStatus(massages.get("status.occupation.withTime." + String.valueOf(dto.getOccupied()),
                new Object[]{duration.getHours(), duration.getMinutes(), duration.getSeconds()}));
    }

    private Duration calculateDurationSinceLastOccupiedStatusChange(Date lastOccupiedStatusChangeDate) {
        long diff = new Date().getTime() - Optional.ofNullable(lastOccupiedStatusChangeDate).orElse(new Date()).getTime();
        long hours = TimeUnit.MILLISECONDS.toHours(diff);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff) - (TimeUnit.MILLISECONDS.toHours(diff) * 60);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff) - (TimeUnit.MILLISECONDS.toMinutes(diff) * 60);
        return new Duration(hours, minutes, seconds);
    }
}
