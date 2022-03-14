package com.elky.chatbot.protocol;

import com.elky.chatbot.model.Chat;
import com.elky.chatbot.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AnswerDto {	

	String Id;

	@NonNull 
	String memberId;

	@NonNull 
	String message;

	@JsonIgnore 
	@NonNull 
	String morphemeAnalysisId;

	public Chat toModel() {
		return Chat.builder()
			.memberId(this.memberId)
			.message(this.message)
			.bot(true)
			.time(DateUtil.Now())
			.morphemeAnalysisId(this.morphemeAnalysisId).build();
	}
}
