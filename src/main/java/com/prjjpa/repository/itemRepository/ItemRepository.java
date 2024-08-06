package com.prjjpa.repository.itemRepository;


import com.prjjpa.domain.item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
	
	private final EntityManager em;
	
	//상품 저장
	public void save(Item item) {
		em.persist(item);
	}
	
	//상품조회
	public Item findById(Long id) {
		return em.find(Item.class, id);
	}
	
	//상품 전체조회
	public List<Item> findAll() {
		return em.createQuery("select i from Item i", Item.class)
				.getResultList();
	}
	
}
