import { Outlet } from 'react-router-dom'

export default function AppLayout() {
  return (
    <div style={{ minHeight: '100vh', display: 'flex', flexDirection: 'column' }}>
      <main style={{ flex: 1, background: 'var(--bg)', overflowY: 'auto' }}>
        <Outlet />
      </main>
    </div>
  )
}
