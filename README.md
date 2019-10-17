# Nangu test app
## Info
Při vývoji jsem použil lokální postgres DB takže pokud chcete aplikaci spustit je třeba ji vytvořit. V resources lze najít skript na její vytvoření.

## Nice to have - málo času
1. Udělat novej konfig a DB proti které by běželi testy.
2. Udělat v DB index nad textem zprávy, aby se rychleji hledalo pomocí ILIKE.
3. Celá spring securita - udělat role, vlastní filter atd. - spousta času takže jsem udělal jednodušší variantu.
4. Upravit ControllerAdvice, aby zpracovával další vyjímky
5. Pokrýt uplně všechny test casy
6. Použít RabbitMQ, který znám z práce(messaging)
7. Udělat nějaké DTO objekty, abych na api nevracel / nepříjmal to samé co je v aplikaci
8. Udělat celistvý integrační test - udělal jsem si pouze volání v Postmanu a tím to prošel
9. Dodržet HATEOAS u Restu
