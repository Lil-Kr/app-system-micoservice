import { httpClient } from "../../api/http.ts";
import { HttpError } from "../../api/http.ts";
import type { ApiResponse } from "../../shared/http/types.ts";
import type { CreateShortLinkPayload, CreateShortLinkResponse, CreateShortLinkServerData } from "./types.ts";

export const createShortLink = async (payload: CreateShortLinkPayload): Promise<CreateShortLinkResponse> => {
  const response = await httpClient.post<ApiResponse<CreateShortLinkServerData>, CreateShortLinkPayload>("/shortUrl/create", payload);
  if (response.code !== 200) {
    throw new HttpError(response.msg || "Request failed", response.code, String(response.code), response);
  }
  return {
    shortLink: response.data.shortUrl,
  };
};
