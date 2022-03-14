package com.elky.chatbot.controller;

import java.io.IOException;

import com.elky.chatbot.model.MorphemeAnalysis;
import com.elky.chatbot.protocol.LearnDto;
import com.elky.chatbot.service.MorphemeAnalysisService;
import com.opencsv.exceptions.CsvValidationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/morpheme-analysis")
public class MorphemeAnalysisController {
	private final MorphemeAnalysisService morphemeAnalysisService;

	@GetMapping
	public Page<MorphemeAnalysis> selectMorphemeAnalysis(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
		return morphemeAnalysisService.selectMorphemeAnalysis(PageRequest.of(page, size));
	}

	@GetMapping("/{id}")
	public MorphemeAnalysis getMorphemeAnalysis(@PathVariable String id) {
		return morphemeAnalysisService.getMorphemeAnalysis(id);
	}

	@PostMapping("/learn")
	public MorphemeAnalysis learn(@RequestBody LearnDto learn) {
		return morphemeAnalysisService.Learn(learn.getQuestion(), learn.getAnswer());
	}

	@PostMapping(path = "csv", consumes = MediaType.TEXT_PLAIN_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void csv(@RequestBody String text) throws CsvValidationException, IOException {
		morphemeAnalysisService.CsvImport(text);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteMorphemeAnalysis(@PathVariable String id) {
		morphemeAnalysisService.deleteMorphemeAnalysis(id);
	}
}
