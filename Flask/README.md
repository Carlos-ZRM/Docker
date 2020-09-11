# Mongo 
``` bash
docker run -d --name some-mongo -p 27017:27017 \
    -e MONGO_INITDB_ROOT_USERNAME=mongoadmin \
    -e MONGO_INITDB_ROOT_PASSWORD=secret \
    mongo
```
# Flask 
```
docker run -dt --name some-flask -p 5000:5000 \
    -e MONGO_INITDB_ROOT_USERNAME=mongoadmin \
    -e MONGO_INITDB_ROOT_PASSWORD=secret \
    -e MONGODB_HOST=some-mongo- \
    --link some-mongo-:MONGODB_HOST \
    -v $(pwd):/python:z \
    --hostname  some-flask \
    flask-rest
```

# GET 
curl -i localhost:5000/mongodb 

# POST
curl --header "Content-Type: application/json"   --request POST   --data '{"First_Name": "Pedro","Age": 50}' -i   http://localhost:5000/registro 


#GET todos los items 
curl http://localhost:5000/todos

# GET un item 
curl http://localhost:5000/todos/todo3

# DELETE un item 
curl http://localhost:5000/todos/todo2 -X DELETE -v

curl --header "Content-Type: application/json"   --request DELETE   --data '{"database": "IshmeetDB","collection": "people","Filtro": {"First_Name": "Jhaaon"}}' -i   http://localhost:5000/mongodb 

curl --header "Content-Type: application/json"   --request DELETE   --data '{"Filtro": {"First_Name": "Jhon"}}' -i   http://localhost:5000/mongodb 

# POST 
curl http://localhost:5000/todos -d "task=sNuevo texto" -X POST -v

curl --header "Content-Type: application/json"   --request POST   --data '{"Pedro Paramo": "Jhaaon","Age": 50}' -i   http://localhost:5000/mongodb 

# PUT 

curl http://localhost:5000/todos/todo3 -d "task=something different" -X PUT -v

https://github.com/ishmeet1995/PublicProjects

curl --header "Content-Type: application/json"   --request PUT   --data '{"Filtro": {"First_Name": "Jhon"},"Datos": {"Last_Name": "Bindra","Age": 26}}' -i   http://localhost:5000/registro 