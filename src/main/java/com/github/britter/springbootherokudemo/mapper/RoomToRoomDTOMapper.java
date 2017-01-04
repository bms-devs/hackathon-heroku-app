package com.github.britter.springbootherokudemo.mapper;

import com.github.britter.springbootherokudemo.entity.db.*;
import com.github.britter.springbootherokudemo.entity.dto.*;

public interface RoomToRoomDTOMapper {
    RoomDTO map(Room room);
}
