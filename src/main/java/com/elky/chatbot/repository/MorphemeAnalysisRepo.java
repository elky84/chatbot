package com.elky.chatbot.repository;

import java.util.List;
import java.util.Optional;

import com.elky.chatbot.model.MorphemeAnalysis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import kr.co.shineware.nlp.komoran.model.Token;

public interface MorphemeAnalysisRepo extends MongoRepository<MorphemeAnalysis, String> {
    Optional<MorphemeAnalysis> findByWordsAndAnswer(List<Token> words, String answer);

    List<MorphemeAnalysis> findByNounsIn(List<String> nouns);

    List<MorphemeAnalysis> findByNounsInAndVerbsIn(List<String> nouns, List<String> verbs);

    Page<MorphemeAnalysis> findAll(Pageable pageable);
}
