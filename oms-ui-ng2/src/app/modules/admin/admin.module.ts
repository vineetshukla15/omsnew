import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { adminRouting } from './admin.routing';
import { AdminComponent } from './admin.component';
import { ManageUserComponent } from './manage-user/manage-user.component';


import { AccordionModule } from "ngx-bootstrap/accordion";
import { ModalModule } from 'ngx-bootstrap/modal';
import { ManageRoleComponent } from './manage-role/manage-role.component';
import { ManageCreativeComponent } from './manage-creative/manage-creative.component';
import { ManageAdSizeComponent } from './manage-ad-size/manage-ad-size.component';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ManagePermissionComponent } from "./manage-permission/manage-permission.component";
import { ManageAdUnitComponent } from './manage-adunit/manage-adunit.component';

import { ManageCompanyComponent } from './manage-company/manage-company.component';
import { ManageContactComponent } from './manage-contact/manage-contact.component';
import { MultiselectDropdownModule } from "angular-2-dropdown-multiselect";

import { MaterialModule } from "@angular/material";
import { UserSearchDialog } from "./manage-user/dialog/user-search/user-search.component";
import { DatepickerModule } from "ngx-bootstrap";
import { DataTableModule } from "../../common/modules/datatable/datatable.module";
import { DataTableSearchModule } from "../../common/modules/dtsearch/dtsearch.module";
import { SharedModule } from "../../common/shared/shared.module";

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
    adminRouting,
    MaterialModule,
    SharedModule
  ],
  declarations: [
    AdminComponent,
    ManageUserComponent,
    ManageRoleComponent,
    ManageCreativeComponent,
    ManageAdSizeComponent,
    ManagePermissionComponent,
    ManageAdUnitComponent,
    ManageCompanyComponent,
    ManageContactComponent,
    UserSearchDialog
  ],
  entryComponents: [
    UserSearchDialog
  ]
})
export class AdminModule { }
