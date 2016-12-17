package com.github.britter.springbootherokudemo.entity;

import com.google.gson.annotations.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
public class RoomDTO {
    @Expose
    private long id;

    @Expose
    private String name;

    @Expose
    private Boolean occupied;

    @Expose
    private Date lastUpdateDate;
}
