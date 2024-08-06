package com.prjjpa.domain.item;

import com.prjjpa.domain.category.Category;
import com.prjjpa.excetion.NotEnoughException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // 재고수 증가하는 로직
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    // 재고수 감소하는 로직
    public void removeStock(int quantity) {
      int restStock = this.stockQuantity -= quantity;
        if (restStock < 0) {
           throw  new NotEnoughException("재고부족입니다");
        }
        this.stockQuantity = restStock;
    }

}
