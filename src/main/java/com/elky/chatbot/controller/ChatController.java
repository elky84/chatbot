package com.elky.chatbot.controller;

import java.util.List;

import com.elky.chatbot.model.Chat;
import com.elky.chatbot.protocol.AnswerDto;
import com.elky.chatbot.protocol.ChatDto;
import com.elky.chatbot.protocol.QuestionDto;
import com.elky.chatbot.protocol.ReactionDto;
import com.elky.chatbot.service.ChatService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
	private final ChatService chatService;

	@GetMapping
	public List<Chat> selectChats() {
		return chatService.selectChats();
	}

	@GetMapping("/member")
	public List<Chat> selectChatsByMember(@RequestParam String memberId) {
		return chatService.selectChatsByMember(memberId);
	}

	@GetMapping("/{id}")
	public Chat getChat(@PathVariable String id) {
		return chatService.getChat(id);
	}

	@PostMapping
	public AnswerDto question(@RequestBody QuestionDto question) {
		return chatService.question(question);
	}

	@PostMapping("/{id}/reaction")
	public void reaction(@PathVariable String id, @RequestBody ReactionDto reaction) {
		chatService.reaction(id, reaction);
	}

	@PutMapping("/{id}")
	public Chat updateChat(@PathVariable String id, @RequestBody ChatDto chat) {
		return chatService.updateChat(id, chat);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteChat(@PathVariable String id) {
		chatService.deleteChat(id);
	}
}
