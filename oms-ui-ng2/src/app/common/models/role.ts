import { Permission } from './permission';

export class Role {
  roleId: number;
  roleName: string;
  roleDesc: string;
  active: boolean;
  permissions: Array<Permission>[];


}