# BookShopAPI
Программа написана с помощью Gradle и Spring Framework и включает в себя следующие запросы:
-http://localhost:8080 - это url аддрес страницы по-умолчанию;
-http://localhost:8080/account - это url аддрес страницы с данными аккаунта;
-http://localhost:8080/market - это url аддрес страницы с книгами имеющимися в магазине;
-http://localhost:8080/allBooks - это url аддрес страницы со всеми книгами даже если их кол-во 0;
-http://localhost:8080/market/deal - это url аддрес покупки книги из магазина(выполняется запросом POST);
-http://localhost:8080/addNewBook - это url аддрес добавления новой книги(выполяется запросом POST);
-http://localhost:8080/deleteBook - это url аддрес удаления книги(выполяется запросом DELETE);
-http://localhost:8080/account/balance - это url аддрес пополнения баланса на аккаунте(выполяется запросом PUT).
Запросы должны отправляться Api только в формат: JSON.

Программа также включает в себя несколько unitTestCase, файл jar(./build/libs/bookShopApi-0.0.1-SNAPSHOT-plain.jar)
который обновляется по мере дополнения библиотек и.т.п, файл log в формате json(./bookShopApiJava/log.json).
