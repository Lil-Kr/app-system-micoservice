import { HttpClient, HttpError } from "../shared/http/index.ts";

export const httpClient = new HttpClient({
  baseURL: "/api",
  timeout: 10000,
});

export { HttpError };
