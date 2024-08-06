package com.prjjpa.memberServiceTest;



import com.prjjpa.domain.address.Address;
import com.prjjpa.domain.member.Member;
import com.prjjpa.repository.memberRepository.MemberRepository;
import com.prjjpa.service.memberService.MemberService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Transactional
@SpringBootTest
public class MemberServiceTest {
	@Autowired
	EntityManager em;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberRepository memberRepository;
	
	@Test
	void 회원가입(){
		//given
		Member member = new Member();
		member.setName("동그라미");
		member.setAddress( new Address("도시","거리","우편번호"));
		//when
		Long saveId =memberService.MemberSave(member);
		//then
		assertEquals(member, memberRepository.findById(saveId));
	}
	
	@Test
	void  중복회원_검증(){
		//given
		Member member = new Member();
		member.setName("동그라미");
		member.setAddress( new Address("도시","거리","우편번호"));
		
		Member member1 = new Member();
		member1.setName("동그라미");
		member1.setAddress( new Address("도시","거리","우편번호"));
		//when
		memberService.MemberSave(member);
		memberService.MemberSave(member1);
		//then
		assertThrows(IllegalStateException.class, () -> memberService.MemberSave(member1));
		
	}
	
	@Test
	void 회원전체_조회() {
		//given
		Member member = new Member();
		member.setName("동그라미0");
		member.setAddress( new Address("도시","거리","우편번호"));
		em.persist(member);
		Member member1 = new Member();
		member1.setName("동그라미");
		member1.setAddress( new Address("도시","거리","우편번호"));
		em.persist(member1);
		Member member2 = new Member();
		member2.setName("동그라미2");
		member2.setAddress( new Address("도시","거리","우편번호"));
		em.persist(member2);
		em.flush();
		//when
		List<Member> members = memberService.MemberAll();
		//then
		assertEquals(3 , members.size());
	}
	
}
