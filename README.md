# wealthcdio-apimanagement
Banking Transaction Processor Coding Kata setup using Spring Boot, Gradle, PostgreSQL, and targeting JDK 25.

## Design Highlights
- **Domain Driven Assertions:** Account encapsulation guarantees that operations like `credit()` and `debit()` manage invariants (such as preventing negative transfers or overdrafts).
- **Concurrency Protection:** Optimistic locking (`@Version`) is applied to the `Account` entity to maintain ledger consistency under multi-threaded request processing.
- **TDD Setup:** Accompanied by mock verification test structures highlighting expected business constraints.