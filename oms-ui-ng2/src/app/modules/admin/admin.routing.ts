import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminComponent } from './admin.component';
import { ManageUserComponent } from './manage-user/manage-user.component';
import { ManageRoleComponent } from './manage-role/manage-role.component';
import { ManageCreativeComponent } from './manage-creative/manage-creative.component';
import { ManageAdSizeComponent } from './manage-ad-size/manage-ad-size.component';
import { ManagePermissionComponent } from "./manage-permission/manage-permission.component";
import { ManageAdUnitComponent } from './manage-adunit/manage-adunit.component';
import { ManageCompanyComponent } from './manage-company/manage-company.component';
import { ManageContactComponent } from './manage-contact/manage-contact.component';

const adminRoutes: Routes = [
  {
    path: '',
    component: AdminComponent,
    children: [
      {
        path: 'manage-user',
        component: ManageUserComponent
      },
      {
        path: 'manage-role',
        component: ManageRoleComponent
      },
      {
        path: 'manage-creative',
        component: ManageCreativeComponent
      },
      {
        path: 'manage-ad-size',
        component: ManageAdSizeComponent
      },
      {
        path: 'manage-permission',
        component: ManagePermissionComponent
      },
      {
        path: 'manage-ad-unit',
        component: ManageAdUnitComponent
      },
      {
        path: 'manage-contact',
        component: ManageContactComponent
      },
      {
        path: 'manage-company',
        component: ManageCompanyComponent
      }
    ]
  }
];

export const adminRouting: ModuleWithProviders = RouterModule.forChild(adminRoutes);
