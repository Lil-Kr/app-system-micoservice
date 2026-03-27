import { defineConfig, type UserConfig } from 'vite'

export const createTestConfig = (env: Record<string, string>): UserConfig =>
  defineConfig({
    server: {
      host: env.VITE_DEV_HOST || '0.0.0.0',
      port: Number(env.VITE_DEV_PORT || 5173),
      proxy: {
        '/api': {
          target: env.VITE_API_BASE_URL || 'http://localhost:8089',
          changeOrigin: true,
          rewrite: path => path.replace(/^\/api/, '')
        }
      }
    }
  })
