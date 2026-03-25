import { httpClient } from "../../api/http.ts";
import type { CreateShortLinkPayload, CreateShortLinkResponse } from "./types.ts";

export const createShortLink = (payload: CreateShortLinkPayload) => {
  return httpClient.post<CreateShortLinkResponse, CreateShortLinkPayload>("/short-link", payload);
};
