import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AccordionModule } from "ngx-bootstrap/accordion";
import { ModalModule } from 'ngx-bootstrap/modal';
import { MultiselectDropdownModule } from "angular-2-dropdown-multiselect";

import { MaterialModule } from "@angular/material";
import { DatepickerModule } from "ngx-bootstrap";
import { DataTableModule } from "../../common/modules/datatable/datatable.module";
import { DataTableSearchModule } from "../../common/modules/dtsearch/dtsearch.module";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { SharedModule } from "../../common/shared/shared.module";
/*import { CampaignReportService } from './campaign-report/campaign.report.service';
import { CampaignReportComponent } from './campaign-report/campaign.report.component';
import { CampaignDetailsComponent } from './campaign-details/campaign.details.component';*/
import { OperationsRouting } from './operations.routing';
import { OperationsComponent } from './operations.component';
import { ManageCreativeComponent } from './manage-creative/manage-creative.component';

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
    OperationsRouting
  ],
  declarations: [
    OperationsComponent,
    ManageCreativeComponent
    /*CampaignComponent,
    CampaignDetailsComponent,
    AdsListDialogComponent,
    CampaignReportComponent*/
  ],
})
export class OperationsModule { }
