import { useEffect, useState } from 'react'
import { Category, createCategory, deleteCategory, getCategories, updateCategory } from './api'

export default function Categories() {
  const [rows, setRows] = useState<Category[]>([])
  const [name, setName] = useState('')
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  async function load() {
    setLoading(true)
    try {
      setError(null)
      setRows(await getCategories())
    } catch (e: any) {
      setError(e?.message ?? 'Load failed')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    load()
  }, [])

  async function onCreate() {
    if (!name.trim()) return
    try {
      await createCategory(name.trim())
      setName('')
      await load()
    } catch (e: any) {
      setError(e?.message ?? 'Create failed')
    }
  }

  async function onRename(row: Category) {
    const next = prompt('New category name', row.categoryName)
    if (!next) return
    try {
      await updateCategory(row.categoryId, next)
      await load()
    } catch (e: any) {
      setError(e?.message ?? 'Update failed')
    }
  }

  async function onDelete(row: Category) {
    if (!confirm(`Delete category: ${row.categoryName}?`)) return
    try {
      await deleteCategory(row.categoryId)
      await load()
    } catch (e: any) {
      setError(e?.message ?? 'Delete failed')
    }
  }

  return (
    <div className="page">
      <div className="page-head">
        <h2 className="h2">Categories</h2>
        <div className="muted">/api/categories</div>
      </div>

      {error && <div className="alert">{error}</div>}

      <div className="panel">
        <div className="row">
          <input
            className="input"
            placeholder="Category name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
          <button className="btn" onClick={onCreate}>
            Create
          </button>
          <button className="btn btn-ghost" onClick={load} disabled={loading}>
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
              <tr key={r.categoryId}>
                <td>{r.categoryName}</td>
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
                  No categories
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  )
}
