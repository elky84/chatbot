package com.elky.chatbot.service;

import java.util.List;
import javax.transaction.Transactional;

import com.elky.chatbot.model.Member;
import com.elky.chatbot.protocol.MemberDto;
import com.elky.chatbot.repository.MemberRepo;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepo memberRepo;

	public List<Member> selectMembers() {
		return memberRepo.findAll();
	}

	public Member getMember(String id) {
		return memberRepo.findById(id).orElseThrow(IllegalArgumentException::new);
	}

	public Member saveMember(MemberDto member) {
		return memberRepo.save(member.toModel());
	}

	public void deleteMember(String id) {
		memberRepo.deleteById(id);
	}

	@Transactional
	public Member updateMember(String id, MemberDto member) {
		Member memberData = memberRepo.findById(id).orElseThrow(IllegalArgumentException::new);
		memberData.update(member.getName(), member.getAddress());
		return memberData;
	}
}
