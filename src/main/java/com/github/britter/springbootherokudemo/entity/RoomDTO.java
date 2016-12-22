package com.github.britter.springbootherokudemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.*;

@Getter
@Setter
public class RoomDTO {

    private long id;

    private String name;

    private Boolean occupied;

    private Long lastUpdateDate;

    private String status;
}
