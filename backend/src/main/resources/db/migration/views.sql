CREATE VIEW vw_critical_stock AS
SELECT
    p.product_id,
    p.product_name,
    p.stock_quantity,
    p.critical_stock_level
FROM product p
WHERE p.stock_quantity <= p.critical_stock_level;


CREATE VIEW vw_monthly_sales AS
SELECT
    DATE_TRUNC('month', s.sale_date) AS month,
    SUM(s.total_amount) AS total_sales
FROM sale s
GROUP BY DATE_TRUNC('month', s.sale_date);


CREATE VIEW vw_dashboard_summary AS
SELECT
    (SELECT COALESCE(SUM(total_amount), 0)
     FROM sale
     WHERE DATE(sale_date) = CURRENT_DATE) AS "todaySalesTotal",

    (SELECT COUNT(*)
     FROM sale
     WHERE DATE(sale_date) = CURRENT_DATE) AS "todaySalesCount",

    (SELECT COUNT(*)
     FROM product
     WHERE stock_quantity <= critical_stock_level
       AND is_active = true) AS "criticalStockProductCount",

    (SELECT COUNT(*)
     FROM product
     WHERE stock_quantity = 0
       AND is_active = true) AS "outOfStockProductCount",

    (SELECT COUNT(*)
     FROM product
     WHERE is_active = true) AS "totalActiveProductCount",

    (SELECT COUNT(*)
     FROM supplier
     WHERE is_active = true) AS "totalSupplierCount";


CREATE VIEW vw_sales_last_7_days AS
SELECT
    DATE(sale_date) AS "saleDate",
    SUM(total_amount) AS "totalSales"
FROM sale
WHERE sale_date >= CURRENT_DATE - INTERVAL '6 days'
GROUP BY DATE(sale_date)
ORDER BY "saleDate";


CREATE VIEW vw_top_selling_products AS
SELECT
    p.product_id AS "productId",
    p.product_name AS "productName",
    SUM(si.quantity) AS "totalQuantitySold"
FROM sale_item si
JOIN product p ON p.product_id = si.product_id
GROUP BY p.product_id, p.product_name
ORDER BY "totalQuantitySold" DESC;