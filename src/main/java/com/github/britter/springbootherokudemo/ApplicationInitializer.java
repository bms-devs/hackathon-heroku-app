package com.github.britter.springbootherokudemo;

import com.github.britter.springbootherokudemo.entity.*;
import com.github.britter.springbootherokudemo.repository.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.lang.reflect.*;
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
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("rooms.json").getFile());
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            Type type = new TypeToken<List<Room>>() {}.getType();
            List<Room> rooms = new Gson().fromJson(bufferedReader, type);
            for (Room room : rooms) {
                roomRepository.save(room);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
