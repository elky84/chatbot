package com.elky.chatbot.repository;

import java.util.List;

import com.elky.chatbot.model.Chat;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepo extends MongoRepository<Chat, String> {
    List<Chat> findByMemberId(String memberId);
}
