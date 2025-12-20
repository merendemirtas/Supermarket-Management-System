/*
TABLE CREATION ORDER (to avoid foreign key errors)
1) role , permission , category , brand , supplier
2) role_permission , app_user
3) product , supplier_product
4) purchase , purchase_item
5) sale , sale_item
*/

/* =========================
   ROLE & PERMISSION
   ========================= */

CREATE TABLE role (
    role_id SERIAL PRIMARY KEY,
    role_code VARCHAR(50) NOT NULL UNIQUE,
    role_name_ui VARCHAR(100) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE permission (
    permission_id SERIAL PRIMARY KEY,
    permission_code VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE role_permission (
    role_id INT NOT NULL,
    permission_id INT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES role(role_id),
    FOREIGN KEY (permission_id) REFERENCES permission(permission_id)
);


/* =========================
   USERS
   ========================= */

CREATE TABLE app_user (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    phone VARCHAR(30),
    role_id INT NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES role(role_id)
);


/* =========================
   CATEGORY & BRAND
   ========================= */

CREATE TABLE category (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE,
    is_active BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE brand (
    brand_id SERIAL PRIMARY KEY,
    brand_name VARCHAR(100) NOT NULL UNIQUE,
    is_active BOOLEAN NOT NULL DEFAULT true
);


/* =========================
   PRODUCT
   ========================= */

CREATE TABLE product (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(200) NOT NULL,
    barcode VARCHAR(50) UNIQUE,
    category_id INT NOT NULL,
    brand_id INT NOT NULL,
    sale_price NUMERIC(10,2) NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,
    critical_stock_level INT NOT NULL DEFAULT 10,
    is_active BOOLEAN NOT NULL DEFAULT true,
    FOREIGN KEY (category_id) REFERENCES category(category_id),
    FOREIGN KEY (brand_id) REFERENCES brand(brand_id),
    CHECK (sale_price > 0),
    CHECK (stock_quantity >= 0),
    CHECK (critical_stock_level >= 0)
);


/* =========================
   SUPPLIER
   ========================= */

CREATE TABLE supplier (
    supplier_id SERIAL PRIMARY KEY,
    supplier_name VARCHAR(200) NOT NULL UNIQUE,
    phone VARCHAR(30),
    email VARCHAR(150),
    address TEXT,
    is_active BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE supplier_product (
    supplier_id INT NOT NULL,
    product_id INT NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    PRIMARY KEY (supplier_id, product_id),
    FOREIGN KEY (supplier_id) REFERENCES supplier(supplier_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);


/* =========================
   PURCHASE
   ========================= */

CREATE TABLE purchase (
    purchase_id SERIAL PRIMARY KEY,
    supplier_id INT NOT NULL,
    created_by_user_id INT NOT NULL,
    purchase_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    note TEXT,
    FOREIGN KEY (supplier_id) REFERENCES supplier(supplier_id),
    FOREIGN KEY (created_by_user_id) REFERENCES app_user(user_id)
);

CREATE TABLE purchase_item (
    purchase_item_id SERIAL PRIMARY KEY,
    purchase_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_cost NUMERIC(10,2),
    FOREIGN KEY (purchase_id) REFERENCES purchase(purchase_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    CHECK (quantity > 0),
    CHECK (unit_cost >= 0)
);


/* =========================
   SALE
   ========================= */

CREATE TABLE sale (
    sale_id SERIAL PRIMARY KEY,
    cashier_user_id INT NOT NULL,
    sale_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount NUMERIC(12,2) NOT NULL DEFAULT 0,
    note TEXT,
    FOREIGN KEY (cashier_user_id) REFERENCES app_user(user_id),
    CHECK (total_amount >= 0)
);

CREATE TABLE sale_item (
    sale_item_id SERIAL PRIMARY KEY,
    sale_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price NUMERIC(10,2) NOT NULL,
    FOREIGN KEY (sale_id) REFERENCES sale(sale_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    CHECK (quantity > 0),
    CHECK (unit_price > 0)
);
