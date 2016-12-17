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

@RestController
@RequestMapping("")
public class RoomRestController {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomRestController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @RequestMapping(value = "/occupied", method = RequestMethod.GET, produces = {"application/json"})
    ResponseEntity<?> getAllRoomsAvailibility() {
        List<Room> allRooms = roomRepository.findAll();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<List<Room>>() {}.getType();
        String allRoomsJson = gson.toJson(allRooms, type);
        System.out.println(allRoomsJson);
        return ResponseEntity.ok(allRoomsJson);
    }

    @RequestMapping(value = "/{roomId}/occupied", method = RequestMethod.POST, consumes = {"text/plain"})
    ResponseEntity<?> updateRoomAvailability(@PathVariable Long roomId, @RequestBody String occupied) {
        System.out.println(roomId);
        System.out.println(occupied);

        Room room = roomRepository.getOne(roomId);
        if (room == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // validate occupied param
        boolean occupiedStatus = Boolean.valueOf(occupied);
        System.out.println(occupiedStatus);

        room.setOccupied(occupiedStatus);
        room.setLastUpdateDate(new Date());

        return ResponseEntity.ok(null);
    }

}
