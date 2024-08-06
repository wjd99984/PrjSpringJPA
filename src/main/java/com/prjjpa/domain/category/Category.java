package com.prjjpa.domain.category;

import com.prjjpa.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany
    // 실무에서 안씀
    @JoinTable(name = "category_id",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent" )
    private List<Category> children = new ArrayList<>();

    //연관관계 메서드
    public void addChildCategory(Category child) {
        children.add(child);
        child.setParent(this);
    }
}
