import { Outlet } from 'react-router-dom'
import Topbar from './Topbar'

export default function AppLayout() {
  return (
    <div style={{ minHeight: '100vh', display: 'flex', flexDirection: 'column' }}>
      <Topbar />
      <main style={{ flex: 1, background: 'var(--paper)', overflowY: 'auto' }}>
        <Outlet />
      </main>
    </div>
  )
}
