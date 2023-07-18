package com.anabada.anabada.item.service;

import com.anabada.anabada.item.model.response.ItemFindResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService service;

    @Test
    void name() {
        long id = 10;
        ItemFindResponse response = service.getItem(id);

    }

}