export interface User {
  id: string;
  fullName: string;
  email: string;
  verified: boolean;
  verificationToken?: string | null;
  refreshToken?: string | null;
  status: boolean;
  role: string;
}

export interface Role {
  id: string;
  name: string;
  description: string;
  status: string;
  userIds: string[];
}

export interface Permission {
  id: string;
  name: string;
  description: string;
  module: string;
  roleIds: string[];
}

export interface Department {
  id: string;
  name: string;
  code: string;
  description: string;
}

export interface Subject {
  id: string;
  departmentId: string;
  name: string;
  description: string;
  code: string;
}

export interface Semester {
  id: string;
  name: string;
  startDate: string;
  endDate: string;
  description: string;
}

export interface AcademicClass {
  id: string;
  name: string;
  departmentId: string;
  academicYear: string;
  generation: number;
  creationAt?: string;
  updatedAt?: string;
}

export interface CourseSchedule {
  id?: string;
  dayOfWeek: string;
  startTime: string;
  endTime: string;
  room: number;
}

export interface Course {
  subjectId: string;
  subjectName?: string | null;
  semesterId: string;
  semesterName?: string | null;
  instructorId: string;
  instructorName?: string | null;
  name: string;
  description: string;
  schedule: string;
  schedules: CourseSchedule[];
  startAt: string;
  endAt: string;
}

export interface StudentAddress {
  id?: string;
  houseNumber: string;
  street: string;
  sangkat: string;
  khan: string;
  province: string;
  country: string;
}

export interface Student {
  id: string;
  studentCode: string;
  khFirstName: string;
  khLastName: string;
  enFirstName: string;
  enLastName: string;
  gender: string;
  dateOfBirth: string;
  enrollmentDate: string;
  email?: string | null;
  phoneNumber?: string | null;
  status: boolean;
  address?: StudentAddress | null;
}

export interface StudentStatistics {
  totalStudent: number;
  totalMale: number;
  totalFemale: number;
}

export interface AuthSession {
  accessToken: string;
  user: User;
}
