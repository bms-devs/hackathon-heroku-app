package com.github.britter.springbootherokudemo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.github.britter.springbootherokudemo.entity.enumeration.RoomStatus;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @Id
    private long id;

    private String name;

    private String token;

    private RoomStatus occupied;

    private Date lastUpdateDate;

    private Date lastOccupiedUpdateDate;

}
