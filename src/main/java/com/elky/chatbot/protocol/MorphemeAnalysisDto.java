package com.elky.chatbot.protocol;

import java.util.List;

import com.elky.chatbot.model.MorphemeAnalysis;

import kr.co.shineware.nlp.komoran.model.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MorphemeAnalysisDto {
	
	List<Token> words;
	
	List<String> nouns;

	List<String> verbs;

	String answer;

	double evaluation;

	public MorphemeAnalysis toModel() {
		return MorphemeAnalysis.builder()
			.words(this.words)
			.nouns(this.nouns)
			.verbs(this.verbs)
			.answer(this.answer)
			.evaluation(this.evaluation).build();
	}
}
