package com.synclife.studyroom.room.repository;

import com.synclife.studyroom.room.entity.Reservation;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query(value = """
    INSERT INTO reservation (user_id, rooms_id, start_at, end_at, time_range)
    VALUES (:userId, :roomId, :startAt, :endAt, CAST(:timeRange AS tstzrange))
    """, nativeQuery = true)
    void saveReservationWithRange(
            @Param("userId") Long userId,
            @Param("roomId") Long roomId,
            @Param("startAt") LocalDateTime startAt,
            @Param("endAt") LocalDateTime endAt,
            @Param("timeRange") String timeRange
    );
}
