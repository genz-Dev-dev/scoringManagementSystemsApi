import { useEffect, useState } from "react";
import { BookOpenText, Building2, GraduationCap, ShieldCheck, Users2 } from "lucide-react";
import { coursesApi, departmentsApi, studentsApi, usersApi } from "@/services/smsApi";
import type { Course, StudentStatistics, User } from "@/types/models";

interface DashboardState {
  departments: number;
  students: StudentStatistics;
  users: number;
  courses: number;
  recentCourses: Course[];
  recentUsers: User[];
}

const defaultState: DashboardState = {
  departments: 0,
  students: {
    totalStudent: 0,
    totalMale: 0,
    totalFemale: 0,
  },
  users: 0,
  courses: 0,
  recentCourses: [],
  recentUsers: [],
};

export default function Dashboard() {
  const [state, setState] = useState(defaultState);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadDashboard = async () => {
      setLoading(true);
      setError("");

      try {
        const [departments, statistics, usersPage, coursesPage] = await Promise.all([
          departmentsApi.list(),
          studentsApi.statistics(),
          usersApi.list({ size: 5, sortBy: "fullName" }),
          coursesApi.list({ size: 5, sortBy: "name" }),
        ]);

        setState({
          departments: departments.length,
          students: statistics,
          users: usersPage.totalElements,
          courses: coursesPage.totalElements,
          recentCourses: coursesPage.items,
          recentUsers: usersPage.items,
        });
      } catch (err) {
        setError(err instanceof Error ? err.message : "Failed to load dashboard.");
      } finally {
        setLoading(false);
      }
    };

    void loadDashboard();
  }, []);

  const cards = [
    {
      label: "មហាវិទ្យាល័យ",
      value: state.departments,
      icon: Building2,
      tone: "bg-amber-50 text-amber-600",
    },
    {
      label: "អ្នកប្រើប្រាស់",
      value: state.users,
      icon: Users2,
      tone: "bg-teal-50 text-teal-600",
    },
    {
      label: "សិស្សសរុប",
      value: state.students.totalStudent,
      icon: GraduationCap,
      tone: "bg-emerald-50 text-emerald-600",
    },
    {
      label: "វគ្គសិក្សា",
      value: state.courses,
      icon: BookOpenText,
      tone: "bg-indigo-50 text-indigo-600",
    },
  ];

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
        <h1 className="text-2xl font-bold tracking-tight text-slate-900">ទិដ្ឋភាពទូទៅ</h1>
        <div className="flex items-center gap-2 rounded-md border border-emerald-200 bg-emerald-50 px-3 py-1.5 text-sm font-medium text-emerald-700">
          <ShieldCheck size={16} />
          ភ្ជាប់ API រួចរាល់
        </div>
      </div>

      {error && (
        <div className="rounded-md border border-rose-200 bg-rose-50 px-4 py-3 text-sm text-rose-700">{error}</div>
      )}

      <section className="grid gap-4 sm:grid-cols-2 lg:grid-cols-4">
        {cards.map(({ label, value, icon: Icon, tone }) => (
          <article
            key={label}
            className={`rounded-lg border border-slate-200 bg-white p-5 shadow-sm ${loading ? "animate-pulse" : ""}`}
          >
            <div className="flex items-center gap-3 mb-2">
               <div className={`inline-flex rounded-md p-2 ${tone}`}>
                 <Icon size={18} />
               </div>
               <p className="text-sm font-medium text-slate-600">{label}</p>
            </div>
            <p className="text-3xl font-bold tracking-tight text-slate-900">{loading ? "..." : value}</p>
          </article>
        ))}
      </section>

      <section className="grid gap-6 lg:grid-cols-2">
        <article className="rounded-lg border border-slate-200 bg-white shadow-sm flex flex-col">
          <div className="border-b border-slate-200 px-5 py-4 flex items-center justify-between">
             <h2 className="text-base font-semibold text-slate-900">វគ្គសិក្សាថ្មីៗ</h2>
             <span className="rounded-md bg-slate-100 px-2.5 py-1 text-xs font-medium text-slate-600">
               {state.recentCourses.length} វគ្គសិក្សា
             </span>
          </div>

          <div className="p-0 flex-1">
            {state.recentCourses.length === 0 && !loading ? (
              <div className="p-8 text-center text-sm text-slate-500">
                មិនមានទិន្នន័យវគ្គសិក្សាទេ
              </div>
            ) : (
              <ul className="divide-y divide-slate-100">
                {state.recentCourses.map((course) => (
                  <li key={`${course.semesterId}-${course.subjectId}`} className="p-4 hover:bg-slate-50 transition-colors">
                    <div className="flex items-start justify-between">
                      <div>
                        <p className="font-medium text-sm text-slate-900">{course.name}</p>
                        <p className="text-xs text-slate-500 mt-0.5">
                          {course.subjectName || "មិនស្គាល់"} · {course.semesterName || "មិនស្គាល់"}
                        </p>
                      </div>
                      <span className="inline-flex rounded-md bg-white border border-slate-200 px-2 py-1 text-xs font-medium text-slate-600">
                        {course.instructorName || "គ្មានគ្រូ"}
                      </span>
                    </div>
                  </li>
                ))}
              </ul>
            )}
          </div>
        </article>

        <article className="space-y-6">
          <div className="rounded-lg border border-slate-200 bg-white shadow-sm">
            <div className="border-b border-slate-200 px-5 py-4">
               <h2 className="text-base font-semibold text-slate-900">ស្ថិតិយេនឌ័រសិស្ស</h2>
            </div>
            <div className="p-5 grid grid-cols-3 gap-4">
              {[
                { label: "សរុប", value: state.students.totalStudent, color: "text-slate-900" },
                { label: "ប្រុស", value: state.students.totalMale, color: "text-blue-600" },
                { label: "ស្រី", value: state.students.totalFemale, color: "text-rose-600" },
              ].map((item) => (
                <div key={item.label} className="rounded-md bg-slate-50 border border-slate-200 p-4 text-center">
                  <p className="text-xs font-medium text-slate-500 mb-1">{item.label}</p>
                  <p className={`text-xl font-bold ${item.color}`}>{loading ? "..." : item.value}</p>
                </div>
              ))}
            </div>
          </div>

          <div className="rounded-lg border border-slate-200 bg-white shadow-sm flex flex-col">
            <div className="border-b border-slate-200 px-5 py-4">
               <h2 className="text-base font-semibold text-slate-900">អ្នកប្រើប្រាស់ថ្មីៗ</h2>
            </div>
            <div className="p-0">
              {state.recentUsers.length === 0 && !loading ? (
                <div className="p-8 text-center text-sm text-slate-500">
                  មិនមានទិន្នន័យអ្នកប្រើប្រាស់ទេ
                </div>
              ) : (
                <ul className="divide-y divide-slate-100">
                  {state.recentUsers.map((user) => (
                    <li key={user.id} className="flex items-center justify-between p-4 hover:bg-slate-50 transition-colors">
                      <div>
                        <p className="font-medium text-sm text-slate-900">{user.fullName}</p>
                        <p className="text-xs text-slate-500">{user.email}</p>
                      </div>
                      <span
                        className={`inline-flex rounded-md px-2 py-1 text-xs font-medium border ${
                          user.status 
                            ? "bg-emerald-50 text-emerald-700 border-emerald-200" 
                            : "bg-rose-50 text-rose-700 border-rose-200"
                        }`}
                      >
                        {user.status ? "សកម្ម" : "អសកម្ម"}
                      </span>
                    </li>
                  ))}
                </ul>
              )}
            </div>
          </div>
        </article>
      </section>
    </div>
  );
}
