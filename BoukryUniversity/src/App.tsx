import { BrowserRouter, Routes, Route } from 'react-router-dom'
import AdminLayout from '@/components/layout/AdminLayout'
import Login from '@/pages/Login'
import Dashboard from '@/pages/Dashboard'
import Users from '@/pages/Users'
import Access from '@/pages/Access'
import Academic from '@/pages/Academic'
import Students from '@/pages/Students'
import Grading from '@/pages/Grading'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        
        {/* Protected Admin Routes */}
        <Route path="/" element={<AdminLayout />}>
          <Route index element={<Dashboard />} />
          <Route path="users" element={<Users />} />
          <Route path="access" element={<Access />} />
          <Route path="academic" element={<Academic />} />
          <Route path="students" element={<Students />} />
          <Route path="grading" element={<Grading />} />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
