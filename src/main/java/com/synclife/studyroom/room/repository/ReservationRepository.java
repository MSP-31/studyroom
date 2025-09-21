package com.synclife.studyroom.room.repository;

import com.synclife.studyroom.room.entity.Reservation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * 예약 시간 범위가 겹치는 모든 예약을 조회합니다.
     * PostgreSQL의 '&&' 연산자를 사용하여 time_range 컬럼과 입력된 범위(tstzrange) 간의 겹침 여부를 검사합니다.
     *
     * @param start 시작 시간
     * @param end   종료 시간
     * @return      예약 리스트 반환
     */
    @Query(value = """
    SELECT * FROM reservation r
    WHERE r.time_range && tstzrange(:startDate, :endDate)""", nativeQuery = true)
    List<Reservation> findByTimeRange(@Param("startDate") ZonedDateTime start,
                                      @Param("endDate") ZonedDateTime end);

    /**
     * 예약 정보를 저장합니다.
     * PostgreSQL의 tstzrange 타입으로 시간 범위를 저장하며, 문자열로 전달된 범위를 CAST하여 삽입합니다.
     * 이후 RETUNING * 을 통하여 Reservation 객체를 반환함
     *
     * @param userId    유저 ID
     * @param roomId    회의실 ID
     * @param timeRange timeRange 형식의 시간 범위
     * @return          예약 정보 반환
     */
    @Query(value = """
    INSERT INTO reservation (user_id, rooms_id, time_range)
    VALUES (:userId, :reservationId, CAST(:timeRange AS tstzrange))
    RETURNING *
    """, nativeQuery = true)
    Reservation saveReservationWithRange(
            @Param("userId") Long userId,
            @Param("reservationId") Long roomId,
            @Param("timeRange") String timeRange
    );
}
