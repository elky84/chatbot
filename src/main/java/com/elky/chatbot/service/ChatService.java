package com.elky.chatbot.service;

import java.util.List;

import com.elky.chatbot.code.ErrorCode;
import com.elky.chatbot.exception.DeveloperException;
import com.elky.chatbot.model.Chat;
import com.elky.chatbot.protocol.AnswerDto;
import com.elky.chatbot.protocol.ChatDto;
import com.elky.chatbot.protocol.QuestionDto;
import com.elky.chatbot.protocol.ReactionDto;
import com.elky.chatbot.repository.ChatRepo;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
	private final ChatRepo chatRepo;
	private final MorphemeAnalysisService morphemeAnalysisService;

	public List<Chat> selectChats() {
		return chatRepo.findAll();
	}

	public List<Chat> selectChatsByMember(String memberId) {
		return chatRepo.findByMemberId(memberId);
	}

	public Chat getChat(String id) {
		return chatRepo.findById(id).orElseThrow(IllegalArgumentException::new);
	}

	private Chat saveChat(Chat chat) {
		return chatRepo.save(chat);
	}

	public void deleteChat(String id) {
		chatRepo.deleteById(id);
	}

	public void reaction(String id, ReactionDto reaction) {
		var chat = getChat(id);
		var morphemeAnalysis = morphemeAnalysisService.getMorphemeAnalysis(chat.getMorphemeAnalysisId());

		switch(reaction.getCode())
		{
			case DISLIKE:
				morphemeAnalysis.setEvaluation(morphemeAnalysis.getEvaluation() - 1);
				break;
			case LIKE:
				morphemeAnalysis.setEvaluation(morphemeAnalysis.getEvaluation() + 1);
				break;
			default:
				throw new DeveloperException(ErrorCode.NOT_IMPLEMENTED_YET);
		}

		morphemeAnalysisService.updateMorphemeAnalysis(chat.getMorphemeAnalysisId(), morphemeAnalysis);
	}


	public AnswerDto question(QuestionDto question) {
		var answerDto = morphemeAnalysisService.getAnswer(question.getMemberId(), question.getMessage());
		if(answerDto == null)
		{
			answerDto = new AnswerDto(question.getMemberId(), 
				ErrorCode.NOT_FOUND_ANSWER.getMessage(),
				"");
		}

		saveChat(question.toModel(answerDto.getMorphemeAnalysisId()));

		var answerChat = saveChat(answerDto.toModel());
		answerDto.setId(answerChat.getId());
		return answerDto;
	}

	public Chat updateChat(String id, ChatDto chat) {
		Chat chatData = chatRepo.findById(id).orElseThrow(IllegalArgumentException::new);
		chatData.update(chat.getMemberId(), chat.isBot(), chat.getMessage(), chat.getTime(), chat.getMorphemeAnalysisId());
		chatRepo.save(chatData);
		return chatData;
	}
}
