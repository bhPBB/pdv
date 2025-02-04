package com.brunobomfim.pdv.services;

import com.brunobomfim.pdv.models.Item;
import com.brunobomfim.pdv.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepository ir;

    public Item cadastrarItem(Item item) {
        return ir.save(item);
    }
}
