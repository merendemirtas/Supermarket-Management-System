import { useState } from 'react'
import { createUser } from './api'

export default function Users() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [fullName, setFullName] = useState('')
  const [roleCode, setRoleCode] = useState<'ADMIN' | 'CASHIER' | 'STOCK_MANAGER'>('CASHIER')
  const [result, setResult] = useState<any>(null)
  const [error, setError] = useState<string | null>(null)

  async function onCreate() {
    setError(null)
    setResult(null)
    try {
      const data = await createUser({ username, password, fullName, roleCode })
      setResult(data)
      setUsername('')
      setPassword('')
      setFullName('')
      setRoleCode('CASHIER')
    } catch (e: any) {
      setError(e?.message ?? 'Create failed')
    }
  }

  return (
    <div className="page">
      <div className="page-head">
        <h2 className="h2">Users</h2>
        <div className="muted">POST /users (backend’de GET yok)</div>
      </div>

      {error && <div className="alert">{error}</div>}

      <div className="panel">
        <div className="grid-2">
          <div>
            <label className="label">Username</label>
            <input className="input" value={username} onChange={(e) => setUsername(e.target.value)} />
          </div>
          <div>
            <label className="label">Password</label>
            <input className="input" type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
          </div>
          <div>
            <label className="label">Full Name</label>
            <input className="input" value={fullName} onChange={(e) => setFullName(e.target.value)} />
          </div>
          <div>
            <label className="label">Role Code</label>
            <select className="input" value={roleCode} onChange={(e) => setRoleCode(e.target.value as any)}>
              <option value="ADMIN">ADMIN</option>
              <option value="CASHIER">CASHIER</option>
              <option value="STOCK_MANAGER">STOCK_MANAGER</option>
            </select>
          </div>
        </div>

        <div className="row">
          <button className="btn" onClick={onCreate}>
            Create User
          </button>
        </div>

        {result && (
          <pre className="code">
            {JSON.stringify(result, null, 2)}
          </pre>
        )}
      </div>
    </div>
  )
}
