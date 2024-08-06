package com.prjjpa.repository.memberRepository;

import com.prjjpa.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class MemberRepository {
	@PersistenceContext
	private EntityManager em;
	
	//회원가입
	public void save(Member member) {
		em.persist(member);
	}
	
	//회원아이디 하나 찾기
	public Member findById(Long id) {
		return em.find(Member.class, id);
	}
	
	//전체 회원 조회
	public List<Member> findAll() {
		return 	em.createQuery("select m from Member m", Member.class)
				.getResultList();
	}
	
	//이름으로
	public  List<Member> findByName(String name) {
		return em.createQuery("select m from Member m where m.name = :name", Member.class)
				.setParameter("name", name)
				.getResultList();
	}
	
}
