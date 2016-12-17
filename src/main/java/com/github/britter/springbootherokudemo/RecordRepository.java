package com.github.britter.springbootherokudemo;

import com.github.britter.springbootherokudemo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Room, Long> {
}
