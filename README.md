# Swift Code API

## 📌 Project Description
Swift Code API is a backend application written in **Java + Spark** that allows managing SWIFT codes for banks. The application uses **MySQL** as the database and enables retrieving, adding, and deleting records.

## 🚀 Technologies
- **Java 17**
- **Spark Java** (micro-framework)
- **MySQL** (database)
- **Jackson** (JSON serialization)
- **Postman** (API testing)

## ⚙️ Installation and Setup

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/paulinaboron/Swift_app.git
cd Swift_app
```

### 2️⃣ Run the Application
To start the application, run the Main.java file.
Server will be available at **http://localhost:8080**.

## 📡 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| **GET** | `/v1/swift-codes/country/:countryISO2` | Retrieves all SWIFT codes for a given country |
| **GET** | `/v1/swift-codes/:swiftCode` | Retrieves details of a SWIFT code (branch or headquarters) |
| **POST** | `/v1/swift-codes` | Adds a new SWIFT code |
| **DELETE** | `/v1/swift-codes/:swiftCode` | Deletes a SWIFT code |

### 🔹 Example Requests
#### Add a SWIFT Code
```bash
POST http://127.0.0.1:8080/v1/swift-codes/
{
  "address": "Address",
  "bankName": "Name",
  "countryISO2": "WE",
  "countryName": "Country",
  "isHeadquarter": true,
  "swiftCode": "WEALALT2XXX"
}
```
#### Retrieve all Banks in a Country
```bash
GET http://127.0.0.1:8080/v1/swift-codes/country/PL
```
#### Delete a SWIFT Code
```bash
DELETE http://127.0.0.1:8080/v1/swift-codes/PLBKPLPWXXX
```

## 🐬 Database
Database is hosted on **freesqldatabase.com**


