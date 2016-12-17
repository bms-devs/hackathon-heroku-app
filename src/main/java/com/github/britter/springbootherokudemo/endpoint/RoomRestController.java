package com.github.britter.springbootherokudemo.endpoint;

import com.github.britter.springbootherokudemo.entity.*;
import com.github.britter.springbootherokudemo.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class RoomRestController {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomRestController(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> updateRoomStatus(@PathVariable Long roomId, @PathVariable String status) {

        Room room = roomRepository.getOne(roomId);

        return null;

    }
}
