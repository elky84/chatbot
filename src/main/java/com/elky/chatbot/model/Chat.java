package com.elky.chatbot.model;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("chat")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
	
	@Id
	String id;

	String memberId;

	boolean bot;
	
	String message;

	Date time;

	String morphemeAnalysisId;

	public void update(String memberId, boolean bot, String message, Date time, String morphemeAnalysisId) {
		this.memberId = memberId;
		this.bot = bot;
		this.message = message;
		this.time = time;
		this.morphemeAnalysisId = morphemeAnalysisId;
	}
}
