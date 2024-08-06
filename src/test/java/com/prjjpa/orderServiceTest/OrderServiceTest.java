package com.prjjpa.orderServiceTest;


import com.prjjpa.domain.address.Address;
import com.prjjpa.domain.item.Book;
import com.prjjpa.domain.item.Item;
import com.prjjpa.domain.member.Member;
import com.prjjpa.domain.order.Order;
import com.prjjpa.domain.order.OrderStatue;
import com.prjjpa.excetion.NotEnoughException;
import com.prjjpa.repository.orderRepository.OrderRepository;
import com.prjjpa.service.itemService.ItemService;
import com.prjjpa.service.orderService.OrderService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class OrderServiceTest {
	
	@Autowired
	private EntityManager em;
	@Autowired
	private  OrderService orderService;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ItemService itemService;
	
	
	@Test
	void 상품추가_상품주문() {
		//given
		Member member = createMember("둥글이");
		Book book = createBook("곰돌이의 모험",10000,15);
		int count = 10;
		//when
		Long orderId = orderService.order(member.getId(), book.getId(), count);
		//then
		Order getOrder = orderRepository.findById(orderId); //상품하나조회
		assertEquals(OrderStatue.ORDER, getOrder.getStatue(), "상품 주문시 상태는 ORDER");
		assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야한다 ");
		assertEquals(10000 * count, getOrder.getTotalPrice(), "주문 가격 *수량");
		assertEquals(5, book.getStockQuantity(),"주문 수량만큼 재고가 줄어야한다 ");
	}
	
	@Test()
	void 상품주문_재공수량초과() {
		//given
		Member member = createMember("끼얏호우 ");
		Book book = createBook("늑대",10000,10);
		int orderCount = 11;
		//when
		orderService.order(member.getId(), book.getId(), orderCount);
		
		//then
		NotEnoughException exception = assertThrows(NotEnoughException.class, ()
				-> orderService.order(member.getId(), book.getId(), orderCount));
		assertEquals("재고부족입니다", exception.getMessage());
	}
	
	@Test
	void 주문취소() {
		//given
		Member member = createMember("끼얏호우");
		Book item= createBook("늑대",10000,10);
		int orderCount = 10;
		//when
		Long orderId=orderService.order(member.getId(), item.getId(), orderCount);
		orderService.cancelOrder(orderId);
		Order getOrder = orderRepository.findById(orderId);
		//then
		assertEquals(OrderStatue.CANCEL, getOrder.getStatue(),"주문 취소시 상태는 CANCEL 이다");
		assertEquals(10, item.getStockQuantity(),"주문이 취소된 상품은 그만큼 재고가 증가 해야함 ");
		
	}
	
	
	
	@Test
	void 상품목록(){
		//given
		Member member = createMember("끼얏호우");
		Book book = createBook("늑대",10000, 10);
		Book book1 = createBook("빨간망토",10000, 10);
		em.persist(book);
		em.persist(book1);
		em.flush();
		//when
		List<Item> item = itemService.findAllItems();
		//then
		assertEquals(2,item.size());
		
	}
	
	
	@Test
	void 주문내역_조회() {
		//given
		Member member = createMember("끼얏호우");
		Book item= createBook("늑대",10000,10);
		Book red = createBook("빨간망토", 10000, 20);
		Book yellow = createBook("도깨비방망이", 10000, 20);
		Book white = createBook("토끼와거북이", 10000, 20);
		em.persist(item);
		em.persist(red);
		em.persist(yellow);
		em.persist(white);
		em.flush();
		//when
		Long orderItem=orderService.order(member.getId(), item.getId(), 3);
		Long ordered=orderService.order(member.getId(), red.getId(), 7);
		Long orderYellow=orderService.order(member.getId(), yellow.getId(), 10);
		Long orderWhite=orderService.order(member.getId(), white.getId(), 15);
		//then
		List<Order> orders = orderService.findAll();
		assertEquals(4,orders.size());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//책
	private Book createBook( String name,  int price, int stockQuantity ) {
		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		em.persist(book);
	
		return book;
	}
	//회원
	private Member createMember(String name) {
		Member member = new Member();
		member.setName(name);
		member.setAddress(new Address("도시","거리","우편번호"));
		em.persist(member);
		
		return member;
	}
	
	
}
