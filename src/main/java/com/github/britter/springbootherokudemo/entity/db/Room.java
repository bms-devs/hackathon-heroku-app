package com.github.britter.springbootherokudemo.entity.db;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
public class Room {

    @Id
    private long id;

    private String name;

    private String token;

    private Boolean occupied;

    private Date lastUpdateDate;

    private Date lastOccupiedUpdateDate;

    public Room() {
    }
}
