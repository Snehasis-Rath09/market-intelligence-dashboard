import { lazy, Suspense } from 'react'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import AppLayout         from './layouts/AppLayout'

const MarketOverview = lazy(() => import('./pages/Market/MarketOverview'))

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<AppLayout />}>
        <Route path="/" element={<Suspense fallback={<div className="route-loading">Loading dashboard…</div>}><MarketOverview /></Suspense>} />
        </Route>
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  )
}
