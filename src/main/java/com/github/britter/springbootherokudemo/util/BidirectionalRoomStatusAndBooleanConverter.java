package com.github.britter.springbootherokudemo.util;

import com.github.britter.springbootherokudemo.entity.enumeration.RoomStatus;

public class BidirectionalRoomStatusAndBooleanConverter {


    // TODO extend when new RoomStatus is added and old API is still in use
    // delete when old API is deleted

    public static Boolean castRoomStatusToBoolean(RoomStatus roomStatus) {
        switch (roomStatus) {
            case AVAILABLE:
                return Boolean.FALSE;
            case OCCUPIED:
                return Boolean.TRUE;
            case UNKNOWN:
                return null;
            default:
                throw new IllegalStateException();
        }
    }

    public static RoomStatus castBooleanToRoomStatus(Boolean occupied) {
        if (occupied == null) {
            return RoomStatus.UNKNOWN;
        } else if (occupied.equals(Boolean.FALSE)) {
            return RoomStatus.AVAILABLE;
        } else {
            return RoomStatus.OCCUPIED;
        }

    }
}
