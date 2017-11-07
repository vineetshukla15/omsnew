import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ProductManagementComponent} from "./product-management.component";
import {ManageProductPricingComponent} from "./manage-pricing/manage.product.pricing.component";


const ProductManagementRoutes: Routes = [
  {
    path: '',
    component: ProductManagementComponent,
    children: [
      {
        path: 'manage-product',
        component: ManageProductPricingComponent
      }
    ]
  }
];

export const ProductManagementRouting: ModuleWithProviders = RouterModule.forChild(ProductManagementRoutes);
