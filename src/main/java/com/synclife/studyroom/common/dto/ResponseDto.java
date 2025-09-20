package com.synclife.studyroom.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseDto<T> {

    private final int statusCode;

    private final String message;

    private final T data;
}
