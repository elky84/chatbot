package com.elky.chatbot.model;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import kr.co.shineware.nlp.komoran.model.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("morpheme_analysis")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MorphemeAnalysis {
	
	@Id
	String id;

	List<Token> words;

	List<String> nouns;

	List<String> verbs;

	String answer;

	double evaluation;

	public void update(List<Token> words, List<String> nouns, List<String> verbs, String answer, double evaluation) {
		this.words = words;
		this.nouns = nouns;
		this.verbs = verbs;
		this.answer = answer;
		this.evaluation = evaluation;
	}
}
