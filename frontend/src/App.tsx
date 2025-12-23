import { Navigate, Route, Routes } from 'react-router-dom'
import './App.css'
import Login from './Login'
import Dashboard from './Dashboard'
import Products from './Products'
import Sales from './Sales'
import Purchases from './Purchases'
import Categories from './Categories'
import Brands from './Brands'
import Suppliers from './Suppliers'
import SupplierProducts from './SupplierProducts'
import Users from './Users'
import Layout from './components/Layout'
import ProtectedRoute from './components/ProtectedRoute'

export default function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />

      <Route
        path="/"
        element={
          <ProtectedRoute>
            <Layout />
          </ProtectedRoute>
        }
      >
        <Route index element={<Navigate to="/dashboard" replace />} />
        <Route path="dashboard" element={<Dashboard />} />
        <Route path="products" element={<Products />} />
        <Route path="categories" element={<Categories />} />
        <Route path="brands" element={<Brands />} />
        <Route path="suppliers" element={<Suppliers />} />
        <Route path="supplier-products" element={<SupplierProducts />} />
        <Route path="sales" element={<Sales />} />
        <Route path="purchases" element={<Purchases />} />
        <Route path="users" element={<Users />} />
      </Route>

      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  )
}
