import { useState } from 'react'
import { createShortLink } from './modules/short-link/index.ts'
import { Button, Card, FieldError, InputGroup, Label, TextField } from '@heroui/react'
import {Copy, Check} from "@gravity-ui/icons";
import { useTranslation } from 'react-i18next'
import { HttpError } from './api/http.ts'

const HTTP_URL_REGEX = /^https?:\/\/[^\s/$.?#].[^\s]*$/i;

function App() {
  const { t, i18n } = useTranslation()
  const [url, setUrl] = useState("");
  const [expireDays, setExpireDays] = useState("");
  const [customCode, setCustomCode] = useState("");
  const [shortLink, setShortLink] = useState("");
  const [copied, setCopied] = useState(false);
  const [showFailedPage, setShowFailedPage] = useState(false);
  const [loading, setLoading] = useState(false);
  const [urlInvalid, setUrlInvalid] = useState(false);
  const [urlFormatInvalid, setUrlFormatInvalid] = useState(false);
  const [expireDaysInvalid, setExpireDaysInvalid] = useState(false);
  const isEnglish = i18n.language.startsWith('en')

  const handleSubmit = async () => {
    const normalizedUrl = url.trim();
    if (!normalizedUrl) {
      setUrlInvalid(true);
      setUrlFormatInvalid(false);
      return;
    }
    if (!HTTP_URL_REGEX.test(normalizedUrl)) {
      setUrlInvalid(false);
      setUrlFormatInvalid(true);
      return;
    }
    const normalizedExpireDays = Number(expireDays);
    if (!Number.isInteger(normalizedExpireDays) || normalizedExpireDays < 1 || normalizedExpireDays > 7) {
      setExpireDaysInvalid(true);
      return;
    }

    setUrlInvalid(false);
    setUrlFormatInvalid(false);
    setExpireDaysInvalid(false);
    setLoading(true);

    try {
      const data = await createShortLink({
        originUrl: normalizedUrl,
        expireDays: normalizedExpireDays,
      });
      setShowFailedPage(false);
      setShortLink(data.shortLink);
      setCopied(false);
    } catch (err) {
      console.error(err);
      if (err instanceof HttpError) {
        if (err.statusCode !== 200 && err.statusCode > 0) {
          setShowFailedPage(true);
        } else {
          alert(err.message);
        }
      } else {
        alert(t('failedToGenerate'));
      }
    } finally {
      setLoading(false);
    }
  };

  const handleCopy = async () => {
    if (!shortLink) return;
    try {
      if (navigator?.clipboard?.writeText) {
        await navigator.clipboard.writeText(shortLink);
      } else {
        const textArea = document.createElement('textarea');
        textArea.value = shortLink;
        textArea.style.position = 'fixed';
        textArea.style.opacity = '0';
        textArea.style.pointerEvents = 'none';
        document.body.appendChild(textArea);
        textArea.focus();
        textArea.select();
        const copiedByExecCommand = document.execCommand('copy');
        document.body.removeChild(textArea);
        if (!copiedByExecCommand) {
          throw new Error(t('copyFailed'));
        }
      }
      setCopied(true);
      setTimeout(() => setCopied(false), 1800);
    } catch (err) {
      if (err instanceof Error) {
        alert(err.message);
      } else {
        alert(t('copyFailed'));
      }
    }
  };

  if (showFailedPage) {
    return (
      <div className="min-h-screen w-full bg-[linear-gradient(135deg,#667eea_0%,#764ba2_100%)] px-4 py-8">
        <div className="mx-auto flex w-full max-w-[700px] pt-[50px]">
          <Card className="w-full">
            <Card.Header className="items-center text-center">
              <Card.Title className="text-2xl text-danger">{t('failedPageTitle')}</Card.Title>
              <Card.Description>{t('failedPageDescription')}</Card.Description>
            </Card.Header>
            <Card.Footer>
              <Button
                fullWidth
                onClick={() => setShowFailedPage(false)}
                className="h-12 rounded-lg border-none bg-[linear-gradient(135deg,#667eea_0%,#764ba2_100%)] text-base font-semibold text-white"
              >
                {t('backToHome')}
              </Button>
            </Card.Footer>
          </Card>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen w-full bg-[linear-gradient(135deg,#667eea_0%,#764ba2_100%)] px-4 py-5">
      <div className="mx-auto flex w-full max-w-[700px] flex-col items-center gap-5 pt-[50px]">
        <Card className="w-full">
          <Card.Header className="w-full gap-2">
            <div className="grid w-full grid-cols-[1fr_auto_1fr] items-center">
              <div />
              <div className="flex items-center justify-center gap-2">
                <span className="text-3xl" aria-hidden="true">🔗</span>
                <Card.Title className="text-3xl">{t('title')}</Card.Title>
              </div>
              <div className="justify-self-end">
                <Button
                  size="sm"
                  onClick={() => i18n.changeLanguage(isEnglish ? 'zh' : 'en')}
                  className="min-w-14 border-none bg-[linear-gradient(135deg,#06b6d4_0%,#3b82f6_55%,#6366f1_100%)] font-medium text-white shadow-md shadow-cyan-500/30"
                >
                  {isEnglish ? 'Chinese' : 'EN'}
                </Button>
              </div>
            </div>
            <Card.Description className="text-center">{t('subtitle')}</Card.Description>
          </Card.Header>

          <Card.Content className="flex flex-col gap-5">
              <TextField variant="primary" isInvalid={urlInvalid || urlFormatInvalid}>
                <Label className="flex items-center gap-1">
                  <span className="text-danger">*</span>
                  <span>{t('originLabel')}</span>
                </Label>
                <InputGroup variant="primary">
                  <InputGroup.Prefix>🔗</InputGroup.Prefix>
                  <InputGroup.Input
                    className="h-12"
                    placeholder={t('originPlaceholder')}
                    value={url}
                    onChange={(e) => {
                      const nextValue = e.target.value;
                      setUrl(nextValue);
                      const nextValueTrimmed = nextValue.trim();
                      if (!nextValueTrimmed) {
                        setUrlFormatInvalid(false);
                        return;
                      }
                      if (urlInvalid && nextValueTrimmed) {
                        setUrlInvalid(false);
                      }
                      if (urlFormatInvalid && HTTP_URL_REGEX.test(nextValueTrimmed)) {
                        setUrlFormatInvalid(false);
                      }
                    }}
                  />
                </InputGroup>
                <FieldError>{urlFormatInvalid ? t('invalidOriginUrl') : t('requiredOriginUrl')}</FieldError>
              </TextField>


            <TextField variant="primary" isInvalid={expireDaysInvalid}>
              <Label className="flex items-center gap-1">
                <span className="text-danger">*</span>
                <span>{t('expireDaysLabel')}</span>
              </Label>
              <InputGroup variant="primary">
                <InputGroup.Prefix>📅</InputGroup.Prefix>
                <InputGroup.Input
                  className="h-12"
                  placeholder={t('expireDaysPlaceholder')}
                  inputMode="numeric"
                  pattern="[0-9]*"
                  value={expireDays}
                  onChange={(e) => {
                    const nextValue = e.target.value;
                    if (!/^\d*$/.test(nextValue)) {
                      return;
                    }
                    setExpireDays(nextValue);
                    const nextNumber = Number(nextValue);
                    if (expireDaysInvalid && Number.isInteger(nextNumber) && nextNumber >= 1 && nextNumber <= 7) {
                      setExpireDaysInvalid(false);
                    }
                  }}
                />
              </InputGroup>
              <FieldError>{t('requiredExpireDays')}</FieldError>
            </TextField>

            <TextField variant="primary">
              <Label>{t('customCodeLabel')}</Label>
              <InputGroup variant="primary">
                <InputGroup.Prefix>✏️</InputGroup.Prefix>
                <InputGroup.Input
                  className="h-12"
                  placeholder={t('customCodePlaceholder')}
                  value={customCode}
                  onChange={(e) => setCustomCode(e.target.value)}
                />
              </InputGroup>
            </TextField>

            {shortLink && (
              <TextField variant="primary">
                <Label>{t('shortUrlLabel')}</Label>
                <InputGroup variant="primary">
                  <InputGroup.Input className="h-12" readOnly value={shortLink} />
                  <InputGroup.Suffix>
                    <Button
                      isIconOnly
                      aria-label={copied ? t('copied') : t('copy')}
                      size="sm"
                      variant={"ghost"}
                      className={copied ? "text-success-foreground" : undefined}
                      onClick={handleCopy}
                    >
                      {copied ? <Check className="size-4"/> : <Copy className="size-4" />}
                    </Button>
                  </InputGroup.Suffix>
                </InputGroup>
              </TextField>
            )}
          </Card.Content>

          <Card.Footer>
            <Button
              onClick={handleSubmit}
              isDisabled={loading}
              fullWidth
              className="h-12 rounded-lg border-none bg-[linear-gradient(135deg,#667eea_0%,#764ba2_100%)] text-base font-semibold text-white"
            >
              {loading ? t('generating') : t('generate')}
            </Button>
          </Card.Footer>
        </Card>

        <Card className="w-full">
          <Card.Header className="items-center">
          <Card.Title className="text-3xl">{t('coreFeatures')}</Card.Title>
          </Card.Header>
          <Card.Content className="grid grid-cols-1 gap-4 sm:grid-cols-2">
            <div className="rounded-large bg-default-100 p-5 text-center">
              <div className="mx-auto mb-3 flex h-12 w-12 items-center justify-center rounded-full bg-warning-200 text-warning-700">⚡</div>
              <div className="mb-1 font-semibold">{t('speedFeatureTitle')}</div>
              <div className="text-sm text-default-600">{t('speedFeatureDesc')}</div>
            </div>
            <div className="rounded-large bg-default-100 p-5 text-center">
              <div className="mx-auto mb-3 flex h-12 w-12 items-center justify-center rounded-full bg-success-200 text-success-700">🔒</div>
              <div className="mb-1 font-semibold">{t('safeFeatureTitle')}</div>
              <div className="text-sm text-default-600">{t('safeFeatureDesc')}</div>
            </div>
            <div className="rounded-large bg-default-100 p-5 text-center">
              <div className="mx-auto mb-3 flex h-12 w-12 items-center justify-center rounded-full bg-primary-200 text-primary-700">📈</div>
              <div className="mb-1 font-semibold">{t('dataFeatureTitle')}</div>
              <div className="text-sm text-default-600">{t('dataFeatureDesc')}</div>
            </div>
            <div className="rounded-large bg-default-100 p-5 text-center">
              <div className="mx-auto mb-3 flex h-12 w-12 items-center justify-center rounded-full bg-secondary-200 text-secondary-700">⚙️</div>
              <div className="mb-1 font-semibold">{t('customFeatureTitle')}</div>
              <div className="text-sm text-default-600">{t('customFeatureDesc')}</div>
            </div>
          </Card.Content>
        </Card>
      </div>
    </div>
  )
}

export default App
