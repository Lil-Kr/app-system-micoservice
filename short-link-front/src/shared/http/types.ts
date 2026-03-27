import type { AxiosRequestConfig, AxiosResponse, InternalAxiosRequestConfig } from "axios";

export type HttpRequestConfig<TData = unknown> = AxiosRequestConfig<TData>;

export interface HttpClientConfig extends AxiosRequestConfig {
  baseURL?: string;
  timeout?: number;
}

export interface HttpInterceptors {
  onRequest?: (
    config: InternalAxiosRequestConfig
  ) => InternalAxiosRequestConfig | Promise<InternalAxiosRequestConfig>;
  onRequestError?: (error: unknown) => unknown;
  onResponse?: <T = unknown>(response: AxiosResponse<T>) => AxiosResponse<T> | Promise<AxiosResponse<T>>;
  onResponseError?: (error: unknown) => unknown;
}

export interface ApiResponse<T> {
  code: number;
  msg: string;
  data: T;
}
