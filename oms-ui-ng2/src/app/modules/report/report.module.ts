import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { reportRouting } from './report.routing';
import { ReportComponent } from './report.component';
import { CampaignComponent } from './campaign/campaign.component';


import { AccordionModule } from "ngx-bootstrap/accordion";
import { ModalModule } from 'ngx-bootstrap/modal';
import { MultiselectDropdownModule } from "angular-2-dropdown-multiselect";

import { MaterialModule } from "@angular/material";
import { DatepickerModule } from "ngx-bootstrap";
import { DataTableModule } from "../../common/modules/datatable/datatable.module";
import { DataTableSearchModule } from "../../common/modules/dtsearch/dtsearch.module";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { SharedModule } from "../../common/shared/shared.module";
import { CampaignReportService } from './campaign-report/campaign.report.service';
import { CampaignReportComponent } from './campaign-report/campaign.report.component';
import { CampaignDetailsComponent } from './campaign-details/campaign.details.component';
import { AdsListDialogComponent } from './ad-dialog/ads.list.dialog.component';

@NgModule({
  imports: [
    AccordionModule.forRoot(),
    BsDropdownModule.forRoot(),
    ModalModule.forRoot(),
    DatepickerModule.forRoot(),
    DataTableModule.forRoot(),
    DataTableSearchModule.forRoot(),
    MultiselectDropdownModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    SharedModule,
    CommonModule,
    reportRouting
  ],
  declarations: [
    ReportComponent,
    CampaignComponent,
    CampaignDetailsComponent,
    AdsListDialogComponent,
    CampaignReportComponent
  ],
  entryComponents: [AdsListDialogComponent],
  providers: [CampaignReportService]
})
export class ReportModule { }
