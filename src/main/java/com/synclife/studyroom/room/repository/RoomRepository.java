package com.synclife.studyroom.room.repository;

import com.synclife.studyroom.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
