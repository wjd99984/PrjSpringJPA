package com.prjjpa.domain.order;


import com.prjjpa.domain.delivery.Delivery;
import com.prjjpa.domain.delivery.DeliveryStatus;
import com.prjjpa.domain.member.Member;
import com.prjjpa.domain.oredrItem.orderItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // sql 작성 할때랑 같음 팀플 백엔드보셈
    private Member member;


    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<orderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    private OrderStatue statue;



    //연관 관계 메서드
    // 양방향 일떄

   /* public void setMember(Member member) {
        this.member = member;
    }*/

    public void addOrderItem(orderItem orderItem) {
        this.orderItems.add(orderItem);
    }
    public void setDelivery (Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    //***생성 매서드 ****

    public static Order createOrder(Member member,Delivery delivery,orderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (orderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatue(OrderStatue.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // 비즈니스 로직
        //주문 취소
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송이 완료된 상품은 취소가 불가능 ");
        }
        this.setStatue(OrderStatue.CANCEL);
        for (orderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // 조회로직
    // 전체 주문가격 조회
    public int getTotalPrice() {
        int totalPrice = 0;
        for (orderItem orderItem : orderItems) {
           totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }



}
