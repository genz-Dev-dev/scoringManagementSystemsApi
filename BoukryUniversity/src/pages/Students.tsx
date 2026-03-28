import { useEffect, useState } from "react";
import { Download, Pencil, Plus, Trash2, Upload } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Modal } from "@/components/ui/modal";
import { Table } from "@/components/ui/table";
import { classesApi, studentsApi, type StudentInput } from "@/services/smsApi";
import type { AcademicClass, Student, StudentStatistics } from "@/types/models";

const selectClassName =
  "flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2";

const emptyStudentForm: StudentInput = {
  classId: "",
  khFirstName: "",
  khLastName: "",
  enFirstName: "",
  enLastName: "",
  gender: "M",
  dateOfBirth: "",
  email: "",
  phoneNumber: "",
  enrollmentDate: "",
  address: {
    houseNumber: "",
    street: "",
    sangkat: "",
    khan: "",
    province: "",
    country: "",
  },
};

const emptyStatistics: StudentStatistics = {
  totalStudent: 0,
  totalMale: 0,
  totalFemale: 0,
};

export default function Students() {
  const [students, setStudents] = useState<Student[]>([]);
  const [classes, setClasses] = useState<AcademicClass[]>([]);
  const [statistics, setStatistics] = useState<StudentStatistics>(emptyStatistics);
  const [classByStudentId, setClassByStudentId] = useState<Record<string, AcademicClass | null>>({});
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingStudent, setEditingStudent] = useState<Student | null>(null);
  const [form, setForm] = useState<StudentInput>(emptyStudentForm);
  const [selectedClassId, setSelectedClassId] = useState("");
  const [importFile, setImportFile] = useState<File | null>(null);

  const loadStudentsPage = async () => {
    setLoading(true);
    setError("");

    try {
      const [studentsPage, classesPage, stats] = await Promise.all([
        studentsApi.list({ size: 100, sortBy: "studentCode" }),
        classesApi.list({ size: 100, sortBy: "name" }),
        studentsApi.statistics(),
      ]);

      setStudents(studentsPage.items);
      setClasses(classesPage.items);
      setStatistics(stats);
      setSelectedClassId((current) => current || classesPage.items[0]?.id || "");

      const classEntries = await Promise.all(
        studentsPage.items.map(async (student) => {
          try {
            const clazz = await studentsApi.getClassByStudentId(student.id);
            return [student.id, clazz] as const;
          } catch {
            return [student.id, null] as const;
          }
        })
      );

      setClassByStudentId(Object.fromEntries(classEntries));
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការទាញយកទិន្នន័យសិស្សបរាជ័យ។");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    void loadStudentsPage();
  }, []);

  const openCreateModal = () => {
    setEditingStudent(null);
    setForm((current: StudentInput) => ({ ...emptyStudentForm, classId: current.classId || selectedClassId }));
    setIsModalOpen(true);
  };

  const openEditModal = async (student: Student) => {
    setSaving(true);
    setError("");

    try {
      const clazz = await studentsApi.getClassByStudentId(student.id);
      setEditingStudent(student);
      setForm({
        classId: clazz.id,
        khFirstName: student.khFirstName,
        khLastName: student.khLastName,
        enFirstName: student.enFirstName,
        enLastName: student.enLastName,
        gender: student.gender,
        dateOfBirth: student.dateOfBirth,
        email: student.email || "",
        phoneNumber: student.phoneNumber || "",
        enrollmentDate: student.enrollmentDate,
        address: {
          houseNumber: student.address?.houseNumber || "",
          street: student.address?.street || "",
          sangkat: student.address?.sangkat || "",
          khan: student.address?.khan || "",
          province: student.address?.province || "",
          country: student.address?.country || "",
        },
      });
      setIsModalOpen(true);
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការទាញយកថ្នាក់របស់សិស្សបរាជ័យ។");
    } finally {
      setSaving(false);
    }
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setSaving(true);
    setError("");

    try {
      if (editingStudent) {
        await studentsApi.update(editingStudent.id, form);
      } else {
        await studentsApi.create(form);
      }

      setIsModalOpen(false);
      setEditingStudent(null);
      setForm(emptyStudentForm);
      await loadStudentsPage();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការរក្សាទុកទិន្នន័យសិស្សបរាជ័យ។");
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (student: Student) => {
    if (!window.confirm(`តើអ្នកចង់លុបទិន្នន័យសិស្ស ${student.studentCode} មែនទេ?`)) return;

    try {
      await studentsApi.remove(student.id);
      await loadStudentsPage();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការលុបទិន្នន័យសិស្សបរាជ័យ។");
    }
  };

  const handleExport = async () => {
    if (!selectedClassId) {
      setError("សូមជ្រើសរើសថ្នាក់មុនពេលទាញយកទិន្នន័យជួរឈរ។");
      return;
    }

    try {
      const blob = await studentsApi.exportByClassId(selectedClassId);
      const url = window.URL.createObjectURL(blob);
      const anchor = document.createElement("a");
      anchor.href = url;
      anchor.download = `students-${selectedClassId}.xlsx`;
      anchor.click();
      window.URL.revokeObjectURL(url);
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការទាញយកទិន្នន័យសិស្សជា Excel បរាជ័យ។");
    }
  };

  const handleImport = async () => {
    if (!selectedClassId || !importFile) {
      setError("សូមជ្រើសរើសថ្នាក់ និងឯកសារ Excel មុនពេលបញ្ចូលទិន្នន័យ។");
      return;
    }

    try {
      await studentsApi.importByClassId(selectedClassId, importFile);
      setImportFile(null);
      await loadStudentsPage();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការបញ្ចូលទិន្នន័យសិស្សតាម Excel បរាជ័យ។");
    }
  };

  const updateAddress = (key: keyof StudentInput["address"], value: string) => {
    setForm((current: StudentInput) => ({
      ...current,
      address: {
        ...current.address,
        [key]: value,
      },
    }));
  };

  const columns = [
    { key: "studentCode", label: "លេខកូដសិស្ស" },
    {
      key: "khName",
      label: "ឈ្មោះខ្មែរ",
      render: (student: Student) => `${student.khFirstName} ${student.khLastName}`,
    },
    {
      key: "enName",
      label: "ឈ្មោះអង់គ្លេស",
      render: (student: Student) => `${student.enFirstName} ${student.enLastName}`,
    },
    {
      key: "class",
      label: "ថ្នាក់",
      render: (student: Student) => classByStudentId[student.id]?.name || "មិនស្គាល់",
    },
    { key: "gender", label: "ភេទ" },
    { key: "email", label: "អ៊ីមែល" },
    {
      key: "status",
      label: "ស្ថានភាព",
      render: (student: Student) => (
        <span
          className={`rounded-md px-2 py-0.5 text-xs font-medium border ${
            student.status 
              ? "bg-emerald-50 text-emerald-700 border-emerald-100" 
              : "bg-rose-50 text-rose-700 border-rose-100"
          }`}
        >
          {student.status ? "សកម្ម" : "អសកម្ម"}
        </span>
      ),
    },
    {
      key: "actions",
      label: "សកម្មភាព",
      render: (student: Student) => (
        <div className="flex items-center gap-2">
          <Button variant="ghost" size="icon" onClick={() => void openEditModal(student)}>
            <Pencil size={16} className="text-sky-600" />
          </Button>
          <Button variant="ghost" size="icon" onClick={() => void handleDelete(student)}>
            <Trash2 size={16} className="text-rose-600" />
          </Button>
        </div>
      ),
    },
  ];

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
        <h1 className="text-2xl font-bold tracking-tight text-slate-900">សិស្ស</h1>
        <Button onClick={openCreateModal}>
          <Plus size={16} className="mr-2" />
          បន្ថែមសិស្ស
        </Button>
      </div>

      {error && <div className="rounded-md border border-rose-200 bg-rose-50 p-3 text-sm text-rose-700">{error}</div>}

      <section className="grid gap-4 sm:grid-cols-3">
        <div className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
          <p className="text-[13px] font-medium text-slate-500">សិស្សសរុប</p>
          <p className="mt-1 text-2xl font-bold tracking-tight text-slate-900">{statistics.totalStudent}</p>
        </div>
        <div className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
          <p className="text-[13px] font-medium text-slate-500">ប្រុស</p>
          <p className="mt-1 text-2xl font-bold tracking-tight text-slate-900">{statistics.totalMale}</p>
        </div>
        <div className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
          <p className="text-[13px] font-medium text-slate-500">ស្រី</p>
          <p className="mt-1 text-2xl font-bold tracking-tight text-slate-900">{statistics.totalFemale}</p>
        </div>
      </section>

      <section className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm flex flex-col lg:flex-row gap-4 lg:items-end">
        <div className="flex-1 min-w-[200px]">
          <label className="mb-1.5 block text-sm font-medium text-slate-700">ជ្រើសរើសថ្នាក់</label>
          <select
            className={selectClassName}
            value={selectedClassId}
            onChange={(event) => setSelectedClassId(event.target.value)}
          >
            <option value="">ជ្រើសរើសថ្នាក់</option>
            {classes.map((clazz) => (
              <option key={clazz.id} value={clazz.id}>
                {clazz.name} · {clazz.academicYear}
              </option>
            ))}
          </select>
        </div>

        <div className="flex-1 min-w-[200px]">
          <label className="mb-1.5 block text-sm font-medium text-slate-700">បញ្ចូលពីឯកសារ Excel</label>
          <Input type="file" accept=".xlsx,.xls" onChange={(event) => setImportFile(event.target.files?.[0] || null)} />
        </div>

        <div className="flex flex-wrap gap-2">
          <Button variant="outline" onClick={() => void handleImport()}>
            <Upload size={16} className="mr-2" />
            បញ្ចូល
          </Button>
          <Button onClick={() => void handleExport()}>
            <Download size={16} className="mr-2" />
            ទាញយក
          </Button>
        </div>
      </section>

      <Table columns={columns} data={students} isLoading={loading || saving} />

      <Modal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        title={editingStudent ? "កែប្រែទិន្នន័យសិស្ស" : "បង្កើតទិន្នន័យសិស្ស"}
      >
        <form onSubmit={handleSubmit} className="space-y-5">
          <div className="grid gap-4 md:grid-cols-2">
            <div className="md:col-span-2">
              <label className="mb-1.5 block text-sm font-medium text-slate-700">ថ្នាក់</label>
              <select
                className={selectClassName}
                value={form.classId}
                onChange={(event) => setForm((current: StudentInput) => ({ ...current, classId: event.target.value }))}
                required
              >
                <option value="">ជ្រើសរើសថ្នាក់</option>
                {classes.map((clazz) => (
                  <option key={clazz.id} value={clazz.id}>
                    {clazz.name} · {clazz.academicYear}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="mb-1.5 block text-sm font-medium text-slate-700">នាមត្រកូល (ខ្មែរ)</label>
              <Input
                value={form.khFirstName}
                onChange={(e) => setForm((current: StudentInput) => ({ ...current, khFirstName: e.target.value }))}
                required
              />
            </div>
            <div>
              <label className="mb-1.5 block text-sm font-medium text-slate-700">នាមខ្លួន (ខ្មែរ)</label>
              <Input
                value={form.khLastName}
                onChange={(e) => setForm((current: StudentInput) => ({ ...current, khLastName: e.target.value }))}
                required
              />
            </div>
            <div>
              <label className="mb-1.5 block text-sm font-medium text-slate-700">នាមត្រកូល (អង់គ្លេស)</label>
              <Input
                value={form.enFirstName}
                onChange={(e) => setForm((current: StudentInput) => ({ ...current, enFirstName: e.target.value }))}
                required
              />
            </div>
            <div>
              <label className="mb-1.5 block text-sm font-medium text-slate-700">នាមខ្លួន (អង់គ្លេស)</label>
              <Input
                value={form.enLastName}
                onChange={(e) => setForm((current: StudentInput) => ({ ...current, enLastName: e.target.value }))}
                required
              />
            </div>
            <div>
              <label className="mb-1.5 block text-sm font-medium text-slate-700">ភេទ</label>
              <select
                className={selectClassName}
                value={form.gender}
                onChange={(event) => setForm((current: StudentInput) => ({ ...current, gender: event.target.value }))}
              >
                <option value="M">ប្រុស (M)</option>
                <option value="F">ស្រី (F)</option>
              </select>
            </div>
            <div>
              <label className="mb-1.5 block text-sm font-medium text-slate-700">ថ្ងៃខែឆ្នាំកំណើត</label>
              <Input
                type="date"
                value={form.dateOfBirth}
                onChange={(e) => setForm((current: StudentInput) => ({ ...current, dateOfBirth: e.target.value }))}
                required
              />
            </div>
            <div>
              <label className="mb-1.5 block text-sm font-medium text-slate-700">ថ្ងៃចូលរៀន</label>
              <Input
                type="date"
                value={form.enrollmentDate}
                onChange={(e) => setForm((current: StudentInput) => ({ ...current, enrollmentDate: e.target.value }))}
                required
              />
            </div>
            <div>
              <label className="mb-1.5 block text-sm font-medium text-slate-700">អ៊ីមែល</label>
              <Input
                type="email"
                value={form.email}
                onChange={(e) => setForm((current: StudentInput) => ({ ...current, email: e.target.value }))}
              />
            </div>
            <div className="md:col-span-2">
              <label className="mb-1.5 block text-sm font-medium text-slate-700">លេខទូរស័ព្ទ</label>
              <Input
                value={form.phoneNumber}
                onChange={(e) => setForm((current: StudentInput) => ({ ...current, phoneNumber: e.target.value }))}
              />
            </div>
          </div>

          <div className="rounded-md border border-slate-200 bg-slate-50 p-4 mt-4">
            <p className="text-sm font-semibold text-slate-900">អាសយដ្ឋាន</p>
            <div className="mt-4 grid gap-4 md:grid-cols-2">
              <Input placeholder="លេខផ្ទះ" value={form.address.houseNumber} onChange={(e) => updateAddress("houseNumber", e.target.value)} />
              <Input placeholder="ផ្លូវ" value={form.address.street} onChange={(e) => updateAddress("street", e.target.value)} />
              <Input placeholder="សង្កាត់/ឃុំ" value={form.address.sangkat} onChange={(e) => updateAddress("sangkat", e.target.value)} />
              <Input placeholder="ខណ្ឌ/ស្រុក" value={form.address.khan} onChange={(e) => updateAddress("khan", e.target.value)} />
              <Input placeholder="ខេត្ត/ក្រុង" value={form.address.province} onChange={(e) => updateAddress("province", e.target.value)} />
              <Input placeholder="ប្រទេស" value={form.address.country} onChange={(e) => updateAddress("country", e.target.value)} />
            </div>
          </div>

          <div className="flex justify-end gap-3 pt-4 border-t border-slate-200">
            <Button type="button" variant="outline" onClick={() => setIsModalOpen(false)}>
              បោះបង់
            </Button>
            <Button type="submit" disabled={saving}>
              {saving ? "កំពុងរក្សាទុក..." : editingStudent ? "ធ្វើបច្ចុប្បន្នភាព" : "ដាក់បញ្ចូលទិន្នន័យ"}
            </Button>
          </div>
        </form>
      </Modal>
    </div>
  );
}
