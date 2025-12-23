import axios, { AxiosError, AxiosHeaders } from 'axios'



const API_BASE = '' // Vite proxy kullandığımız için boş bırakıyoruz.

export type AuthResponse = { accessToken: string; expiresIn: number }
export type MeResponse = {
  userId: string
  username: string
  role: string
  permissions: string[]
}

export type Category = { categoryId: number; categoryName: string; isActive: boolean }
export type Brand = { brandId: number; brandName: string; isActive: boolean }
export type Supplier = {
  supplierId: number
  supplierName: string
  contactName?: string
  phone?: string
  email?: string
  address?: string
  isActive: boolean
}

export type Product = {
  productId: number
  productName: string
  barcode: string
  categoryName: string
  brandName: string
  stockQuantity: number
  criticalStockLevel: number
  salePrice: number
  isActive: boolean
}

export type SupplierProduct = {
  supplierProductId: number
  supplierId: number
  supplierName: string
  productId: number
  productName: string
  purchasePrice: number
  isActive: boolean
}

export type SaleItem = { productId: number; quantity: number }
export type SaleResponse = {
  saleId: number
  cashierUsername: string
  saleDate: string
  totalAmount: number
  note?: string
  items: Array<{
    productId: number
    productName: string
    quantity: number
    unitPrice: number
    lineTotal: number
  }>
}

export type PurchaseItem = { productId: number; quantity: number; unitCost?: number | null }
export type PurchaseResponse = {
  purchaseId: number
  supplierId: number
  supplierName: string
  createdByUsername: string
  purchaseDate: string
  note?: string
  items: Array<{
    productId: number
    productName: string
    quantity: number
    unitCost: number
    lineTotal: number
  }>
}

export type DashboardSummary = {
  todaySalesTotal: number
  todaySalesCount: number
  criticalStockProductCount: number
  outOfStockProductCount: number
  totalActiveProductCount: number
  totalSupplierCount: number
}

export type SalesTrend = { saleDate: string; totalSales: number }
export type TopSelling = { productId: number; productName: string; totalQuantitySold: number }

export type CreateUserRequest = {
  username: string
  password: string
  fullName: string
  roleCode: 'ADMIN' | 'CASHIER' | 'STOCK_MANAGER'
}

export const tokenStore = {
  get(): string | null {
    return localStorage.getItem('accessToken')
  },
  set(t: string) {
    localStorage.setItem('accessToken', t)
  },
  clear() {
    localStorage.removeItem('accessToken')
    localStorage.removeItem('me')
  },
  setMe(me: MeResponse) {
    localStorage.setItem('me', JSON.stringify(me))
  },
  getMe(): MeResponse | null {
    const raw = localStorage.getItem('me')
    return raw ? (JSON.parse(raw) as MeResponse) : null
  },
}

export const api = axios.create({
  baseURL: API_BASE,
  timeout: 15000,
})

api.interceptors.request.use((config) => {
  const token = tokenStore.get()
  if (!token) return config

  // Axios v1: headers bazen AxiosHeaders class’ıdır
  const headers = (config.headers ?? new AxiosHeaders()) as AxiosHeaders
  headers.set('Authorization', `Bearer ${token}`)
  config.headers = headers

  return config
})


function normalizeError(e: unknown): string {
  if (axios.isAxiosError(e)) {
    const ae = e as AxiosError<any>
    const msg =
      ae.response?.data?.message ||
      ae.response?.data?.error ||
      (typeof ae.response?.data === 'string' ? ae.response?.data : null)
    if (msg) return String(msg)
    return `HTTP ${ae.response?.status ?? ''} ${ae.message}`.trim()
  }
  return 'Beklenmeyen hata oluştu.'
}

export async function login(username: string, password: string): Promise<AuthResponse> {
  try {
    const { data } = await api.post<AuthResponse>('/auth/login', { username, password })
    tokenStore.set(data.accessToken)
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}

export async function me(): Promise<MeResponse> {
  try {
    const { data } = await api.get<MeResponse>('/auth/me')
    tokenStore.setMe(data)
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}

export function logout() {
  tokenStore.clear()
}

/* USERS (Backend'de sadece POST var) */
export async function createUser(req: CreateUserRequest) {
  try {
    const { data } = await api.post('/users', req)
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}

/* CATEGORIES */
export async function getCategories(): Promise<Category[]> {
  try {
    const { data } = await api.get<Category[]>('/api/categories')
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function createCategory(categoryName: string): Promise<Category> {
  try {
    const { data } = await api.post<Category>('/api/categories', { categoryName })
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function updateCategory(id: number, categoryName: string): Promise<Category> {
  try {
    const { data } = await api.put<Category>(`/api/categories/${id}`, { categoryName })
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function deleteCategory(id: number): Promise<void> {
  try {
    await api.delete(`/api/categories/${id}`)
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}

/* BRANDS */
export async function getBrands(): Promise<Brand[]> {
  try {
    const { data } = await api.get<Brand[]>('/api/brands')
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function createBrand(brandName: string): Promise<Brand> {
  try {
    const { data } = await api.post<Brand>('/api/brands', { brandName })
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function updateBrand(id: number, brandName: string): Promise<Brand> {
  try {
    const { data } = await api.put<Brand>(`/api/brands/${id}`, { brandName })
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function deleteBrand(id: number): Promise<void> {
  try {
    await api.delete(`/api/brands/${id}`)
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}

/* SUPPLIERS */
export async function getSuppliers(): Promise<Supplier[]> {
  try {
    const { data } = await api.get<Supplier[]>('/api/suppliers')
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function createSupplier(payload: Omit<Supplier, 'supplierId' | 'isActive'>): Promise<Supplier> {
  try {
    const { data } = await api.post<Supplier>('/api/suppliers', payload)
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function updateSupplier(
  id: number,
  payload: Omit<Supplier, 'supplierId' | 'isActive'>
): Promise<Supplier> {
  try {
    const { data } = await api.put<Supplier>(`/api/suppliers/${id}`, payload)
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function deleteSupplier(id: number): Promise<void> {
  try {
    await api.delete(`/api/suppliers/${id}`)
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}

/* PRODUCTS */
export async function getProducts(): Promise<Product[]> {
  try {
    const { data } = await api.get<Product[]>('/api/products')
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function getProductById(id: number): Promise<Product> {
  try {
    const { data } = await api.get<Product>(`/api/products/${id}`)
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function getCriticalStockProducts(): Promise<Product[]> {
  try {
    const { data } = await api.get<Product[]>('/api/products/critical-stock')
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function createProduct(payload: {
  productName: string
  barcode: string
  categoryId: number
  brandId: number
  stockQuantity: number
  criticalStockLevel: number
  salePrice: number
}): Promise<Product> {
  try {
    const { data } = await api.post<Product>('/api/products', payload)
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function updateProduct(
  id: number,
  payload: { productName: string; criticalStockLevel: number; salePrice: number }
): Promise<Product> {
  try {
    const { data } = await api.put<Product>(`/api/products/${id}`, payload)
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function deleteProduct(id: number): Promise<void> {
  try {
    await api.delete(`/api/products/${id}`)
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}

/* SUPPLIER-PRODUCTS */
export async function createSupplierProduct(payload: {
  supplierId: number
  productId: number
  purchasePrice: number
}): Promise<SupplierProduct> {
  try {
    const { data } = await api.post<SupplierProduct>('/api/supplier-products', payload)
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function getSuppliersByProduct(productId: number): Promise<SupplierProduct[]> {
  try {
    const { data } = await api.get<SupplierProduct[]>(`/api/supplier-products/by-product/${productId}`)
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}

/* SALES */
export async function createSale(payload: { note?: string; items: SaleItem[] }): Promise<SaleResponse> {
  try {
    const { data } = await api.post<SaleResponse>('/api/sales', payload)
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function getSaleById(id: number): Promise<SaleResponse> {
  try {
    const { data } = await api.get<SaleResponse>(`/api/sales/${id}`)
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}

/* PURCHASES */
export async function createPurchase(payload: {
  supplierId: number
  note?: string
  items: PurchaseItem[]
}): Promise<PurchaseResponse> {
  try {
    const { data } = await api.post<PurchaseResponse>('/api/purchases', payload)
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function getPurchaseById(id: number): Promise<PurchaseResponse> {
  try {
    const { data } = await api.get<PurchaseResponse>(`/api/purchases/${id}`)
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}

/* DASHBOARD */
export async function getDashboardSummary(): Promise<DashboardSummary> {
  try {
    const { data } = await api.get<DashboardSummary>('/api/dashboard/summary')
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function getSalesLast7Days(): Promise<SalesTrend[]> {
  try {
    const { data } = await api.get<SalesTrend[]>('/api/dashboard/sales-last-7-days')
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
export async function getTopSellingProducts(limit = 5): Promise<TopSelling[]> {
  try {
    const { data } = await api.get<TopSelling[]>('/api/dashboard/top-selling-products', { params: { limit } })
    return data
  } catch (e) {
    throw new Error(normalizeError(e))
  }
}
