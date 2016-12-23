package com.github.britter.springbootherokudemo.endpoint;

import com.github.britter.springbootherokudemo.endpoint.dto.RoomDTO;
import com.github.britter.springbootherokudemo.endpoint.dto.RoomToRoomDTOMapper;
import com.github.britter.springbootherokudemo.entity.Room;
import com.github.britter.springbootherokudemo.entity.enumeration.RoomStatus;
import com.github.britter.springbootherokudemo.repository.*;
import com.github.britter.springbootherokudemo.util.DateTimeoutChecker;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.github.britter.springbootherokudemo.util.BidirectionalRoomStatusAndBooleanConverter.castBooleanToRoomStatus;
import static com.github.britter.springbootherokudemo.util.BidirectionalRoomStatusAndBooleanConverter.castRoomStatusToBoolean;

@RestController
@RequestMapping("/occupied")
public class RoomRestController {

    private final RoomRepository roomRepository;
    private final RoomToRoomDTOMapper mapper;

    @Autowired
    public RoomRestController(RoomRepository roomRepository, RoomToRoomDTOMapper mapper) {
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

        final Boolean currentOccupied = Boolean.valueOf(occupied);
        final Boolean lastOccupied = castRoomStatusToBoolean(room.getOccupied());
        final Date lastUpdateDate = room.getLastUpdateDate();

        if (!currentOccupied.equals(lastOccupied) || DateTimeoutChecker.dateTimeout(lastUpdateDate)) {
            room.setLastOccupiedUpdateDate(new Date());
        }

        room.setOccupied(castBooleanToRoomStatus(currentOccupied));
        room.setLastUpdateDate(new Date());

        roomRepository.save(room);

        return ResponseEntity.ok(null);
    }
}
