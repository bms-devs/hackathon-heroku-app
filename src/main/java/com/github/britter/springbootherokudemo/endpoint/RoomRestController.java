package com.github.britter.springbootherokudemo.endpoint;

import com.github.britter.springbootherokudemo.entity.*;
import com.github.britter.springbootherokudemo.repository.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/occupied")
public class RoomRestController {

    final static int TIMEOUT = 70;

    private final RoomRepository roomRepository;
    private final Gson gson;

    @Autowired
    public RoomRestController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;

        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .serializeNulls()
                .create();
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json"})
    ResponseEntity<?> getAllRoomsAvailibility() {
        List<RoomDTO> allRooms = roomRepository.findAll()
                .stream().map(r -> new RoomDTO(r))
                .collect(Collectors.toList());

        for (RoomDTO room : allRooms) {
            changeStatusIfTimeout(room);
        }

        Type type = new TypeToken<List<RoomDTO>>() {}.getType();
        String responseJson = gson.toJson(allRooms, type);

        return ResponseEntity.ok(responseJson);
    }

    @RequestMapping(value = "/{roomId}", method = RequestMethod.GET, produces = {"application/json"})
    ResponseEntity<?> getSpecificRoomAvailibility(@PathVariable Long roomId) {
        Room room = roomRepository.getOne(roomId);
        if (room == null) {
            return ResponseEntity.badRequest().body(null);
        }

        RoomDTO roomDTO = new RoomDTO(room);
        changeStatusIfTimeout(roomDTO);
        String responseJson = gson.toJson(roomDTO);

        return ResponseEntity.ok(responseJson);
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

    private void changeStatusIfTimeout(RoomDTO room) {
        Date now = new Date();
        if (room.getLastUpdateDate() != null && now.getTime() - room.getLastUpdateDate().getTime() > 1000*TIMEOUT) {
            room.setOccupied(null);
        }
    }

}
