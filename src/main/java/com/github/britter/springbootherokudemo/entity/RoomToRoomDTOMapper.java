package com.github.britter.springbootherokudemo.entity;

import com.github.britter.springbootherokudemo.model.Duration;
import com.github.britter.springbootherokudemo.util.DateTimeoutChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class RoomToRoomDTOMapper {

    @Autowired
    private MessageSource messageSource;

    public RoomDTO map(Room room){
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setOccupied(room.getOccupied());
        final Date lastUpdateDate = room.getLastUpdateDate();
        dto.setLastUpdateDate(lastUpdateDate);

        // change status to unknown on timeout
        if (DateTimeoutChecker.dateTimeout(lastUpdateDate)) {
            dto.setOccupied(null);
        }

        Duration duration = calculateLastOccupiedUpdateDuration(room.getLastOccupiedUpdateDate());
        dto.setStatus(messageSource.getMessage("status.occupation." + String.valueOf(dto.getOccupied()),
                new Object[]{duration.getHours(), duration.getMinutes(), duration.getSeconds()}, Locale.getDefault()));

        return dto;
    }

    private Duration calculateLastOccupiedUpdateDuration(Date lastOccupiedUpdateDate) {
        long diff = new Date().getTime() - Optional.ofNullable(lastOccupiedUpdateDate).orElse(new Date()).getTime();
        long hours = TimeUnit.MILLISECONDS.toHours(diff);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        return new Duration(hours, minutes, seconds);
    }
}
