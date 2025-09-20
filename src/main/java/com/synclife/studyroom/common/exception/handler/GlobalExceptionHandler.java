package com.synclife.studyroom.common.exception.handler;

import com.synclife.studyroom.common.exception.dto.ErrorResponseDto;
import com.synclife.studyroom.common.exception.exceptions.CustomException;
import com.synclife.studyroom.common.exception.messages.ExceptionMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * CustomException 발생 시 호출되는 예외 처리 핸들러
     * ExceptionMessage 기반으로 에러 응답을 생성하여 클라이언트에 반환합니다.
     * @param ex 사용자 정의 예외 객체
     * @return   에러 정보가 담긴 ResponseEntity
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException ex) {
        ErrorResponseDto error = ErrorResponseDto.of(ex.getExceptionMessage());
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    /**
     * 정의되지 않은 예외(Exception)가 발생했을 때 호출되는 핸들러
     * 사전에 정의된 ExceptionMessage(INTERNAL_SERVER_ERROR)를 기반으로 에러 응답을 생성하여 반환합니다.
     * @param ex 처리되지 않은 일반 예외 객체
     * @return   서버 내부 오류에 대한 에러 정보를 담은 ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralException(Exception ex) {
        return ResponseEntity.status(ExceptionMessage.INTERNAL_SERVER_ERROR.getStatus())
                .body(ErrorResponseDto.of(ExceptionMessage.INTERNAL_SERVER_ERROR));
    }

    /**
     * 요청 파라미터의 타입이 잘못된 경우 발생하는 예외 처리 핸들러
     * @param ex MethodArgumentTypeMismatchException - 타입 변환 실패 예외 객체
     * @return   입력값 오류에 대한 에러 정보를 담은 ResponseEntity
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(ExceptionMessage.INPUT_INVALID_VALUE.getStatus())
                .body(ErrorResponseDto.of(ExceptionMessage.INPUT_INVALID_VALUE));
    }

    /**
     * 유효성 검사 실패 시 호출되는 예외 처리 핸들러
     * 사전에 정의된 ExceptionMessage(INPUT_INVALID_VALUE)를 기반으로 에러 응답을 생성하여 반환합니다.
     * @param ex      MethodArgumentNotValidException - @Valid 검증 실패 시 발생하는 예외 객체
     * @param headers 요청 헤더 정보
     * @param status  기본 응답 상태 코드
     * @param request 현재 요청 정보 (IP, 세션 등 포함)
     * @return        입력값 오류에 대한 에러 정보를 담은 ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode  status,
            @NonNull WebRequest  request) {
        return ResponseEntity.status(ExceptionMessage.INPUT_INVALID_VALUE.getStatus())
                .body(ErrorResponseDto.of(ExceptionMessage.INPUT_INVALID_VALUE));
    }

    /**
     * 요청 본문(JSON 등)을 읽을 수 없을 때 발생하는 예외 처리 핸들러
     * 사전에 정의된 ExceptionMessage(INPUT_INVALID_VALUE)를 기반으로 에러 응답을 생성하여 반환합니다.
     * @param ex      HttpMessageNotReadableException - 요청 본문 파싱 실패 예외 객체
     * @param headers 요청 헤더 정보
     * @param status  기본 응답 상태 코드
     * @param request 현재 요청 정보 (URI, 세션 등 포함)
     * @return        커스텀 에러 정보가 담긴 ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode  status,
            @NonNull WebRequest  request) {
        return ResponseEntity.status(ExceptionMessage.INPUT_INVALID_VALUE.getStatus())
                .body(ErrorResponseDto.of(ExceptionMessage.INPUT_INVALID_VALUE));
    }

}
