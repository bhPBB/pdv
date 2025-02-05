package com.brunobomfim.pdv.repositories;

import com.brunobomfim.pdv.models.Item;
import com.brunobomfim.pdv.models.ItemPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, ItemPk> {
}
