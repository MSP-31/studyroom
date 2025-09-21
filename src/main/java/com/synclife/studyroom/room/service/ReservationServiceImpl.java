package com.synclife.studyroom.room.service;

import com.synclife.studyroom.common.exception.exceptions.CustomException;
import com.synclife.studyroom.common.exception.messages.ExceptionMessage;
import com.synclife.studyroom.common.jwt.JwtService;
import com.synclife.studyroom.room.dto.ReservationRequestDto;
import com.synclife.studyroom.room.dto.ReservationResponseDto;
import com.synclife.studyroom.room.entity.Reservation;
import com.synclife.studyroom.room.entity.Room;
import com.synclife.studyroom.room.repository.ReservationRepository;
import com.synclife.studyroom.room.repository.RoomRepository;
import com.synclife.studyroom.user.entity.User;
import com.synclife.studyroom.user.entity.UserRoleType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    private final JwtService jwtService;

    /**
     * 회의실 예약 생성 메서드
     *
     * @param requestDto 회의실 아이디, 시작시간, 끝시간
     * @return           예약 정보 반환
     */
    @Override
    public ReservationResponseDto createReservation(ReservationRequestDto requestDto){
        User user = jwtService.getUser();
        Room room = roomRepository.findById(requestDto.getRoomId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.ROOM_NOT_FOUND));

        String timeRange = setTstzrangeFormat(requestDto);

        Reservation reservation = reservationRepository.saveReservationWithRange(
                user.getId(),
                room.getId(),
                timeRange
        );

        return new ReservationResponseDto(reservation, requestDto);
    }

    /**
     * 해당 날짜 예약 현황 조회 메서드
     * @param date 현재 날짜 데이터
     * @return 예약 리스트 DTO
     */
    @Override
    public List<ReservationResponseDto> getRoomsByDate(LocalDate date) {
        ZoneId zone = ZoneId.of("Asia/Seoul");
        ZonedDateTime start = date.atStartOfDay(zone);
        ZonedDateTime end = date.plusDays(1).atStartOfDay(zone);

        List<Reservation> reservations = reservationRepository.findByTimeRange(start,end);
        return toReservationResponseDto(reservations);
    }

    /**
     * 예약 취소 메서드
     * @param id 예약 id
     */
    @Override
    public void deleteReservation(Long id) {
        User user = jwtService.getUser();

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.RESERVATION_NOT_FOUND));
        Long userId = reservation.getUser().getId();

        if (!user.getRole().equals(UserRoleType.ROLE_ADMIN) && !user.getId().equals(userId)){
            throw new CustomException(ExceptionMessage.RESERVATION_CANCEL_NOT_ALLOWED);
        }
        reservationRepository.deleteById(id);
    }

    /**
     * 예약 엔티티들을 응답용 DTO로 변환하는 메서드
     * @param reservations 지정된 범위의 예약정보 리스트
     * @return 예약정보 DTO
     */
    private List<ReservationResponseDto> toReservationResponseDto(List<Reservation> reservations) {
        return reservations.stream()
                .map(reservation -> ReservationResponseDto.builder()
                        .reservationId(reservation.getId())
                        .userId(reservation.getUser().getId())
                        .roomId(reservation.getRoom().getId())
                        .roomName(reservation.getRoom().getRoomName())
                        .location(reservation.getRoom().getLocation())
                        .capacity(reservation.getRoom().getCapacity())
                        .startAt(getDateTime(reservation.getTimeRange(),0))
                        .endAt(getDateTime(reservation.getTimeRange(),1))
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * timeRage를 보기좋게 형식을 변환하는 메서드
     * @param timeRange tstzrange 형식의 시간 경계
     * @param part 0: 시작시간, 1: 끝시간
     * @return 시간 문자열(yyyy-MM-dd HH:mm) 반환
     */
    private String getDateTime (String timeRange, int part){
        // 불필요한 특수문자를 제거하고 , 단위로 분리하여 배열에 삽입
        timeRange = timeRange.replace("[", "")
                             .replace(")", "")
                             .replace("\"", "");
        String[] parts = timeRange.split(",");
        String cleaned = parts[part].replace("+09", "+09:00");

        // 데이터 포맷 변경
        DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSXXX");
        DateTimeFormatter output = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        ZonedDateTime dateTime = ZonedDateTime.parse(cleaned, input);

        return dateTime.format(output);
    }

    /**
     * 시작 시간과 끝 시간을 DTO로 받아서 tstzrange에 맞게 형식을 변환하는 메서드
     * @param requestDto 시작 시간과 종료 시간이 담긴 DTO
     * @return tstzrange 형식의 문자열
     */
    private String setTstzrangeFormat(ReservationRequestDto requestDto){
        // 시간대 지정
        ZonedDateTime startZoned = requestDto.getStartAt().atZone(ZoneId.of("Asia/Seoul"));
        ZonedDateTime endZoned = requestDto.getEndAt().atZone(ZoneId.of("Asia/Seoul"));

        if (startZoned.equals(endZoned)){
            // 시작과 끝이 같으면 오류
            throw new CustomException(ExceptionMessage.RESERVATION_TIME_INVALID);
        }else if (startZoned.isAfter(endZoned)){
            // 시작이 끝 보다 늦으면 오류
            throw new CustomException(ExceptionMessage.RESERVATION_START_AFTER_END);
        }

        // tstzrange 포맷에 맞춰서 저장
        return String.format("[%s, %s)",
                startZoned.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                endZoned.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        );
    }
}
