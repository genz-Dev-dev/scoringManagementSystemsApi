import type { User } from "@/types/models";

const ACCESS_TOKEN_KEY = "accessToken";
const SESSION_USER_KEY = "sessionUser";

export function storeSession(accessToken: string, user: User) {
  sessionStorage.setItem(ACCESS_TOKEN_KEY, accessToken);
  sessionStorage.setItem(SESSION_USER_KEY, JSON.stringify(user));
}

export function clearSession() {
  sessionStorage.removeItem(ACCESS_TOKEN_KEY);
  sessionStorage.removeItem(SESSION_USER_KEY);
}

export function getAccessToken() {
  return sessionStorage.getItem(ACCESS_TOKEN_KEY);
}

export function getSessionUser(): User | null {
  const raw = sessionStorage.getItem(SESSION_USER_KEY);
  if (!raw) return null;

  try {
    return JSON.parse(raw) as User;
  } catch {
    return null;
  }
}
