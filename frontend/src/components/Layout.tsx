import { NavLink, Outlet, useNavigate } from 'react-router-dom'
import { logout, tokenStore } from '../api'

function Item({ to, label }: { to: string; label: string }) {
  return (
    <NavLink
      to={to}
      className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}
      end
    >
      {label}
    </NavLink>
  )
}

export default function Layout() {
  const nav = useNavigate()
  const me = tokenStore.getMe()

  return (
    <div className="shell">
      <aside className="sidebar">
        <div className="brand">
          <div className="brand-title">Supermarket</div>
          <div className="brand-sub">Management System</div>
        </div>

        <nav className="nav">
          <Item to="/dashboard" label="Dashboard" />
          <Item to="/products" label="Products" />
          <Item to="/categories" label="Categories" />
          <Item to="/brands" label="Brands" />
          <Item to="/suppliers" label="Suppliers" />
          <Item to="/supplier-products" label="Supplier–Product" />
          <Item to="/sales" label="Sales" />
          <Item to="/purchases" label="Purchases" />
          <Item to="/users" label="Users (Create)" />
        </nav>

        <div className="sidebar-footer">
          <div className="me">
            <div className="me-username">{me?.username ?? '—'}</div>
            <div className="me-role">{me?.role ?? ''}</div>
          </div>

          <button
            className="btn btn-ghost"
            onClick={() => {
              logout()
              nav('/login')
            }}
          >
            Logout
          </button>
        </div>
      </aside>

      <main className="main">
        <Outlet />
      </main>
    </div>
  )
}
