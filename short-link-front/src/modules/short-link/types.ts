export interface CreateShortLinkPayload {
  originUrl: string;
  expireDays: number;
}

export interface CreateShortLinkResponse {
  shortLink: string;
}

export interface CreateShortLinkServerData {
  shortCode: string;
  shortUrl: string;
  originUrl: string;
  originUrlHash: string;
  expireDays: number;
  accessCount: number;
  status: number;
  createTime: string;
  updateTime: string;
}
