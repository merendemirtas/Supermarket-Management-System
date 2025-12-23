import { useEffect, useMemo, useState } from 'react'
import {
  getDashboardSummary,
  getSalesLast7Days,
  getTopSellingProducts,
  DashboardSummary,
  SalesTrend,
  TopSelling,
} from './api'

import { Line } from 'react-chartjs-2'
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js'

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend)

export default function Dashboard() {
  const [summary, setSummary] = useState<DashboardSummary | null>(null)
  const [trend, setTrend] = useState<SalesTrend[]>([])
  const [top, setTop] = useState<TopSelling[]>([])
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    ;(async () => {
      try {
        setError(null)
        const [s, t, tp] = await Promise.all([
          getDashboardSummary(),
          getSalesLast7Days(),
          getTopSellingProducts(5),
        ])
        setSummary(s)
        setTrend(t)
        setTop(tp)
      } catch (e: any) {
        setError(e?.message ?? 'Dashboard load failed')
      }
    })()
  }, [])

const chartData = useMemo(() => {
  if (!trend || trend.length === 0) {
    return {
      labels: [],
      datasets: [],
    }
  }

  return {
    labels: trend.map((x) => x.saleDate),
    datasets: [
      {
        label: 'Sales (7 days)',
        data: trend.map((x) => x.totalSales),
        borderColor: 'rgba(110, 168, 255, 1)',     // ÇİZGİ RENGİ (zorunlu)
        backgroundColor: 'rgba(110, 168, 255, 0.2)',
        tension: 0.3,                              // Hafif yumuşatma
        borderWidth: 2,
        pointRadius: 4,
        pointBackgroundColor: 'rgba(110, 168, 255, 1)',
      },
    ],
  }
}, [trend])

  return (
    <div className="page">
      <div className="page-head">
        <h2 className="h2">Dashboard</h2>
        <div className="muted">/api/dashboard/*</div>
      </div>

      {error && <div className="alert">{error}</div>}

      <div className="grid-3">
        <div className="kpi">
          <div className="kpi-title">Today Sales Total</div>
          <div className="kpi-value">{summary?.todaySalesTotal?.toFixed(2) ?? '—'}</div>
        </div>
        <div className="kpi">
          <div className="kpi-title">Today Sales Count</div>
          <div className="kpi-value">{summary?.todaySalesCount ?? '—'}</div>
        </div>
        <div className="kpi">
          <div className="kpi-title">Critical Stock Products</div>
          <div className="kpi-value">{summary?.criticalStockProductCount ?? '—'}</div>
        </div>

        <div className="kpi">
          <div className="kpi-title">Out of Stock</div>
          <div className="kpi-value">{summary?.outOfStockProductCount ?? '—'}</div>
        </div>
        <div className="kpi">
          <div className="kpi-title">Active Products</div>
          <div className="kpi-value">{summary?.totalActiveProductCount ?? '—'}</div>
        </div>
        <div className="kpi">
          <div className="kpi-title">Total Suppliers</div>
          <div className="kpi-value">{summary?.totalSupplierCount ?? '—'}</div>
        </div>
      </div>

      <div className="grid-2">
        <div className="panel">
          <div className="panel-head">
            <h3 className="h3">Sales Trend (Last 7 Days)</h3>
          </div>
          <Line data={chartData} />
        </div>

        <div className="panel">
          <div className="panel-head">
            <h3 className="h3">Top Selling Products</h3>
          </div>

          <table className="table">
            <thead>
              <tr>
                <th>Product</th>
                <th className="right">Qty Sold</th>
              </tr>
            </thead>
            <tbody>
              {top.map((p) => (
                <tr key={p.productId}>
                  <td>{p.productName}</td>
                  <td className="right">{p.totalQuantitySold}</td>
                </tr>
              ))}
              {top.length === 0 && (
                <tr>
                  <td colSpan={2} className="muted">
                    No data
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
}
