package com.github.britter.springbootherokudemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Duration {
    private long hours;
    private long minutes;
    private long seconds;
}