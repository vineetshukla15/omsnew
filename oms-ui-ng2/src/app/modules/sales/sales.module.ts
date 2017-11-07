import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { salesRouting } from './sales.routing';
import { SalesComponent } from './sales.component';
import { SalesService } from './sales.service';
import { ProposalComponent } from "./proposal/proposal.component";
import { OpportunityComponent } from "./opportunity/opportunity.component";

import { AccordionModule } from "ngx-bootstrap/accordion";
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { MultiselectDropdownModule } from "angular-2-dropdown-multiselect";

import { MaterialModule } from "@angular/material";

import { DatepickerModule } from "ngx-bootstrap";
import { DataTableModule } from "../../common/modules/datatable/datatable.module";
import { DataTableSearchModule } from "../../common/modules/dtsearch/dtsearch.module";
import { SelectListModule } from "../../common/modules/select-list/selectlist.module";
import { ProposalListComponent } from "./proposal/list/proposal.list.component";
import { ProposalAddComponent } from "./proposal/add/proposal.add.component";
import { ProposalEditComponent } from "./proposal/edit/proposal.edit.component";
import { ProposalLineitemComponent } from "./proposal-lineitem/proposal.lineitem.component";
import { ProposalLineitemListComponent } from "./proposal-lineitem/list/proposal.lineitem.list.component";
import { ProposalLineitemAddComponent } from "./proposal-lineitem/add/proposal.lineitem.add.component";
import { ManageProductComponent } from "./manage-product/manage-product.component";
import { ManageRateCardComponent } from "./manage-ratecard/manage-ratecard.component";
import { UploadDocumentDialog } from "./upload-document/upload-document.component";
import { ReactiveFormsModule } from '@angular/forms';
import { ProposalNonApprovedListComponent } from "./proposal/non_approved_list/proposal.non.approved.list.component";
import { ProposalNonApprovedEditComponent } from "./proposal/non_approved/proposal.non.approved.edit.component";
import { ProposalLineitemNonApprovedComponent } from "./proposal-lineitem/non_approved/proposal.lineitem.non.approved.component";
import { OrderComponent } from "./order/order.component";
import { OrderListComponent } from "./order/list/order.list.component";
import { OrderViewComponent } from "./order/view/order.view.component";
import { OrderLineitemComponent } from "./order-lineitem/order.lineitem.component";
import { OrderLineitemListComponent } from "./order-lineitem/list/order.lineitem.list.component";
import { OrderLineitemViewComponent } from "./order-lineitem/view/order.lineitem.view.component";
import { OrderLineitemAddComponent } from "./order-lineitem/add/order.lineitem.add.component";
import { SharedModule } from "../../common/shared/shared.module";
import { AlertModule } from 'ngx-bootstrap';

@NgModule({
  imports: [
    AccordionModule.forRoot(),
    BsDropdownModule.forRoot(),
    ModalModule.forRoot(),
    DatepickerModule.forRoot(),
    DataTableModule.forRoot(),
    DataTableSearchModule.forRoot(),
    SelectListModule.forRoot(),
    MultiselectDropdownModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    salesRouting,
    MaterialModule,
    AlertModule.forRoot(),
    SharedModule
  ],
  declarations: [
    SalesComponent,
    ProposalComponent,
    ProposalListComponent,
    ProposalEditComponent,
    ProposalAddComponent,
    OpportunityComponent,
    ProposalLineitemComponent,
    ProposalLineitemListComponent,
    ProposalLineitemAddComponent,
    UploadDocumentDialog,
    ProposalNonApprovedListComponent,
    ProposalNonApprovedEditComponent,
    ProposalLineitemNonApprovedComponent,
    ManageProductComponent,
    ManageRateCardComponent,
    OrderComponent,
    OrderListComponent,
    OrderViewComponent,
    OrderLineitemComponent,
    OrderLineitemListComponent,
    OrderLineitemViewComponent,
    OrderLineitemAddComponent
  ],
  entryComponents: [
    UploadDocumentDialog
  ],
  providers: [SalesService],
})
export class SalesModule { }
