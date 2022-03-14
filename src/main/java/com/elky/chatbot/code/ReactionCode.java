package com.elky.chatbot.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReactionCode {
    LIKE("좋아요"),
    DISLIKE("싫어요"),
    ;

    private String message;
}