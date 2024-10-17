# CSCI318 Assignment 3: Microservice Application of Ecommerce Platform - Group 22

## Step 1: Setting Up Apache Kafka
- Download the Kafka binary package
- Download a Kafka binary package, (`kafka_2.13-3.7.0.tgz`), from the Apache Kafka downloads page https://kafka.apache.org/downloads and once dowloaded unzip it.

## Step 2: Open terminal within the folder where code is located
  - Right click into the folder with the code and Open in terminal
  - You will need two terminals
  - In the Kafka terminals enter the following commands (each in a separate Terminal session).

## Step 3: Run the Application
- Once Apache Kafka is running, its time to run the SpringBoot Application:
    - You can run the SpringBoot application using your IDE or via the command line.
- Using IDE:
    - **In IntelliJ IDEA, right-click on the SpringApplication class (usually named SpringApplication.java) and select Run (the green triangle).**
    - Run each micro service separately and individually 

## Step 4: Enter the following commands for both MacOs and Windows, and expect the attached results:

  ### Use Case 1: Create Customer

  Code for MacOs:
```bash
curl -X POST http://localhost:8071/customers \
-H "Content-Type: application/json" \
-d '{
    "name": "John Doe",
    "email": "johndoe@example.com",
    "address": "123 Main St",
    "password": "securePassword123",
    "cardDetails": {
        "cardNumber": "1234567812345678",
        "cardHolderName": "John Doe",
        "expiryDate": "12/25",
        "cvv": "123"
    }
}'
```

  Code for Windows:
```shell
curl -X POST http://localhost:8071/customers ^
-H "Content-Type: application/json" ^
-d "{ \"name\": \"John Doe\", \"email\": \"johndoe@example.com\", \"address\": \"123 Main St\", \"password\": \"securePassword123\", \"cardDetails\": { \"cardNumber\": \"1234567812345678\", \"cardHolderName\": \"John Doe\", \"expiryDate\": \"12/25\", \"cvv\": \"123\" } }"
```

  Result: 
  ```json
{"id":3,"name":"John Doe","email":"johndoe@example.com","address":"123 Main St"}
```

  ### Use Case 2: Create Vendor

  Code for MacOs:
```bash
curl -X POST http://localhost:8075/vendors \
-H "Content-Type: application/json" \
-d '{
    "name": "Vendor 1",
    "address": "123 Vendor Street"
}'
```

  Code for Windows:
```shell
curl -X POST http://localhost:8075/vendors ^
-H "Content-Type: application/json" ^
-d "{ \"name\": \"Vendor 1\", \"address\": \"123 Vendor Street\" }"
```

  Result: 
  ```json
{"id":3,"name":"Vendor 1","address":"123 Vendor Street"}%
```

  ### Use Case 3: Create Product

  Code for MacOs:
```bash
curl -X POST "http://localhost:8075/products?vendorId=3" \
-H "Content-Type: application/json" \
-d '{
    "name": "Laptop",
    "price": 1200.00,
    "inStock": 50
}'

```

  Code for Windows:
```shell
curl -X POST "http://localhost:8075/products?vendorId=3" ^
-H "Content-Type: application/json" ^
-d "{ \"name\": \"Laptop\", \"price\": 1200.00, \"inStock\": 50 }"
```

  Result: 
  ```json
{"id":5,"name":"Laptop","price":1200.0,"inStock":50,"vendor":{"id":3,"name":"Vendor 1","address":"123 Vendor Street"}}% 
```

  ### Use case 4: Add product to order (data transfer)

  Code for MacOs:
```bash
curl -X POST "http://localhost:8072/orders/add/products?customerId=3&productId=2" \
-H "Content-Type: application/json"
```

  Code for Windows:
```shell
curl -X POST "http://localhost:8072/orders/add/products?customerId=3&productId=2" ^
-H "Content-Type: application/json"
```

  Result: 
  ```json
{"id":2,"customerId":3,"productIds":[2],"totalAmount":800.0,"creationDate":"2024-10-07T22:21:09.191266","message":"Successfully add product!","orderStatus":"PENDING"}%     
```

  ### Use Case 5: Create completed order and shipment by pressing the checkout button (data transfer)

  Code for MacOs:
```bash
curl -X POST "http://localhost:8072/orders/checkout/customers/3" \
-H "Content-Type: application/json"
```

  Code for Windows:
```shell
curl -X POST "http://localhost:8072/orders/checkout/customers/3" ^
-H "Content-Type: application/json"
```

  Result: 
  ```json
"Checkout successfully"                                                     
```

  ### Use Case 6: Confirm shipment (data transfer)

  Code for MacOs:
```bash
curl -X POST "http://localhost:8074/shipments/2/confirm?confirm=true" \
-H "Content-Type: application/json"
```

  Code for Windows:
```shell
curl -X POST "http://localhost:8074/shipments/2/confirm?confirm=true" ^
-H "Content-Type: application/json"
```

  Result: 
  ```json
"Shipment confirmation processed successfully"
```

  ### Use Case 7: View Payments by Customer ID (data transfer)

  Code for MacOs:
```bash
curl -X GET "http://localhost:8073/payments/customers/3" \
-H "Content-Type: application/json"
```

  Code for Windows:
```shell
curl -X GET "http://localhost:8073/payments/customers/3" ^
-H "Content-Type: application/json"
```

  Result: 
  ```json
[{"paymentId":3,"customerId":3,"orderId":2,"amount":800.0,"cardNumber":"1234567812345678","paymentDate":"2024-10-07T22:25:19.995967"}]%  
```

  ### Use Case 8: Cancel Order (data transfer)

  Code for MacOs:
```bash
curl -X POST "http://localhost:8074/shipments/2/confirm?confirm=false" \
-H "Content-Type: application/json"
```

  Code for Windows:
```shell
curl -X POST "http://localhost:8074/shipments/2/confirm?confirm=false" ^
-H "Content-Type: application/json"
```

  Result: 
  ```json
"Order has been canceled"
```

  ### Use Case 9: Retrieve all products by vendor ID

  Code for MacOs:
```bash
curl -X GET "http://localhost:8075/products/vendor/1" \
-H "Content-Type: application/json"
```

  Code for Windows:
```shell
curl -X GET "http://localhost:8075/products/vendor/1" ^
-H "Content-Type: application/json"
```

  Result: 
  ```json
[{"id":1,"name":"Laptop","price":1200.0,"inStock":49,"vendor":{"id":1,"name":"Tech Supplies Co.","address":"123 Tech Ave, Silicon Valley, CA"}},{"id":2,"name":"Smartphone","price":800.0,"inStock":29,"vendor":{"id":1,"name":"Tech Supplies Co.","address":"123 Tech Ave, Silicon Valley, CA"}}]
```

  ### Use Case 10: Retrieve all orders by customer ID (data transfer)

  Code for MacOs:
```bash
curl -X GET "http://localhost:8072/orders/customers/3" \
-H "Content-Type: application/json"
```

  Code for Windows:
```shell
curl -X GET "http://localhost:8072/orders/customers/3" ^
-H "Content-Type: application/json"
```

  Result: 
  ```json
[{"id":2,"customerId":3,"productIds":[2],"totalAmount":800.0,"creationDate":"2024-10-07T22:21:09.191266","message":"Successfully add product!","orderStatus":"COMPLETED"}]%                                                                     
```

  ### Use Case 11: Retrieve all shipments by customer ID (data transfer)

  Code for MacOs:
```bash
curl -X GET "http://localhost:8074/shipments/customers/3" \
-H "Content-Type: application/json"
```

  Code for Windows:
```shell
curl -X GET "http://localhost:8074/shipments/customers/3" ^
-H "Content-Type: application/json"
```

  Result: 
  ```json
[{"id":2,"orderId":2,"customerId":3}]
```

  ### Use Case 12: Retrieve all receipts by customer ID (data transfer)

  Code for MacOs:
```bash
curl -X GET "http://localhost:8073/receipts/customers/3" \
-H "Content-Type: application/json"
```

  Code for Windows:
```shell
curl -X GET "http://localhost:8073/receipts/customers/3" ^
-H "Content-Type: application/json"
```

  Result: 
  ```json
[{"receiptId":1,"orderId":2,"amountPaid":800.0,"paymentDate":"2024-10-07T22:25:19.995967","customerId":3,"paymentId":3}]% 
```

  ### Use Case 13: Create feedback for product (stream processing)

  Code for MacOs:
```bash
curl -X POST "http://localhost:8075/products/feedback?productId=1&rating=5"
```

  Code for Windows:
```shell
curl -X POST "http://localhost:8075/products/feedback?productId=1&rating=5"
```

  Result: 
  ```json
"Feedback sent successfully"
```

  Code for MacOs:
```bash
curl -X POST "http://localhost:8075/products/feedback?productId=1&rating=3"
```

  Code for Windows:
```shell
curl -X POST "http://localhost:8075/products/feedback?productId=1&rating=3"

```

  Result: 
  ```json
"Feedback sent successfully"
```

  ### Use Case 14: Retrieve feedback rating for product (stream processing)

  Code for MacOs:
```bash
curl -X GET "http://localhost:8076/feedback"
```

  Code for Windows:
```shell
curl -X GET "http://localhost:8076/feedback"
```

  Result: 
  ```json
[
    {
        "productId": 1,
        "rating": 4.0
    }
]
```
  ### Use Case 15: Create Vendor Rating (stream processing)
  Code for MacOs:
```bash
curl -X POST http://localhost:8075/vendors/rating \
     -H "Content-Type: application/json" \
     -d '{"vendorId": 1, "rating": 5}'
```

  Code for Windows:
```shell
curl -X POST http://localhost:8075/vendors/rating \
     -H "Content-Type: application/json" \
     -d '{"vendorId": 1, "rating": 5}'
```

  Result: 
  ```json
"Rating of vendor is sent successfully"
```
  
  ### Use Case 16: Retrieve Vendor Rating (stream processing)

  Code for MacOs:
```bash
curl -X GET http://localhost:8077/rating/1
```

  Code for Windows:
```shell
curl -X GET http://localhost:8077/rating/1
```

  Result: 
  ```json
{
    "vendorId": 1,
    "rating": 5.0
}
```


## Step 5: Close the application
  - stop your code and close the terminal
