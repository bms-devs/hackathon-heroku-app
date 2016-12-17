package com.github.britter.springbootherokudemo.repository;

import com.github.britter.springbootherokudemo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
