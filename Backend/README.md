# Backend

This is a RESTful API for e-commerce app built with Java, Spring boot, and Postgres. 
The API allow users to add products to their carts and make orders, and allow vendors to manage products. 
The project includes user authentication, authorization, input validation, and error handling. 

## Features
- <p align="left"><strong> Authentication & Authorization: </strong> Sign up and login. </p>
- <p align="left"><strong> Vendor account management :</strong>  accounts receivable and payable  </p>
- <p align="left"><strong> CRUD Operations: </strong>Full CRUD for products and dealing with cart</p>
- <p align="left"><strong> Validations: </strong>Input validation for all API requests to ensure data integrity.</p>
- <p align="left"><strong> Error Handling</strong> </p>

## Technologies Used
- <p align="left"> <strong> Backend: </strong> Java, Spring boot</p>
- <p align="left"> <strong> Database: </strong> Postgres, Hibernate/JPA</p>
- <p align="left"> <strong>Authentication: </strong> JSON Web Tokens (JWT)</p>



## Run
  To run this project locally, follow these steps:
  
    git clone https://github.com/Mostafamaag/e-commerce-task
    cd e-commerce-task
    mvn clean install
    mvn spring-boot:run or run it from IntelliJ
  
## API Endpoints:
  - **POST /auth/user/register:** Register a new user
  - **POST /auth/user/login:** Login with credentials and receive JWT token
  - **POST /auth/vendor/register:** Register a new ventor
  - **POST /auth/ventor/login:** Login with credentials and receive JWT token
  - **POST /shop:** Get all products
  - **GET /products:** Get vendor products
  - **POST /products:** Upload new product
  - **PUT /products/productId:** Update product
  - **GET /cart:** Get cart
  - **POST /cart/productId:** Add product to cart
  - **DELETE /cart/productId:** Delete product frpm cart
  - **POST /cart/checkout:** order cart
  - **GET /orders:** Get user's order
  - **GET /receivables:** get account receivable
  - **GET /payables:** get account payable
  - **POST /payables/pay/payableId:** pay for vendor

 

