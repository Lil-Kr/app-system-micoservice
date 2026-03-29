import { HttpClient, HttpError } from "../shared/http/index.ts";

const apiBaseUrl = import.meta.env.VITE_API_BASE_URL || "/api";

export const httpClient = new HttpClient({
  baseURL: apiBaseUrl,
  timeout: 10000,
});

export { HttpError };
