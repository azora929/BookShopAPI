# BookShopAPI
Программа написана с помощью Spring Framework и включает в себя следующие запросы:
-http://localhost:8080 - это страница по-умолчанию;
-http://localhost:8080/account - это страница с данными аккаунта;
-http://localhost:8080/market - это страница с книгами имеющимися в магазине;
-http://localhost:8080/allBooks - это страница со всеми книгами даже если их нету в наличии;
-http://localhost:8080/market/deal - это url аддрес покупки книги из магазина(выполняется запросом POST);
-http://localhost:8080/addNewBook - это url аддрес добавления новой книги(выполяется запросом POST);
-http://localhost:8080/deleteBook - это url аддрес удаления книги(выполяется запросом DELETE);
-http://localhost:8080/account/balance - это url аддрес пополнения баланса на аккаунте(выполяется запросом PUT).
Запросы должны отправляться Api только в формат: JSON.

Программа также включает в себя несколько unitTestCase, файл jar(./build/libs/bookShopApi-0.0.1-SNAPSHOT-plain.jar)
который обновляется по мере дополнения библиотек и.т.п, файл log в формате json(./bookShopApiJava/log.json).
