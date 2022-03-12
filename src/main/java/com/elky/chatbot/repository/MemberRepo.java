package com.elky.chatbot.repository;

import com.elky.chatbot.model.Member;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberRepo extends MongoRepository<Member, String> {
}
