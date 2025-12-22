--------------------------------------------------
-- ROLES
--------------------------------------------------
INSERT INTO roles (id, role_code, description, created_at)
VALUES
    (1, 'ADMIN', 'System administrator', NOW()),
    (2, 'STOCK_MANAGER', 'Stock and product manager', NOW()),
    (3, 'CASHIER', 'Cashier user', NOW())
ON CONFLICT (id) DO NOTHING;

--------------------------------------------------
-- PERMISSIONS
--------------------------------------------------
INSERT INTO permissions (id, permission_code, description, created_at)
VALUES
    (1, 'USER_MANAGE', 'Create and manage users', NOW()),
    (2, 'PRODUCT_VIEW', 'View products', NOW()),
    (3, 'PRODUCT_MANAGE', 'Create/update/delete products', NOW()),
    (4, 'STOCK_MANAGE', 'Manage stock', NOW()),
    (5, 'SALE_CREATE', 'Create sales', NOW()),
    (6, 'SALE_VIEW', 'View sales', NOW()),
    (7, 'REPORT_VIEW', 'View reports', NOW())
ON CONFLICT (id) DO NOTHING;

--------------------------------------------------
-- ROLE_PERMISSIONS
--------------------------------------------------

-- ADMIN -> ALL
INSERT INTO role_permissions (role_id, permission_id, created_at)
SELECT r.id, p.id, NOW()
FROM roles r, permissions p
WHERE r.role_code = 'ADMIN'
ON CONFLICT DO NOTHING;

-- STOCK_MANAGER
INSERT INTO role_permissions (role_id, permission_id, created_at)
SELECT r.id, p.id, NOW()
FROM roles r
JOIN permissions p ON p.permission_code IN (
    'PRODUCT_VIEW',
    'PRODUCT_MANAGE',
    'STOCK_MANAGE'
)
WHERE r.role_code = 'STOCK_MANAGER'
ON CONFLICT DO NOTHING;

-- CASHIER
INSERT INTO role_permissions (role_id, permission_id, created_at)
SELECT r.id, p.id, NOW()
FROM roles r
JOIN permissions p ON p.permission_code IN (
    'SALE_CREATE',
    'SALE_VIEW'
)
WHERE r.role_code = 'CASHIER'
ON CONFLICT DO NOTHING;
