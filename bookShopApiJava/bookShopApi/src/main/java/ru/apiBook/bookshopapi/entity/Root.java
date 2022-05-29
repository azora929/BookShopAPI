package ru.apiBook.bookshopapi.entity;

import java.util.ArrayList;
import java.util.List;

public class Root {
    private Account account;
    private List<Books> books;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }

    public Books getBookByIdRoot(int id){
        for(Books book: books) {
            if(book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public boolean addNewBook(Books book){
        for(Books book1:books){
            if(book1.getId() == book.getId()){
                return false;
            }
        }
        books.add(book);
        return true;
    }

    public boolean deleteBookFromRoot(int id){
        for(Books book:books){
            if(book.getId() == id){
                books.remove(id);
                return true;
            }
        }
        return false;
    }

    public List<Books> setBooksWithoutAmount0(){
        List<Books> booksList = new ArrayList<Books>();
        for(Books book:books){
            if(book.getAmount() > 0){
                booksList.add(book);
            }
        }
        return booksList;
    }
}
