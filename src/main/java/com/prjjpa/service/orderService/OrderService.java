package com.prjjpa.service.orderService;


import com.prjjpa.domain.delivery.Delivery;
import com.prjjpa.domain.item.Item;
import com.prjjpa.domain.member.Member;
import com.prjjpa.domain.order.Order;
import com.prjjpa.domain.oredrItem.orderItem;
import com.prjjpa.repository.itemRepository.ItemRepository;
import com.prjjpa.repository.memberRepository.MemberRepository;
import com.prjjpa.repository.orderRepository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.prjjpa.domain.oredrItem.orderItem.createOrderItem;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	
	//주문 저장 로직
	public Long order(Long memberId, Long itemId,  int count) {
		//각각의 엔티티 조회
		Member member =memberRepository.findById(memberId);
		Item item = itemRepository.findById(itemId);
		//배송정보
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());
		//주문 상품 생성
		orderItem orderItem = createOrderItem(count, item, item.getPrice());
		// 주문 생성
		Order order = Order.createOrder(member, delivery,orderItem);
		
		orderRepository.save(order);
		return order.getId();
	}
	
	//주문 취소 로직
	public void  cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId);
		order.cancel();
	}
	
	//주문 조회 로직
	 public Order findById(Long id) {
		return orderRepository.findById(id);
	 }
	 
	 //주문 전체 조회 로직
	public List<Order> findAll() {
		return orderRepository.findAll();
	}
}


