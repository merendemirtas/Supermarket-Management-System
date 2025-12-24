  📘 Supermarket Management System
  
A full-stack supermarket management platform built with Spring Boot, React + TypeScript, PostgreSQL, JWT Authentication, and Role-Based Access Control (RBAC).
It provides complete modules for inventory, procurement, supplier relations, user management, sales operations, and analytics dashboards.

  🚀 Features
  
  🔐 Authentication & Security
  
JWT Authentication (/api/auth/login)

Stateless Spring Security

Role-Based Access Control (RBAC)

Permission-level method security (hasAuthority)

Protected frontend routes

Token-based session handling with Axios interceptors


  🛒 Functional Modules
  
Product Management

Category & Brand Management

Supplier & Supplier-Product Relation Management

Sales Operations (Cashier module)

Purchase / Stock Entry Operations (Warehouse module)

User Management (Admin module)
Inventory Monitoring (Critical stock alerts)
  📊 Dashboard & Analytics
Summary metrics (total sales, total suppliers, total products)
Last 7 days sales trend (line chart)
Top-selling products (bar chart)
Real-time analytics powered by Chart.js

  🛠️ Tech Stack
  
  Backend
  
Java 17+
Spring Boot
Spring Web (REST)
Spring Security (JWT)
Spring Data JPA (Hibernate)
PostgreSQL
Lombok
ModelMapper/DTO Structure
RBAC Authorization
Layered Architecture (Controller → Service → Repository → Entity)

  Frontend
  
React (18+)
TypeScript
Vite
Axios
React Router DOM
Chart.js
Custom Components (ProtectedRoute, Sidebar, Layout)

  🧱 Domain Architecture
  
✔ AUTH
Login, session validation, JWT generation.
✔ USER
Create users, list users, assign roles.
✔ PRODUCT
Product info, critical stock, brand/category links.
✔ CATEGORY & BRAND
CRUD operations for organizing catalog.
✔ SUPPLIER
Supplier management + Supplier-Product mapping.
✔ STOCK / PURCHASE
Stock entry operations for warehouse management.
✔ SALES
Sales module for cashiers.
✔ DASHBOARD
Analytics for managers.

  👤 Role-Based Access Control (RBAC)

Roles

ADMIN	Store Manager
STOCK_MANAGER	Warehouse / Procurement Manager
CASHIER	Cashier
Permissions List (Complete)
USER_MANAGE
CATEGORY_VIEW
CATEGORY_MANAGE
BRAND_VIEW
BRAND_MANAGE
SUPPLIER_VIEW
SUPPLIER_MANAGE
SUPPLIER_PRODUCT_VIEW
SUPPLIER_PRODUCT_MANAGE
PRODUCT_VIEW
PRODUCT_MANAGE
SALE_VIEW
SALE_CREATE
STOCK_MANAGE
PURCHASE_VIEW
PURCHASE_CREATE
REPORT_VIEW
Role Permission Mapping
ADMIN
Full access (all permissions)
STOCK_MANAGER
CATEGORY_VIEW
BRAND_VIEW
PRODUCT_VIEW
PRODUCT_MANAGE
SUPPLIER_VIEW
SUPPLIER_MANAGE
SUPPLIER_PRODUCT_VIEW
SUPPLIER_PRODUCT_MANAGE
STOCK_MANAGE
PURCHASE_VIEW
PURCHASE_CREATE
CASHIER
PRODUCT_VIEW
CATEGORY_VIEW
BRAND_VIEW
SALE_CREATE
SALE_VIEW

  📡 API Endpoints (Complete)
Below is the full list extracted from your backend:
  🔐 AUTH
Method	Endpoint	Description
POST	/api/auth/login	Login (JWT generation)
POST	/api/auth/register	Register user
GET	/api/auth/me	Get current user
  👤 USER
Method	Endpoint	Description
POST	/api/users	Create user
GET	/api/users	List users
  📁 CATEGORY
Method	Endpoint
POST	/api/categories
GET	/api/categories
PUT	/api/categories/{id}
DELETE	/api/categories/{id}
  🏷️ BRAND
Method	Endpoint
POST	/api/brands
GET	/api/brands
PUT	/api/brands/{id}
DELETE	/api/brands/{id}
  🚚 SUPPLIER
Method	Endpoint
POST	/api/suppliers
GET	/api/suppliers
PUT	/api/suppliers/{id}
DELETE	/api/suppliers/{id}
  🔗 SUPPLIER–PRODUCT
Method	Endpoint
POST	/api/supplier-products
GET	/api/supplier-products/by-product/{productId}
  🛒 PRODUCT
Method	Endpoint
POST	/api/products
GET	/api/products
GET	/api/products/critical-stock
GET	/api/products/{id}
PUT	/api/products/{id}
DELETE	/api/products/{id}
  💰 SALE
Method	Endpoint
POST	/api/sales
GET	/api/sales
GET	/api/sales/{id}
  📥 PURCHASE (STOCK)
Method	Endpoint
POST	/api/purchases
GET	/api/purchases
GET	/api/purchases/{id}
  📊 DASHBOARD
Method	Endpoint
GET	/api/dashboard/summary
GET	/api/dashboard/sales-last-7-days
GET	/api/dashboard/top-selling-products

  🛠️ Installation & Setup
Backend
cd backend
./mvnw clean install
./mvnw spring-boot:run
Backend runs on:
http://localhost:8080
Frontend
cd frontend
npm install
npm run dev
Frontend runs on:
http://localhost:5173

  🌍 Environment Variables (Frontend)
Create a .env file:
VITE_API_URL=http://localhost:8080
  🗄️ Database Setup
Run SQL scripts (roles, permissions, role-permissions):
INSERT INTO roles (...);
INSERT INTO permissions (...);
INSERT INTO role_permissions (...);
(You already generated and inserted them.)

🖥️ Screenshots 
<img width="1680" height="954" alt="Ekran Resmi 2025-12-23 18 32 26" src="https://github.com/user-attachments/assets/d8080611-8f63-42d6-8dd9-f8280101b2bc" />

## Screenshots
<img width="1566" height="585" alt="Ekran Resmi 2025-12-23 12 09 16" src="https://github.com/user-attachments/assets/282bc206-e52f-4cb2-9c41-78e62eb62461" />


📄 License
MIT License

👤 Author
Eren
GitHub: https://github.com/merendemirtas
