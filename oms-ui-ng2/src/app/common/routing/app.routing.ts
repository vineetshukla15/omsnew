import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from '../../modules/login/login.component';
import { NotFoundComponent } from '../../modules/not-found/not-found.component';
import { DashboardComponent } from "../../modules/dashboard/dashboard.component";
import {DashboardProposalComponent} from "../../modules/dashboard/proposal/dashboard-proposal.component";


const appRoutes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'login/:token',
    component: LoginComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      {
        path: 'proposal',
        component: DashboardProposalComponent
      },
      {
        path: 'admin',
        loadChildren: 'app/modules/admin/admin.module#AdminModule'
      },
      {
        path: 'sales',
        loadChildren: 'app/modules/sales/sales.module#SalesModule'
      },
      {
        path: 'operations',
        loadChildren: 'app/modules/operations/operations.module#OperationsModule'
      },
      {
        path: 'report',
        loadChildren: 'app/modules/report/report.module#ReportModule'
      },
      {
        path: 'product-management',
        loadChildren: 'app/modules/product-management/product-management.module#ProductManagementModule'
      }
    ]
  },
  { path: '**', component: NotFoundComponent }
];

export const appRouting: ModuleWithProviders = RouterModule.forRoot(appRoutes, { useHash: true });
