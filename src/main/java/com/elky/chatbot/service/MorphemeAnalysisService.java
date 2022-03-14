package com.elky.chatbot.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.elky.chatbot.model.MorphemeAnalysis;
import com.elky.chatbot.protocol.AnswerDto;
import com.elky.chatbot.protocol.MorphemeAnalysisDto;
import com.elky.chatbot.repository.MorphemeAnalysisRepo;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MorphemeAnalysisService {
	private final MorphemeAnalysisRepo morphemeAnalysisRepo;
	private final Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT);
	private final Random random = new Random();

	public KomoranResult analyze(String message) {
		return komoran.analyze(message);
	}

	public List<Token> phrases(String message) {
		var result = analyze(message);
		return result.getTokenList();
	} 

	public List<String> nouns(List<Token> tokens)
	{
		return tokens
			.stream()
			.filter(x -> x.getPos().startsWith("N"))
			.map(x -> x.getMorph())
			.distinct()
			.collect(Collectors.toList());
	}

	public List<String> verbs(List<Token> tokens)
	{
		return tokens
			.stream()
			.filter(x -> x.getPos().startsWith("VV") || x.getPos().startsWith("VA"))
			.map(x -> x.getMorph())
			.distinct()
			.collect(Collectors.toList());
	}

	public void CsvImport(String text) throws CsvValidationException, IOException
	{
		var csvReader = new CSVReader(new StringReader(text));

		String[] nextLine;
		while ((nextLine = csvReader.readNext()) != null) {
			if(nextLine.length < 2)
				break;

			Learn(nextLine[0], nextLine[1]);
		}
	}

	public MorphemeAnalysis Learn(String question, String answer)
	{
		var tokens = phrases(question);
		var nouns = nouns(tokens);
		var verbs = verbs(tokens);

		return upsertMorphemeAnalysis(new MorphemeAnalysisDto(tokens, nouns, verbs, answer, 0));
	}

	public AnswerDto getAnswer(String memberId, String question) {
		var tokens = phrases(question);
		var nouns = nouns(tokens);
		var verbs = verbs(tokens);

		var morphemeAnalysisList = morphemeAnalysisRepo.findByNounsInAndVerbsIn(nouns, verbs);
		if(morphemeAnalysisList.isEmpty())
		{
			morphemeAnalysisList = morphemeAnalysisRepo.findByNounsIn(nouns);
			if(morphemeAnalysisList.isEmpty())
			{
				return null;
			}
		}

		Optional<Long> highNounMatches = morphemeAnalysisList
								.stream()
								.map(x -> ContainsNouns(x.getNouns(), nouns))
								.sorted(Collections.reverseOrder())
								.findFirst();

		if( highNounMatches.isPresent() && highNounMatches.get() > 1)
		{
			var highestList = morphemeAnalysisList
								.stream()
								.filter(x -> {
									var matchGap = ContainsNouns(x.getNouns(), nouns) - highNounMatches.get();
									var evalutionPoint = x.getEvaluation() * 50;
									return 0 >= matchGap * 100 + evalutionPoint;
								})
								.collect(Collectors.toList());

			if(!highestList.isEmpty())
			{
				var morphemeAnalysis = highestList.get(random.nextInt(highestList.size()));
				return new AnswerDto(memberId, morphemeAnalysis.getAnswer(), morphemeAnalysis.getId());	
			}
		}

		var morphemeAnalysis = morphemeAnalysisList.get(random.nextInt(morphemeAnalysisList.size()));
		return new AnswerDto(memberId, morphemeAnalysis.getAnswer(), morphemeAnalysis.getId());
	}

	private long ContainsNouns(List<String> nouns, List<String> nouns2)
	{
		return nouns.stream()
					.map(str -> Collections.frequency(nouns2, str))
					.count();
	}

	public MorphemeAnalysis upsertMorphemeAnalysis(MorphemeAnalysisDto morphemeAnalysis) {
		return morphemeAnalysisRepo.save(morphemeAnalysis.toModel());
	}

	public Page<MorphemeAnalysis> selectMorphemeAnalysis(Pageable pageable) {
		return morphemeAnalysisRepo.findAll(pageable);
	}

	public MorphemeAnalysis getMorphemeAnalysis(String id) {
		return morphemeAnalysisRepo.findById(id).orElseThrow(IllegalArgumentException::new);
	}

	public MorphemeAnalysis saveMorphemeAnalysis(MorphemeAnalysisDto morphemeAnalysis) {
		return morphemeAnalysisRepo.save(morphemeAnalysis.toModel());
	}

	public void deleteMorphemeAnalysis(String id) {
		morphemeAnalysisRepo.deleteById(id);
	}

	@Transactional
	public MorphemeAnalysis updateMorphemeAnalysis(String id, MorphemeAnalysisDto morphemeAnalysis) {
		var morphemeAnalysisData = morphemeAnalysisRepo.findById(id).orElseThrow(IllegalArgumentException::new);
		morphemeAnalysisData.update(morphemeAnalysis.getWords(), morphemeAnalysis.getNouns(), morphemeAnalysis.getVerbs(), morphemeAnalysis.getAnswer(), morphemeAnalysis.getEvaluation());
		morphemeAnalysisRepo.save(morphemeAnalysisData);
		return morphemeAnalysisData;
	}

	@Transactional
	public MorphemeAnalysis updateMorphemeAnalysis(String id, MorphemeAnalysis morphemeAnalysis) {
		var morphemeAnalysisData = morphemeAnalysisRepo.findById(id).orElseThrow(IllegalArgumentException::new);
		morphemeAnalysisData.update(morphemeAnalysis.getWords(), morphemeAnalysis.getNouns(), morphemeAnalysis.getVerbs(), morphemeAnalysis.getAnswer(), morphemeAnalysis.getEvaluation());
		morphemeAnalysisRepo.save(morphemeAnalysisData);
		return morphemeAnalysisData;
	}
}
