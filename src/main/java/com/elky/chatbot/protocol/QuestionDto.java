package com.elky.chatbot.protocol;

import com.elky.chatbot.model.Chat;
import com.elky.chatbot.util.DateUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
	
	String memberId;

	String message;

	public Chat toModel(String morphemeAnalysisId) {
		return Chat.builder()
			.memberId(this.memberId)
			.message(this.message)
			.time(DateUtil.Now())
			.morphemeAnalysisId(morphemeAnalysisId).build();
	}
}
