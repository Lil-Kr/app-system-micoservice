import { defineConfig, type ConfigEnv, loadEnv } from 'vite'
import { createBaseConfig } from './environment/vite.base.config'
import { createDevConfig } from './environment/vite.dev.config'
import { createTestConfig } from './environment/vite.test.config'
import { createProdConfig } from './environment/vite.prod.config'

const modeResolver = {
  development: createDevConfig,
  test: createTestConfig,
  production: createProdConfig
}

export default defineConfig(({ mode }: ConfigEnv) => {
  const env = loadEnv(mode, process.cwd(), '')
  const baseConfig = createBaseConfig(env.VITE_BASE_URL || '/')
  const createModeConfig = modeResolver[mode as keyof typeof modeResolver] || createDevConfig
  const modeConfig = createModeConfig(env)

  return {
    ...baseConfig,
    ...modeConfig
  }
})
