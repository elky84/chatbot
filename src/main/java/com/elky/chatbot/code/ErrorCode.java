package com.elky.chatbot.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND(404, "PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500, "INTER SERVER ERROR"),
    ;

    private int status;
    private String message;
}