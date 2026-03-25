export interface CreateShortLinkPayload {
  url: string;
  expireDays?: number;
  customCode?: string;
}

export interface CreateShortLinkResponse {
  shortLink: string;
}
