# Silverbars Marketplace
Coding exercise solution by _Tomasz Przybysz_

## Intro

The solution was coded:

 - in a _Test Driven_ manner
 - minding _Clean Code_
 - adapting good OOP principles like _DRY_ and _SOLID_
 - testing behaviour not implementation  

## Assumptions

 - Price and quantity units and precision standardization is not within responsibility of the _Live Board_
 - Orders summary position doesn't need to carry info about type or source order quantities
 - Blank user id is not not valid
 - Negative number or zero is neither a valid quantity nor a valid price

## Decisions 

### Responsibility split

...between the stateful board and the stateless summarise logic.
Thanks to it the logic can be managed and tested in separation (see: [`LiveOrderBoardTest`](src/test/groovy/com/tprzyb/silverbars/LiveOrderBoardTest.groovy) and [`OrderSummaryTest`](src/test/groovy/com/tprzyb/silverbars/OrderSummaryTest.groovy)).
That's a good way of avoiding the need for stubbing or wiring any service/persistence layer just to test domain logic.
In general it's better not to mix separate concerns anyway.

### Value objects

All stateless data classes are immutable and have a correct _equals_ and _hashCode_ contract implemented.
It not only helps encapsulation and preventing bugs but as well allows testing by comparing the whole objects without increasing the noise at tests with irrelevant details.

## Notes
Although clean code and good OOP principles always remain, there are different tools and styles preferred at different companies and teams. Should any of the choices in the solution be not to your liking, please keep it mind one needs to know the team's preferences first to follow them. I hope I'll have a chance to learn yours soon.

Enjoy the code!