package com.elky.chatbot.protocol;

import java.util.Date;

import com.elky.chatbot.model.Chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
	
	String memberId;

	boolean bot;

	String message;

	Date time;

	String morphemeAnalysisId;

	public Chat toModel() {
		return Chat.builder()
			.memberId(this.memberId)
			.bot(this.bot)
			.message(this.message)
			.time(this.time)
			.morphemeAnalysisId(this.morphemeAnalysisId).build();
	}
}
