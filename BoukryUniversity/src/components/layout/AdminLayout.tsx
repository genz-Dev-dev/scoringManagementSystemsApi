import { useEffect, useState } from "react";
import { Navigate, NavLink, Outlet, useLocation } from "react-router-dom";
import { BookMarked, BookOpenCheck, GraduationCap, LayoutDashboard, LogOut, ShieldCheck, Users2 } from "lucide-react";
import { clearSession, getSessionUser } from "@/services/session";
import { usersApi } from "@/services/smsApi";

const navItems = [
  { to: "/", label: "ផ្ទាំងគ្រប់គ្រង", icon: LayoutDashboard },
  { to: "/users", label: "អ្នកប្រើប្រាស់", icon: Users2 },
  { to: "/access", label: "សិទ្ធិចូលប្រើ", icon: ShieldCheck },
  { to: "/academic", label: "ការសិក្សា", icon: BookMarked },
  { to: "/students", label: "សិស្ស", icon: GraduationCap },
  { to: "/grading", label: "ការដាក់ពិន្ទុ", icon: BookOpenCheck },
];

export default function AdminLayout() {
  const location = useLocation();
  const user = getSessionUser();
  const [sessionChecked, setSessionChecked] = useState(false);
  const [authenticated, setAuthenticated] = useState(true);

  useEffect(() => {
    if (!user) return;

    let ignore = false;

    const validateSession = async () => {
      try {
        const status = await usersApi.isAuthenticated();
        if (!ignore) {
          setAuthenticated(status);
        }

        if (!status) {
          clearSession();
        }
      } catch {
        if (!ignore) {
          setAuthenticated(false);
        }
        clearSession();
      } finally {
        if (!ignore) {
          setSessionChecked(true);
        }
      }
    };

    void validateSession();

    return () => {
      ignore = true;
    };
  }, [user]);

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  if (!sessionChecked) {
    return (
      <div className="min-h-screen bg-slate-50 px-4 py-6 text-slate-900 flex items-center justify-center">
        <div className="text-center">
          <div className="mx-auto h-8 w-8 animate-spin rounded-full border-4 border-slate-200 border-t-teal-600" />
          <p className="mt-4 text-sm font-medium text-slate-600">កំពុងផ្ទៀងផ្ទាត់សម័យរបស់អ្នក...</p>
        </div>
      </div>
    );
  }

  if (!authenticated) {
    return <Navigate to="/login" replace />;
  }

  const handleLogout = () => {
    clearSession();
    window.location.href = "/login";
  };

  return (
    <div className="flex h-screen bg-slate-50 text-slate-900 overflow-hidden font-sans">
      {/* Standard Fixed Sidebar */}
      <aside className="hidden w-64 flex-col border-r border-slate-200 bg-white lg:flex relative z-10 shadow-sm">
        <div className="flex h-16 items-center border-b border-slate-200 px-6">
          <div className="flex flex-col">
            <span className="text-lg font-bold tracking-tight text-slate-900">ប៊ូគ្រី</span>
            <span className="text-[10px] uppercase tracking-wider text-teal-700 font-semibold">សាកលវិទ្យាល័យ</span>
          </div>
        </div>

        <nav className="flex-1 overflow-y-auto p-4 space-y-1">
          {navItems.map(({ to, label, icon: Icon }) => (
            <NavLink
              key={to}
              to={to}
              end={to === "/"}
              className={({ isActive }) =>
                [
                  "flex items-center gap-3 rounded-md px-3 py-2 text-[13px] font-medium transition-colors",
                  isActive
                    ? "bg-teal-50 text-teal-700"
                    : "text-slate-600 hover:bg-slate-100 hover:text-slate-900",
                ].join(" ")
              }
            >
              <Icon size={16} />
              <span>{label}</span>
            </NavLink>
          ))}
        </nav>

        <div className="border-t border-slate-200 p-4">
          <div className="flex items-center justify-between">
            <div className="min-w-0 pr-2">
              <p className="truncate text-sm font-semibold text-slate-900">{user.fullName}</p>
              <p className="truncate text-xs text-slate-500">{user.role}</p>
            </div>
            <button
              onClick={handleLogout}
              className="text-slate-400 p-1.5 hover:text-rose-600 hover:bg-rose-50 rounded-md transition-colors"
              title="ចាកចេញ"
            >
              <LogOut size={16} />
            </button>
          </div>
        </div>
      </aside>

      {/* Main Content Area */}
      <div className="flex flex-1 flex-col min-w-0">
        <header className="flex h-16 items-center justify-between border-b border-slate-200 bg-white px-6 lg:px-8 shadow-sm relative z-0">
          <div className="flex items-center gap-4">
            <h2 className="text-lg font-semibold text-slate-900">
              {navItems.find((item) => item.to === (location.pathname === "/" ? "/" : `/${location.pathname.split("/")[1]}`))
                ?.label || "Workspace"}
            </h2>
          </div>
          
          <div className="flex items-center gap-3">
             <div className="hidden md:flex flex-col items-end">
               <span className="text-sm font-medium text-slate-900">ប្រតិបត្តិការ</span>
               <span className="text-[11px] text-teal-700 font-medium">
                 {user.verified ? "គណនីបានផ្ទៀងផ្ទាត់" : "កំពុងរង់ចាំ"}
               </span>
             </div>
          </div>
        </header>

        {/* Mobile Nav */}
        <div className="flex overflow-x-auto border-b border-slate-200 bg-white p-3 lg:hidden space-x-2 hide-scrollbar">
          {navItems.map(({ to, label }) => (
            <NavLink
              key={to}
              to={to}
              end={to === "/"}
              className={({ isActive }) =>
                [
                  "rounded-md px-3 py-1.5 text-xs font-medium whitespace-nowrap",
                  isActive ? "bg-teal-700 text-white" : "bg-slate-100 text-slate-700",
                ].join(" ")
              }
            >
              {label}
            </NavLink>
          ))}
        </div>

        <main className="flex-1 overflow-auto bg-slate-50 p-6 lg:p-8">
          <div className="mx-auto max-w-7xl">
            <Outlet />
          </div>
        </main>
      </div>
    </div>
  );
}
