package com.brunobomfim.pdv.controllers;

import com.brunobomfim.pdv.models.Item;
import com.brunobomfim.pdv.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itens")
public class ItemController {

    @Autowired
    private ItemService is;

    @PostMapping
    public Item cadastrarItem(@RequestBody Item item) {
        return is.cadastrarItem(item);
    }
}
