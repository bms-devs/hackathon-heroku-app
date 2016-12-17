package com.github.britter.springbootherokudemo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.gson.annotations.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
public class Room {

    @Id
    @Expose
    private long id;

    @Expose
    private String name;

    private String token;

    @Expose
    private Boolean occupied;

    @Expose
    private Date lastUpdateDate;

}
