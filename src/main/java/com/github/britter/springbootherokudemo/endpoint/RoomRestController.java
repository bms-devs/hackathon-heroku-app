package com.github.britter.springbootherokudemo.endpoint;

import com.github.britter.springbootherokudemo.entity.db.*;
import com.github.britter.springbootherokudemo.entity.dto.*;
import com.github.britter.springbootherokudemo.mapper.*;
import com.github.britter.springbootherokudemo.repository.*;
import com.github.britter.springbootherokudemo.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.*;

@RestController
@RequestMapping("/occupied")
public class RoomRestController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    @Qualifier("restMapper")
    private RoomToRoomDTOMapper mapper;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private RoomService roomService;


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

        roomService.updateRoomOccupied(room, occupied);
        webSocketService.sendStatusUpdateNotificationToAllRegisteredClients();

        return ResponseEntity.ok(null);
    }


}
