# pa165-machine-rental

cURL commands for entity Machine

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
