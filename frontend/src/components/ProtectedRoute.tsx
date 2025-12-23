import { ReactNode } from 'react'
import { Navigate } from 'react-router-dom'
import { tokenStore } from '../api'

export default function ProtectedRoute({ children }: { children: ReactNode }) {
  const token = tokenStore.get()
  if (!token) return <Navigate to="/login" replace />
  return <>{children}</>
}
