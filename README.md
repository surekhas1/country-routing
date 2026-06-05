# country-routing
The country dataset provided in the assignment has been downloaded from:

https://raw.githubusercontent.com/mledoze/countries/master/countries.json

and stored locally under:

src/main/resources/countries.json

The application loads the dataset during startup and builds an in-memory graph for route calculations.

#Build Process:
mvn clean package

Run:
java -jar target/country-routing-0.0.1.jar

Test: Using Postman/Browser
GET http://localhost:8080/routing/CZE/ITA

Expected:
{
  "route": ["CZE", "AUT", "ITA"]
}
