package com.sparta.newsfeedproject.domain.exception;

import com.sparta.newsfeedproject.domain.exception.eunm.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
