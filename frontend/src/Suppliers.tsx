import { useEffect, useState } from 'react'
import { Supplier, createSupplier, deleteSupplier, getSuppliers, updateSupplier } from './api'

const emptyForm = {
  supplierName: '',
  contactName: '',
  phone: '',
  email: '',
  address: '',
}

export default function Suppliers() {
  const [rows, setRows] = useState<Supplier[]>([])
  const [form, setForm] = useState({ ...emptyForm })
  const [error, setError] = useState<string | null>(null)

  async function load() {
    try {
      setError(null)
      setRows(await getSuppliers())
    } catch (e: any) {
      setError(e?.message ?? 'Load failed')
    }
  }

  useEffect(() => {
    load()
  }, [])

  async function onCreate() {
    if (!form.supplierName.trim()) return
    try {
      await createSupplier({
        supplierName: form.supplierName.trim(),
        contactName: form.contactName || undefined,
        phone: form.phone || undefined,
        email: form.email || undefined,
        address: form.address || undefined,
      })
      setForm({ ...emptyForm })
      await load()
    } catch (e: any) {
      setError(e?.message ?? 'Create failed')
    }
  }

  async function onEdit(row: Supplier) {
    const supplierName = prompt('Supplier name', row.supplierName) ?? row.supplierName
    const contactName = prompt('Contact name', row.contactName ?? '') ?? row.contactName ?? ''
    const phone = prompt('Phone', row.phone ?? '') ?? row.phone ?? ''
    const email = prompt('Email', row.email ?? '') ?? row.email ?? ''
    const address = prompt('Address', row.address ?? '') ?? row.address ?? ''

    try {
      await updateSupplier(row.supplierId, {
        supplierName,
        contactName,
        phone,
        email,
        address,
      })
      await load()
    } catch (e: any) {
      setError(e?.message ?? 'Update failed')
    }
  }

  async function onDelete(row: Supplier) {
    if (!confirm(`Delete supplier: ${row.supplierName}?`)) return
    try {
      await deleteSupplier(row.supplierId)
      await load()
    } catch (e: any) {
      setError(e?.message ?? 'Delete failed')
    }
  }

  return (
    <div className="page">
      <div className="page-head">
        <h2 className="h2">Suppliers</h2>
        <div className="muted">/api/suppliers</div>
      </div>

      {error && <div className="alert">{error}</div>}

      <div className="panel">
        <div className="grid-2">
          <div>
            <label className="label">Supplier Name *</label>
            <input className="input" value={form.supplierName} onChange={(e) => setForm({ ...form, supplierName: e.target.value })} />
          </div>
          <div>
            <label className="label">Contact Name</label>
            <input className="input" value={form.contactName} onChange={(e) => setForm({ ...form, contactName: e.target.value })} />
          </div>
          <div>
            <label className="label">Phone</label>
            <input className="input" value={form.phone} onChange={(e) => setForm({ ...form, phone: e.target.value })} />
          </div>
          <div>
            <label className="label">Email</label>
            <input className="input" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} />
          </div>
          <div className="col-span-2">
            <label className="label">Address</label>
            <input className="input" value={form.address} onChange={(e) => setForm({ ...form, address: e.target.value })} />
          </div>
        </div>

        <div className="row">
          <button className="btn" onClick={onCreate}>
            Create Supplier
          </button>
          <button className="btn btn-ghost" onClick={load}>
            Refresh
          </button>
        </div>
      </div>

      <div className="panel">
        <table className="table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Contact</th>
              <th>Phone</th>
              <th>Email</th>
              <th className="right">Actions</th>
            </tr>
          </thead>
          <tbody>
            {rows.map((r) => (
              <tr key={r.supplierId}>
                <td>{r.supplierName}</td>
                <td>{r.contactName ?? ''}</td>
                <td>{r.phone ?? ''}</td>
                <td>{r.email ?? ''}</td>
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
                <td colSpan={5} className="muted">
                  No suppliers
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  )
}
