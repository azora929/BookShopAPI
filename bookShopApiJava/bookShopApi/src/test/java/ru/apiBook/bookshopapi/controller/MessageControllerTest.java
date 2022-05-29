package ru.apiBook.bookshopapi.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.apiBook.bookshopapi.entity.Account;
import ru.apiBook.bookshopapi.entity.Books;
import ru.apiBook.bookshopapi.entity.Root;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MessageController.class)
class MessageControllerTest {

    @MockBean
    private Root root;

    @Autowired
    private MockMvc mvc;

    @Test
    void messageMarket() throws Exception {
        when(root.getBooks()).thenReturn(Collections.singletonList(new Books() {{setId(6); setName("Сказки Востока");}}));
        this.mvc
                .perform(MockMvcRequestBuilders
                        .get("/market")
                        .header("id", 6))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)));
    }

    @Test
    void messageAccount() throws Exception {
        when(root.getAccount()).thenReturn((new Account(2000.0, null)));
        this.mvc
                .perform(MockMvcRequestBuilders
                        .get("/account")
                        .header("balance", 2000.0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)));
    }

    @Test
    void messageMarketDeal() throws Exception {
        this.mvc
                .perform(MockMvcRequestBuilders
                        .post("/market/deal")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"id\":\"0\", \"amount\":\"1\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}