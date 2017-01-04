package com.github.britter.springbootherokudemo.endpoint;

import com.github.britter.springbootherokudemo.entity.db.*;
import com.github.britter.springbootherokudemo.entity.dto.*;
import com.github.britter.springbootherokudemo.mapper.*;
import com.github.britter.springbootherokudemo.repository.*;
import com.github.britter.springbootherokudemo.util.*;
import com.github.britter.springbootherokudemo.websocket.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.*;

@RestController
@RequestMapping("/occupied")
public class RoomRestController {

    private final RoomRepository roomRepository;
    private final RoomToRoomDTORestMapper mapper;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    public RoomRestController(RoomRepository roomRepository, RoomToRoomDTORestMapper mapper) {
        this.roomRepository = roomRepository;
        this.mapper = mapper;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json"})
    ResponseEntity<List<RoomDTO>> getAllRoomsAvailibility() {
        List<RoomDTO> allRooms = roomRepository.findAll()
                .stream().map(mapper::map)
                .collect(Collectors.toList());

        return ResponseEntity.ok(allRooms);
    }

    @RequestMapping(value = "/{roomId}", method = RequestMethod.GET, produces = {"application/json"})
    ResponseEntity<RoomDTO> getSpecificRoomAvailibility(@PathVariable Long roomId) {
        Room room = roomRepository.getOne(roomId);
        if (room == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(mapper.map(room));
    }

    @RequestMapping(value = "/{roomId}", method = { RequestMethod.POST, RequestMethod.PUT }, consumes = {"text/plain"})
    ResponseEntity<?> updateRoomAvailability(@PathVariable Long roomId, @RequestBody String occupied) {
        Room room = roomRepository.getOne(roomId);

        if (room == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (!occupied.equals("true") && !occupied.equals("false")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        updateRoom(room, occupied);
        webSocketService.sendStatusUpdateNotificationToAllRegisteredClients();

        return ResponseEntity.ok(null);
    }

    private void updateRoom(Room room, String occupied){
        final Boolean currentOccupied = Boolean.valueOf(occupied);
        final Boolean lastOccupied = room.getOccupied();
        final Date lastUpdateDate = room.getLastUpdateDate();

        if (!currentOccupied.equals(lastOccupied) || DateTimeoutChecker.dateRestTimeout(lastUpdateDate)) {
            room.setLastOccupiedUpdateDate(new Date());
        }
        room.setOccupied(currentOccupied);
        room.setLastUpdateDate(new Date());

        roomRepository.save(room);
    }
}
