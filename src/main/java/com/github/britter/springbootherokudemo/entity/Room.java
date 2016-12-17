package com.github.britter.springbootherokudemo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.*;

@Entity
@Getter
@Setter
public class Room {

    @Id
    private long id;

    private RoomStatus status;

    private Date lastUpdateDate;

}
