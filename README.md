## City List Application

Create an enterprise-grade "city list" application (it will stay there for years, will be extended and
maintained) which allows the user to do the following:

- browse through the paginated list of cities with the corresponding photos
- search by the name
- edit the city (both name and photo)

Notes
- initial list of cities should be populated using the attached cities.csv file
- city addition, deletion and sorting are not in the scope of this task

Technical clarification
- Spring Boot
- any build system
-  any database
- any frontend stack
- Usage of Spring Data REST is prohibited

### How to run the project
1. Checkout the project
2. Build (mvn)
3. Start the app
4. Use the Postman collection from resources/postman to test the APIs
   GET http://localhost:8080/cities (Query params: page, size, name)
   PUT http://localhost:8080/cities/{cityId}