export class ConstantConfig {
  public static QUERY_TYPES: Array<string> = ['sql', 'optionalClauseSql'];
  public static STATUS_TYPES: Array<Object> = [
    { id: 'A', labelName: 'Active' },
    { id: 'C', labelName: 'Created' },
    { id: 'I', labelName: 'Inactive' },
  ];

  public static AUTH_TOKEN: string = "auth-token";
  public static MOBILE_CHECK: boolean = /Android|webOS|iPhone|Opera Mini/i.test(navigator.userAgent);
  public static COPY_RIGHT: string = "Tavant Copyright 2017- 1.0";

  public static PROPOSAL_ERROR_MESSAGES: Object = {
    "nameError": "Proposal 'name' is required",
    "MediaPlannerError": "'Media planner' is required field",
    "traffickerModelError": "'Trafficker' is required field",
    "createdOnError": "'Requested on' is required field",
    "dueDateError": "'Due on' is required field",
    "startDateError": "'Start date' is required field",
    "endDateError": "'End date' is required field",
    "pricingModelError": "'Pricing model' is required field",
    "budgetError": "'Budget' is required field",
    "currencyModelError": "'Currency' is required field",
    "percentageOfCloseError": "'Probability of close' is required field"
  };

  /*{
   "menu" : "Product",
   "iconClass":'salesImg',
   "subMenu" : [{
   "name" : 'Manage Pricing',
   "path" : ''
   }
   ]
   },*/
  public static SIDE_NAV: Array<Object> = [{
    "menu": "Dashboard",
    "iconClass": 'dashboardImg',
    "subMenu": [{
      "name": 'Proposal',
      "path": 'proposal',
      "permKey": 'view_proposal'
    }]
  }, {
    "menu": "Sales",
    "iconClass": 'salesImg',
    "subMenu": [{
      "name": 'Opportunities',
      "path": 'sales/opportunity',
      "permKey": 'view_opportunity'
    }, {
      "name": 'Products',
      "path": 'sales/manage-product',
      "permKey": 'view_products'
    }, {
      "name": 'Proposals',
      "path": 'sales/proposal/list',
      "permKey": 'view_proposal'
    }, {
      "name": 'Proposal Line Items',
      "path": 'sales/proposal-lineitem/list',
      "permKey": 'view_proposal'
    }, {
      "name": 'Rates',
      "path": 'sales/manage-ratecard',
      "permKey": 'view_rate_cards'
    } /*, {
      "name": 'Non Approved Proposals ',
      "path": 'sales/non-approved-proposal/list'
    }*/

      /*{
        "name": 'Options',
        "path": ''
      },
      {
        "name": 'Product',
        "path": ''
      },
      {
        "name": 'Rates',
        "path": ''
      }*/
    ]
  }, {
    "menu": "Operations",
    "iconClass": 'DeliveryImg',
    "subMenu": [
      {
      "name": 'Creatives',
      "path": 'operations/manage-creative',
      "permKey": 'view_creatives'
    },{
      "name": 'Order',
      "path": 'sales/order/list',
      "permKey": 'view_order'
    }, {
      "name": 'Order Line Items',
      "path": 'sales/order-lineitem/list',
      "permKey": 'view_order'
    }

    /*{
      "name": 'Manage Pricing',
      "path": '',
      "permKey": 'delivery_view'
    }, {
      "name": 'Manage Product',
      "path": '',
      "permKey": 'delivery_view'
    },
    {
      "name": 'Manage Creative',
      "path": '',
      "permKey": 'delivery_view'
    },
    {
      "name": 'Manage Attributes',
      "path": '',
      "permKey": 'delivery_view'
    }*/
    ]
  }, {
    "menu": "Financial",
    "iconClass": 'FinanceImg',
    "subMenu": [{
      "name": 'Manage Pricing',
      "path": '',
      "permKey": 'financial'
    }, {
      "name": 'Manage Product',
      "path": '',
      "permKey": 'financial'
    },
    {
      "name": 'Manage Creative',
      "path": '',
      "permKey": 'financial'
    },
    {
      "name": 'Manage Attributes',
      "path": '',
      "permKey": 'financial'
    }
    ]
  }, {
    "menu": "Reports",
    "iconClass": 'ReportsImg',
    "subMenu": [{
      "name": 'Campaign',
      "path": 'report/campaign',
      "permKey": 'view_campaign'
    }, {
      "name": 'Campaign Report',
      "path": 'report/campaign-report',
      "permKey": 'view_campaign_report'
    }]
  }, {
    "menu": "Admin",
    "iconClass": 'adminImg',
    "subMenu": [{
      "name": 'Users',
      "path": 'admin/manage-user',
      "permKey": 'view_users'
    }, {
      "name": 'Roles',
      "path": 'admin/manage-role',
      "permKey": 'view_roles'
    },
    {
      "name": 'Ad Size',
      "path": 'admin/manage-ad-size',
      "permKey": 'view_ad_size'
    },
    {
      "name": 'Ad Units',
      "path": 'admin/manage-ad-unit',
      "permKey": 'view_ad_units'
    },
    {
      "name": 'Companies',
      "path": 'admin/manage-company',
      "permKey": 'view_companies'
    },
    {
      "name": 'Contacts',
      "path": 'admin/manage-contact',
      "permKey": 'view_contacts'
    }
    ]
  }
  ];
}
