package com.synclife.studyroom.common.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.synclife.studyroom.common.exception.messages.ExceptionMessage;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "에러 응답 DTO")
public record ErrorResponseDto(
        @Schema(description = "에러 코드", example = "R005") String errorCode,
        @Schema(description = "에러 메시지", example = "해당하는 예약을 찾을 수 없습니다.") String message,
        @Schema(description = "HTTP 상태 코드", example = "404") int status) {

    public static ErrorResponseDto of(ExceptionMessage em) {
        return new ErrorResponseDto(
                em.getCode(),
                em.getMessage(),
                em.getStatus().value()
        );
    }
}
