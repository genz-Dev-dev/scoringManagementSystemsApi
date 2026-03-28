import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { ArrowRight, Lock, Mail, ShieldCheck } from "lucide-react";
import { authApi } from "@/services/smsApi";
import { storeSession } from "@/services/session";

export default function Login() {
  const [email, setEmail] = useState("admin@gmail.com");
  const [password, setPassword] = useState("admin123");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [notice, setNotice] = useState("");
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  useEffect(() => {
    const token = searchParams.get("token");
    if (!token) return;

    let ignore = false;

    const verifyEmail = async () => {
      setLoading(true);
      setError("");
      setNotice("");

      try {
        await authApi.verifyEmail(token);
        if (!ignore) {
          setNotice("ការផ្ទៀងផ្ទាត់អ៊ីមែលបានជោគជ័យ។ អ្នកអាចចូលបានឥឡូវនេះ។");
        }
      } catch (err) {
        if (!ignore) {
          setError(err instanceof Error ? err.message : "ការផ្ទៀងផ្ទាត់អ៊ីមែលបានបរាជ័យ។");
        }
      } finally {
        if (!ignore) {
          setLoading(false);
        }
      }
    };

    void verifyEmail();

    return () => {
      ignore = true;
    };
  }, [searchParams]);

  const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    setNotice("");

    try {
      const session = await authApi.signIn(email, password);
      storeSession(session.accessToken, session.user);
      navigate("/", { replace: true });
    } catch (err: unknown) {
      const message =
        err instanceof Error ? err.message : "ការចូលបរាជ័យ។ សូមពិនិត្យមើលការតភ្ជាប់ម៉ាស៊ីនមេរបស់អ្នក។";
      setError(message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-slate-50 flex items-center justify-center p-4">
      <div className="w-full max-w-md bg-white rounded-lg shadow-sm border border-slate-200 overflow-hidden">
        <div className="p-6 border-b border-slate-200 bg-slate-50 border-t-4 border-t-teal-600">
          <div className="flex items-center gap-3">
             <div className="rounded-md bg-teal-100 p-2 text-teal-700">
                <ShieldCheck size={20} />
             </div>
             <div>
               <h2 className="text-xl font-bold tracking-tight text-slate-900">ចូលគណនីប្រព័ន្ធ</h2>
               <p className="text-xs text-slate-500 mt-0.5 uppercase tracking-wider font-semibold">សាកលវិទ្យាល័យប៊ូគ្រី</p>
             </div>
          </div>
        </div>
        
        <div className="p-6">
          <form onSubmit={handleLogin} className="space-y-4">
            {notice && (
              <div className="rounded-md border border-emerald-200 bg-emerald-50 p-3 text-sm text-emerald-700">
                {notice}
              </div>
            )}

            {error && (
              <div className="rounded-md border border-rose-200 bg-rose-50 p-3 text-sm text-rose-700">
                {error}
              </div>
            )}

            <div className="space-y-1.5">
              <label className="block text-sm font-medium text-slate-700">អ៊ីមែល</label>
              <div className="relative">
                <div className="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
                  <Mail size={16} />
                </div>
                <input
                  type="email"
                  required
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="block h-10 w-full rounded-md border border-slate-300 bg-white pl-10 pr-3 text-sm outline-none transition focus:border-teal-500 focus:ring-1 focus:ring-teal-500"
                  placeholder="admin@boukry.edu"
                />
              </div>
            </div>

            <div className="space-y-1.5">
              <label className="block text-sm font-medium text-slate-700">ពាក្យសម្ងាត់</label>
              <div className="relative">
                <div className="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
                  <Lock size={16} />
                </div>
                <input
                  type="password"
                  required
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="block h-10 w-full rounded-md border border-slate-300 bg-white pl-10 pr-3 text-sm outline-none transition focus:border-teal-500 focus:ring-1 focus:ring-teal-500"
                  placeholder="••••••••"
                />
              </div>
            </div>

            <button
              type="submit"
              disabled={loading}
              className="mt-6 flex h-10 w-full items-center justify-center gap-2 rounded-md bg-teal-700 px-4 text-sm font-medium text-white transition hover:bg-teal-800 focus:outline-none focus:ring-2 focus:ring-teal-500 focus:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
            >
              {loading ? "កំពុងដំណើរការ..." : "ចូលរួម"}
              <ArrowRight size={16} />
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
