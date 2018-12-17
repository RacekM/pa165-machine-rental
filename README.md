# pa165-machine-rental

#### Running the project

1. Clone the repository 
```
git clone https://github.com/RacekM/pa165-machine-rental
```
2. Go into project folder
```
cd pa165-machine-rental
```
3. Run mvn clean install in root of the project
```
mvn clean install
```
4. Go to the rental-angular direcotory
```
cd rental-angular
```
5. Run mvn cargo:run to start the web server
```
mvn cargo:run
```
6. Visit http://localhost:8080/pa165
Login with credentials if needed:
As admin or user:
```
username = admin, password = admin
username = user1, password = user1
username = user2, password = user2
```

# cURL commands for entity Machine

#### Machine.getAll:
```
curl -i -X GET http://admin:admin@localhost:8080/pa165/rest/machines/
```
#### Machine.get:
```
curl -i -X GET http://admin:admin@localhost:8080/pa165/rest/machines/{id}
```
#### Machine.create: 
triple quotes due to problems on windows
```
curl -i -X POST -H "Content-Type: application/json" --data "{"""name""":"""{machine_name_from_user}"""}" http://admin:admin@localhost:8080/pa165/rest/machines/create
```
#### Machine.delete: 
```
curl -i -X DELETE http://admin:admin@localhost:8080/pa165/rest/machines/{id}
```
