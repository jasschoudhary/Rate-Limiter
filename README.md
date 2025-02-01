# rate-limiting-example
Demo project for Spring Boot Rate Limiting 

Implemented Rate Limiter Using  :

1. Token bucket 
2. Fixed Window counter

run : 
mvn spring-boot:run

Test in terminal : 
for i in {1..10}; do   curl -H "X-API-Key: test-api-key" http://localhost:8080/api/rate-limiting/resource; done

O/p : 

7 request are served but after that gets 429 . 