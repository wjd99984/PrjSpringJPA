package com.prjjpa.repository.orderRepository;


import com.prjjpa.domain.order.Order;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
	
	private  final EntityManager em;
	
	//주문저장
	public void save(Order order) {
		em.persist(order);
	}
	
	//주문조회
	public Order findById(Long id) {
		return em.find(Order.class, id);
	}
	
	//주문 전체 조회
	public List<Order> findAll() {
		return em.createQuery("select o from Order o", Order.class)
				.getResultList();
	}
}
