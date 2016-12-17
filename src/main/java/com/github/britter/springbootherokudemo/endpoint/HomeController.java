/*
 * Copyright 2015 Benedikt Ritter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.britter.springbootherokudemo.endpoint;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import com.github.britter.springbootherokudemo.entity.*;
import com.github.britter.springbootherokudemo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {

    private RoomRepository repository;

    @Autowired
    public HomeController(RoomRepository repository) {
//        Room room1 = new Room();
//        room1.setId(1);
//        room1.setLastUpdateDate(new Date());
//        room1.setStatus(RoomStatus.AVAILABLE);
//        repository.save(room1);
//
//        Room room2 = new Room();
//        room2.setId(2);
//        room2.setLastUpdateDate(new Date());
//        room2.setStatus(RoomStatus.BUSY);
//        repository.save(room2);


        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(ModelMap model) {
        List<Room> rooms = repository.findAll();
        model.addAttribute("rooms", rooms);
        return "home";
    }

}