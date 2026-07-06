import {
  ApartmentOutlined,
  AppstoreOutlined,
  BookOutlined,
  DatabaseOutlined,
  FileImageOutlined,
  PictureOutlined,
  HomeOutlined,
  MergeCellsOutlined,
  SlidersOutlined,
  SnippetsOutlined,
  TagsOutlined,
  ToolOutlined,
  UnorderedListOutlined,
  UsergroupAddOutlined,
  UserOutlined,
  GatewayOutlined,
  ApiOutlined,
  FileTextOutlined
} from '@ant-design/icons/lib/icons'
import { lazy } from 'react'

/**
 * 路由渲染页面
 * key: 路由路径, 由后端拼接而成
 */
export const componentMap = {
  _admin_home: lazy(() => import('@/views/home')),
  // 博客管理
  // _admin_blog: lazy(() => import('@/views/home')),
  _admin_blog_index: lazy(() => import('@/views/blog/blogContent/BlogList')),
  _admin_blog_label: lazy(() => import('@/views/blog/label/Label')),
  _admin_blog_category: lazy(() => import('@/views/blog/category/BlogCategory')),
  _admin_blog_topic: lazy(() => import('@/views/blog/topic/BlogTopic')),
  _admin_blog_diary: lazy(() => import('@/views/blog/diary/Diary')),

  // 图片管理
  _admin_image_category: lazy(() => import('@/views/image/ImageCategory')),
  _admin_image_pictures: lazy(() => import('@/views/image/ImageManage')),

  // 系统管理
  _admin_sys_admin: lazy(() => import('@/views/sys/admin/Admin')),
  _admin_sys_org: lazy(() => import('@/views/sys/org/Org')),
  _admin_sys_role: lazy(() => import('@/views/sys/role/Role')),
  _admin_sys_acl: lazy(() => import('@/views/sys/acl/Acl')),
  _admin_sys_acl_data: lazy(() => import('@/views/sys/aclData/AclData')),
  _admin_sys_dict: lazy(() => import('@/views/sys/dict/Dict')),

  // 网关路由管理
  _admin_gateway_config: lazy(() => import('@/views/gateway/GatewayRoute')),
  _admin_gateway_log: lazy(() => import('@/views/gateway/GatewayLogs'))
} as const

/**
 * 菜单图标
 */
export const iconMap = {
  _admin_home: <HomeOutlined />,
  // 博客管理
  _admin_blog: <BookOutlined />,
  _admin_blog_index: <UnorderedListOutlined />,
  _admin_blog_label: <TagsOutlined />,
  _admin_blog_category: <SnippetsOutlined />,
  _admin_blog_topic: <SlidersOutlined />,
  _admin_blog_diary: <BookOutlined />,

  // 图片管理
  _admin_image: <SlidersOutlined />,
  _admin_image_category: <PictureOutlined />,
  _admin_image_pictures: <FileImageOutlined />,

  // 组件管理

  // 系统管理
  _admin_sys: <ToolOutlined />,
  _admin_sys_admin: <UserOutlined />,
  _admin_sys_org: <ApartmentOutlined />,
  _admin_sys_role: <UsergroupAddOutlined />,
  _admin_sys_acl: <MergeCellsOutlined />,
  _admin_sys_acl_data: <DatabaseOutlined />,
  _admin_sys_dict: <AppstoreOutlined />,

  // 网关路由管理
  _admin_gateway: <GatewayOutlined />,
  _admin_gateway_config: <ApiOutlined />,
  _admin_gateway_log: <FileTextOutlined />
} as const
