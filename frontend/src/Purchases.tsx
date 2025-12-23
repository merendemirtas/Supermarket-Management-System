import { useEffect, useState } from 'react'
import { Product, PurchaseResponse, Supplier, createPurchase, getProducts, getPurchaseById, getSuppliers } from './api'

type Row = { productId: number | ''; quantity: number; unitCost: number | '' }

export default function Purchases() {
  const [suppliers, setSuppliers] = useState<Supplier[]>([])
  const [products, setProducts] = useState<Product[]>([])
  const [supplierId, setSupplierId] = useState<number | ''>('')
  const [note, setNote] = useState('')
  const [items, setItems] = useState<Row[]>([{ productId: '', quantity: 1, unitCost: '' }])
  const [result, setResult] = useState<PurchaseResponse | null>(null)

  const [findId, setFindId] = useState<number | ''>('')
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

  function setItem(i: number, patch: Partial<Row>) {
    setItems((prev) => prev.map((r, idx) => (idx === i ? { ...r, ...patch } : r)))
  }

  async function onCreate() {
    if (!supplierId) return
    const payload = {
      supplierId: Number(supplierId),
      note: note || undefined,
      items: items
        .filter((x) => x.productId && x.quantity > 0)
        .map((x) => ({
          productId: Number(x.productId),
          quantity: Number(x.quantity),
          unitCost: x.unitCost === '' ? null : Number(x.unitCost),
        })),
    }
    if (payload.items.length === 0) return

    try {
      setError(null)
      const res = await createPurchase(payload)
      setResult(res)
      setSupplierId('')
      setNote('')
      setItems([{ productId: '', quantity: 1, unitCost: '' }])
    } catch (e: any) {
      setError(e?.message ?? 'Create purchase failed')
    }
  }

  async function onFind() {
    if (!findId) return
    try {
      setError(null)
      setResult(await getPurchaseById(Number(findId)))
    } catch (e: any) {
      setError(e?.message ?? 'Get purchase failed')
    }
  }

  return (
    <div className="page">
      <div className="page-head">
        <h2 className="h2">Purchases</h2>
        <div className="muted">POST /api/purchases, GET /api/purchases/{'{id}'}</div>
      </div>

      {error && <div className="alert">{error}</div>}

      <div className="panel">
        <div className="grid-2">
          <div>
            <label className="label">Supplier *</label>
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
            <label className="label">Find Purchase by ID</label>
            <div className="row">
              <input className="input" type="number" value={findId} onChange={(e) => setFindId(e.target.value ? Number(e.target.value) : '')} />
              <button className="btn btn-ghost" onClick={onFind}>
                Get
              </button>
            </div>
          </div>

          <div className="col-span-2">
            <label className="label">Note</label>
            <input className="input" value={note} onChange={(e) => setNote(e.target.value)} />
          </div>
        </div>

        <div className="panel-head">
          <h3 className="h3">Items</h3>
        </div>

        {items.map((r, idx) => (
          <div className="row" key={idx}>
            <select className="input" value={r.productId} onChange={(e) => setItem(idx, { productId: e.target.value ? Number(e.target.value) : '' })}>
              <option value="">Select product…</option>
              {products.map((p) => (
                <option key={p.productId} value={p.productId}>
                  {p.productName}
                </option>
              ))}
            </select>

            <input className="input" type="number" min={1} value={r.quantity} onChange={(e) => setItem(idx, { quantity: Number(e.target.value) })} />
            <input className="input" type="number" placeholder="unitCost (optional)" value={r.unitCost} onChange={(e) => setItem(idx, { unitCost: e.target.value ? Number(e.target.value) : '' })} />

            <button className="btn btn-danger" onClick={() => setItems((prev) => prev.filter((_, i) => i !== idx))} disabled={items.length === 1}>
              Remove
            </button>
          </div>
        ))}

        <div className="row">
          <button className="btn btn-ghost" onClick={() => setItems((prev) => [...prev, { productId: '', quantity: 1, unitCost: '' }])}>
            Add Item
          </button>
          <button className="btn" onClick={onCreate}>
            Create Purchase
          </button>
        </div>
      </div>

      {result && (
        <div className="panel">
          <div className="panel-head">
            <h3 className="h3">Result</h3>
          </div>
          <pre className="code">{JSON.stringify(result, null, 2)}</pre>
        </div>
      )}
    </div>
  )
}
