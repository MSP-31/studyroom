package com.synclife.studyroom.room.repository;

import com.synclife.studyroom.room.entity.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // List<Reservation> findByStartAt(LocalDate date);

    // @Query("SELECT r FROM Reservation r WHERE DATE(r.start_at) = :date")
    // List<Reservation> findByStartAt(@Param("date") LocalDate date);

    @Query("SELECT r FROM Reservation r WHERE r.startAt BETWEEN :start AND :end")
    List<Reservation> findByStartAt(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


}
