/* =========================
   PRODUCT
   ========================= */

ALTER TABLE product
ADD CONSTRAINT chk_product_sale_price
CHECK (sale_price > 0);

ALTER TABLE product
ADD CONSTRAINT chk_product_stock_quantity
CHECK (stock_quantity >= 0);

ALTER TABLE product
ADD CONSTRAINT chk_product_critical_stock
CHECK (critical_stock_level >= 0);


/* =========================
   PURCHASE
   ========================= */

ALTER TABLE purchase_item
ADD CONSTRAINT chk_purchase_item_quantity
CHECK (quantity > 0);

ALTER TABLE purchase_item
ADD CONSTRAINT chk_purchase_item_unit_cost
CHECK (unit_cost >= 0);


/* =========================
   SALE
   ========================= */

ALTER TABLE sale
ADD CONSTRAINT chk_sale_total_amount
CHECK (total_amount >= 0);

ALTER TABLE sale_item
ADD CONSTRAINT chk_sale_item_quantity
CHECK (quantity > 0);

ALTER TABLE sale_item
ADD CONSTRAINT chk_sale_item_unit_price
CHECK (unit_price > 0);


