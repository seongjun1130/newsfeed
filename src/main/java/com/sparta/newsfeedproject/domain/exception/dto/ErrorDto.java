package com.sparta.newsfeedproject.domain.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDto {
    private final HttpStatus status;
    private final String message;
}
