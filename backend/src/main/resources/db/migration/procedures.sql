CREATE OR REPLACE FUNCTION sp_daily_sales_summary(p_date DATE)
RETURNS TABLE (
    total_sales NUMERIC,
    total_transactions INT
)
AS $$
BEGIN
    RETURN QUERY
    SELECT
        COALESCE(SUM(total_amount), 0),
        COUNT(*)
    FROM sale
    WHERE DATE(sale_date) = p_date;
END;
$$ LANGUAGE plpgsql;
