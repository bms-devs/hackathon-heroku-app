package com.github.britter.springbootherokudemo.repository;

import com.github.britter.springbootherokudemo.entity.db.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
