import apiClient from "@/services/apiClient";
import type {
  AcademicClass,
  AuthSession,
  Course,
  CourseSchedule,
  Department,
  Permission,
  Role,
  Semester,
  Student,
  StudentAddress,
  StudentStatistics,
  Subject,
  User,
} from "@/types/models";
import type { MultipleResponse, PaginatedResult, SingleResponse } from "@/types/api";

type DepartmentResponse = Department;
type PermissionResponse = Permission;
type RoleResponse = Role;
type SubjectResponse = Subject;
type SemesterResponse = {
  id: string;
  name: string;
  startDate: string;
  endDate: string;
  description: string;
};
type ClassResponse = {
  id: string;
  name: string;
  department_id: string;
  academic_year: string;
  generation: number;
  creation_at?: string;
  updated_at?: string;
};
type CourseScheduleResponse = CourseSchedule;
type CourseResponse = {
  subjectId: string;
  subjectName?: string | null;
  semesterId: string;
  semesterName?: string | null;
  instructorId: string;
  instructorName?: string | null;
  name: string;
  description: string;
  schedule: string;
  schedules: CourseScheduleResponse[];
  startAt: string;
  endAt: string;
};
type StudentAddressResponse = {
  id?: string;
  houseNumber: string;
  street: string;
  sangkat: string;
  khan: string;
  province: string;
  country: string;
};
type StudentResponse = {
  id: string;
  student_code: string;
  kh_first_name: string;
  kh_last_name: string;
  en_first_name: string;
  en_last_name: string;
  gender: string;
  date_of_birth: string;
  enrollment_date: string;
  email?: string | null;
  phone_number?: string | null;
  address?: StudentAddressResponse | null;
  status: boolean;
};
type UserResponse = {
  id: string;
  fullName: string;
  email: string;
  verified: boolean;
  verificationToken?: string | null;
  refreshToken?: string | null;
  status: boolean;
  role: string;
};
type RefreshTokenResponse = {
  token: string;
};

export interface PageQuery {
  page?: number;
  size?: number;
  sortBy?: string;
  ascending?: boolean;
}

export interface DepartmentInput {
  name: string;
  code: string;
  description: string;
}

export interface SubjectInput {
  departmentId: string;
  name: string;
  description: string;
  code: string;
}

export interface SemesterInput {
  name: string;
  startDate: string;
  endDate: string;
  description: string;
}

export interface ClassInput {
  name: string;
  departmentId: string;
  academicYear: string;
  generation: number;
}

export interface CourseInput {
  subjectId: string;
  semesterId: string;
  instructorId: string;
  name: string;
  description: string;
  startAt: string;
  endAt: string;
  schedules: CourseSchedule[];
}

export interface StudentInput {
  classId: string;
  khFirstName: string;
  khLastName: string;
  enFirstName: string;
  enLastName: string;
  gender: string;
  dateOfBirth: string;
  email: string;
  phoneNumber: string;
  enrollmentDate: string;
  address: StudentAddress;
}

export interface UserCreateInput {
  fullName: string;
  email: string;
  password: string;
  role: string;
}

export interface RoleInput {
  name: string;
  description: string;
  status?: string;
  userIds: string[];
}

export interface PermissionInput {
  name: string;
  description: string;
  module: string;
  roleIds: string[];
}

function unwrapSingle<T>(response: SingleResponse<T>): T {
  return response.data;
}

function unwrapPage<T>(response: MultipleResponse<T>): PaginatedResult<T> {
  return {
    items: response.content,
    page: response.number,
    size: response.size,
    totalPages: response.totalPage,
    totalElements: response.totalElement,
    hasPrevious: response.hastPrevious,
    hasNext: response.hastNext,
  };
}

function toDepartment(item: DepartmentResponse): Department {
  return item;
}

function toPermission(item: PermissionResponse): Permission {
  return item;
}

function toRole(item: RoleResponse): Role {
  return item;
}

function toSubject(item: SubjectResponse): Subject {
  return item;
}

function toSemester(item: SemesterResponse): Semester {
  return item;
}

function toClass(item: ClassResponse): AcademicClass {
  return {
    id: item.id,
    name: item.name,
    departmentId: item.department_id,
    academicYear: item.academic_year,
    generation: item.generation,
    creationAt: item.creation_at,
    updatedAt: item.updated_at,
  };
}

function toCourse(item: CourseResponse): Course {
  return item;
}

function toStudentAddress(item?: StudentAddressResponse | null): StudentAddress | null {
  if (!item) return null;
  return item;
}

function toStudent(item: StudentResponse): Student {
  return {
    id: item.id,
    studentCode: item.student_code,
    khFirstName: item.kh_first_name,
    khLastName: item.kh_last_name,
    enFirstName: item.en_first_name,
    enLastName: item.en_last_name,
    gender: item.gender,
    dateOfBirth: item.date_of_birth,
    enrollmentDate: item.enrollment_date,
    email: item.email,
    phoneNumber: item.phone_number,
    status: item.status,
    address: toStudentAddress(item.address),
  };
}

function toUser(item: UserResponse): User {
  return item;
}

function toPageParams(query?: PageQuery) {
  return {
    page: query?.page ?? 0,
    size: query?.size ?? 20,
    sortBy: query?.sortBy ?? "id",
    ascending: query?.ascending ?? true,
  };
}

export const authApi = {
  async refreshAccessToken(refreshToken: string) {
    const response = await apiClient.request<SingleResponse<RefreshTokenResponse>>({
      url: "/auth/refresh-token",
      method: "GET",
      data: { token: refreshToken },
      headers: {
        "Content-Type": "application/json",
      },
    });

    return unwrapSingle(response.data).token;
  },
  async signIn(email: string, password: string): Promise<AuthSession> {
    const response = await apiClient.post<SingleResponse<UserResponse>>("/auth/signin", { email, password });
    const user = toUser(unwrapSingle(response.data));
    let accessToken = user.verificationToken || "";

    if (!accessToken && user.refreshToken) {
      accessToken = await authApi.refreshAccessToken(user.refreshToken);
    }

    if (!accessToken) {
      throw new Error("Login succeeded but no access token was returned by the backend.");
    }

    return { accessToken, user };
  },
  async verifyEmail(token: string) {
    const response = await apiClient.get<SingleResponse<UserResponse>>("/auth/signup/verify", { params: { token } });
    return toUser(unwrapSingle(response.data));
  },
};

export const usersApi = {
  async list(query?: PageQuery) {
    const response = await apiClient.get<MultipleResponse<UserResponse>>("/users", { params: toPageParams(query) });
    const page = unwrapPage(response.data);
    return {
      ...page,
      items: page.items.map(toUser),
    };
  },
  async create(input: UserCreateInput) {
    const response = await apiClient.post<SingleResponse<UserResponse>>("/auth/signup", input);
    return toUser(unwrapSingle(response.data));
  },
  async delete(id: string) {
    await apiClient.delete(`/users/${id}`);
  },
  async updateStatus(id: string, status: boolean) {
    await apiClient.put(`/users/update-status/${id}`, null, { params: { status } });
  },
  async sendResetOtp(email: string) {
    const response = await apiClient.post<SingleResponse<Record<string, unknown>>>("/users/send-otp", { email });
    return unwrapSingle(response.data);
  },
  async verifyResetOtp(otp: string) {
    const response = await apiClient.post<SingleResponse<Record<string, unknown>>>("/users/verify-otp", { otp });
    return unwrapSingle(response.data);
  },
  async resetPassword(password: string) {
    const response = await apiClient.post<SingleResponse<UserResponse>>("/users/reset-password", { password });
    return toUser(unwrapSingle(response.data));
  },
  async isAuthenticated() {
    const response = await apiClient.get<boolean>("/users/is-authenticated");
    return response.data;
  },
};

export const rolesApi = {
  async list() {
    const response = await apiClient.get<SingleResponse<RoleResponse[]>>("/roles");
    return unwrapSingle(response.data).map(toRole);
  },
  async listByStatus(status: string) {
    const response = await apiClient.get<SingleResponse<RoleResponse[]>>("/roles/", { params: { status } });
    return unwrapSingle(response.data).map(toRole);
  },
  async getById(id: string) {
    const response = await apiClient.get<SingleResponse<RoleResponse>>(`/roles/${id}`);
    return toRole(unwrapSingle(response.data));
  },
  async create(input: RoleInput) {
    const response = await apiClient.post<SingleResponse<RoleResponse>>("/roles", input);
    return toRole(unwrapSingle(response.data));
  },
  async update(id: string, input: RoleInput) {
    const response = await apiClient.put<SingleResponse<RoleResponse>>(`/roles/${id}`, input);
    return toRole(unwrapSingle(response.data));
  },
  async updateStatus(id: string, status: string) {
    await apiClient.request({
      url: `/roles/status/${id}`,
      method: "PUT",
      params: { status },
      data: { status },
      headers: {
        "Content-Type": "application/json",
      },
    });
  },
  async assignPermissions(roleId: string, permissionIds: string[]) {
    const response = await apiClient.post<SingleResponse<RoleResponse>>(`/roles/${roleId}/permission`, {
      permissionIds,
    });
    return toRole(unwrapSingle(response.data));
  },
  async replacePermissions(roleId: string, permissionIds: string[]) {
    const response = await apiClient.put<SingleResponse<RoleResponse>>(`/roles/${roleId}/permission`, {
      permissionIds,
    });
    return toRole(unwrapSingle(response.data));
  },
  async removePermission(roleId: string, permissionId: string) {
    await apiClient.delete(`/roles/${roleId}/permission/${permissionId}`);
  },
};

export const permissionsApi = {
  async list() {
    const response = await apiClient.get<SingleResponse<PermissionResponse[]>>("/permissions");
    return unwrapSingle(response.data).map(toPermission);
  },
  async listByModule(module: string) {
    const response = await apiClient.get<SingleResponse<PermissionResponse[]>>(`/permissions/module/${module}`);
    return unwrapSingle(response.data).map(toPermission);
  },
  async getById(id: string) {
    const response = await apiClient.get<SingleResponse<PermissionResponse>>(`/permissions/${id}`);
    return toPermission(unwrapSingle(response.data));
  },
  async create(input: PermissionInput) {
    const response = await apiClient.post<SingleResponse<PermissionResponse>>("/permissions", input);
    return toPermission(unwrapSingle(response.data));
  },
  async update(id: string, input: PermissionInput) {
    const response = await apiClient.put<SingleResponse<PermissionResponse>>(`/permissions/${id}`, input);
    return toPermission(unwrapSingle(response.data));
  },
  async remove(id: string) {
    await apiClient.delete(`/permissions/${id}`);
  },
};

export const departmentsApi = {
  async list() {
    const response = await apiClient.get<SingleResponse<DepartmentResponse[]>>("/departments");
    return unwrapSingle(response.data).map(toDepartment);
  },
  async create(input: DepartmentInput) {
    const response = await apiClient.post<SingleResponse<DepartmentResponse>>("/departments", input);
    return toDepartment(unwrapSingle(response.data));
  },
  async update(id: string, input: DepartmentInput) {
    const response = await apiClient.put<SingleResponse<DepartmentResponse>>(`/departments/${id}`, input);
    return toDepartment(unwrapSingle(response.data));
  },
  async remove(id: string) {
    await apiClient.delete(`/departments/${id}`);
  },
};

export const subjectsApi = {
  async list() {
    const response = await apiClient.get<SingleResponse<SubjectResponse[]>>("/subjects");
    return unwrapSingle(response.data).map(toSubject);
  },
  async create(input: SubjectInput) {
    const response = await apiClient.post<SingleResponse<SubjectResponse>>("/subjects", input);
    return toSubject(unwrapSingle(response.data));
  },
  async update(id: string, input: SubjectInput) {
    const response = await apiClient.put<SingleResponse<SubjectResponse>>(`/subjects/${id}`, input);
    return toSubject(unwrapSingle(response.data));
  },
  async remove(id: string) {
    await apiClient.delete(`/subjects/${id}`);
  },
};

export const semestersApi = {
  async list() {
    const response = await apiClient.get<SingleResponse<SemesterResponse[]>>("/semesters");
    return unwrapSingle(response.data).map(toSemester);
  },
  async create(input: SemesterInput) {
    const response = await apiClient.post<SingleResponse<SemesterResponse>>("/semesters", input);
    return toSemester(unwrapSingle(response.data));
  },
  async update(id: string, input: SemesterInput) {
    const response = await apiClient.put<SingleResponse<SemesterResponse>>(`/semesters/${id}`, input);
    return toSemester(unwrapSingle(response.data));
  },
  async remove(id: string) {
    await apiClient.delete(`/semesters/${id}`);
  },
};

export const classesApi = {
  async list(query?: PageQuery) {
    const response = await apiClient.get<MultipleResponse<ClassResponse>>("/classes", { params: toPageParams(query) });
    const page = unwrapPage(response.data);
    return {
      ...page,
      items: page.items.map(toClass),
    };
  },
  async create(input: ClassInput) {
    const response = await apiClient.post<SingleResponse<ClassResponse>>("/classes", input);
    return toClass(unwrapSingle(response.data));
  },
  async update(id: string, input: ClassInput) {
    const response = await apiClient.put<SingleResponse<ClassResponse>>(`/classes/${id}`, input);
    return toClass(unwrapSingle(response.data));
  },
  async remove(id: string) {
    await apiClient.delete(`/classes/${id}`);
  },
};

export const coursesApi = {
  async list(query?: PageQuery) {
    const response = await apiClient.get<MultipleResponse<CourseResponse>>("/courses", {
      params: { ...toPageParams(query), sortBy: query?.sortBy ?? "name" },
    });
    const page = unwrapPage(response.data);
    return {
      ...page,
      items: page.items.map(toCourse),
    };
  },
  async create(input: CourseInput) {
    const response = await apiClient.post<SingleResponse<CourseResponse>>("/courses", input);
    return toCourse(unwrapSingle(response.data));
  },
  async update(course: Course, input: CourseInput) {
    const response = await apiClient.put<SingleResponse<CourseResponse>>(
      `/courses/${course.semesterId}/${course.subjectId}`,
      input
    );
    return toCourse(unwrapSingle(response.data));
  },
  async remove(course: Course) {
    await apiClient.delete(`/courses/${course.semesterId}/${course.subjectId}`);
  },
};

export const studentsApi = {
  async list(query?: PageQuery) {
    const response = await apiClient.get<MultipleResponse<StudentResponse>>("/students", {
      params: toPageParams(query),
    });
    const page = unwrapPage(response.data);
    return {
      ...page,
      items: page.items.map(toStudent),
    };
  },
  async create(input: StudentInput) {
    const response = await apiClient.post<SingleResponse<StudentResponse>>("/students", input);
    return toStudent(unwrapSingle(response.data));
  },
  async update(id: string, input: StudentInput) {
    const response = await apiClient.put<SingleResponse<StudentResponse>>(`/students/${id}`, input);
    return toStudent(unwrapSingle(response.data));
  },
  async getClassByStudentId(id: string) {
    const response = await apiClient.get<SingleResponse<ClassResponse>>(`/students/${id}/classes`);
    return toClass(unwrapSingle(response.data));
  },
  async remove(id: string) {
    await apiClient.delete(`/students/${id}`);
  },
  async statistics() {
    const response = await apiClient.get<SingleResponse<StudentStatistics>>("/students/statistics");
    return unwrapSingle(response.data);
  },
  async importByClassId(classId: string, file: File) {
    const formData = new FormData();
    formData.append("classId", classId);
    formData.append("file", file);
    await apiClient.post("/students/import-student", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
  async exportByClassId(classId: string) {
    const response = await apiClient.get<Blob>("/students/export-student", {
      params: { classId },
      responseType: "blob",
    });
    return response.data;
  },
};
