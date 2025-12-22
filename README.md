# 🛒 MShop API

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A professional Spring Boot e-commerce REST API with JWT authentication, role-based access control, and domain-driven design architecture.

## ✨ Features

- 🔐 **JWT Authentication** - Secure token-based authentication with Bearer tokens
- 👥 **Role-Based Access Control** - Separate permissions for CUSTOMER and ADMIN roles
- 🛍️ **Product Management** - Complete CRUD operations with category filtering and search
- 📦 **Order Management** - Place orders with automatic stock management and order tracking
- 🔒 **Security** - Spring Security with stateless session management and password encryption
- 🏗️ **Domain-Driven Design** - Clean architecture with separated domains (user, product, order, auth)
- 🌏 **Timezone Support** - Configurable timezone (default: Asia/Bangkok UTC+7)
- 📅 **Custom Date Format** - Formatted timestamps: "HH:mm:ss - dd/MM/yyyy"
- ✅ **Input Validation** - Request validation with proper error handling
- 📊 **Database Migration** - Automatic schema generation with Hibernate

## 🛠️ Tech Stack

| Category | Technology |
|----------|-----------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 4.0.1 |
| **Security** | Spring Security + JWT |
| **Database** | MySQL 8.0+ |
| **ORM** | Spring Data JPA (Hibernate) |
| **Build Tool** | Maven |
| **Authentication** | JSON Web Tokens (JWT) |
| **Utilities** | Lombok |

## 📁 Project Structure

```
mshop-api/
├── src/main/java/dev/manhtran/mshop_api/
│   ├── config/                    # Application & Security Configuration
│   │   └── SecurityConfig.java
│   │
│   ├── common/                    # Shared Components
│   │   └── security/
│   │       ├── JwtUtil.java              # JWT token generation & validation
│   │       └── JwtAuthenticationFilter.java  # JWT filter for requests
│   │
│   ├── user/                      # User Domain
│   │   ├── entity/
│   │   │   └── User.java
│   │   ├── repository/
│   │   │   └── UserRepository.java
│   │   └── service/
│   │       └── CustomUserDetailsService.java
│   │
│   ├── auth/                      # Authentication Domain
│   │   ├── controller/
│   │   │   └── AuthController.java
│   │   ├── dto/
│   │   │   ├── LoginRequest.java
│   │   │   ├── SignupRequest.java
│   │   │   └── AuthResponse.java
│   │   └── service/
│   │       └── AuthService.java
│   │
│   ├── product/                   # Product Domain
│   │   ├── controller/
│   │   │   ├── ProductController.java       # Public endpoints
│   │   │   └── AdminProductController.java  # Admin endpoints
│   │   ├── dto/
│   │   │   └── ProductRequest.java
│   │   ├── entity/
│   │   │   └── Product.java
│   │   ├── repository/
│   │   │   └── ProductRepository.java
│   │   └── service/
│   │       └── ProductService.java
│   │
│   └── order/                     # Order Domain
│       ├── controller/
│       │   ├── OrderController.java         # User endpoints
│       │   └── AdminOrderController.java    # Admin endpoints
│       ├── dto/
│       │   ├── PlaceOrderRequest.java
│       │   ├── OrderItemRequest.java
│       │   └── OrderResponse.java
│       ├── entity/
│       │   ├── Order.java
│       │   └── OrderItem.java
│       ├── repository/
│       │   ├── OrderRepository.java
│       │   └── OrderItemRepository.java
│       └── service/
│           └── OrderService.java
│
└── src/main/resources/
    └── application.properties     # Configuration file
```

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+** (or MySQL 5.7+)
- **Git**

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/tranhoangmanh/mshop-api.git
   cd mshop-api
   ```

2. **Create MySQL database**
   ```sql
   CREATE DATABASE mshop_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

3. **Configure application properties**
   
   Edit `src/main/resources/application.properties`:
   
   ```properties
   # Database Configuration
   spring.datasource.url=jdbc:mysql://localhost:3306/mshop_db?useSSL=false&serverTimezone=Asia/Bangkok&allowPublicKeyRetrieval=true
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   
   # JWT Secret (generate a secure random string, minimum 256 bits)
   jwt.secret=your_secure_jwt_secret_key_here
   jwt.expiration=86400000
   ```

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080`

## 📚 API Documentation

### Base URL
```
http://localhost:8080
```

### Authentication Endpoints

#### 1. Sign Up
Register a new user account.

```http
POST /api/auth/signup
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "phone": "+1234567890",
  "address": "123 Main St, City, Country"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "email": "user@example.com",
  "role": "CUSTOMER"
}
```

#### 2. Login
Authenticate and receive JWT token.

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

#### 3. Get Current User
Get authenticated user information.

```http
GET /api/auth/me
Authorization: Bearer {your_jwt_token}
```

### Product Endpoints (Public)

#### List All Active Products
```http
GET /api/products
```

#### Get Product by ID
```http
GET /api/products/{id}
```

#### Get Products by Category
```http
GET /api/products/category/{category}
```

#### Search Products
```http
GET /api/products/search?keyword={keyword}
```

### Admin Product Endpoints

#### Create Product
```http
POST /api/admin/products
Authorization: Bearer {admin_token}
Content-Type: application/json

{
  "name": "Laptop Pro 15",
  "description": "High-performance laptop with 16GB RAM",
  "price": 1299.99,
  "stock": 50,
  "category": "Electronics",
  "imageUrl": "https://example.com/laptop.jpg"
}
```

#### Update Product
```http
PUT /api/admin/products/{id}
Authorization: Bearer {admin_token}
Content-Type: application/json

{
  "name": "Updated Product Name",
  "price": 999.99,
  "stock": 100
}
```

#### Delete Product
```http
DELETE /api/admin/products/{id}
Authorization: Bearer {admin_token}
```

#### Toggle Product Status
```http
PATCH /api/admin/products/{id}/toggle-status
Authorization: Bearer {admin_token}
```

### Order Endpoints

#### Place Order
```http
POST /api/orders
Authorization: Bearer {token}
Content-Type: application/json

{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 3,
      "quantity": 1
    }
  ],
  "shippingAddress": "123 Main St, City, Country",
  "phone": "+1234567890",
  "note": "Please deliver before 5pm"
}
```

**Response:**
```json
{
  "id": 1,
  "userId": 5,
  "userEmail": "user@example.com",
  "items": [
    {
      "id": 1,
      "productId": 1,
      "productName": "Laptop Pro 15",
      "quantity": 2,
      "price": 1299.99,
      "subtotal": 2599.98
    }
  ],
  "totalAmount": 2599.98,
  "status": "PENDING",
  "shippingAddress": "123 Main St, City, Country",
  "phone": "+1234567890",
  "note": "Please deliver before 5pm",
  "createdAt": "21:30:45 - 22/12/2025",
  "updatedAt": "21:30:45 - 22/12/2025"
}
```

#### Get My Orders
```http
GET /api/orders
Authorization: Bearer {token}
```

#### Get Order Details
```http
GET /api/orders/{id}
Authorization: Bearer {token}
```

### Admin Order Endpoints

#### List All Orders
```http
GET /api/admin/orders
Authorization: Bearer {admin_token}
```

#### Update Order Status
```http
PATCH /api/admin/orders/{id}/status
Authorization: Bearer {admin_token}
Content-Type: application/json

{
  "status": "CONFIRMED"
}
```

**Valid Order Statuses:**
- `PENDING` - Order placed, awaiting confirmation
- `CONFIRMED` - Order confirmed by admin
- `SHIPPING` - Order is being shipped
- `DELIVERED` - Order delivered to customer
- `CANCELLED` - Order cancelled

## 🔐 Security

### Authentication Flow
1. User signs up or logs in
2. Server generates JWT token
3. Client includes token in `Authorization: Bearer {token}` header
4. Server validates token for protected endpoints

### Password Security
- All passwords are hashed using **BCrypt** algorithm
- Passwords are never stored in plain text
- Minimum password requirements can be configured

### Role-Based Access Control

| Endpoint Pattern | Access Level |
|-----------------|--------------|
| `/api/auth/**` | Public |
| `/api/products/**` | Public (read-only) |
| `/api/orders/**` | Authenticated users |
| `/api/admin/**` | Admin only |

## 💾 Database Schema

The application automatically creates the following tables:

### Users Table
```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(100) NOT NULL,
  phone VARCHAR(20),
  address VARCHAR(255),
  role VARCHAR(20) NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL
);
```

### Products Table
```sql
CREATE TABLE products (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(200) NOT NULL,
  description TEXT,
  price DECIMAL(10,2) NOT NULL,
  stock INT NOT NULL DEFAULT 0,
  category VARCHAR(100),
  image_url VARCHAR(500),
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL
);
```

### Orders & Order Items Tables
```sql
CREATE TABLE orders (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  total_amount DECIMAL(10,2) NOT NULL,
  status VARCHAR(20) NOT NULL,
  shipping_address VARCHAR(255),
  phone VARCHAR(20),
  note TEXT,
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_items (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  subtotal DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (order_id) REFERENCES orders(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);
```

## 👨‍💼 Creating an Admin User

By default, all new users are created with `CUSTOMER` role. To create an admin user:

1. **Sign up normally** through the API
2. **Update the role** in the database:
   ```sql
   UPDATE users SET role = 'ADMIN' WHERE email = 'admin@example.com';
   ```
3. **Login again** to get a new token with admin privileges

## 🧪 Testing

### Manual Testing with cURL

**Sign Up:**
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123",
    "fullName": "Test User"
  }'
```

**Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

**Create Product (Admin):**
```bash
curl -X POST http://localhost:8080/api/admin/products \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Product",
    "price": 99.99,
    "stock": 10
  }'
```

### Testing Tools
- **Postman** - Import the API collection
- **cURL** - Command-line testing
- **Insomnia** - REST client
- **Thunder Client** - VS Code extension

## 🔧 Configuration

### Environment Variables (Recommended for Production)

Instead of hardcoding values in `application.properties`, use environment variables:

```properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/mshop_db}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:password}
jwt.secret=${JWT_SECRET:your_default_secret}
jwt.expiration=${JWT_EXPIRATION:86400000}
```

Set environment variables:
```bash
export DB_URL=jdbc:mysql://your-host:3306/your-database
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export JWT_SECRET=your_secure_secret_key
```

### JWT Token Expiration
Default: 24 hours (86400000 milliseconds)

To change:
```properties
jwt.expiration=3600000  # 1 hour
```

## 📦 Deployment

### Building for Production

```bash
mvn clean package -DskipTests
```

The JAR file will be created in `target/mshop-api-0.0.1-SNAPSHOT.jar`

### Running the JAR

```bash
java -jar target/mshop-api-0.0.1-SNAPSHOT.jar
```

### Docker Deployment (Optional)

Create a `Dockerfile`:
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/mshop-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:
```bash
docker build -t mshop-api .
docker run -p 8080:8080 mshop-api
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**Tran Hoang Manh**

- GitHub: [@tranhoangmanh](https://github.com/tranhoangmanh)

## 🙏 Acknowledgments

- Spring Boot team for the amazing framework
- All contributors who help improve this project

## 📞 Support

If you have any questions or issues, please open an issue on GitHub.

---

⭐ **Star this repository if you find it helpful!**
