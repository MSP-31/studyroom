package com.synclife.studyroom.common.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.synclife.studyroom.common.exception.messages.ExceptionMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {
    private final String errorCode;
    private final String message;
    private final int status;

    public static ErrorResponseDto of(ExceptionMessage em){
        return new ErrorResponseDto(
                em.getCode(),
                em.getMessage(),
                em.getStatus().value()
        );
    }
}
