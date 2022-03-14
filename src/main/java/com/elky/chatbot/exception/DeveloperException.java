package com.elky.chatbot.exception;

import com.elky.chatbot.code.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeveloperException extends RuntimeException {
    private final ErrorCode errorCode;
}