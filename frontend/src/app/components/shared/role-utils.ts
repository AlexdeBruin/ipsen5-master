import { Role } from './role';

export function roleIdFromRoleName(accountRole: string): number {
  if (accountRole === 'GROUP') {
    return 1;
  } else if (accountRole === 'ADMIN') {
    return 3;
  } else {
    // we'll just let the backend handle this
    return 0;
  }
}

export function makeAccountRole(roleName: string): Role {
  return {
    roleId: roleIdFromRoleName(roleName),
    roleName: roleName
  };
}

