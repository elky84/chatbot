package com.elky.chatbot.protocol;

import com.elky.chatbot.code.ReactionCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDto {
	
	String memberId;

	ReactionCode code;
}