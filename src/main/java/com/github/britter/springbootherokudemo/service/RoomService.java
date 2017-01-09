package com.github.britter.springbootherokudemo.service;

import com.github.britter.springbootherokudemo.entity.db.*;
import com.github.britter.springbootherokudemo.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public void updateRoomOccupied(Room room, Boolean newOccupiedStatus) {
        room.setOccupied(newOccupiedStatus);
        room.setLastOccupiedStatusChangeDate(new Date());
        room.setLastUpdateDate(new Date());

        roomRepository.save(room);
    }

    public void updateOnlyLastUpdateDate(Room room) {
        room.setLastUpdateDate(new Date());
        roomRepository.save(room);
    }

    public boolean hasRoomOccupiedStatusChanged(Room room, Boolean newOccupiedStatus) {
        return !newOccupiedStatus.equals(room.getOccupied());
    }
}
