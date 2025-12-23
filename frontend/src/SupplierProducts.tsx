import { useEffect, useState } from 'react'
import {
  createSupplierProduct,
  getProducts,
  getSuppliers,
  getSuppliersByProduct,
  Product,
  Supplier,
  SupplierProduct,
} from './api'

export default function SupplierProducts() {
  const [suppliers, setSuppliers] = useState<Supplier[]>([])
  const [products, setProducts] = useState<Product[]>([])
  const [relations, setRelations] = useState<SupplierProduct[]>([])
  const [productId, setProductId] = useState<number | ''>('')
  const [supplierId, setSupplierId] = useState<number | ''>('')
  const [purchasePrice, setPurchasePrice] = useState<number>(0)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    ;(async () => {
      try {
        setError(null)
        const [s, p] = await Promise.all([getSuppliers(), getProducts()])
        setSuppliers(s)
        setProducts(p)
      } catch (e: any) {
        setError(e?.message ?? 'Load failed')
      }
    })()
  }, [])

  async function loadByProduct(pid: number) {
    try {
      setError(null)
      setRelations(await getSuppliersByProduct(pid))
    } catch (e: any) {
      setError(e?.message ?? 'Load relations failed')
    }
  }

  async function onCreate() {
    if (!supplierId || !productId) return
    try {
      setError(null)
      await createSupplierProduct({
        supplierId: Number(supplierId),
        productId: Number(productId),
        purchasePrice: Number(purchasePrice),
      })
      await loadByProduct(Number(productId))
    } catch (e: any) {
      setError(e?.message ?? 'Create relation failed')
    }
  }

  return (
    <div className="page">
      <div className="page-head">
        <h2 className="h2">Supplier–Product</h2>
        <div className="muted">/api/supplier-products</div>
      </div>

      {error && <div className="alert">{error}</div>}

      <div className="panel">
        <div className="grid-2">
          <div>
            <label className="label">Product</label>
            <select
              className="input"
              value={productId}
              onChange={(e) => {
                const v = e.target.value ? Number(e.target.value) : ''
                setProductId(v)
                if (v) loadByProduct(v)
              }}
            >
              <option value="">Select…</option>
              {products.map((p) => (
                <option key={p.productId} value={p.productId}>
                  {p.productName}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="label">Supplier</label>
            <select className="input" value={supplierId} onChange={(e) => setSupplierId(e.target.value ? Number(e.target.value) : '')}>
              <option value="">Select…</option>
              {suppliers.map((s) => (
                <option key={s.supplierId} value={s.supplierId}>
                  {s.supplierName}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="label">Purchase Price</label>
            <input className="input" type="number" value={purchasePrice} onChange={(e) => setPurchasePrice(Number(e.target.value))} />
          </div>
        </div>

        <div className="row">
          <button className="btn" onClick={onCreate}>
            Create Relation
          </button>
        </div>
      </div>

      <div className="panel">
        <div className="panel-head">
          <h3 className="h3">Suppliers by Selected Product</h3>
        </div>

        <table className="table">
          <thead>
            <tr>
              <th>Supplier</th>
              <th>Product</th>
              <th className="right">Purchase Price</th>
              <th>Active</th>
            </tr>
          </thead>
          <tbody>
            {relations.map((r) => (
              <tr key={r.supplierProductId}>
                <td>{r.supplierName}</td>
                <td>{r.productName}</td>
                <td className="right">{r.purchasePrice}</td>
                <td>{String(r.isActive)}</td>
              </tr>
            ))}
            {relations.length === 0 && (
              <tr>
                <td colSpan={4} className="muted">
                  Select a product to see relations
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  )
}
