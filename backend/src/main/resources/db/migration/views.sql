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
    -- Bugünkü satışlar
    (SELECT COALESCE(SUM(total_amount), 0)
     FROM sale
     WHERE DATE(sale_date) = CURRENT_DATE) AS today_sales_total,

    (SELECT COUNT(*)
     FROM sale
     WHERE DATE(sale_date) = CURRENT_DATE) AS today_sales_count,

    -- Kritik stoklu ürün sayısı
    (SELECT COUNT(*)
     FROM product
     WHERE stock_quantity <= critical_stock_level
       AND is_active = true) AS critical_stock_product_count,

    -- Stokta olmayan ürün sayısı
    (SELECT COUNT(*)
     FROM product
     WHERE stock_quantity = 0
       AND is_active = true) AS out_of_stock_product_count,

    -- Toplam aktif ürün
    (SELECT COUNT(*)
     FROM product
     WHERE is_active = true) AS total_active_product_count,

    -- Toplam tedarikçi
    (SELECT COUNT(*)
     FROM supplier
     WHERE is_active = true) AS total_supplier_count;



CREATE VIEW vw_sales_last_7_days AS
SELECT
    DATE(sale_date) AS sale_date,
    SUM(total_amount) AS total_sales
FROM sale
WHERE sale_date >= CURRENT_DATE - INTERVAL '6 days'
GROUP BY DATE(sale_date)
ORDER BY sale_date;



CREATE VIEW vw_top_selling_products AS
SELECT
    p.product_id,
    p.product_name,
    SUM(si.quantity) AS total_quantity_sold
FROM sale_item si
JOIN product p ON p.product_id = si.product_id
GROUP BY p.product_id, p.product_name
ORDER BY total_quantity_sold DESC;
