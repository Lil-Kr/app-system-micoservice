import { httpClient } from "../../api/http.ts";
import type { ApiResponse } from "../../shared/http/types.ts";
import type { CreateShortLinkPayload, CreateShortLinkResponse, CreateShortLinkServerData } from "./types.ts";

export const createShortLink = async (payload: CreateShortLinkPayload): Promise<CreateShortLinkResponse> => {
  const response = await httpClient.post<ApiResponse<CreateShortLinkServerData>, CreateShortLinkPayload>("/shortUrl/create", payload);
  return {
    shortLink: response.data.shortUrl,
  };
};
