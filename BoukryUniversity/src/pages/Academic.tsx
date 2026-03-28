import { useEffect, useMemo, useState } from "react";
import { CalendarRange, FolderKanban, Layers3, Network, Pencil, Plus, Shapes, Trash2 } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Modal } from "@/components/ui/modal";
import { Table } from "@/components/ui/table";
import {
  classesApi,
  coursesApi,
  departmentsApi,
  semestersApi,
  subjectsApi,
  usersApi,
  type ClassInput,
  type CourseInput,
  type DepartmentInput,
  type SemesterInput,
  type SubjectInput,
} from "@/services/smsApi";
import type { AcademicClass, Course, CourseSchedule, Department, Semester, Subject, User } from "@/types/models";

type AcademicTab = "departments" | "subjects" | "semesters" | "classes" | "courses";

const selectClassName =
  "flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2";

const emptyDepartment: DepartmentInput = {
  name: "",
  code: "",
  description: "",
};

const emptySubject: SubjectInput = {
  departmentId: "",
  name: "",
  description: "",
  code: "",
};

const emptySemester: SemesterInput = {
  name: "",
  startDate: "",
  endDate: "",
  description: "",
};

const emptyClass: ClassInput = {
  name: "",
  departmentId: "",
  academicYear: "",
  generation: 1,
};

const emptyCourse: CourseInput = {
  subjectId: "",
  semesterId: "",
  instructorId: "",
  name: "",
  description: "",
  startAt: "",
  endAt: "",
  schedules: [
    {
      dayOfWeek: "MONDAY",
      startTime: "09:00",
      endTime: "11:00",
      room: 101,
    },
  ],
};

const tabs: Array<{ key: AcademicTab; label: string; singular: string; icon: typeof FolderKanban; description: string }> = [
  {
    key: "departments",
    label: "មហាវិទ្យាល័យ",
    singular: "មហាវិទ្យាល័យ",
    icon: FolderKanban,
    description: "អង្គភាពមូលដ្ឋានសម្រាប់ការសិក្សាដែលប្រើដោយមុខវិជ្ជា និងថ្នាក់រៀន។",
  },
  {
    key: "subjects",
    label: "មុខវិជ្ជា",
    singular: "មុខវិជ្ជា",
    icon: Shapes,
    description: "បញ្ជីមុខវិជ្ជាដែលភ្ជាប់ជាមួយមហាវិទ្យាល័យ។",
  },
  {
    key: "semesters",
    label: "ឆមាស",
    singular: "ឆមាស",
    icon: CalendarRange,
    description: "កាលវិភាគសម្រាប់ឆមាសសិក្សា។",
  },
  {
    key: "classes",
    label: "ថ្នាក់រៀន",
    singular: "ថ្នាក់រៀន",
    icon: Layers3,
    description: "ថ្នាក់រៀនដែលបែងចែកតាមឆ្នាំ និងជំនាន់។",
  },
  {
    key: "courses",
    label: "វគ្គសិក្សា",
    singular: "វគ្គសិក្សា",
    icon: Network,
    description: "វគ្គសិក្សាដែលមានមុខវិជ្ជា គ្រូបង្រៀន និងកាលវិភាគ។",
  },
];

export default function Academic() {
  const [activeTab, setActiveTab] = useState<AcademicTab>("departments");
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [modalTab, setModalTab] = useState<AcademicTab | null>(null);

  const [departments, setDepartments] = useState<Department[]>([]);
  const [subjects, setSubjects] = useState<Subject[]>([]);
  const [semesters, setSemesters] = useState<Semester[]>([]);
  const [classes, setClasses] = useState<AcademicClass[]>([]);
  const [courses, setCourses] = useState<Course[]>([]);
  const [users, setUsers] = useState<User[]>([]);

  const [editingDepartment, setEditingDepartment] = useState<Department | null>(null);
  const [editingSubject, setEditingSubject] = useState<Subject | null>(null);
  const [editingSemester, setEditingSemester] = useState<Semester | null>(null);
  const [editingClass, setEditingClass] = useState<AcademicClass | null>(null);
  const [editingCourse, setEditingCourse] = useState<Course | null>(null);

  const [departmentForm, setDepartmentForm] = useState<DepartmentInput>(emptyDepartment);
  const [subjectForm, setSubjectForm] = useState<SubjectInput>(emptySubject);
  const [semesterForm, setSemesterForm] = useState<SemesterInput>(emptySemester);
  const [classForm, setClassForm] = useState<ClassInput>(emptyClass);
  const [courseForm, setCourseForm] = useState<CourseInput>(emptyCourse);

  const instructors = useMemo(() => users.filter((user) => user.status), [users]);

  const loadAcademicData = async () => {
    setLoading(true);
    setError("");

    try {
      const [departmentList, subjectList, semesterList, classPage, coursePage, userPage] = await Promise.all([
        departmentsApi.list(),
        subjectsApi.list(),
        semestersApi.list(),
        classesApi.list({ size: 100, sortBy: "name" }),
        coursesApi.list({ size: 100, sortBy: "name" }),
        usersApi.list({ size: 100, sortBy: "fullName" }),
      ]);

      setDepartments(departmentList);
      setSubjects(subjectList);
      setSemesters(semesterList);
      setClasses(classPage.items);
      setCourses(coursePage.items);
      setUsers(userPage.items);
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការទាញយកទិន្នន័យនៃការសិក្សាបរាជ័យ។");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    void loadAcademicData();
  }, []);

  const resetModals = () => {
    setModalTab(null);
    setEditingDepartment(null);
    setEditingSubject(null);
    setEditingSemester(null);
    setEditingClass(null);
    setEditingCourse(null);
    setDepartmentForm(emptyDepartment);
    setSubjectForm(emptySubject);
    setSemesterForm(emptySemester);
    setClassForm(emptyClass);
    setCourseForm(emptyCourse);
  };

  const openDepartmentModal = (department?: Department) => {
    setEditingDepartment(department || null);
    setDepartmentForm(department ? { name: department.name, code: department.code, description: department.description } : emptyDepartment);
    setModalTab("departments");
  };

  const openSubjectModal = (subject?: Subject) => {
    setEditingSubject(subject || null);
    setSubjectForm(
      subject
        ? {
            departmentId: subject.departmentId,
            name: subject.name,
            description: subject.description,
            code: subject.code,
          }
        : { ...emptySubject, departmentId: departments[0]?.id || "" }
    );
    setModalTab("subjects");
  };

  const openSemesterModal = (semester?: Semester) => {
    setEditingSemester(semester || null);
    setSemesterForm(
      semester
        ? {
            name: semester.name,
            startDate: semester.startDate,
            endDate: semester.endDate,
            description: semester.description,
          }
        : emptySemester
    );
    setModalTab("semesters");
  };

  const openClassModal = (clazz?: AcademicClass) => {
    setEditingClass(clazz || null);
    setClassForm(
      clazz
        ? {
            name: clazz.name,
            departmentId: clazz.departmentId,
            academicYear: clazz.academicYear,
            generation: clazz.generation,
          }
        : { ...emptyClass, departmentId: departments[0]?.id || "" }
    );
    setModalTab("classes");
  };

  const openCourseModal = (course?: Course) => {
    setEditingCourse(course || null);
    setCourseForm(
      course
        ? {
            subjectId: course.subjectId,
            semesterId: course.semesterId,
            instructorId: course.instructorId,
            name: course.name,
            description: course.description,
            startAt: course.startAt,
            endAt: course.endAt,
            schedules: course.schedules.map((schedule) => ({
              dayOfWeek: schedule.dayOfWeek.toUpperCase(),
              startTime: schedule.startTime,
              endTime: schedule.endTime,
              room: schedule.room,
            })),
          }
        : {
            ...emptyCourse,
            subjectId: subjects[0]?.id || "",
            semesterId: semesters[0]?.id || "",
            instructorId: instructors[0]?.id || "",
          }
    );
    setModalTab("courses");
  };

  const handleDepartmentSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setSaving(true);
    try {
      if (editingDepartment) {
        await departmentsApi.update(editingDepartment.id, departmentForm);
      } else {
        await departmentsApi.create(departmentForm);
      }
      resetModals();
      await loadAcademicData();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការរក្សាទុកមហាវិទ្យាល័យបរាជ័យ។");
    } finally {
      setSaving(false);
    }
  };

  const handleSubjectSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setSaving(true);
    try {
      if (editingSubject) {
        await subjectsApi.update(editingSubject.id, subjectForm);
      } else {
        await subjectsApi.create(subjectForm);
      }
      resetModals();
      await loadAcademicData();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការរក្សាទុកមុខវិជ្ជាបរាជ័យ។");
    } finally {
      setSaving(false);
    }
  };

  const handleSemesterSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setSaving(true);
    try {
      if (editingSemester) {
        await semestersApi.update(editingSemester.id, semesterForm);
      } else {
        await semestersApi.create(semesterForm);
      }
      resetModals();
      await loadAcademicData();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការរក្សាទុកឆមាសបរាជ័យ។");
    } finally {
      setSaving(false);
    }
  };

  const handleClassSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setSaving(true);
    try {
      if (editingClass) {
        await classesApi.update(editingClass.id, classForm);
      } else {
        await classesApi.create(classForm);
      }
      resetModals();
      await loadAcademicData();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការរក្សាទុកថ្នាក់រៀនបរាជ័យ។");
    } finally {
      setSaving(false);
    }
  };

  const handleCourseSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setSaving(true);
    try {
      if (editingCourse) {
        await coursesApi.update(editingCourse, courseForm);
      } else {
        await coursesApi.create(courseForm);
      }
      resetModals();
      await loadAcademicData();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការរក្សាទុកវគ្គសិក្សាបរាជ័យ។");
    } finally {
      setSaving(false);
    }
  };

  const removeDepartment = async (department: Department) => {
    if (!window.confirm(`តើអ្នកចង់លុបមហាវិទ្យាល័យ ${department.name} មែនទេ?`)) return;
    try {
      await departmentsApi.remove(department.id);
      await loadAcademicData();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការលុបមហាវិទ្យាល័យបរាជ័យ។");
    }
  };

  const removeSubject = async (subject: Subject) => {
    if (!window.confirm(`តើអ្នកចង់លុបមុខវិជ្ជា ${subject.name} មែនទេ?`)) return;
    try {
      await subjectsApi.remove(subject.id);
      await loadAcademicData();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការលុបមុខវិជ្ជាបរាជ័យ។");
    }
  };

  const removeSemester = async (semester: Semester) => {
    if (!window.confirm(`តើអ្នកចង់លុបឆមាស ${semester.name} មែនទេ?`)) return;
    try {
      await semestersApi.remove(semester.id);
      await loadAcademicData();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការលុបឆមាសបរាជ័យ។");
    }
  };

  const removeClass = async (clazz: AcademicClass) => {
    if (!window.confirm(`តើអ្នកចង់លុបថ្នាក់រៀន ${clazz.name} មែនទេ?`)) return;
    try {
      await classesApi.remove(clazz.id);
      await loadAcademicData();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការលុបថ្នាក់រៀនបរាជ័យ។");
    }
  };

  const removeCourse = async (course: Course) => {
    if (!window.confirm(`តើអ្នកចង់លុបវគ្គសិក្សា ${course.name} មែនទេ?`)) return;
    try {
      await coursesApi.remove(course);
      await loadAcademicData();
    } catch (err) {
      setError(err instanceof Error ? err.message : "ការលុបវគ្គសិក្សាបរាជ័យ។");
    }
  };

  const updateCourseSchedule = <K extends keyof CourseSchedule>(index: number, key: K, value: CourseSchedule[K]) => {
    setCourseForm((current) => ({
      ...current,
      schedules: current.schedules.map((schedule, scheduleIndex) =>
        scheduleIndex === index ? { ...schedule, [key]: value } : schedule
      ),
    }));
  };

  const addCourseSchedule = () => {
    setCourseForm((current) => ({
      ...current,
      schedules: [
        ...current.schedules,
        {
          dayOfWeek: "MONDAY",
          startTime: "09:00",
          endTime: "11:00",
          room: 101,
        },
      ],
    }));
  };

  const removeCourseSchedule = (index: number) => {
    setCourseForm((current) => ({
      ...current,
      schedules: current.schedules.filter((_, scheduleIndex) => scheduleIndex !== index),
    }));
  };

  const departmentById = (id: string) => departments.find((department) => department.id === id);
  const subjectById = (id: string) => subjects.find((subject) => subject.id === id);
  const semesterById = (id: string) => semesters.find((semester) => semester.id === id);

  const currentTab = tabs.find((tab) => tab.key === activeTab)!;

  const renderTable = () => {
    switch (activeTab) {
      case "departments":
        return (
          <Table
            isLoading={loading}
            data={departments}
            columns={[
              { key: "code", label: "លេខកូដ" },
              { key: "name", label: "ឈ្មោះ" },
              { key: "description", label: "ការពិពណ៌នា" },
              {
                key: "actions",
                label: "សកម្មភាព",
                render: (department: Department) => (
                  <div className="flex items-center gap-2">
                    <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => openDepartmentModal(department)}>
                      <Pencil size={16} className="text-sky-600" />
                    </Button>
                    <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => void removeDepartment(department)}>
                      <Trash2 size={16} className="text-rose-600" />
                    </Button>
                  </div>
                ),
              },
            ]}
          />
        );
      case "subjects":
        return (
          <Table
            isLoading={loading}
            data={subjects}
            columns={[
              { key: "code", label: "លេខកូដ" },
              { key: "name", label: "ឈ្មោះ" },
              {
                key: "departmentId",
                label: "មហាវិទ្យាល័យ",
                render: (subject: Subject) => departmentById(subject.departmentId)?.name || subject.departmentId,
              },
              { key: "description", label: "ការពិពណ៌នា" },
              {
                key: "actions",
                label: "សកម្មភាព",
                render: (subject: Subject) => (
                  <div className="flex items-center gap-2">
                    <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => openSubjectModal(subject)}>
                      <Pencil size={16} className="text-sky-600" />
                    </Button>
                    <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => void removeSubject(subject)}>
                      <Trash2 size={16} className="text-rose-600" />
                    </Button>
                  </div>
                ),
              },
            ]}
          />
        );
      case "semesters":
        return (
          <Table
            isLoading={loading}
            data={semesters}
            columns={[
              { key: "name", label: "ឈ្មោះ" },
              { key: "startDate", label: "ថ្ងៃចាប់ផ្តើម" },
              { key: "endDate", label: "ថ្ងៃបញ្ចប់" },
              { key: "description", label: "ការពិពណ៌នា" },
              {
                key: "actions",
                label: "សកម្មភាព",
                render: (semester: Semester) => (
                  <div className="flex items-center gap-2">
                    <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => openSemesterModal(semester)}>
                      <Pencil size={16} className="text-sky-600" />
                    </Button>
                    <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => void removeSemester(semester)}>
                      <Trash2 size={16} className="text-rose-600" />
                    </Button>
                  </div>
                ),
              },
            ]}
          />
        );
      case "classes":
        return (
          <Table
            isLoading={loading}
            data={classes}
            columns={[
              { key: "name", label: "ថ្នាក់" },
              {
                key: "departmentId",
                label: "មហាវិទ្យាល័យ",
                render: (clazz: AcademicClass) => departmentById(clazz.departmentId)?.name || clazz.departmentId,
              },
              { key: "academicYear", label: "ឆ្នាំសិក្សា" },
              { key: "generation", label: "ជំនាន់" },
              {
                key: "actions",
                label: "សកម្មភាព",
                render: (clazz: AcademicClass) => (
                  <div className="flex items-center gap-2">
                    <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => openClassModal(clazz)}>
                      <Pencil size={16} className="text-sky-600" />
                    </Button>
                    <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => void removeClass(clazz)}>
                      <Trash2 size={16} className="text-rose-600" />
                    </Button>
                  </div>
                ),
              },
            ]}
          />
        );
      case "courses":
        return (
          <Table
            isLoading={loading}
            data={courses}
            columns={[
              { key: "name", label: "វគ្គសិក្សា" },
              {
                key: "subjectName",
                label: "មុខវិជ្ជា",
                render: (course: Course) => course.subjectName || subjectById(course.subjectId)?.name || course.subjectId,
              },
              {
                key: "semesterName",
                label: "ឆមាស",
                render: (course: Course) => course.semesterName || semesterById(course.semesterId)?.name || course.semesterId,
              },
              { key: "instructorName", label: "គ្រូបង្រៀន" },
              {
                key: "schedule",
                label: "កាលវិភាគ",
                render: (course: Course) =>
                  course.schedules.length === 0 ? (
                    <span className="text-slate-400">គ្មានកាលវិភាគ</span>
                  ) : (
                    <div className="flex flex-col gap-1">
                      {course.schedules.map((s, i) => (
                        <span key={i} className="text-[12px] bg-slate-50 px-2 py-0.5 rounded-md border border-slate-200">
                          {s.dayOfWeek} {s.startTime}-{s.endTime} P{s.room}
                        </span>
                      ))}
                    </div>
                  ),
              },
              {
                key: "actions",
                label: "សកម្មភាព",
                render: (course: Course) => (
                  <div className="flex items-center gap-2">
                    <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => openCourseModal(course)}>
                      <Pencil size={16} className="text-sky-600" />
                    </Button>
                    <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => void removeCourse(course)}>
                      <Trash2 size={16} className="text-rose-600" />
                    </Button>
                  </div>
                ),
              },
            ]}
          />
        );
    }
  };

  const renderModal = () => {
    switch (modalTab) {
      case "departments":
        return (
          <Modal isOpen onClose={resetModals} title={editingDepartment ? "កែប្រែមហាវិទ្យាល័យ" : "បង្កើតមហាវិទ្យាល័យ"}>
            <form onSubmit={handleDepartmentSubmit} className="space-y-4">
              <div>
                <label className="mb-2 block text-[13px] font-medium text-slate-700">លេខកូដ</label>
                <Input className="" value={departmentForm.code} onChange={(e) => setDepartmentForm((c) => ({ ...c, code: e.target.value }))} required />
              </div>
              <div>
                <label className="mb-2 block text-[13px] font-medium text-slate-700">ឈ្មោះបញ្ជាក់</label>
                <Input className="" value={departmentForm.name} onChange={(e) => setDepartmentForm((c) => ({ ...c, name: e.target.value }))} required />
              </div>
              <div>
                <label className="mb-2 block text-[13px] font-medium text-slate-700">ការពិពណ៌នា</label>
                <Input
                  className=""
                  value={departmentForm.description}
                  onChange={(e) => setDepartmentForm((c) => ({ ...c, description: e.target.value }))}
                  required
                />
              </div>
              <div className="flex justify-end gap-3 pt-4 border-t border-slate-200 mt-6">
                <Button className="" type="button" variant="outline" onClick={resetModals}>
                  បោះបង់
                </Button>
                <Button className="" type="submit" disabled={saving}>
                  {saving ? "កំពុងរក្សាទុក..." : "រក្សាទុកមហាវិទ្យាល័យ"}
                </Button>
              </div>
            </form>
          </Modal>
        );
      case "subjects":
        return (
          <Modal isOpen onClose={resetModals} title={editingSubject ? "កែប្រែមុខវិជ្ជា" : "បង្កើតមុខវិជ្ជា"}>
            <form onSubmit={handleSubjectSubmit} className="space-y-4">
              <div>
                <label className="mb-2 block text-[13px] font-medium text-slate-700">មហាវិទ្យាល័យ</label>
                <select
                  className={selectClassName}
                  value={subjectForm.departmentId}
                  onChange={(e) => setSubjectForm((c) => ({ ...c, departmentId: e.target.value }))}
                >
                  {departments.map((department) => (
                    <option key={department.id} value={department.id}>
                      {department.name}
                    </option>
                  ))}
                </select>
              </div>
              <div>
                <label className="mb-2 block text-[13px] font-medium text-slate-700">លេខកូដ</label>
                <Input className="" value={subjectForm.code} onChange={(e) => setSubjectForm((c) => ({ ...c, code: e.target.value }))} required />
              </div>
              <div>
                <label className="mb-2 block text-[13px] font-medium text-slate-700">ឈ្មោះបញ្ជាក់</label>
                <Input className="" value={subjectForm.name} onChange={(e) => setSubjectForm((c) => ({ ...c, name: e.target.value }))} required />
              </div>
              <div>
                <label className="mb-2 block text-[13px] font-medium text-slate-700">ការពិពណ៌នា</label>
                <Input className="" value={subjectForm.description} onChange={(e) => setSubjectForm((c) => ({ ...c, description: e.target.value }))} required />
              </div>
              <div className="flex justify-end gap-3 pt-4 border-t border-slate-200 mt-6">
                <Button className="" type="button" variant="outline" onClick={resetModals}>
                  បោះបង់
                </Button>
                <Button className="" type="submit" disabled={saving}>
                  {saving ? "កំពុងរក្សាទុក..." : "រក្សាទុកមុខវិជ្ជា"}
                </Button>
              </div>
            </form>
          </Modal>
        );
      case "semesters":
        return (
          <Modal isOpen onClose={resetModals} title={editingSemester ? "កែប្រែឆមាស" : "បង្កើតឆមាស"}>
            <form onSubmit={handleSemesterSubmit} className="space-y-4">
              <div>
                <label className="mb-2 block text-[13px] font-medium text-slate-700">ឈ្មោះ</label>
                <Input className="" value={semesterForm.name} onChange={(e) => setSemesterForm((c) => ({ ...c, name: e.target.value }))} required />
              </div>
              <div className="grid gap-4 md:grid-cols-2">
                <div>
                  <label className="mb-2 block text-[13px] font-medium text-slate-700">ថ្ងៃចាប់ផ្តើម</label>
                  <Input className="" type="date" value={semesterForm.startDate} onChange={(e) => setSemesterForm((c) => ({ ...c, startDate: e.target.value }))} required />
                </div>
                <div>
                  <label className="mb-2 block text-[13px] font-medium text-slate-700">ថ្ងៃបញ្ចប់</label>
                  <Input className="" type="date" value={semesterForm.endDate} onChange={(e) => setSemesterForm((c) => ({ ...c, endDate: e.target.value }))} required />
                </div>
              </div>
              <div>
                <label className="mb-2 block text-[13px] font-medium text-slate-700">ការពិពណ៌នា</label>
                <Input
                  className=""
                  value={semesterForm.description}
                  onChange={(e) => setSemesterForm((c) => ({ ...c, description: e.target.value }))}
                  required
                />
              </div>
              <div className="flex justify-end gap-3 pt-4 border-t border-slate-200 mt-6">
                <Button className="" type="button" variant="outline" onClick={resetModals}>
                  បោះបង់
                </Button>
                <Button className="" type="submit" disabled={saving}>
                  {saving ? "កំពុងរក្សាទុក..." : "រក្សាទុកឆមាស"}
                </Button>
              </div>
            </form>
          </Modal>
        );
      case "classes":
        return (
          <Modal isOpen onClose={resetModals} title={editingClass ? "កែប្រែថ្នាក់រៀន" : "បង្កើតថ្នាក់រៀន"}>
            <form onSubmit={handleClassSubmit} className="space-y-4">
              <div>
                <label className="mb-2 block text-[13px] font-medium text-slate-700">មហាវិទ្យាល័យ</label>
                <select
                  className={selectClassName}
                  value={classForm.departmentId}
                  onChange={(e) => setClassForm((c) => ({ ...c, departmentId: e.target.value }))}
                >
                  {departments.map((department) => (
                    <option key={department.id} value={department.id}>
                      {department.name}
                    </option>
                  ))}
                </select>
              </div>
              <div className="grid gap-4 md:grid-cols-2">
                <div>
                  <label className="mb-2 block text-[13px] font-medium text-slate-700">ឈ្មោះថ្នាក់</label>
                  <Input className="" value={classForm.name} onChange={(e) => setClassForm((c) => ({ ...c, name: e.target.value }))} required />
                </div>
                <div>
                  <label className="mb-2 block text-[13px] font-medium text-slate-700">ឆ្នាំសិក្សា</label>
                  <Input
                    className=""
                    value={classForm.academicYear}
                    onChange={(e) => setClassForm((c) => ({ ...c, academicYear: e.target.value }))}
                    required
                  />
                </div>
              </div>
              <div>
                <label className="mb-2 block text-[13px] font-medium text-slate-700">ជំនាន់</label>
                <Input
                  className=""
                  type="number"
                  value={classForm.generation}
                  onChange={(e) => setClassForm((c) => ({ ...c, generation: Number(e.target.value) }))}
                  required
                />
              </div>
              <div className="flex justify-end gap-3 pt-4 border-t border-slate-200 mt-6">
                <Button className="" type="button" variant="outline" onClick={resetModals}>
                  បោះបង់
                </Button>
                <Button className="" type="submit" disabled={saving}>
                  {saving ? "កំពុងរក្សាទុក..." : "រក្សាទុកថ្នាក់រៀន"}
                </Button>
              </div>
            </form>
          </Modal>
        );
      case "courses":
        return (
          <Modal isOpen onClose={resetModals} title={editingCourse ? "កែប្រែវគ្គសិក្សា" : "បង្កើតវគ្គសិក្សា"}>
            <form onSubmit={handleCourseSubmit} className="space-y-4">
              <div className="grid gap-4 md:grid-cols-2">
                <div>
                  <label className="mb-2 block text-[13px] font-medium text-slate-700">មុខវិជ្ជា</label>
                  <select
                    className={selectClassName}
                    value={courseForm.subjectId}
                    disabled={Boolean(editingCourse)}
                    onChange={(e) => setCourseForm((c) => ({ ...c, subjectId: e.target.value }))}
                  >
                    {subjects.map((subject) => (
                      <option key={subject.id} value={subject.id}>
                        {subject.name}
                      </option>
                    ))}
                  </select>
                </div>
                <div>
                  <label className="mb-2 block text-[13px] font-medium text-slate-700">ឆមាស</label>
                  <select
                    className={selectClassName}
                    value={courseForm.semesterId}
                    disabled={Boolean(editingCourse)}
                    onChange={(e) => setCourseForm((c) => ({ ...c, semesterId: e.target.value }))}
                  >
                    {semesters.map((semester) => (
                      <option key={semester.id} value={semester.id}>
                        {semester.name}
                      </option>
                    ))}
                  </select>
                </div>
              </div>

              <div>
                <label className="mb-2 block text-[13px] font-medium text-slate-700">គ្រូបង្រៀន</label>
                <select
                  className={selectClassName}
                  value={courseForm.instructorId}
                  onChange={(e) => setCourseForm((c) => ({ ...c, instructorId: e.target.value }))}
                >
                  {instructors.map((user) => (
                    <option key={user.id} value={user.id}>
                      {user.fullName}
                    </option>
                  ))}
                </select>
              </div>

              <div>
                <label className="mb-2 block text-[13px] font-medium text-slate-700">ឈ្មោះវគ្គសិក្សា</label>
                <Input className="" value={courseForm.name} onChange={(e) => setCourseForm((c) => ({ ...c, name: e.target.value }))} required />
              </div>
              <div>
                <label className="mb-2 block text-[13px] font-medium text-slate-700">ការពិពណ៌នា</label>
                <Input className="" value={courseForm.description} onChange={(e) => setCourseForm((c) => ({ ...c, description: e.target.value }))} required />
              </div>

              <div className="grid gap-4 md:grid-cols-2">
                <div>
                  <label className="mb-2 block text-[13px] font-medium text-slate-700">ថ្ងៃចាប់ផ្តើម</label>
                  <Input className="" type="date" value={courseForm.startAt} onChange={(e) => setCourseForm((c) => ({ ...c, startAt: e.target.value }))} required />
                </div>
                <div>
                  <label className="mb-2 block text-[13px] font-medium text-slate-700">ថ្ងៃបញ្ចប់</label>
                  <Input className="" type="date" value={courseForm.endAt} onChange={(e) => setCourseForm((c) => ({ ...c, endAt: e.target.value }))} required />
                </div>
              </div>

              <div className="rounded-md border border-slate-200 bg-slate-50 p-4">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-medium text-slate-900">កាលវិភាគ</p>
                    <p className="mt-1 text-[11px] uppercase tracking-wider text-slate-500 font-medium">
                      វគ្គសិក្សាមួយអាចមានម៉ោងសិក្សាច្រើនបន្តបន្ទាប់គ្នា។
                    </p>
                  </div>
                  <Button type="button" variant="outline" className="h-8 text-[12px]" onClick={addCourseSchedule}>
                    <Plus size={14} className="mr-2" />
                    បន្ថែមម៉ោងសិក្សា
                  </Button>
                </div>

                <div className="mt-4 space-y-4">
                  {courseForm.schedules.map((schedule, index) => (
                    <div key={`${schedule.dayOfWeek}-${index}`} className="rounded-md border border-slate-200 bg-white p-4">
                      <div className="grid gap-4 md:grid-cols-4">
                        <div>
                          <label className="mb-2 block text-[13px] font-medium text-slate-700">ថ្ងៃ</label>
                          <select
                            className={selectClassName}
                            value={schedule.dayOfWeek}
                            onChange={(e) => updateCourseSchedule(index, "dayOfWeek", e.target.value)}
                          >
                            {["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"].map((day) => (
                              <option key={day} value={day}>
                                {{"MONDAY": "ច័ន្ទ", "TUESDAY": "អង្គារ", "WEDNESDAY": "ពុធ", "THURSDAY": "ព្រហស្បតិ៍", "FRIDAY": "សុក្រ", "SATURDAY": "សៅរ៍", "SUNDAY": "អាទិត្យ"}[day] || day}
                              </option>
                            ))}
                          </select>
                        </div>
                        <div>
                          <label className="mb-2 block text-[13px] font-medium text-slate-700">ម៉ោងចាប់ផ្តើម</label>
                          <Input
                            className=""
                            type="time"
                            value={schedule.startTime}
                            onChange={(e) => updateCourseSchedule(index, "startTime", e.target.value)}
                            required
                          />
                        </div>
                        <div>
                          <label className="mb-2 block text-[13px] font-medium text-slate-700">ម៉ោងបញ្ចប់</label>
                          <Input
                            className=""
                            type="time"
                            value={schedule.endTime}
                            onChange={(e) => updateCourseSchedule(index, "endTime", e.target.value)}
                            required
                          />
                        </div>
                        <div>
                          <label className="mb-2 block text-[13px] font-medium text-slate-700">បន្ទប់</label>
                          <Input
                            className=""
                            type="number"
                            value={schedule.room}
                            onChange={(e) => updateCourseSchedule(index, "room", Number(e.target.value))}
                            required
                          />
                        </div>
                      </div>

                      {courseForm.schedules.length > 1 && (
                        <div className="mt-4 flex justify-end">
                          <Button className="h-8 text-[12px]" type="button" variant="ghost" onClick={() => removeCourseSchedule(index)}>
                            <Trash2 size={14} className="mr-2 text-rose-600" />
                            លុបម៉ោងសិក្សា
                          </Button>
                        </div>
                      )}
                    </div>
                  ))}
                </div>
              </div>

              <div className="flex justify-end gap-3 pt-4 border-t border-slate-200 mt-6">
                <Button className="" type="button" variant="outline" onClick={resetModals}>
                  បោះបង់
                </Button>
                <Button className="" type="submit" disabled={saving}>
                  {saving ? "កំពុងរក្សាទុក..." : "រក្សាទុកវគ្គសិក្សា"}
                </Button>
              </div>
            </form>
          </Modal>
        );
      default:
        return null;
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
        <h1 className="text-2xl font-bold tracking-tight text-slate-900">ទិន្នន័យមេនៃការសិក្សា</h1>
      </div>

      {error && <div className="rounded-md border border-slate-200 bg-rose-50 p-3 text-sm text-rose-700">{error}</div>}

      <section className="rounded-lg border border-slate-200 bg-white shadow-sm flex flex-col overflow-hidden">
        <div className="border-b border-slate-200 flex items-center justify-between px-1 bg-slate-50/50">
          <nav className="flex items-center gap-0 overflow-x-auto">
            {tabs.map(({ key, label, icon: Icon }) => (
              <button
                key={key}
                onClick={() => setActiveTab(key)}
                className={`flex items-center gap-2 px-4 py-3 text-sm font-medium border-b-2 transition whitespace-nowrap ${
                  activeTab === key
                    ? "border-teal-600 text-teal-700"
                    : "border-transparent text-slate-500 hover:text-slate-700 hover:border-slate-300"
                }`}
              >
                <Icon size={16} />
                {label}
              </button>
            ))}
          </nav>
          <div className="pr-3">
            <Button
              size="sm"
              onClick={() => {
                switch (activeTab) {
                  case "departments":
                    openDepartmentModal();
                    break;
                  case "subjects":
                    openSubjectModal();
                    break;
                  case "semesters":
                    openSemesterModal();
                    break;
                  case "classes":
                    openClassModal();
                    break;
                  case "courses":
                    openCourseModal();
                    break;
                }
              }}
            >
              <Plus size={14} className="mr-1.5" />
              បន្ថែម{currentTab.singular}
            </Button>
          </div>
        </div>

        {renderTable()}
      </section>

      {modalTab && renderModal()}
    </div>
  );
}
