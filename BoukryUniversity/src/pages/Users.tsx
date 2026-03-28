import { useEffect, useState } from "react";
import { BadgeCheck, KeyRound, MailPlus, Power, ShieldCheck, Trash2, UserRoundPlus } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Modal } from "@/components/ui/modal";
import { Table } from "@/components/ui/table";
import { usersApi } from "@/services/smsApi";
import { getSessionUser } from "@/services/session";
import type { User } from "@/types/models";

const emptyForm = {
  fullName: "",
  email: "",
  password: "",
  role: "ROLE_STAFF",
};

const selectClassName =
  "flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2";

export default function Users() {
  const sessionUser = getSessionUser();
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [form, setForm] = useState(emptyForm);
  const [sessionValid, setSessionValid] = useState<boolean | null>(null);
  const [isSecurityModalOpen, setIsSecurityModalOpen] = useState(false);
  const [securityStep, setSecurityStep] = useState<1 | 2 | 3>(1);
  const [otp, setOtp] = useState("");
  const [newPassword, setNewPassword] = useState("");

  const loadUsers = async () => {
    setLoading(true);
    setError("");

    try {
      const [page, authenticated] = await Promise.all([
        usersApi.list({ size: 100, sortBy: "fullName" }),
        usersApi.isAuthenticated(),
      ]);
      setUsers(page.items);
      setSessionValid(authenticated);
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការទាញយកទិន្នន័យអ្នកប្រើប្រាស់បរាជ័យ។");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    void loadUsers();
  }, []);

  const handleCreate = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setSaving(true);
    setError("");

    try {
      await usersApi.create(form);
      setForm(emptyForm);
      setIsModalOpen(false);
      await loadUsers();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការបង្កើតអ្នកប្រើប្រាស់បរាជ័យ។");
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (user: User) => {
    if (!window.confirm(`តើអ្នកចង់លុប ${user.fullName} មែនទេ?`)) return;

    try {
      await usersApi.delete(user.id);
      await loadUsers();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការលុបអ្នកប្រើប្រាស់បរាជ័យ។");
    }
  };

  const handleToggleStatus = async (user: User) => {
    try {
      await usersApi.updateStatus(user.id, !user.status);
      await loadUsers();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការធ្វើបច្ចុប្បន្នភាពស្ថានភាពអ្នកប្រើប្រាស់បរាជ័យ។");
    }
  };

  const resetSecurityModal = () => {
    setIsSecurityModalOpen(false);
    setSecurityStep(1);
    setOtp("");
    setNewPassword("");
  };

  const handleSendOtp = async () => {
    if (!sessionUser?.email) return;

    setSaving(true);
    setError("");

    try {
      await usersApi.sendResetOtp(sessionUser.email);
      setSecurityStep(2);
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការផ្ញើលេខកូដ OTP បរាជ័យ។");
    } finally {
      setSaving(false);
    }
  };

  const handleVerifyOtp = async () => {
    setSaving(true);
    setError("");

    try {
      await usersApi.verifyResetOtp(otp);
      setSecurityStep(3);
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការផ្ទៀងផ្ទាត់ OTP បរាជ័យ។");
    } finally {
      setSaving(false);
    }
  };

  const handleResetPassword = async () => {
    setSaving(true);
    setError("");

    try {
      await usersApi.resetPassword(newPassword);
      resetSecurityModal();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការកំណត់ពាក្យសម្ងាត់ឡើងវិញបរាជ័យ។");
    } finally {
      setSaving(false);
    }
  };

  const columns = [
    {
      key: "fullName",
      label: "អ្នកប្រើប្រាស់",
      render: (user: User) => (
        <div>
          <p className="font-medium text-slate-900">{user.fullName}</p>
          <p className="mt-1 text-[13px] text-slate-500">{user.email}</p>
        </div>
      ),
    },
    {
      key: "role",
      label: "តួនាទី",
      render: (user: User) => (
        <span className="rounded-md bg-slate-100 px-2 py-0.5 text-xs font-medium text-slate-700">{user.role}</span>
      ),
    },
    {
      key: "verified",
      label: "ការផ្ទៀងផ្ទាត់",
      render: (user: User) => (
        <span
          className={`rounded-md px-2 py-0.5 text-xs font-medium border ${user.verified
              ? "bg-emerald-50 text-emerald-700 border-emerald-100"
              : "bg-amber-50 text-amber-700 border-amber-100"
            }`}
        >
          {user.verified ? "បានផ្ទៀងផ្ទាត់" : "រង់ចាំផ្ទៀងផ្ទាត់"}
        </span>
      ),
    },
    {
      key: "status",
      label: "ស្ថានភាព",
      render: (user: User) => (
        <span
          className={`rounded-md px-2 py-0.5 text-xs font-medium border ${user.status
              ? "bg-teal-50 text-teal-700 border-teal-100"
              : "bg-rose-50 text-rose-700 border-rose-100"
            }`}
        >
          {user.status ? "សកម្ម" : "អសកម្ម"}
        </span>
      ),
    },
    {
      key: "actions",
      label: "សកម្មភាព",
      render: (user: User) => (
        <div className="flex items-center gap-2">
          <Button variant="outline" size="sm" className="h-8" onClick={() => void handleToggleStatus(user)}>
            <Power size={14} className="mr-2" />
            {user.status ? "បិទ" : "បើក"}
          </Button>
          <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => void handleDelete(user)}>
            <Trash2 size={16} className="text-rose-600" />
          </Button>
        </div>
      ),
    },
  ];

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
        <h1 className="text-2xl font-bold tracking-tight text-slate-900">អ្នកប្រើប្រាស់</h1>
        <Button onClick={() => setIsModalOpen(true)}>
          <UserRoundPlus size={16} className="mr-2" />
          អញ្ជើញអ្នកប្រើប្រាស់
        </Button>
      </div>

      {error && <div className="rounded-md border border-rose-200 bg-rose-50 p-3 text-sm text-rose-700">{error}</div>}

      <section className="grid gap-4 sm:grid-cols-3">
        <div className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
          <p className="text-[13px] font-medium text-slate-500">គណនីសរុប</p>
          <p className="mt-1 text-2xl font-bold tracking-tight text-slate-900">{users.length}</p>
        </div>
        <div className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
          <p className="text-[13px] font-medium text-slate-500">បានផ្ទៀងផ្ទាត់</p>
          <p className="mt-1 text-2xl font-bold tracking-tight text-slate-900">{users.filter((user) => user.verified).length}</p>
        </div>
        <div className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
          <p className="text-[13px] font-medium text-slate-500">សកម្ម</p>
          <p className="mt-1 text-2xl font-bold tracking-tight text-slate-900">{users.filter((user) => user.status).length}</p>
        </div>
      </section>

      <section className="grid gap-6 lg:grid-cols-2">
        <article className="rounded-lg border border-slate-200 bg-white shadow-sm flex flex-col">
          <div className="border-b border-slate-200 px-5 py-4">
             <h2 className="text-base font-semibold text-slate-900 flex items-center gap-2">
               <ShieldCheck size={18} className="text-teal-600" />
               ស្ថានភាពការផ្ទៀងផ្ទាត់
             </h2>
          </div>
          <div className="p-5 flex-1 flex flex-col justify-center">
            <div className="flex flex-col sm:flex-row sm:items-center gap-3">
               <span
                 className={`inline-flex rounded-md px-3 py-1.5 text-[13px] font-medium border ${sessionValid === true
                     ? "bg-emerald-50 text-emerald-700 border-emerald-200"
                     : sessionValid === false
                       ? "bg-rose-50 text-rose-700 border-rose-200"
                       : "bg-amber-50 text-amber-700 border-amber-200"
                   }`}
               >
                 {sessionValid === true
                   ? "មានសិទ្ធិចូលប្រើប្រាស់"
                   : sessionValid === false
                     ? "គ្មានសិទ្ធិចូលប្រើប្រាស់"
                     : "កំពុងពិនិត្យស្ថានភាព"}
               </span>
               {sessionUser?.email && <span className="text-sm text-slate-500">{sessionUser.email}</span>}
            </div>
          </div>
        </article>

        <article className="rounded-lg border border-slate-200 bg-white shadow-sm flex flex-col">
          <div className="border-b border-slate-200 px-5 py-4">
             <h2 className="text-base font-semibold text-slate-900 flex items-center gap-2">
               <KeyRound size={18} className="text-amber-600" />
               កំណត់ពាក្យសម្ងាត់ថ្មីជាមួយ OTP
             </h2>
          </div>
          <div className="p-5 flex-1 flex flex-col justify-center items-start">
            <p className="text-sm text-slate-500 mb-4">
               ដំណើរការប្រើប្រាស់ OTP ដើម្បីប្តូរពាក្យសម្ងាត់សម្រាប់គណនីរបស់អ្នក។
            </p>
            <Button onClick={() => setIsSecurityModalOpen(true)}>
              <BadgeCheck size={16} className="mr-2" />
              ចាប់ផ្តើមការកំណត់ពាក្យសម្ងាត់
            </Button>
          </div>
        </article>
      </section>

      <Table columns={columns} data={users} isLoading={loading} />

      <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} title="បង្កើតគណនីអ្នកប្រើប្រាស់">
        <form onSubmit={handleCreate} className="space-y-4">
          <div>
            <label className="mb-1.5 block text-sm font-medium text-slate-700">ឈ្មោះពេញ</label>
            <Input
              required
              value={form.fullName}
              onChange={(event) => setForm((current) => ({ ...current, fullName: event.target.value }))}
            />
          </div>

          <div>
            <label className="mb-1.5 block text-sm font-medium text-slate-700">អ៊ីមែល</label>
            <Input
              type="email"
              required
              value={form.email}
              onChange={(event) => setForm((current) => ({ ...current, email: event.target.value }))}
            />
          </div>

          <div>
            <label className="mb-1.5 block text-sm font-medium text-slate-700">ពាក្យសម្ងាត់</label>
            <Input
              type="password"
              required
              minLength={5}
              value={form.password}
              onChange={(event) => setForm((current) => ({ ...current, password: event.target.value }))}
            />
          </div>

          <div>
            <label className="mb-1.5 block text-sm font-medium text-slate-700">តួនាទី</label>
            <select
              className={selectClassName}
              value={form.role}
              onChange={(event) => setForm((current) => ({ ...current, role: event.target.value }))}
            >
              <option value="ROLE_STAFF">បុគ្គលិករដ្ឋបាល (ROLE_STAFF)</option>
              <option value="ROLE_ADMIN">អ្នកគ្រប់គ្រង (ROLE_ADMIN)</option>
            </select>
          </div>

          <div className="rounded-md border border-teal-200 bg-teal-50 p-3 text-sm text-teal-800 flex items-start gap-2">
            <MailPlus size={16} className="mt-0.5" />
            <p>
              ការបង្កើតអ្នកប្រើប្រាស់នឹងផ្ញើអ៊ីមែលផ្ទៀងផ្ទាត់ ហើយបង្កើតគណនីក្នុងស្ថានភាពរង់ចាំរហូតដល់បញ្ចប់។
            </p>
          </div>

          <div className="flex justify-end gap-3 pt-4 border-t border-slate-200 mt-6">
            <Button type="button" variant="outline" onClick={() => setIsModalOpen(false)}>
              បោះបង់
            </Button>
            <Button type="submit" disabled={saving}>
              {saving ? "កំពុងបង្កើត..." : "បង្កើត"}
            </Button>
          </div>
        </form>
      </Modal>

      <Modal isOpen={isSecurityModalOpen} onClose={resetSecurityModal} title="កំណត់ពាក្យសម្ងាត់ថ្មីជាមួយ OTP">
        <div className="space-y-6">
          <div className="flex gap-2">
            {[1, 2, 3].map((step) => (
              <div
                key={step}
                className={`h-1.5 flex-1 rounded-full ${securityStep >= step ? "bg-teal-700" : "bg-slate-100"}`}
              />
            ))}
          </div>

          {securityStep === 1 && (
            <div className="space-y-4">
              <div>
                <label className="mb-1.5 block text-sm font-medium text-slate-700">អ៊ីមែលគណនី</label>
                <Input value={sessionUser?.email || ""} disabled />
              </div>
              <p className="text-sm text-slate-600 bg-slate-50 p-3 rounded-md border border-slate-200">
                ជំហានរទី 1: ផ្ញើលេខកូដ OTP ទៅកាន់អ៊ីមែលដែលបានភ្ជាប់ជាមួយគណនីរបស់អ្នក។
              </p>
              <div className="flex justify-end gap-3 pt-2">
                <Button type="button" variant="outline" onClick={resetSecurityModal}>
                  បោះបង់
                </Button>
                <Button type="button" disabled={saving || !sessionUser?.email} onClick={() => void handleSendOtp()}>
                  {saving ? "កំពុងផ្ញើ..." : "ផ្ញើ OTP"}
                </Button>
              </div>
            </div>
          )}

          {securityStep === 2 && (
            <div className="space-y-4">
              <div>
                <label className="mb-1.5 block text-sm font-medium text-slate-700">បញ្ចូល OTP</label>
                <Input className="text-center text-lg tracking-widest" value={otp} onChange={(event) => setOtp(event.target.value)} placeholder="000000" />
              </div>
              <p className="text-sm text-slate-600 bg-slate-50 p-3 rounded-md border border-slate-200">
                ជំហានទី 2: ផ្ទៀងផ្ទាត់ OTP ដែលទទួលបានតាមរយៈអ៊ីមែល។
              </p>
              <div className="flex justify-end gap-3 pt-2">
                <Button type="button" variant="outline" onClick={resetSecurityModal}>
                  បោះបង់
                </Button>
                <Button type="button" disabled={saving || !otp.trim()} onClick={() => void handleVerifyOtp()}>
                  {saving ? "កំពុងផ្ទៀងផ្ទាត់..." : "ផ្ទៀងផ្ទាត់ OTP"}
                </Button>
              </div>
            </div>
          )}

          {securityStep === 3 && (
            <div className="space-y-4">
              <div>
                <label className="mb-1.5 block text-sm font-medium text-slate-700">ពាក្យសម្ងាត់ថ្មី</label>
                <Input
                  type="password"
                  minLength={5}
                  value={newPassword}
                  onChange={(event) => setNewPassword(event.target.value)}
                  placeholder="យ៉ាងតិច ៥ តួអក្សរ"
                />
              </div>
              <p className="text-sm text-slate-600 bg-slate-50 p-3 rounded-md border border-slate-200">
                ជំហានទី 3: កំណត់ពាក្យសម្ងាត់ថ្មី។
              </p>
              <div className="flex justify-end gap-3 pt-2">
                <Button type="button" variant="outline" onClick={resetSecurityModal}>
                  បោះបង់
                </Button>
                <Button type="button" disabled={saving || newPassword.length < 5} onClick={() => void handleResetPassword()}>
                  {saving ? "កំពុងប្តូរ..." : "ប្តូរពាក្យសម្ងាត់"}
                </Button>
              </div>
            </div>
          )}
        </div>
      </Modal>
    </div>
  );
}
