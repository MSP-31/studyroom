package com.synclife.studyroom.common.exception.exceptions;

import com.synclife.studyroom.common.exception.messages.ExceptionMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
@RequiredArgsConstructor
public class BaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4010119068958861531L;

    private final String type;
    private final HttpStatus status;
    private final String errorCode;
    private final ExceptionMessage exceptionMessage;

    public BaseException(ExceptionMessage message) {
        super(message.getMessage());
        this.type = message.name();
        this.status = message.getStatus();
        this.errorCode = message.getCode();
        this.exceptionMessage = message;
    }

}
