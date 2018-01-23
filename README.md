Nice Kata, with multiple traps.

- I used JGiven for application layer (User Stories layer) testing. Run mvn verify for the html report, then in /target/jgiven-reports/html
- Unit tests for domain (not completely covered, but important points).
- DDD spirit for the domain design: Good is the only entity, all others are Values Objects (immutables)
- "framework" package contains some usefull stuff, for testing invariants and immutable set.

-------------------------------------

Supermarket Pricing KATA

The problem domain is something seemingly simple: pricing goods at supermarkets.

Some things in supermarkets have simple prices: this can of beans costs $0.65. Other things have more complex prices. For example:

•     three for a dollar (so what’s the price if I buy 4, or 5?)

•     $1.99/pound (so what does 4 ounces cost?)

•     buy two, get one free (so does the third item have a price?)

The exercise is to experiment with a model that is flexible enough to deal with these (and other) pricing schemes, and at the same time are generally usable how do you keep an audit trail of pricing decisions.


-------------------------------------


Test Class: supermarket.application.ShoppingTest

 Empty cart

   Given an empty cart
    When calculate total
    Then result is 0


 Total for goods by weight

   Given an empty cart
    When add 4 POUND pudding at price $Amount(1.99)/POUND
         calculate total
    Then result is 7.96


 Total for goods by weight and by unit

   Given an empty cart
    When add 1 POUND pudding at price $Amount(10)/POUND
     And add 1 POUND pudding at price $Amount(10)/POUND
     And add 2 chocolate at price $2/unit
     And add 2 chocolate at price $2/unit
         calculate total
    Then result is 28.00


 Total for goods by weight with unit conversion

   Given an empty cart
    When add 4 OUNCE pudding at price $Amount(1.99)/POUND
         calculate total
    Then result is 0.50


 Total for goods with discount by lot

   Given an empty cart
     And a discount on good can of beans at price $0.65/unit (3 for $1/unit)
    When add 3 can of beans at price $0.65/unit
     And calculate total
    Then result is 1.00
    When add 1 can of beans at price $0.65/unit
     And calculate total
    Then result is 1.65
    When add 1 can of beans at price $0.65/unit
     And calculate total
    Then result is 2.30


 Total for goods with discount one for free

   Given an empty cart
     And a discount on good can of beans at price $1/unit (buy 2 get one free)
    When add 1 can of beans at price $1/unit
     And calculate total
    Then result is 1.00
    When add 1 can of beans at price $1/unit
     And calculate total
    Then result is 2.00
    When add 1 can of beans at price $1/unit
     And calculate total
    Then result is 2.00
    When add 1 can of beans at price $1/unit
     And calculate total
    Then result is 3.00
    When add 1 can of beans at price $1/unit
     And calculate total
    Then result is 4.00
    When add 1 can of beans at price $1/unit
     And calculate total
    Then result is 4.00


 Total for multiple goods

   Given an empty cart
    When add 1 can of beans at price $0.65/unit
     And add 1 chocolate at price $1/unit
    When calculate total
    Then result is 1.65


 Total for one good

   Given an empty cart
    When add 1 can of beans at price $0.65/unit
     And calculate total
    Then result is 0.65


 Total for two time the same good

   Given an empty cart
    When add 1 can of beans at price $0.65/unit
     And add 2 can of beans at price $0.65/unit
     And calculate total
    Then result is 1.95