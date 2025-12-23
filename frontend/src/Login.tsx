import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { login, me, tokenStore } from './api'

export default function Login() {
  const nav = useNavigate()
  const [username, setUsername] = useState('admin')
  const [password, setPassword] = useState('123456')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    // Token varsa direkt dashboard
    if (tokenStore.get()) nav('/dashboard')
  }, [nav])

  async function onSubmit(e: React.FormEvent) {
    e.preventDefault()
    setError(null)
    setLoading(true)
    try {
      await login(username, password)
      await me()
      nav('/dashboard')
    } catch (err: any) {
      setError(err?.message ?? 'Login failed')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="center">
      <form className="card" onSubmit={onSubmit}>
        <h1 className="h1">Login</h1>
        <p className="muted">JWT ile giriş yap ve dashboard’a geç.</p>

        <label className="label">Username</label>
        <input className="input" value={username} onChange={(e) => setUsername(e.target.value)} />

        <label className="label">Password</label>
        <input
          className="input"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        {error && <div className="alert">{error}</div>}

        <button className="btn" disabled={loading}>
          {loading ? 'Signing in…' : 'Login'}
        </button>

        <div className="hint">
          Backend endpoints: <code>/auth/login</code>, <code>/auth/me</code>
        </div>
      </form>
    </div>
  )
}
