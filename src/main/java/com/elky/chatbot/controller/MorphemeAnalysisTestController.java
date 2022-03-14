package com.elky.chatbot.controller;

import java.util.List;

import com.elky.chatbot.protocol.MorphemeAnalysisTestDto;
import com.elky.chatbot.service.MorphemeAnalysisService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/morpheme-analysis/test")
public class MorphemeAnalysisTestController {
	private final MorphemeAnalysisService morphemeAnalysisService;

	@PostMapping("/analyze")
	public KomoranResult analyze(@RequestBody MorphemeAnalysisTestDto dto) {
		return morphemeAnalysisService.analyze(dto.getMessage());
	}

	@PostMapping("/phrases")
	public List<Token> phrases(@RequestBody MorphemeAnalysisTestDto dto) {
		return morphemeAnalysisService.phrases(dto.getMessage());
	}
}
