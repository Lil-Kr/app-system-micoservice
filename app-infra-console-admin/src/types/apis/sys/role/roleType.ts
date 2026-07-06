import { Result, ResultPage } from '@/types/base/response'
import { BaseApi } from '../..'
import { BaseEntityPageType } from '@/types/base'
import { AclModuleTreeResp } from '../acl/aclType'
import { GetProp, TableColumnsType, TableProps, TransferProps, TreeDataNode } from 'antd/lib'

export interface RoleTableType {
  key: string
  surrogateId: string
  name: string
  type: number
  status: number
  remark: string
  createTime?: string
  updateTime?: string
}

export interface SysRole {
  id: string
  surrogateId: string
  name: string
  type: number
  remark: string
  status: number
  deleted: number
  operateIp: string
  creatorId: string
  operator: string
  createTime: string
  updateTime: string
}

export interface SysRoleTableType extends SysRole {}

export interface RoleListPageReq extends BaseEntityPageType {
  name?: string
  type?: number
}

export interface RoleAddReq {
  name: string
  type: number
  status: number
  remark?: string
}

export interface RoleEditReq {
  roleId: string
  name: string
  type: number
  status: number
  remark?: string
}

export interface RoleDelReq {
  surrogateId: string
}

export interface RoleAclTreeReq {
  roleId: string
}

// ======================================= role acl type

export interface UpdateRoleAclsReq {
  roleId: string
  aclIdList: string[]
}

export interface RoleAclTreeType extends TreeDataNode {
  checked?: boolean
  hasAcl?: boolean
  children?: RoleAclTreeType[] // 递归 children
}

export interface UpdateRoleAclsReq {
  roleId: string
  aclIdList: string[]
}

/**
 * ============================= role-admin =============================
 */
export type TransferItem = GetProp<TransferProps, 'dataSource'>[number]
export type TableRowSelection<T extends object> = TableProps<T>['rowSelection']

export interface TableTransferProps extends Omit<TransferProps<TransferItem>, 'dataSource'> {
  dataSource: RoleAdminTableType[]
  leftColumns: TableColumnsType<RoleAdminTableType>
  rightColumns: TableColumnsType<RoleAdminTableType>
}

// =============================== api req =============================

export interface RoleAdminReq {
  roleId: string
}

export interface RoleAdminTableType {
  key?: string
  id: string
  account: string
  userName: string
  remark: string
  createTime: string
  updateTime: string
  disabled?: boolean
}

export interface RoleAdminListResp {
  selectedAdminList: RoleAdminTableType[]
  unSelectedAdminList: RoleAdminTableType[]
}

export interface UpdateRoleAdminReq {
  roleId: string
  userIdList: string[]
}

export interface RoleApi extends BaseApi {
  retrievePageRoleList(req: RoleListPageReq): Promise<ResultPage<SysRoleTableType>>
  add(req: RoleAddReq): Promise<Result<string>>
  update(params: RoleEditReq): Promise<Result<string>>
  delete(req: RoleDelReq): Promise<Result<string>>
  roleAclTree(req: RoleAclTreeReq): Promise<Result<AclModuleTreeResp[]>>
  updateRoleAcls(req: UpdateRoleAclsReq): Promise<Result<string>>
  roleAdminList(req: RoleAdminReq): Promise<Result<RoleAdminListResp>>
  updateRoleAdmin(req: UpdateRoleAdminReq): Promise<Result<string>>
}
