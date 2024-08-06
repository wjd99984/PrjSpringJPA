package com.prjjpa.service.memberService;


import com.prjjpa.domain.member.Member;
import com.prjjpa.repository.memberRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	//회원 저장 로직
	@Transactional
	public Long MemberSave(Member member) {
		validateDuplicateMember(member);
		memberRepository.save(member);
		return  member.getId();
	}
	
	private void validateDuplicateMember(Member member) {
		List<Member> members = memberRepository.findByName(member.getName());
		
		if(!members.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다");
		}
	}
	
	//회원 조회 로직
	public Member MemberById(Long id) {
	return 	memberRepository.findById(id);
	}
	
	//회원 전체 조회 로직
	public List<Member> MemberAll() {
	return 	memberRepository.findAll();
	}
	
	//화원정보 수정
	@Transactional
	public void  updateMember(Long id ,String name) {
		Member member = memberRepository.findById(id);
		member.setName(name);
	}
	

	
	
}
