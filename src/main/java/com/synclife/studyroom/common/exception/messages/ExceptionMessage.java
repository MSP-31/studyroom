package com.synclife.studyroom.common.exception.messages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    // 공통 에러
    INTERNAL_SERVER_ERROR("G001", "서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INPUT_INVALID_VALUE("G002", "요청 값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),

    // 회원 관련 에러
    USER_NOT_FOUND("U001", "해당 회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_USERNAME("U002", "이미 사용 중인 이름입니다.", HttpStatus.CONFLICT),
    PASSWORD_MISMATCH("U003", "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_PERMISSION_USER("U004","접근 권한이 없습니다!",HttpStatus.FORBIDDEN),

    // 방/예약 관련 에러
    CAPACITY_MUST_BE_POSITIVE("R001", "수용 인원은 0보다 커야 합니다.", HttpStatus.BAD_REQUEST),
    ROOM_NOT_FOUND("R002", "해당하는 회의실이 없습니다.", HttpStatus.NOT_FOUND),
    RESERVATION_TIME_INVALID("R003", "시작 시간과 끝 시간이 같습니다.", HttpStatus.BAD_REQUEST),
    RESERVATION_START_AFTER_END("R004", "시작 시간이 끝 시간보다 늦을 수 없습니다.", HttpStatus.BAD_REQUEST),
    RESERVATION_NOT_FOUND("R005", "해당하는 예약이 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;

}
