package com.synclife.studyroom.room.repository;

import com.synclife.studyroom.room.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
