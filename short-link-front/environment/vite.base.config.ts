import { defineConfig, type UserConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

export const createBaseConfig = (base: string): UserConfig =>
  defineConfig({
    base,
    plugins: [
      react(),
      tailwindcss()
    ]
  })
