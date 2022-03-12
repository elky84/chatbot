package com.elky.chatbot.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("member")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	
	@Id
	String id;

	String name;
	
	String address;

	public void update(String name, String address) {
		this.name = name;
		this.address = address;
	}
}
