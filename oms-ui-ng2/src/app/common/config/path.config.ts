export class PathConfig {

    public static get API_ENDPOINT(): string { return 'http://test.com/'; }
    public static get LOOKUP_LIST(): string {
        return PathConfig.API_ENDPOINT + "bts/v1/api/admin/admin";
    }

    //public static BASE_URL_API: string = 'http://192.168.64.56:7998/oms-media/v2.0/';  
    public static BASE_URL_API: string = 'http://192.168.64.56:7120/oms-media/v2.0/';
    //public static BASE_URL_API: string = 'http://localhost:8181/oms-media/v2.0/';
    //public static BASE_URL_API: string = 'http://192.168.64.56:7998/oms-media/v2.0/';
    //public static BASE_URL_API: string = 'http://192.168.64.55:8181/oms-media/v1.8/';
    //public static BASE_URL_API: string = 'http://192.168.64.55:7998/oms-media/v1.8/';
    //public static BASE_URL_API: string = 'http://localhost:8181/oms-media/v1.8/';
    public static USER_LIST: string = "users/";
    public static USER_GET_LIST: string = "user/list/"; 
    public static AUTHENTICATE: string = "authenticate";
    public static OATH_TOKEN: string = "oauth/token" 
    public static USER_ADD: string = "user/";
    public static USER_DELETE: string = "user/";
    public static USER_WITH_ID: string = "user/";
    public static USER_SEARCH_LAST_NAME_FROM_LADP: string = "searchUserByLastName/";
    public static USER_SEARCH_FIRST_NAME_FROM_LADP: string = "searchUserByFirstName/";
    public static USER_LIST_WITH_ROLE: string = "user/role/list";

    public static CREATIVE_LIST: string = "creative/list";
    public static CREATIVE_LIST_GET: string = "cretiveasset/";
    public static CREATIVE_DELETE: string = "creative/";
    public static CREATE_CREATIVE: string = "creativeassetjson/";
    public static CREATIVE_ADD: string = "creative/";
    public static CREATIVE_BY_ID: string = "creativebyid/";
    public static CREATIVE_LIST_API: string = "creative/list";
    public static UPLOAD_CREATIVE_ASSET: string = "uploadcretiveasset/";

    public static PRODUCT_LIST: string = "product/list";
    public static PRODUCT_GET_LIST: string = "product";
    public static PRIORITY_GET_LIST: string = "product/delivery/metadata/priority";

    public static PRODUCT_TYPE_LIST: string = "productType/list";
    public static PRODUCT_ADD: string = "product";
    public static PRODUCT_DELETE: string = "product/";


    public static AD_UNIT_LIST: string = "adUnit/list";
    public static AD_UNIT_ADD: string = "adUnit/";
    public static AD_UNIT_GET_BY_ID: string = "adUnit/";
    public static AD_UNIT_DELETE: string = "adUnit/";
    public static AD_UNIT_UPDATE: string = "adUnit/";

    public static ROLE_LIST: string = "role/list";
    public static ROLE_ADD: string = "role/";
    public static ROLE_GET_BY_ID: string = "role/";
    public static ROLE_DELETE: string = "role/";

    public static RATE_CARD_LIST: string = "rateCard/list";
    public static RATE_CARD_ADD: string = "rateCard/";
    public static RATE_CARD_GET_BY_ID: string = "rateCard/";
    public static RATE_CARD_DELETE: string = "rateCard/";

    public static RATE_TYPE_LIST: string = "rateType/list/";

    public static PERMISSION_LIST: string = "hlpermission/";

    public static LOGED_IN_USER: string = "loggedInUser/";

    public static COMPANY_LIST: string = "company/list";
    public static COMPANY_ADD: string = "company/";
    public static COMPANY_GET_BY_ID: string = "company/";
    public static COMPANY_DELETE: string = "company/";
    public static COMPANY_STATUS: string = "companyStatus/list";

    public static CONTACT_LIST: string = "contact/list";
    public static CONTACT_ADD: string = "contact/";
    public static CONTACT_GET_BY_ID: string = "contact/";
    public static CONTACT_DELETE: string = "contact/";
    public static SIGNUP_API: string = "registration/";
    public static SIGNUP_CONFIRM_API: string = "registration/confirm";
    public static SET_REGISTRATION: string = "registration/setPassword";

    public static TARGET_TYPE_LIST: string = "audienceTargetType/list";

    public static OPPORTUNITY_LIST: string = "opportunity/list";
    public static OPPORTUNITY_ADD: string = "opportunity/";
    public static OPPORTUNITY_GET_BY_ID: string = "opportunity/";
    public static OPPORTUNITY_DELETE: string = "opportunity/";
    public static OPPORTUNITY_FILEUPLOAD: string = "opportunity/documents";
    public static OPPORTUNITY_COPY: string = "opportunity/copy/";
    public static OPPORTUNITY_VERSION_UPDATE: string = "opportunity/versionUpdate/";

    public static SALESCATEGORY_GET_LIST: string = "salesCategory/list";
    public static BILLINGSOURCE_GET_LIST: string = "billingSource/list";
    //public static SALESPERSON_GET_LIST = "searchUserByRoleName/Sales Person";
    public static TRAFFICKER_GET_LIST = "searchUserByRoleName/Trafficker";
    public static SALESMANAGER_GET_LIST = "searchUserByRoleName/Sales Manager";
    public static MEDIAPLANNER_GET_LIST = "searchUserByRoleName/Media Planner";
    public static PRICINGMANAGER_GET_LIST = "searchUserByRoleName/Pricing Manager";
    public static ADMIN_GET_LIST = "searchUserByRoleName/Administrator";
    public static LEGAL_GET_LIST = "searchUserByRoleName/Legal";
    public static SPEC_TYPE_LIST: string = "specTypes/";

    public static PROPOSAL_LIST: string = "proposal/list";
    public static PROPOSAL_ADD: string = "proposal/";
    public static PROPOSAL_GET_BY_ID: string = "proposal/";
    public static PROPOSAL_DELETE: string = "proposal/";
    public static PROPOSAL_LINEITEM_GET_PRICE = "lineItem/getPrice";
    public static PROPOSAL_STATUS = "proposal/status/list";
    public static PROPOSAL_LINEITEM_LIST = "lineItem/list";
    public static PROPOSAL_LINEITEM_DELETE: string = "lineItem/";
    public static PROPOSAL_FILEUPLOAD: string = "proposal/documents";
    public static PROPOSAL_VERSION_UPDATE: string = "proposal/updateVersion/";

    public static WORKFLOW_START = "start-proposal-approval";
    public static FIND_TASK_ID = "process/"; ///process/{{processid}}/task/candidateuser/411  
    public static ASSIGN_TASK = "process/";// process/{{processid}}/assignTask/1205005/411
    public static TASK_GET_ID = "proposal/task/";
    // Dashboard related uri
    public static DASHBOARD_PROPOSAL_LIST: string = "dashboard/tasks/";
    public static DASHBOARD_STATISTICS: string = "dashboard/statistics/";
    public static DASHBOARD_PROPOSAL_COUNTS: string = "dashboard/proposalCount/";
    public static DASHBOARD_ADMIN_USER_LIST: string = "/user/list";
    public static DASHBOARD_PROPOSAL_INACTIVE_LIST: string = "dashboard/candidateTasks/";

    public static DELIVERY_IMPRESSIONS_GET: string = "product/delivery/metadata/delivery_impressions";
    public static ROTATE_CREATIVES_GET: string = "product/delivery/metadata/rotate_creatives";
    public static DISPLAY_CREATIVES_GET: string = "product/delivery/metadata/display_creatives";

    public static CAMPAIGN_LIST: string = "vpzcampaigns";
    public static GOAL_LIST: string = "vpzgoal";
    public static GOAL_AD_LIST: string = "vpzads";
    public static CAMPAIGN_REPORT_LIST: string = "campaignreport/8275608d-d8f0-471e-a850-718166f3c660";

    public static CRETIVEASSET_LIST: string = "cretiveasset";

    public static ORDER_LIST: string = "order/list";
    public static ORDER: string = "order/";   

    public static ORDER_LINEITEM_LIST: string = "orderLineItem/list";   
    
}
