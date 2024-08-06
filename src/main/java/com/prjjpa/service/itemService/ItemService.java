package com.prjjpa.service.itemService;


import com.prjjpa.domain.item.Item;
import com.prjjpa.repository.itemRepository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	//상품 저장 로직
	@Transactional
	public void ItemSave(Item item) {
		itemRepository.save(item);
	}
	
	//상품 조회 로직
	public Item findItemById(Long id) {
		return itemRepository.findById(id);
	}
	
	//상품 전체 조회 로직
	public List<Item> findAllItems() {
		return itemRepository.findAll();
	}
	
}
