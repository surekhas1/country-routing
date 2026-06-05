# country-routing
Country Routing Service - Release Notes
Spring Boot REST API that calculates land route between two countries using BFS on real-world border data.

Features

•	Finds shortest land route between countries 

•	Uses CCA3 country codes 

•	Data source downloaded from:

       https://raw.githubusercontent.com/mledoze/countries/master/countries.json and stored locally under:  src/main/resources/countries.json
   
•	The application loads the dataset during startup and builds an in-memory graph for route calculations.

•	REST endpoint:

      GET /routing/{origin}/{destination}
      
•	Returns HTTP 400 if no route exists

Tech Stack

•	Java 17+ 

•	Spring Boot 

•	Maven

Build Instructions

•	Open terminal in project root (where pom.xml exists):

    Run: mvn clean install
    This will generate JAR inside: target/country-routing-0.0.1.jar
    
•	Run Application: 

    Using Jar: java -jar target/country-routing-0.0.1-SNAPSHOT.jar
    Using Maven: mvn spring-boot:run
    
•	Application runs at http://localhost:8080

API Testing (Postman)

•	Request: GET /routing/CZE/ITA

•	Example: http://localhost:8080/routing/CZE/ITA

•	Response: Expected

   </> JSON
   
    {
      "route": ["CZE", "AUT", "ITA"]
    }



