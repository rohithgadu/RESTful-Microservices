# RESTful-Microservices

• A RESTful Microservices, built using Spring Boot which has Product-Service, Order-Service and Inventory-Service.

• Used Eureka server for service discovery and MongoDB and MySql as the database.

• Used KeyCloak for identity and access management, and Spring Cloud Sleuth and Zipkin for distributed tracing.


## Tech Stack

- `Backend Framework:` `Spring Boot`
- `Databases:` `MongoDb, MySQL`
- `Service Discovery:` `Eureka Server, Eureka Client`
- `Authorization Server:` `Keycloak`
- `Fault Tolerance:` `Resilience4j`
- `Distributed Tracing:` `Zipkin`


## System Design

![image](https://user-images.githubusercontent.com/84178107/200546497-65db0739-0c8e-4526-8b57-e4bc57f51a67.png)

## Installation

Keycloak Docker Image

```bash
  docker run -p 8180:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:20.0.1 start-dev
```

Zipkin Docker Image

```bash
  docker run -d -p 9411:9411 openzipkin/zipkin
```

## API Reference 

#### AUTHORIZATION OAuth 2.0

Configure and get New Access Token Using Postman and KeyCloak Client


|    Configuration     |  |  
| -------- | ------- |
|   `Token Name`      | `token` |
|   `Grant Type`      | `Client Credentials` |
|   `Access Token URL`      | `http://localhost:8180/realms/spring-boot-microservices-realm/protocol/openid-connect/token` |
|   `Client ID`      | `spring-cloud-client` |
|   `Client Secret`      | `Jxli46NJXCDWHcDarvR8Ul4BuCc1dBYd` |
|   `Scope`      | `openid` |
|   `Client Authentication`      | `Send as Basic Auth Header` |


### API Endpoints


#### Get Available Products
```javascript
  GET http://localhost:8080/api/product
```
#### Example Response
```json 
[
  {
    "id": "6364ea69906ea11f14e5b2f3",
    "name": "Iphone 13",
    "description": "Iphone 13",
    "price": 120000
  },
  {
    "id": "6364ea94906ea11f14e5b2f4",
    "name": "Legion 5",
    "description": "Legion 5",
    "price": 80000
  },
  {
    "id": "6368c04c3e806727453333eb",
    "name": "Hp Pavilion",
    "description": "Hp Pavilion",
    "price": 50000
  },
  {
    "id": "636a489d65f8da7270084425",
    "name": "Dell Vostro",
    "description": "Dell Vostro",
    "price": 60000
  }
]
```

#### Add Product
```javascript
  Post http://localhost:8080/api/product
```
```json 
{
    "name": "Dell Vostro",
    "description": "Dell Vostro Laptop",
    "price": 60000
}
```
#### Example Response
```
201 Created
```

#### Place a order


```javascript
  Post http://localhost:8080/api/order
```

```json 
{
    "orderItemsDtoList":[
        {
            "skuCode":"iphone_13",
            "price":120000,
            "quantity":1
        }
    ]
}
```
#### Example Response

```
Order Placed Successfully
```
#### Update Inventory
```javascript
  Post http://localhost:8080/api/inventory
```

```json 
{
    "skuCode":"dell_vostro",
    "quantity":10
}
```
#### Example Response
```
Inventory Successfully Updated
```
