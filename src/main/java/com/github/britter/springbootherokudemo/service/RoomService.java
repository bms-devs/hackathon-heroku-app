package com.github.britter.springbootherokudemo.service;

import com.github.britter.springbootherokudemo.entity.db.*;
import com.github.britter.springbootherokudemo.repository.*;
import com.github.britter.springbootherokudemo.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public void updateRoomOccupied(Room room, String occupied){
        final Boolean lastOccupied = room.getOccupied();
        final Date lastUpdateDate = room.getLastUpdateDate();
        final Boolean currentOccupied = Boolean.valueOf(occupied);

        if (!currentOccupied.equals(lastOccupied) || DateTimeoutChecker.dateTimeout(lastUpdateDate)) {
            room.setLastOccupiedUpdateDate(new Date());
        }
        room.setOccupied(currentOccupied);
        room.setLastUpdateDate(new Date());

        roomRepository.save(room);
    }
}
