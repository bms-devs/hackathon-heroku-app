package com.github.britter.springbootherokudemo.endpoint;

import com.github.britter.springbootherokudemo.entity.*;
import com.github.britter.springbootherokudemo.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/occupied")
public class RoomRestController {

    private final RoomRepository roomRepository;
    private final RoomToRoomDTOMapper mapper;

    @Autowired
    public RoomRestController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        this.mapper = new RoomToRoomDTOMapper();
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

        room.setOccupied(Boolean.valueOf(occupied));
        room.setLastUpdateDate(new Date());
        roomRepository.save(room);

        return ResponseEntity.ok(null);
    }
}
