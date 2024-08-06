package com.prjjpa.domain.delivery;

import com.prjjpa.domain.address.Address;
import com.prjjpa.domain.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
    private Order order;


    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //ORDINAL  중간에 잘못들어가면 ㅈㅈ 쳐야되서 무조건싱글로
    private DeliveryStatus status; //Ready ,Comp

}
