import { useEffect, useState } from 'react'
import { Product, SaleResponse, createSale, getProducts, getSaleById } from './api'

type Row = { productId: number | ''; quantity: number }

export default function Sales() {
  const [products, setProducts] = useState<Product[]>([])
  const [note, setNote] = useState('')
  const [items, setItems] = useState<Row[]>([{ productId: '', quantity: 1 }])
  const [result, setResult] = useState<SaleResponse | null>(null)

  const [findId, setFindId] = useState<number | ''>('')
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    ;(async () => {
      try {
        setProducts(await getProducts())
      } catch (e: any) {
        setError(e?.message ?? 'Load products failed')
      }
    })()
  }, [])

  function setItem(i: number, patch: Partial<Row>) {
    setItems((prev) => prev.map((r, idx) => (idx === i ? { ...r, ...patch } : r)))
  }

  async function onCreate() {
    const payload = {
      note: note || undefined,
      items: items
        .filter((x) => x.productId && x.quantity > 0)
        .map((x) => ({ productId: Number(x.productId), quantity: Number(x.quantity) })),
    }
    if (payload.items.length === 0) return

    try {
      setError(null)
      const res = await createSale(payload)
      setResult(res)
      setNote('')
      setItems([{ productId: '', quantity: 1 }])
    } catch (e: any) {
      setError(e?.message ?? 'Create sale failed')
    }
  }

  async function onFind() {
    if (!findId) return
    try {
      setError(null)
      setResult(await getSaleById(Number(findId)))
    } catch (e: any) {
      setError(e?.message ?? 'Get sale failed')
    }
  }

  return (
    <div className="page">
      <div className="page-head">
        <h2 className="h2">Sales</h2>
        <div className="muted">POST /api/sales, GET /api/sales/{'{id}'}</div>
      </div>

      {error && <div className="alert">{error}</div>}

      <div className="panel">
        <div className="grid-2">
          <div>
            <label className="label">Note</label>
            <input className="input" value={note} onChange={(e) => setNote(e.target.value)} />
          </div>
          <div>
            <label className="label">Find Sale by ID</label>
            <div className="row">
              <input className="input" type="number" value={findId} onChange={(e) => setFindId(e.target.value ? Number(e.target.value) : '')} />
              <button className="btn btn-ghost" onClick={onFind}>
                Get
              </button>
            </div>
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

            <button className="btn btn-danger" onClick={() => setItems((prev) => prev.filter((_, i) => i !== idx))} disabled={items.length === 1}>
              Remove
            </button>
          </div>
        ))}

        <div className="row">
          <button className="btn btn-ghost" onClick={() => setItems((prev) => [...prev, { productId: '', quantity: 1 }])}>
            Add Item
          </button>
          <button className="btn" onClick={onCreate}>
            Create Sale
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
