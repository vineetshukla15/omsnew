import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import {AccordionModule } from "ngx-bootstrap/accordion";
import {ProductManagementComponent} from "./product-management.component";
import {ProductManagementRouting} from "./product-management.routing";
import {ManageProductPricingComponent} from "./manage-pricing/manage.product.pricing.component";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

@NgModule({
  imports: [
    AccordionModule.forRoot(),
    BsDropdownModule.forRoot(),
    CommonModule,
    FormsModule,
    ProductManagementRouting
  ],
  declarations: [
    ProductManagementComponent,
    ManageProductPricingComponent
  ],
  entryComponents: [
  ]
})
export class ProductManagementModule { }
