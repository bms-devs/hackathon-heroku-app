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

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date lastUpdateDate;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="CET")
    private Date lastOccupiedUpdateDate;
}
