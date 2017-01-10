package com.github.britter.springbootherokudemo.entity.dto;

import lombok.*;

@Getter
@Setter
public class RoomDTO {

    private long id;

    private String name;

    private Boolean occupied;

    private Long lastUpdateDate;

    private Long lastOccupiedStatusChangeDate;

    private String status;
}
