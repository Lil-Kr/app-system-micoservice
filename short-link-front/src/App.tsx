import { useState } from 'react'
import { createShortLink } from './modules/short-link/index.ts'
import { Button, Card, Description, FieldError, InputGroup, Label, TextField } from '@heroui/react'
import { HttpError } from './api/http.ts'

function App() {
  const [url, setUrl] = useState("");
  const [customCode, setCustomCode] = useState("");
  const [shortLink, setShortLink] = useState("");
  const [loading, setLoading] = useState(false);
  const [urlInvalid, setUrlInvalid] = useState(false);
  const [compressionRatio, setCompressionRatio] = useState(0);
  const [savedChars, setSavedChars] = useState(0);
  const [responseTime, setResponseTime] = useState(0);
  
  const handleSubmit = async () => {
    const normalizedUrl = url.trim();
    if (!normalizedUrl) {
      setUrlInvalid(true);
      return;
    }

    setUrlInvalid(false);
    setLoading(true);
    const startTime = Date.now();

    try {
      const data = await createShortLink({
        url: normalizedUrl,
        customCode: customCode || undefined,
      });
      setShortLink(data.shortLink);
      const duration = Date.now() - startTime;
      const originLength = normalizedUrl.length;
      const shortLength = data.shortLink.length;
      const saved = Math.max(originLength - shortLength, 0);
      const ratio = originLength > 0 ? Number(((saved / originLength) * 100).toFixed(1)) : 0;
      setResponseTime(duration);
      setSavedChars(saved);
      setCompressionRatio(ratio);
    } catch (err) {
      console.error(err);
      if (err instanceof HttpError) {
        alert(err.message);
      } else {
        alert("Failed to generate short link");
      }
    } finally {
      setLoading(false);
    }
  };

  const handleCopy = async () => {
    if (!shortLink) return;
    await navigator.clipboard.writeText(shortLink);
    alert("Copied!");
  };
  
  return (
    <div className="min-h-screen w-full bg-[linear-gradient(135deg,#667eea_0%,#764ba2_100%)] px-4 py-5">
      <div className="mx-auto flex w-full max-w-[500px] flex-col items-center gap-5 pt-[50px]">
        <Card className="w-full">
          <Card.Header className="flex flex-col items-center gap-2 text-center">
            <div className="flex items-center justify-center gap-2">
              <span className="text-3xl" aria-hidden="true">🔗</span>
              <Card.Title className="text-3xl">{'ShortLink'}</Card.Title>
            </div>
            <Card.Description>快速、安全、可靠的短链接生成服务</Card.Description>
          </Card.Header>

          <Card.Content className="flex flex-col gap-5">
            <TextField variant="primary" isInvalid={urlInvalid}>
              <Label className="flex items-center gap-1">
                <span className="text-danger">*</span>
                <span>原始链接</span>
              </Label>
              <InputGroup variant="primary">
                <InputGroup.Prefix>🔗</InputGroup.Prefix>
                <InputGroup.Input
                  className="h-12"
                  placeholder="请输入需要缩短的链接"
                  value={url}
                  onChange={(e) => {
                    const nextValue = e.target.value;
                    setUrl(nextValue);
                    if (urlInvalid && nextValue.trim()) {
                      setUrlInvalid(false);
                    }
                  }}
                />
              </InputGroup>
              <FieldError>请输入原始链接</FieldError>
            </TextField>

            <TextField variant="primary">
              <Label>自定义短码（可选）</Label>
              <InputGroup variant="primary">
                <InputGroup.Prefix>✏️</InputGroup.Prefix>
                <InputGroup.Input
                  className="h-12"
                  placeholder="留空则自动生成"
                  value={customCode}
                  onChange={(e) => setCustomCode(e.target.value)}
                />
              </InputGroup>
            </TextField>
          </Card.Content>

          <Card.Footer>
            <Button
              onClick={handleSubmit}
              isDisabled={loading}
              fullWidth
              className="h-12 rounded-lg border-none bg-[linear-gradient(135deg,#667eea_0%,#764ba2_100%)] text-base font-semibold text-white"
            >
              {loading ? "生成中..." : "生成短链接"}
            </Button>
          </Card.Footer>
        </Card>

        {shortLink && (
          <Card className="w-full border-success/30 bg-success-50">
            <Card.Header className="pb-2">
              <Card.Title className="flex items-center gap-2 text-success-700">
                <span aria-hidden="true">✅</span>
                <span>生成成功！</span>
              </Card.Title>
            </Card.Header>
            <Card.Content className="flex flex-col gap-4">
              <div className="flex flex-col gap-3 sm:flex-row">
                <Description className="flex-1 break-all rounded-medium border border-success-200 bg-white px-3 py-2 text-success-700">
                  {shortLink}
                </Description>
                <Button onClick={handleCopy}>复制</Button>
              </div>
              <div className="grid grid-cols-1 gap-3 text-center sm:grid-cols-3">
                <div className="rounded-medium bg-white/80 p-3">
                  <div className="text-lg font-semibold text-foreground">{compressionRatio}%</div>
                  <div className="text-xs text-default-600">压缩比</div>
                </div>
                <div className="rounded-medium bg-white/80 p-3">
                  <div className="text-lg font-semibold text-foreground">{savedChars}</div>
                  <div className="text-xs text-default-600">节省字符</div>
                </div>
                <div className="rounded-medium bg-white/80 p-3">
                  <div className="text-lg font-semibold text-foreground">{responseTime}ms</div>
                  <div className="text-xs text-default-600">响应时间</div>
                </div>
              </div>
            </Card.Content>
          </Card>
        )}

        <Card className="w-full">
          <Card.Header className="items-center">
          <Card.Title className="text-3xl">{'核心特性'}</Card.Title>
          </Card.Header>
          <Card.Content className="grid grid-cols-1 gap-4 sm:grid-cols-2">
            <div className="rounded-large bg-default-100 p-5 text-center">
              <div className="mx-auto mb-3 flex h-12 w-12 items-center justify-center rounded-full bg-warning-200 text-warning-700">⚡</div>
              <div className="mb-1 font-semibold">极速生成</div>
              <div className="text-sm text-default-600">毫秒级响应，高性能处理</div>
            </div>
            <div className="rounded-large bg-default-100 p-5 text-center">
              <div className="mx-auto mb-3 flex h-12 w-12 items-center justify-center rounded-full bg-success-200 text-success-700">🔒</div>
              <div className="mb-1 font-semibold">安全可靠</div>
              <div className="text-sm text-default-600">企业级安全保障</div>
            </div>
            <div className="rounded-large bg-default-100 p-5 text-center">
              <div className="mx-auto mb-3 flex h-12 w-12 items-center justify-center rounded-full bg-primary-200 text-primary-700">📈</div>
              <div className="mb-1 font-semibold">数据统计</div>
              <div className="text-sm text-default-600">详细的访问分析</div>
            </div>
            <div className="rounded-large bg-default-100 p-5 text-center">
              <div className="mx-auto mb-3 flex h-12 w-12 items-center justify-center rounded-full bg-secondary-200 text-secondary-700">⚙️</div>
              <div className="mb-1 font-semibold">自定义短码</div>
              <div className="text-sm text-default-600">支持个性化短码</div>
            </div>
          </Card.Content>
        </Card>
      </div>
    </div>
  )
}

export default App
