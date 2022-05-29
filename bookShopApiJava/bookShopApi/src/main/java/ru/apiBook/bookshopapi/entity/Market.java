package ru.apiBook.bookshopapi.entity;

import java.util.List;

public class Market {
    private List<Books> products;

    public Market(List<Books> products) {
        this.products = products;
    }

    public List<Books> getProducts() {
        return products;
    }

    public void setProducts(List<Books> products) {
        this.products = products;
    }
}
