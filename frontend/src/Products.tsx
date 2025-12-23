import { useEffect, useState } from 'react'
import {
  Brand,
  Category,
  Product,
  createProduct,
  deleteProduct,
  getBrands,
  getCategories,
  getCriticalStockProducts,
  getProducts,
  updateProduct,
} from './api'

export default function Products() {
  const [rows, setRows] = useState<Product[]>([])
  const [cats, setCats] = useState<Category[]>([])
  const [brands, setBrands] = useState<Brand[]>([])
  const [showCritical, setShowCritical] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const [form, setForm] = useState({
    productName: '',
    barcode: '',
    categoryId: '',
    brandId: '',
    stockQuantity: 0,
    criticalStockLevel: 0,
    salePrice: 0,
  })

  async function load() {
    try {
      setError(null)
      const [c, b] = await Promise.all([getCategories(), getBrands()])
      setCats(c)
      setBrands(b)
      const list = showCritical ? await getCriticalStockProducts() : await getProducts()
      setRows(list)
    } catch (e: any) {
      setError(e?.message ?? 'Load failed')
    }
  }

  useEffect(() => {
    load()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [showCritical])

  async function onCreate() {
    if (!form.productName.trim() || !form.barcode.trim() || !form.categoryId || !form.brandId) return
    try {
      await createProduct({
        productName: form.productName.trim(),
        barcode: form.barcode.trim(),
        categoryId: Number(form.categoryId),
        brandId: Number(form.brandId),
        stockQuantity: Number(form.stockQuantity),
        criticalStockLevel: Number(form.criticalStockLevel),
        salePrice: Number(form.salePrice),
      })
      setForm({
        productName: '',
        barcode: '',
        categoryId: '',
        brandId: '',
        stockQuantity: 0,
        criticalStockLevel: 0,
        salePrice: 0,
      })
      await load()
    } catch (e: any) {
      setError(e?.message ?? 'Create failed')
    }
  }

  async function onEdit(row: Product) {
    const productName = prompt('Product name', row.productName)
    if (!productName) return
    const criticalStockLevel = Number(prompt('Critical stock level', String(row.criticalStockLevel)) ?? row.criticalStockLevel)
    const salePrice = Number(prompt('Sale price', String(row.salePrice)) ?? row.salePrice)

    try {
      await updateProduct(row.productId, { productName, criticalStockLevel, salePrice })
      await load()
    } catch (e: any) {
      setError(e?.message ?? 'Update failed')
    }
  }

  async function onDelete(row: Product) {
    if (!confirm(`Delete product: ${row.productName}?`)) return
    try {
      await deleteProduct(row.productId)
      await load()
    } catch (e: any) {
      setError(e?.message ?? 'Delete failed')
    }
  }

  return (
    <div className="page">
      <div className="page-head">
        <h2 className="h2">Products</h2>
        <div className="row">
          <label className="chip">
            <input type="checkbox" checked={showCritical} onChange={(e) => setShowCritical(e.target.checked)} />
            Show critical stock only
          </label>
          <button className="btn btn-ghost" onClick={load}>
            Refresh
          </button>
        </div>
      </div>

      {error && <div className="alert">{error}</div>}

      <div className="panel">
        <div className="grid-3">
          <div>
            <label className="label">Product Name *</label>
            <input className="input" value={form.productName} onChange={(e) => setForm({ ...form, productName: e.target.value })} />
          </div>
          <div>
            <label className="label">Barcode *</label>
            <input className="input" value={form.barcode} onChange={(e) => setForm({ ...form, barcode: e.target.value })} />
          </div>
          <div>
            <label className="label">Category *</label>
            <select className="input" value={form.categoryId} onChange={(e) => setForm({ ...form, categoryId: e.target.value })}>
              <option value="">Select…</option>
              {cats.map((c) => (
                <option key={c.categoryId} value={c.categoryId}>
                  {c.categoryName}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="label">Brand *</label>
            <select className="input" value={form.brandId} onChange={(e) => setForm({ ...form, brandId: e.target.value })}>
              <option value="">Select…</option>
              {brands.map((b) => (
                <option key={b.brandId} value={b.brandId}>
                  {b.brandName}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="label">Stock Quantity</label>
            <input className="input" type="number" value={form.stockQuantity} onChange={(e) => setForm({ ...form, stockQuantity: Number(e.target.value) })} />
          </div>
          <div>
            <label className="label">Critical Stock Level</label>
            <input className="input" type="number" value={form.criticalStockLevel} onChange={(e) => setForm({ ...form, criticalStockLevel: Number(e.target.value) })} />
          </div>

          <div>
            <label className="label">Sale Price</label>
            <input className="input" type="number" value={form.salePrice} onChange={(e) => setForm({ ...form, salePrice: Number(e.target.value) })} />
          </div>
        </div>

        <div className="row">
          <button className="btn" onClick={onCreate}>
            Create Product
          </button>
        </div>
      </div>

      <div className="panel">
        <table className="table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Barcode</th>
              <th>Category</th>
              <th>Brand</th>
              <th className="right">Stock</th>
              <th className="right">Critical</th>
              <th className="right">Price</th>
              <th className="right">Actions</th>
            </tr>
          </thead>
          <tbody>
            {rows.map((r) => (
              <tr key={r.productId}>
                <td>{r.productName}</td>
                <td>{r.barcode}</td>
                <td>{r.categoryName}</td>
                <td>{r.brandName}</td>
                <td className="right">{r.stockQuantity}</td>
                <td className="right">{r.criticalStockLevel}</td>
                <td className="right">{r.salePrice}</td>
                <td className="right">
                  <button className="btn btn-ghost" onClick={() => onEdit(r)}>
                    Edit
                  </button>
                  <button className="btn btn-danger" onClick={() => onDelete(r)}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
            {rows.length === 0 && (
              <tr>
                <td colSpan={8} className="muted">
                  No products
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  )
}
