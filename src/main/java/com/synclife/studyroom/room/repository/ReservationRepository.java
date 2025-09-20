package com.synclife.studyroom.room.repository;

import com.synclife.studyroom.room.entity.Reservation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = """
    SELECT * FROM reservation r
    WHERE r.time_range && tstzrange(:startDate, :endDate)""", nativeQuery = true)
    List<Reservation> findByTimeRange(@Param("startDate") ZonedDateTime start,
                                      @Param("endDate") ZonedDateTime end);

    @Modifying
    @Query(value = """
    INSERT INTO reservation (user_id, rooms_id, time_range)
    VALUES (:userId, :roomId, CAST(:timeRange AS tstzrange))
    """, nativeQuery = true)
    void saveReservationWithRange(
            @Param("userId") Long userId,
            @Param("roomId") Long roomId,
            @Param("timeRange") String timeRange
    );
}
