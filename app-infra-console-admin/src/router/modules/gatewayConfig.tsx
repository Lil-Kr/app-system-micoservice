import LazyLoad from '@/components/router/LazyLoad'
import { RouterItemType } from '@/types/router/routeType'
import { ToolOutlined, UserOutlined, ApartmentOutlined } from '@ant-design/icons'
import { lazy } from 'react'

const gatewayConfig: RouterItemType[] = [
  {
    meta: {
      key: '/gateway',
      title: '网关路由管理',
      layout: false,
      icon: <ToolOutlined />
    },
    path: '/admin/gateway',
    children: [
      {
        meta: { key: 'gateway-index', title: 'gateway-index' },
        index: true,
        element: LazyLoad(lazy(() => import('@/views/gateway/GatewayRoute')))
      },
      {
        meta: {
          key: '/index',
          title: 'API管理',
          layout: false,
          icon: <UserOutlined />
        },
        path: '/admin/gateway/index',
        element: LazyLoad(lazy(() => import('@/views/gateway/GatewayRoute')))
      },
      {
        meta: {
          key: '/log',
          title: '网关API变更日志',
          layout: false,
          icon: <ApartmentOutlined />
        },
        path: '/admin/gateway/log',
        element: LazyLoad(lazy(() => import('@/views/gateway/GatewayLogs')))
      }
    ]
  }
]

export { gatewayConfig }
