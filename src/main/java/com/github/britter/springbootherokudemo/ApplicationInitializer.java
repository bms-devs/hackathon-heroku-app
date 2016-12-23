package com.github.britter.springbootherokudemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.britter.springbootherokudemo.entity.*;
import com.github.britter.springbootherokudemo.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

@Component
public class ApplicationInitializer implements ApplicationRunner {

    private final RoomRepository roomRepository;

    @Autowired
    public ApplicationInitializer(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("rooms.json");

        ObjectMapper mapper = new ObjectMapper();
        List<Room> rooms = mapper.readValue(inputStream, new TypeReference<List<Room>>(){});
        roomRepository.save(rooms);
    }
}
