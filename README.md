# card-rest-api


#### Description

Add a new Card Rest Api built with Java 11, Spring Boot, Maven, Apache Derby  

The exposed endpoint is:  

**/card**

The following methods are supported:  

POST **/card**  
*create a new card*  

GET **/card/{id}**  
*get card data*  

PUT **/card/{id}**  
*modify card data*  

DELETE **/card/{id}**  
*delete a card*  


A card is associated with a customer. Customer data are not managed. Card table and data contains only a 'customer id'.  

### Running the tests
mvn test  

### Install and Run
Build:  
mvn package -Dmaven.test.skip  

then run with:  
mvn spring-boot:run

or  

java -jar target/card-rest-api-0.0.1-SNAPSHOT.jar

