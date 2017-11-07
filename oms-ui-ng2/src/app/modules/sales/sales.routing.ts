import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SalesComponent } from './sales.component';
import { ProposalComponent } from "./proposal/proposal.component";
import { OpportunityComponent } from "./opportunity/opportunity.component";
import { ProposalListComponent } from "./proposal/list/proposal.list.component";
import { ProposalAddComponent } from "./proposal/add/proposal.add.component";
import { ProposalEditComponent } from "./proposal/edit/proposal.edit.component";
import { ProposalLineitemComponent } from "./proposal-lineitem/proposal.lineitem.component";
import { ProposalLineitemListComponent } from "./proposal-lineitem/list/proposal.lineitem.list.component";
import { ProposalLineitemAddComponent } from "./proposal-lineitem/add/proposal.lineitem.add.component";
import { ProposalNonApprovedListComponent } from "./proposal/non_approved_list/proposal.non.approved.list.component";
import { ProposalNonApprovedEditComponent } from "./proposal/non_approved/proposal.non.approved.edit.component";
import { ProposalLineitemNonApprovedComponent } from "./proposal-lineitem/non_approved/proposal.lineitem.non.approved.component";
import { ManageProductComponent } from "./manage-product/manage-product.component";
import { ManageRateCardComponent } from "./manage-ratecard/manage-ratecard.component";
import { OrderComponent } from "./order/order.component";
import { OrderListComponent } from "./order/list/order.list.component";
import { OrderViewComponent } from "./order/view/order.view.component";
import { OrderLineitemComponent } from "./order-lineitem/order.lineitem.component";
import { OrderLineitemListComponent } from "./order-lineitem/list/order.lineitem.list.component";
import { OrderLineitemViewComponent } from "./order-lineitem/view/order.lineitem.view.component";
import { OrderLineitemAddComponent } from "./order-lineitem/add/order.lineitem.add.component";

const salesRoutes: Routes = [
  {
    path: '',
    component: SalesComponent,
    children: [
      {
        path: 'proposal',
        component: ProposalComponent,
        children: [
          {
            path: 'list',
            component: ProposalListComponent
          },
          {
            path: 'add/:callback',
            component: ProposalAddComponent
          },
          {
            path: 'add',
            component: ProposalAddComponent
          },
          {
            path: 'edit',
            component: ProposalEditComponent
          }
        ]
      },
      {
        path: 'proposal-lineitem',
        component: ProposalLineitemComponent,
        children: [
          {
            path: 'list',
            component: ProposalLineitemListComponent
          },
          {
            path: 'add/:callback',
            component: ProposalLineitemAddComponent
          },
          {
            path: 'add',
            component: ProposalLineitemAddComponent
          },
          {
            path: 'edit/:callback',
            component: ProposalLineitemAddComponent
          },
          {
            path: 'edit',
            component: ProposalLineitemAddComponent
          }

        ]
      }, {
        path: 'non-approved-proposal',
        component: ProposalComponent,
        children: [
          {
            path: 'list',
            component: ProposalNonApprovedListComponent
          },
          {
            path: 'edit',
            component: ProposalNonApprovedEditComponent
          }
        ]
      },
      {
        path: 'non-approved-proposal-lineitem',
        component: ProposalLineitemComponent,
        children: [
          {
            path: 'list',
            component: ProposalLineitemListComponent
          },

          {
            path: 'edit/:callback',
            component: ProposalLineitemNonApprovedComponent
          },
          {
            path: 'edit',
            component: ProposalLineitemNonApprovedComponent
          }

        ]
      },
      {
        path: 'opportunity',
        component: OpportunityComponent
      },
      // {
      //   path: 'order',
      //   component: OrderListComponent
      // },
      {
        path: 'order',
        component: OrderComponent,
        children: [
          {
            path: 'list',
            component: OrderListComponent
          },
          {
            path: 'view',
            component: OrderViewComponent
          }
        ]
      },
      {
        path: 'order-lineitem',
        component: OrderLineitemComponent,
        children: [
          {
            path: 'add',
            component: OrderLineitemAddComponent
          },
          {
            path: 'list',
            component: OrderLineitemListComponent
          },
          {
            path: 'view',
            component: OrderLineitemViewComponent
          },
          {
            path: 'edit/:callback',
            component: OrderLineitemAddComponent
          },
          {
            path: 'add/:callback',
            component: OrderLineitemAddComponent
          }
        ]
      },
      {
        path: 'manage-product',
        component: ManageProductComponent
      },
      {
        path: 'manage-ratecard',
        component: ManageRateCardComponent
      }
    ]
  }
];

export const salesRouting: ModuleWithProviders = RouterModule.forChild(salesRoutes);
