package com.anabada.anabada.item.repository;

import com.anabada.anabada.item.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {



}
