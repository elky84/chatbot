package com.elky.chatbot.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND(404, "PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500, "INTER SERVER ERROR"),
    NOT_FOUND_ANSWER(500, "적절한 대답을 찾지 못했어요"),
    NOT_IMPLEMENTED_YET(500, "미구현 기능"),
    ;

    private int status;
    private String message;
}