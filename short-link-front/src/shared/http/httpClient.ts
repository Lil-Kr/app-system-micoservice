import axios, { AxiosError, type AxiosInstance } from "axios";
import type { HttpClientConfig, HttpInterceptors, HttpRequestConfig } from "./types.ts";

export class HttpError extends Error {
  readonly statusCode: number;
  readonly code?: string;
  readonly data?: unknown;

  constructor(message: string, statusCode: number, code?: string, data?: unknown) {
    super(message);
    this.name = "HttpError";
    this.statusCode = statusCode;
    this.code = code;
    this.data = data;
  }
}

const toHttpError = (error: unknown) => {
  if (error instanceof HttpError) {
    return error;
  }

  if (axios.isAxiosError(error)) {
    const axiosError = error as AxiosError<{ message?: string; msg?: string }>;
    const statusCode = axiosError.response?.status ?? 0;
    const message =
      axiosError.response?.data?.msg ??
      axiosError.response?.data?.message ??
      axiosError.message ??
      "Network request failed";
    return new HttpError(message, statusCode, axiosError.code, axiosError.response?.data);
  }

  if (error instanceof Error) {
    return new HttpError(error.message, 0);
  }

  return new HttpError("Unknown request error", 0);
};

export class HttpClient {
  private readonly instance: AxiosInstance;

  constructor(config: HttpClientConfig = {}, interceptors?: HttpInterceptors) {
    this.instance = axios.create({
      timeout: 10000,
      ...config,
    });

    this.instance.interceptors.request.use(
      (requestConfig) => (interceptors?.onRequest ? interceptors.onRequest(requestConfig) : requestConfig),
      (error) => (interceptors?.onRequestError ? interceptors.onRequestError(error) : Promise.reject(toHttpError(error)))
    );

    this.instance.interceptors.response.use(
      (response) => (interceptors?.onResponse ? interceptors.onResponse(response) : response),
      (error) => (interceptors?.onResponseError ? interceptors.onResponseError(error) : Promise.reject(toHttpError(error)))
    );
  }

  async request<TResponse = unknown, TData = unknown>(config: HttpRequestConfig<TData>): Promise<TResponse> {
    const response = await this.instance.request<TResponse, { data: TResponse }, TData>(config);
    return response.data;
  }

  get<TResponse = unknown>(url: string, config?: HttpRequestConfig) {
    return this.request<TResponse>({ ...config, url, method: "GET" });
  }

  post<TResponse = unknown, TData = unknown>(url: string, data?: TData, config?: HttpRequestConfig<TData>) {
    return this.request<TResponse, TData>({ ...config, url, data, method: "POST" });
  }

  put<TResponse = unknown, TData = unknown>(url: string, data?: TData, config?: HttpRequestConfig<TData>) {
    return this.request<TResponse, TData>({ ...config, url, data, method: "PUT" });
  }

  patch<TResponse = unknown, TData = unknown>(url: string, data?: TData, config?: HttpRequestConfig<TData>) {
    return this.request<TResponse, TData>({ ...config, url, data, method: "PATCH" });
  }

  delete<TResponse = unknown>(url: string, config?: HttpRequestConfig) {
    return this.request<TResponse>({ ...config, url, method: "DELETE" });
  }
}
