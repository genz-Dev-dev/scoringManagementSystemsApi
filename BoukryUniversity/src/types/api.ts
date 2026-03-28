export interface SingleResponse<T> {
  success: boolean;
  status: number;
  message: string;
  data: T;
  timestamp: string;
}

export interface MultipleResponse<T> {
  success: boolean;
  status: number;
  message: string;
  content: T[];
  number: number;
  size: number;
  totalPage: number;
  totalElement: number;
  hastPrevious: boolean;
  hastNext: boolean;
  timestamp: string;
}

export interface PaginatedResult<T> {
  items: T[];
  page: number;
  size: number;
  totalPages: number;
  totalElements: number;
  hasPrevious: boolean;
  hasNext: boolean;
}

export interface ApiErrorResponse {
  success?: boolean;
  status?: number;
  message?: string;
  timestamp?: string;
  errors?: Record<string, string>;
}
