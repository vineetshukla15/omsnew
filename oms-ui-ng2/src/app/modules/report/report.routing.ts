import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ReportComponent } from './report.component';
import { CampaignComponent } from './campaign/campaign.component';
import { CampaignReportComponent } from './campaign-report/campaign.report.component';
import { CampaignDetailsComponent } from './campaign-details/campaign.details.component';


const reportRoutes: Routes = [
  {
    path: '',
    component: ReportComponent,
    children: [
      {
        path: 'campaign',
        component: CampaignComponent
      },
      {
        path: 'campaign-details',
        component: CampaignDetailsComponent
      },
      {
        path: 'campaign-report',
        component: CampaignReportComponent
      }
    ]
  }
];

export const reportRouting: ModuleWithProviders = RouterModule.forChild(reportRoutes);
