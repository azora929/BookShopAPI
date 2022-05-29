package ru.apiBook.bookshopapi.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.apiBook.bookshopapi.entity.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private ParserJsonFile parser = null;
    private Root root = null;

    private Account account = null;
    private Market market = null;

    public MessageController(){
        parser = new ParserJsonFile();
        root = parser.parseJson();
        account = root.getAccount();
        market = new Market(root.setBooksWithoutAmount0());
        logger.info("Message Controller start");
    }

    @GetMapping("/")
    public ResponseEntity<String> messageDefault(){
        logger.debug("Default Page");
        return ResponseEntity.ok("BooksShopAPI");
    }

    @GetMapping("/account")
    public ResponseEntity<Account> messageAccount(){
        logger.debug("Account Page");
        return ResponseEntity.ok(account);
    }

    @GetMapping("/market")
    public ResponseEntity<Market> messageMarket(){
        logger.debug("Market Page");
        return ResponseEntity.ok(market);
    }

    @GetMapping("/allBooks")
    public ResponseEntity<List<Books>> messageAllBooks(){
        logger.debug("AllBooks Page");
        return ResponseEntity.ok(root.getBooks());
    }

    @PostMapping(path = "/market/deal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> messageMarketDeal(@RequestBody Deal newDeal){
        logger.debug("market/deal");
        logger.debug("Acc:" + account + " " + "deal:" + newDeal);
        return marketDeal(account, newDeal);
    }

    @PostMapping(path = "/addNewBook", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Books>> messageAddNewBook(@RequestBody Books book){
        if(root.addNewBook(book)){
            logger.debug("/addNewBook");
            logger.debug("Book was added " + book);
            parser.toJson(root);
            root.setBooksWithoutAmount0();
            return new ResponseEntity<>(root.getBooks(), HttpStatus.OK);
        }
        else {
            logger.error("Incorrect data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/deleteBook", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Books>> messageDeleteBook(@RequestBody Books book){
        if(root.deleteBookFromRoot(book.getId())){
            logger.debug("/deleteBook");
            logger.debug("Book was deleted");
            parser.toJson(root);
            root.setBooksWithoutAmount0();
            return new ResponseEntity<>(root.getBooks(), HttpStatus.OK);
        }
        else {
            logger.error("Incorrect data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/account/balance", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> messageTopUpBalance(@RequestBody Account account){
        if(root.getAccount().topUpBalance(account.getMoney())){
            logger.debug("Balance was top up");
            logger.debug("Money {}. Account {}.", account.getMoney(), root.getAccount());
            parser.toJson(root);
            return new ResponseEntity<>(root.getAccount(), HttpStatus.OK);
        }
        logger.debug("Incorrect data");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Account> marketDeal(Account account, Deal deal){
        if(account.getPurchaseHistory() == null && account.getMoney() >= root.getBookByIdRoot(deal.getId()).getPrice() * deal.getAmount() && root.getBookByIdRoot(deal.getId()).getAmount() >= deal.getAmount() && root.getBookByIdRoot(deal.getId()) != null){
            root.getAccount().setPurchaseHistory(new ArrayList<Books>() {{add(root.getBookByIdRoot(deal.getId())); get(0).setAmount(deal.getAmount());}});
            setChanges(deal);
            logger.debug("User bought the first book {} in quantities {}", root.getBookByIdRoot(deal.getId()).getName(),deal.getAmount());
            parser.toJson(root);
            return new ResponseEntity<>(root.getAccount(), HttpStatus.OK);
        }
        if (account.getMoney() >= root.getBookByIdRoot(deal.getId()).getPrice() * deal.getAmount() && root.getBookByIdRoot(deal.getId()).getAmount() >= deal.getAmount() && root.getBookByIdRoot(deal.getId()) != null) {
            setPurchaseHistory(deal);
            setChanges(deal);
            logger.debug("User bought the first book {} in quantities {}", root.getBookByIdRoot(deal.getId()).getName(),deal.getAmount());
            parser.toJson(root);
            return new ResponseEntity<>(root.getAccount(), HttpStatus.OK);
        }
        logger.error("Not enough money/books or incorrect data");
        logger.debug("User money {} , Books cost {}", account.getMoney(), root.getBookByIdRoot(deal.getId()).getPrice() * deal.getAmount());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private void setChanges(Deal deal){
        logger.debug("setChanges");
        account.setMoney(account.getMoney() - root.getBookByIdRoot(deal.getId()).getPrice() * deal.getAmount());
        root.getBookByIdRoot(deal.getId()).setAmount(root.getBookByIdRoot(deal.getId()).getAmount() - deal.getAmount());
        root.setAccount(account);
    }

    private void setPurchaseHistory(Deal deal){
        logger.debug("setPurchaseHistory");
        if(account.checkIdBook(deal.getId())){
            account.getBookByIdAccount(deal.getId()).setAmount(account.getBookByIdAccount(deal.getId()).getAmount() + deal.getAmount());
        }
        else{
            Books book = makeNewBook(deal);
            account.getPurchaseHistory().add(book);
        }
    }

    private Books makeNewBook(Deal deal){
        logger.debug("NewBook");
        Books newBook = new Books();
        newBook.setId(root.getBookByIdRoot(deal.getId()).getId());
        newBook.setAuthor(root.getBookByIdRoot(deal.getId()).getAuthor());
        newBook.setName(root.getBookByIdRoot(deal.getId()).getName());
        newBook.setPrice(root.getBookByIdRoot(deal.getId()).getPrice());
        newBook.setAmount(deal.getAmount());
        return newBook;
    }
}
