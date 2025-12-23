import { useEffect, useState } from 'react'
import { Brand, createBrand, deleteBrand, getBrands, updateBrand } from './api'

export default function Brands() {
  const [rows, setRows] = useState<Brand[]>([])
  const [name, setName] = useState('')
  const [error, setError] = useState<string | null>(null)

  async function load() {
    try {
      setError(null)
      setRows(await getBrands())
    } catch (e: any) {
      setError(e?.message ?? 'Load failed')
    }
  }

  useEffect(() => {
    load()
  }, [])

  async function onCreate() {
    if (!name.trim()) return
    try {
      await createBrand(name.trim())
      setName('')
      await load()
    } catch (e: any) {
      setError(e?.message ?? 'Create failed')
    }
  }

  async function onRename(row: Brand) {
    const next = prompt('New brand name', row.brandName)
    if (!next) return
    try {
      await updateBrand(row.brandId, next)
      await load()
    } catch (e: any) {
      setError(e?.message ?? 'Update failed')
    }
  }

  async function onDelete(row: Brand) {
    if (!confirm(`Delete brand: ${row.brandName}?`)) return
    try {
      await deleteBrand(row.brandId)
      await load()
    } catch (e: any) {
      setError(e?.message ?? 'Delete failed')
    }
  }

  return (
    <div className="page">
      <div className="page-head">
        <h2 className="h2">Brands</h2>
        <div className="muted">/api/brands</div>
      </div>

      {error && <div className="alert">{error}</div>}

      <div className="panel">
        <div className="row">
          <input className="input" placeholder="Brand name" value={name} onChange={(e) => setName(e.target.value)} />
          <button className="btn" onClick={onCreate}>
            Create
          </button>
          <button className="btn btn-ghost" onClick={load}>
            Refresh
          </button>
        </div>

        <table className="table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Active</th>
              <th className="right">Actions</th>
            </tr>
          </thead>
          <tbody>
            {rows.map((r) => (
              <tr key={r.brandId}>
                <td>{r.brandName}</td>
                <td>{String(r.isActive)}</td>
                <td className="right">
                  <button className="btn btn-ghost" onClick={() => onRename(r)}>
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
                <td colSpan={3} className="muted">
                  No brands
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  )
}
