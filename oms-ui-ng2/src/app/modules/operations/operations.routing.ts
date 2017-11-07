import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OperationsComponent } from './operations.component';
/*import { CampaignComponent } from './campaign/campaign.component';
import { CampaignReportComponent } from './campaign-report/campaign.report.component';
import { CampaignDetailsComponent } from './campaign-details/campaign.details.component';*/
import { ManageCreativeComponent } from './manage-creative/manage-creative.component';


const operationsRoutes: Routes = [
  {
    path: '',
    component: OperationsComponent,
    children: [
      {
        path: 'manage-creative',
        component: ManageCreativeComponent
      }
    ]
  }
];

export const OperationsRouting: ModuleWithProviders = RouterModule.forChild(operationsRoutes);
