import {
  Component, OnInit, AfterViewInit, ElementRef, HostListener, EventEmitter, Output, Input
} from '@angular/core';
import { HttpService } from '../../../../common/services/http.service';
import { PathConfig } from '../../../../common/config/path.config';
import { ConstantConfig } from '../../../../common/config/constant.config';
import { LocalStorageService } from "../../../../common/services/local-storage.service";
import { Broadcaster } from '../../../../common/services/broadcaster.service';
import { EmitterService } from '../../../../common/services/emitter.service';
import { AuthService } from '../../../../common/services/auth.service';
import { Proposal } from '../../../../common/models/proposal';
import { Order } from '../../../../common/models/order';
import { MdDialog, MdDialogRef } from '@angular/material';
import { ConfirmDialogComponent } from "../../../../common/modules/dialog/confirm/confirm.component";
import { DateFormat } from "../../../../common/modules/date.format";
import { IMultiSelectOption } from "angular-2-dropdown-multiselect";
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { IMultiSelectTexts } from "angular-2-dropdown-multiselect";
import { AdUnit } from '../../../../common/models/ad.unit';
import { ProposalLineItem } from '../../../../common/models/proposal.lineitem';
import { workflowTask } from '../../../../common/models/workflow.task';
import { AudienceTargetType } from '../../../../common/models/audience.target.type';
import { AudienceTargetValues } from '../../../../common/models/audience.target.values';
import { Router, ActivatedRoute } from '@angular/router';
import { DropDownDataFilter } from '../../../../common/filter/dropdown.filter';
import { UploadDocumentDialog } from "../../upload-document/upload-document.component";
import { objectSharingService } from '../../../../common/services/objectSharingService';
import { DropDownSetting } from '../../../../common/utils/dropdown.settings';
import { ApplicationMessages } from '../../../../common/config/application.messages';
import { DateFormatterComponent } from "../../../../common/utils/date-format.component";
import * as _ from 'lodash';

declare var $: any;

@Component({
  templateUrl: './order.view.component.html',
  styleUrls: ['./../order.component.scss'],
})
export class OrderViewComponent implements OnInit {

  @Input() disable: boolean = true;
  showForm: Boolean = false;
  showLineItemForm: Boolean = false;

  proposalListDTConfig: Object = {};
  proposalListRefresh: Boolean = false;
  proposalListSearchModel: any = {};
  newOrder: Order = new Order();
  workflowTask: workflowTask = new workflowTask();
  currentStatusName: string;
  errorMessage: string;
  previousStatus: string;
  proposalId: number;
  isAlertMessageBoxVisible: Boolean = true;
  successMessage: string;

  editMode: Boolean = false;
  proposalSold: boolean = false;
  showSchedule: boolean = false;
  showFinancialDetails: boolean = false;
  //audienceTargetValues: Array<Object> = [{ "id": 1, "label": 'Lorem Ipsum' }, { "id": 1, "label": 'Lorem Ipsum', "isSelected": true }, { "id": 1, "label": 'Lorem Ipsum' }, { "id": 1, "label": 'Lorem Ipsum' }, { "id": 1, "label": 'Lorem Ipsum' }]
  audienceTargetValues: Array<Object>;
  selectedTargetTypeList = [];
  targetTypeList: Array<Object> = [];
  mediaPlannerList: Array<Object> = [];
  lineItemConfig: Object;
  lineItemRefresh: Boolean = false;
  productOptionsModel: number[];
  productList: Array<Object> = [];
  assignToList: Array<Object> = [];
  productOptions: IMultiSelectOption[];
  searchSettings: IMultiSelectSettings;
  singleSelectSettings: IMultiSelectSettings;
  myTexts: IMultiSelectTexts;
  proposalDocumentTable: boolean = true;
  proposalDocument1: Array<Object> = [];
  versionOptions: IMultiSelectOption[];
  versionOptionsModel: Array<Object> = [];
  saleCategoryOptions: IMultiSelectOption[];
  saleCategoryModel = [];
  saleManagerOptions: IMultiSelectOption[];
  saleManagerModel = [];
  traffickerOptions: IMultiSelectOption[];
  traffickerModel = [];
  assignToOptions: IMultiSelectOption[];
  assignToOptionsModel: Array<Object> = [];
  mediaPlannerOptions: IMultiSelectOption[];
  mediaPlannerOptionsModel: Array<Object> = [];
  agencyOptions: IMultiSelectOption[];
  agencyModel = [];
  advertiserOptions: IMultiSelectOption[];
  advertiserModel = [];
  temp1 = [];
  temp2 = [];
  temp3 = [];
  temp4 = [];
  temp5 = [];
  temp6 = [];
  pricingModelOptions: Array<Object> = [{
    "id": "Net",
    "name": "Net"
  }, {
    "id": "Gross",
    "name": "Gross"
  }];
  pricingModel = [];
  logedInUserFullName: string;
  logedInUserId: number;
  typeOptions: Array<Object> = [{
    "id": 1,
    "name": "Agency"
  }, {
    "id": 2,
    "name": "Advertiser"
  }];
  typeModel = [];
  statusModel = [];
  remainingCost: number = 0;

  statusOptions: Array<Object> = [];
  currrencyModel = [];
  currencyOptions: Array<Object> = [{
    "id": "USD",
    "name": "USD"
  },
  {
    "id": "INR",
    "name": "INR"
  },
  {
    "id": "EUR",
    "name": "EUR"
  }];

  assignedBy: string;
  adUnitList: Array<AdUnit> = [];
  disabledFormElements: Boolean = true;
  adUnitOptions: IMultiSelectOption[];
  adUnitOptionsModel: number[];
  //newProposal: ProposalLineItem = new ProposalLineItem();
  newAudienceTargetType: AudienceTargetType = new AudienceTargetType();
  audienceTargetValuesList: Array<AudienceTargetValues> = [];
  newAudienceTargetValues: AudienceTargetValues = new AudienceTargetValues();
  targetTypeConfig: Object;
  documentConfig: Object;
  proposalDocument: Array<Object> = [];
  showAudienceTargetValuesForm: Boolean = false;
  seletedAudienceTargetValuesArr: Array<Object>
  assignToDisplayName: String = null;
  specTypeList: Array<Object> = [];
  keysGetter = Object.keys;
  selectedId: number = 0;
  initialSelectedId: number = 0;
  auditObj: Array<Object> = [];
  isGoBtnEnable: boolean = false;
  loginUserId: number = -1;
  proposalName: string = "";
  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService,
    private dialog: MdDialog,
    private elementRef: ElementRef,
    private router: Router,
    private activatedRouter: ActivatedRoute,
    emitterService: EmitterService,
    public getSetService: objectSharingService
  ) { }

  typeOptionsChange(data) {
    if (!_.isEmpty(data) && data.id) {
      this.assignToDisplayName = data.firstName + ' ' + data.lastName;
      this.assignToOptionsModel[0] = data.id;
      this.selectedId = data.id;
      this.isGoBtnEnable = this.initialSelectedId != data.id;
    }
  }

  ngOnInit() {
    //this.getLoginUserDetails();
    this.loadAllDropdowns();
    this.loadProposalData();
    this.loggedInUserDetails();
    let param = this.activatedRouter.snapshot.queryParams["proposalItem"];
    if (param !== undefined) {
      this.newOrder = JSON.parse(param);
    }    
    this.searchSettings = {
      enableSearch: true,
      dynamicTitleMaxItems: 3,
      displayAllSelectedText: true
    };

    this.singleSelectSettings = DropDownSetting.singleSelectSettings;
    this.myTexts = DropDownSetting.textSetting;
    this.proposalListDTConfig = {
      "columnDefs": [
        {
          "targets": 2,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              var date = new DateFormat().getDateFromNanoSecond(data);
              template = '<span>' + date + '</span>';
            }
            return template;
          }
        },
        {
          "targets": 3,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              var date = new DateFormat().getDateFromNanoSecond(data);
              template = '<span>' + date + '</span>';
            }
            return template;
          }
        },
        {
          "targets": 4,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              var date = new DateFormat().getDateFromNanoSecond(data);
              template = '<span>' + date + '</span>';
            }
            return template;
          }
        },
        {
          "className": "text-center",
          "orderable": false,
          "targets": 9,
          "render": function (data, type, full, meta) {
            var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
              '<a href="javascript:void(0);" data-name="edit" data-custom="' + data + '"><span class="fa fa-pencil" aria-hidden="true"></span>Edit</a>' +
              "<a href='javascript:void(0);' data-name='delete' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>" +
              '</div>';
            return template;
          }
        }],
      "columns": [
        { "title": 'Name', "data": "name" },
        { "title": 'Opportunity', "data": "opportunity.name" },
        { "title": "Start Date", "data": "startDate" },
        { "title": "End Date", "data": "endDate" },
        { "title": "Due Date", "data": "dueDate" },
        { "title": 'Budget', "data": "budget" },
        { "title": "Advertiser", "data": "opportunity.advertiser.name" },
        { "title": "Advertiser Discount", "data": "advertiserDiscount" },
        { "title": '', "data": "proposalId" }
      ],
      "apiURL": PathConfig.BASE_URL_API + PathConfig.PROPOSAL_LIST
    }


    this.lineItemConfig = {
      "dom": 'tr',
      "columnDefs": [
        {
          "orderable": false,
          "targets": 6,
          "render": function (data, type, full, meta) {
            var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
              "<a href='javascript:void(0);' data-name='edit' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-pencil' aria-hidden='true'></span>View</a>" +

              '</div>';
            return template;
          }
        }, {
          "orderable": false,
          "targets": 3,
          "render": function (data, type, full, meta) {
            var startDate = new DateFormatterComponent().changeDateFormatTo_dd_mm_yy(full.flightStartdate, 'dd-MMM-yyyy');
            var endDate = new DateFormatterComponent().changeDateFormatTo_dd_mm_yy(full.flightEndDate, 'dd-MMM-yyyy');

            var template = '<div>' + startDate + ' to ' + endDate + '</div>';
            return template;
          }
        }
      ],
      "columns": [
        { "title": 'Name', "data": "name" },
        { "title": 'CPM', "data": "proposedCost" },
        { "title": 'Impression', "data": "quantity" },
        { "title": 'Flight', "data": "flightStartdate" },
        { "title": 'Priority', "data": "priority" }, 
        { "title": 'VPZ Goal Id', "data": "vpzGoalId" },
        { "title": '', "data": "lineItemId" }
      ]
    }

    this.targetTypeConfig = {
      "processing": false,
      "serverSide": false,
      "dom": 'tr',
      "columnDefs": [
        {
          "orderable": false,
          "targets": 2,
          "render": function (data, type, full, meta) {
            var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
              "<a href='javascript:void(0);' data-name='edit' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-pencil' aria-hidden='true'></span>Edit</a>" +
              "<a href='javascript:void(0);' data-name='delete' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>" +
              '</div>';
            return template;
          }
        }],
      "columns": [
        { "title": 'Target Type', "data": "name" },
        { "title": 'Elements', "data": "targetTypeAudienceName" },
        { "title": '', "data": "targetTypeId" }
      ]
    }

    this.documentConfig = {
      "processing": false,
      "serverSide": false,
      "dom": 'tr',
      "columnDefs": [
        {
          "orderable": false,
          "targets": 3,
          "render": function (data, type, full, meta) {
            var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
              "<a href='javascript:void(0);' data-name='delete' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>" +
              '</div>';
            return template;
          }
        }],
      "columns": [
        { "title": 'NAME', "data": "name" },
        { "title": 'TYPE', "data": "type" },
        { "title": 'MODIFIED BY', "data": "username" },
        { "title": "ACTION", "data": "targetTypeId" }
      ]
    }
  }

  newRowsAdded = [];

  /*getLoginUserDetails(){
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let url = PathConfig.BASE_URL_API + PathConfig.LOGED_IN_USER;
    var data = this.httpService.get(url, auth_token);

    data.subscribe(
      data => {
        if (data.id) {
          this.loginUserId = data.id;
        }
      }
    );
  }*/


  loadAllDropdowns() {
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let url1 = PathConfig.BASE_URL_API + PathConfig.SALESCATEGORY_GET_LIST;
    let url2 = PathConfig.BASE_URL_API + PathConfig.SALESMANAGER_GET_LIST;
    let url3 = PathConfig.BASE_URL_API + PathConfig.TRAFFICKER_GET_LIST;
    let url4 = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_STATUS;
    let url5 = PathConfig.BASE_URL_API + PathConfig.COMPANY_LIST;
    let url6 = PathConfig.BASE_URL_API + PathConfig.MEDIAPLANNER_GET_LIST;

    var data1 = this.httpService.get(url1, auth_token);
    var data2 = this.httpService.get(url2, auth_token);
    var data3 = this.httpService.get(url3, auth_token);
    var data4 = this.httpService.get(url4, auth_token);
    var data5 = this.httpService.get(url5, auth_token);
    var data6 = this.httpService.get(url6, auth_token);

    let self = this;
    data1.subscribe(
      data1 => {
        if (data1) {
          this.saleCategoryOptions = this.convertDataIntoDropdown(data1, 'salesCatagoryId', 'name');
          this.temp1 = [];
          this.temp1 = data1;
        }
      });

    data2.subscribe(
      data2 => {
        if (data2) {
          this.saleManagerOptions = this.convertDataIntoDropdown(data2, 'id', 'username');
          this.temp2 = [];
          this.temp2 = data2;
        }
      });

    data3.subscribe(
      data3 => {
        if (data3) {
          this.traffickerOptions = this.convertDataIntoDropdown(data3, 'id', 'username');
          this.temp3 = [];
          this.temp3 = data3;
        }
      });

    data4.subscribe(
      data4 => {
        if (data4) {
          this.statusOptions = this.convertDataIntoDropdown(data4, 'statusId', 'name');
          this.temp4 = [];
          this.temp4 = data4;
        }
      });

    data5.subscribe(
      data5 => {
        if (data5) {
          let agencyData = data5.filter(function (value) {
            if (value.type == 'Agency') {
              return value;
            }
          });
          let advertiserData = data5.filter(function (value) {
            if (value.type == 'Advertiser') {
              return value;
            }
          });
          this.agencyOptions = this.convertDataIntoDropdown(agencyData, 'companyId', 'name');
          this.temp5 = [];
          this.temp5 = agencyData;
          this.advertiserOptions = this.convertDataIntoDropdown(advertiserData, 'companyId', 'name');
          this.temp6 = [];
          this.temp6 = advertiserData;
        }
      });

    data6.subscribe(
      data6 => {
        if (data6) {
          this.mediaPlannerOptions = this.convertDataIntoDropdownForUser(data6, 'id', 'firstName', 'lastName');
          this.mediaPlannerList = data6;
        }
      });
    this.loadProposalDataAfterLineitem();
  }


  loadProposalDataAfterLineitem() {
    let param = this.activatedRouter.snapshot.queryParams["proposalItem"];
    this.loggedInUserDetails();
    if (param !== undefined) {
      this.newOrder = new Order();
      this.newOrder = JSON.parse(param);
  //    this.loadFileDocuments(this.newProposal.proposalId);
      /*this.newProposal['saleCategoryModel'];
      this.newProposal['saleManagerModel'];
      this.newProposal['traffickerModel'];
      this.newProposal['statusModel'];
      this.newProposal['agencyModel'];
      this.newProposal['advertiserModel'];*/
      this.loadDropdownSaveData(this.newOrder);
    } else {
      let proposalId = this.activatedRouter.snapshot.queryParams["proposalId"];
      this.loadProposalData();
 //     this.loadFileDocuments(proposalId);
    }
  }

  loadFileDocuments(proposalId) {
    let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_FILEUPLOAD + '/' + proposalId;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);

    data.subscribe(
      data => {
        if (data) {
          for (var i in data) {
            data[i]['proposalId'] = proposalId;
            this.actionOnUploadFile(data[i], null, false, true);
          }
          let self = this;
          self.proposalDocument1 = [];
          setTimeout(function () {
            self.proposalDocument1 = self.proposalDocument;
          }, 5);
        }
      });
  }

  // on Menu Selection
  onProposalTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'edit') {
      //edit code here
      this.editLineitem(JSON.parse(data['value']));
    }

  }

  //back to table button
  backToTable() {
    this.showForm = false;
    this.proposalListRefresh = true;
    this.showForm = false;
    this.proposalSold = false;
    this.proposalListRefresh = true;
    this.errorMessage = "";
    this.router.navigate(['dashboard/sales/order/list']);
    /* let param = this.activatedRouter.snapshot.queryParams["callback"];
     if (param === 'addProposal') {
       this.router.navigate(['dashboard/sales/non-approved-proposal/list']);
     } else {
       this.router.navigate(['dashboard/sales/non-approved-proposal-lineitem/list']);
     }*/
  }
  //back to form
  backToForm() {
    this.showLineItemForm = false;
    this.showAudienceTargetValuesForm = false;
  }


  //search dropdown list
  proposalSearchTableDD: Array<Object> = [
    { "id": 0, "labelName": "Name" },
    { "id": 1, "labelName": "Opportunity" },
    { "id": 2, "labelName": "Start Date" },
    { "id": 3, "labelName": "End Date" },
    { "id": 4, "labelName": "Due Date" },
    { "id": 5, "labelName": "Budget" },
    { "id": 6, "labelName": "Advertiser" },
    { "id": 6, "labelName": "Advertiser Discount" },
  ];
  //proposal dropdown Event
  proposalSearchEvent(data) {
    this.proposalListSearchModel['searchTxt'] = data['text'];
    this.proposalListSearchModel['colIndex'] = data['dd-id'];
  }

  // delete proposal
  proposalObjToDelete: any;
  deleteproposalOnConfirm(obj) {
    let url = PathConfig.BASE_URL_API + PathConfig.AD_UNIT_DELETE;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.delete(url, obj, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.proposalObjToDelete = null;
          this.newOrder = new Order();
        }
      });
    return data;
  }



  editproposal(id) {
    let url = PathConfig.BASE_URL_API + PathConfig.ORDER + id;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.newOrder = data;
          this.showForm = true;
          this.editMode = true;
        }
      });
    return data;
  }

  loadAssignToWithDropdownData(data: any) {
    this.assignToOptionsModel = [];
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);

    let assignToUrl = PathConfig.BASE_URL_API + PathConfig.USER_GET_LIST

    /* let assignToUrl = PathConfig.BASE_URL_API;
     if (this.currentStatus == 6 || this.currentStatus == 1) {
       assignToUrl = assignToUrl + PathConfig.MEDIAPLANNER_GET_LIST;
     } else if (this.currentStatus == 4) {
       assignToUrl = assignToUrl + PathConfig.PRICINGMANAGER_GET_LIST;
     } else if (this.currentStatus == 5) {
       assignToUrl = assignToUrl + PathConfig.LEGAL_GET_LIST;
     } else if (this.currentStatus == 7 || this.currentStatus == 3) {
       assignToUrl = assignToUrl + PathConfig.ADMIN_GET_LIST;
     } else {
       assignToUrl = "";
     } */

    if (assignToUrl != "") {
      var assignToData = this.httpService.get(assignToUrl, auth_token);
      let self = this;
      assignToData.subscribe(
        assignToData => {
          if (assignToData) {
            for (var user in assignToData) {
              if (assignToData[user].role) {
                assignToData[user].roleName = assignToData[user].role.roleName;
              } else {
                assignToData[user].roleName = "";
              }
            }

            var dataBaseOnRoleBy = _.groupBy(assignToData, 'roleName');
            //this.assignToOptions = this.convertDataIntoDropdownForAssignToUser(assignToData, 'id', 'firstName', 'lastName', 'roleName');
            this.assignToOptions = dataBaseOnRoleBy ? dataBaseOnRoleBy : [];

            //this.assignToOptions = this.convertDataIntoDropdownForAssignToUser(assignToData, 'id', 'firstName', 'lastName', 'roleName');
            this.assignToList = assignToData;
            if (data.assignTo != null) {
              this.assignToOptionsModel[0] = data.assignTo.id;
              this.assignToDisplayName = data.assignTo.firstName + ' ' + data.assignTo.lastName;
            }

            let self = this;
            setTimeout(function () {
              if (data && data.assignTo && data.assignTo.id) {
                self.selectedId = data.assignTo.id;
                self.initialSelectedId = data.assignTo.id;
              }
            }, 100);
          }
        });
    }
  }

  loadDropdownSaveData(data) {
  //  this.loadAssignToWithDropdownData(data);
    this.proposalName=data.name;
   // this.assignedBy = data.salesPerson.firstName + " " + data.salesPerson.lastName;
    if (data.trafficker != null) {
      this.traffickerModel[0] = data.trafficker.id;
    }
    
    if (data.advertiser != null) {
       this.advertiserModel[0] = data.advertiser.companyId;
     }
    // this.saleManagerModel[0] = data.salesPerson.id;
    // 
    // this.traffickerModel[0] = data.trafficker.id;
    // if (data.agency != null) {
    //   this.agencyModel[0] = data.agency.companyId;
    // }
    // if (data.advertiser != null) {
    //   this.advertiserModel[0] = data.advertiser.companyId;
    // }
    // if (data.mediaPlanner != null) {
    //   this.mediaPlannerOptionsModel[0] = data.mediaPlanner.id;
    // }
    // if (data.salesCategory != null) {
    //   this.saleCategoryModel[0] = data.salesCategory.salesCatagoryId;
    // }
    // this.pricingModel[0] = data.pricingModel;
    // this.currrencyModel[0] = data.currency;
    // if (data.status != undefined || data.status != null) {
    //   this.statusModel[0] = data.status.statusId;
    //   this.currentStatusName = _.includes(data.status.name, "Review") ? "Under " + data.status.name : data.status.name;
    // }
  }

  // assignTask() {
  //   let findTaskUrl = PathConfig.BASE_URL_API + PathConfig.TASK_GET_ID + this.newOrder.proposalId + "/" + this.newOrder.assignTo.id;
  //   let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
  //   let data = this.httpService.get(findTaskUrl, auth_token);

  //   let self = this;
  //   data.subscribe(
  //     data => {
  //       if (data) {
  //         let taskId = data.id;
  //         let processId = data.executionId;

  //         let assignTaskUrl = PathConfig.BASE_URL_API + PathConfig.ASSIGN_TASK + processId + "/assignTask/" + taskId + "/" + this.newOrder.assignTo.id;
  //         var AssignedTaskData = this.httpService.post(assignTaskUrl, "", auth_token);
  //         AssignedTaskData.subscribe(
  //           AssignedTaskData => {
  //             if (AssignedTaskData) {
  //               this.successMessage = ApplicationMessages.PROPOSAL_UPDATE;
  //               this.isAlertMessageBoxVisible = false;
  //               this.showMessage(this);
  //               this.router.navigate(['dashboard/sales/proposal/edit'], { queryParams: { callback: 'editProposal', proposalId: this.newOrder.proposalId } });
  //             }
  //           }, error => {
  //             if (error) {
  //               console.log("Error in assigning a task");
  //               this.errorMessage = error;
  //             }
  //           });
  //       }
  //     }, error => {
  //       if (error) {
  //         console.log("Error in finding task");
  //         this.errorMessage = error;
  //       }
  //     });
  // }

  showMessage(self) {
    setTimeout(function () {
      self.isAlertMessageBoxVisible = true;
    }, 3000);
  }

  // save the form
  saveForm() {

    // let sel = this.assignToOptionsModel[0];
    // this.newOrder.assignTo = this.assignToList.length > 0 && this.assignToList.filter(function (value) {
    //   if (value['id'] == sel) {
    //     return value;
    //   }
    // })[0];

    // if (this.newOrder.assignTo != undefined) {
    //   if (this.newOrder.assignTo.role != undefined && this.newOrder.assignTo.role.roleId == 2) {
    //     this.newOrder.mediaPlanner = this.newOrder.assignTo;
    //   }
    //   if (this.newOrder.submitted) {
    //     this.assignTask();
    //   } else {
    //     /* this.newProposal.submitted = true;
    //      this.statusModel[0] = "6";
    //      let selStatus = this.statusModel[0];
    //      this.newProposal.status = this.temp4.length > 0 && this.temp4.filter(function (value) {
    //        if (value.statusId == selStatus) {
    //          return value;
    //        }
    //      })[0];
         
    //      this.initiateWorkFlow(); */
    //     this.updateProposal();
    //   }
    // }
  }
  // updateProposal() {
  //   let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_ADD;
  //   let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
  //   this.proposalId = this.newOrder.proposalId;

  //   var data = this.httpService.put(url, this.newOrder, auth_token);
  //   let self = this;
  //   data.subscribe(
  //     data => {
  //       if (data) {
  //         this.successMessage = ApplicationMessages.PROPOSAL_UPDATE;
  //         this.isAlertMessageBoxVisible = false;
  //         this.showMessage(this);
  //         this.router.navigate(['dashboard/sales/proposal/edit'], { queryParams: { callback: 'editProposal', proposalId: this.proposalId } });
  //       }
  //     }, error => {
  //       if (error) {
  //         this.loadAllDropdowns();
  //       }
  //     });
  //   this.newOrder = new Order();
  //   return data;

  // }

  // initiateWorkFlow() {
  //   console.log("Initiating workflow...");
  //   let workflowUrl = PathConfig.BASE_URL_API + PathConfig.WORKFLOW_START;
  //   var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
  //   this.workflowTask.proposal = this.newOrder;
  //   this.workflowTask.version = 0; // this.selectedVersion;
  //   this.workflowTask.comment = this.newOrder.action;
  //   this.workflowTask.proposalStatus = this.newOrder.status.statusId;
  //   this.workflowTask.userId = this.logedInUserId;
  //   this.workflowTask.action = true;

  //   var workflowData = this.httpService.post(workflowUrl, this.workflowTask, auth_token);
  //   let self = this;

  //   workflowData.subscribe(
  //     workflowData => {
  //       if (workflowData) {
  //         let processId = workflowData.id;
  //         console.log("Process Id" + processId);
  //         this.backToTable();
  //       }
  //     });
  // }


  resetForm() {
    this.newOrder = new Order();
    this.errorMessage = "";
    this.editMode = false;
  }

  actionOnUploadFile(file, index, remove, add) {
    if (add) {
      file.username = this.logedInUserFullName;
      this.proposalDocument.push(file);
    }
    if (remove && index) {
      this.proposalDocument.splice(0, index);
    }
  }

  fileChange(event) {
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      let file: File = fileList[0];
      let formData: FormData = new FormData();
      formData.append('uploadFile', file, file.name);
      this.actionOnUploadFile(file, null, false, true);
      let self = this;
      self.proposalDocument1 = [];
      setTimeout(function () {
        self.proposalDocument1 = self.proposalDocument;
      }, 5);
    }
  }

  showDocuments(obj) {
    for (var i in obj) {
      this.actionOnUploadFile(obj[i], null, false, true);
    }
    let self = this;
    self.proposalDocument1 = [];
    setTimeout(function () {
      self.proposalDocument1 = self.proposalDocument;
    }, 5);
  }

  convertDataIntoDropdown(array, propId, propLabel) {
    let newArr = array.map(function (val, index) {
      return { 'id': val[propId], 'name': val[propLabel] };
    });
    return newArr;

  }

  convertDataIntoDropdownForUser(array, propId, propLabel1, propLabel2) {
    let newArr = array.map(function (val, index) {
      return { 'id': val[propId], 'name': val[propLabel1] + " " + val[propLabel2] };
    });
    return newArr;
  }

  convertDataIntoDropdownForAssignToUser(array, propId, propLabel1, propLabel2, propLabel3) {
    let newArr = array.map(function (val, index) {
      return { 'id': val[propId], 'name': val[propLabel1] + " " + val[propLabel2] + "(" + val[propLabel3] + ")" };
    });
    return newArr;
  }

  toggleForm() {
    this.showForm = !this.showForm;
    this.resetForm();

    this, this.audienceTargetValues = [];
  }





  dueDateChanged(newDate) {
    this.newOrder.dueDate = new Date(newDate); //this.getFormatDate(newDate);
  }

  addNewLineitem() {
    let param = this.activatedRouter.snapshot.queryParams["callback"];
    if (param === 'addProposal') {
      this.router.navigate(['dashboard/sales/proposal-lineitem/add'], { queryParams: { callback: 'addProposal', proposalItem: JSON.stringify(this.newOrder) } });
    } else {
      this.router.navigate(['dashboard/sales/proposal-lineitem/add'], { queryParams: { proposalItem: JSON.stringify(this.newOrder) } });
    }
  }

  editLineitem(lineitem) {
    let id;
    if (lineitem['orderLineItemId'] !== undefined) {
      id = lineitem['orderLineItemId'];
    } else {
      id = lineitem['orderLineItemId'];
    }
    let param = this.activatedRouter.snapshot.queryParams["callback"];
    if (param === 'addProposal') {
      this.router.navigate(['dashboard/sales/order-lineitem/add'], { queryParams: { callback: 'addProposal', proposalItem: JSON.stringify(this.newOrder), 'lineitemId': id } });
    } else {
      this.router.navigate(['dashboard/sales/order-lineitem/add'], { queryParams: { proposalItem: JSON.stringify(this.newOrder), 'lineitemId': id } });
    }
  }

  loggedInUserDetails() {
    if (this.authService.isLoggedIn) {
      var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let url = PathConfig.BASE_URL_API + PathConfig.LOGED_IN_USER;
      var data = this.httpService.get(url, auth_token);
      let self = this;

      data.subscribe(
        data => {
          if (data) {
            this.logedInUserFullName = data.firstName + " " + data.lastName;
            this.logedInUserId = data.id;
          }
        });
      return data;
    }
  }
  //upload documents
  uploadDocuments() {
    //upload modal
    let dialogRef = this.dialog.open(UploadDocumentDialog);
    dialogRef.afterClosed().subscribe(result => {
      if (this.getSetService.obj != undefined) {
        for (var i in this.getSetService.obj.file) {
          this.getSetService.obj.file[i].purpose = this.getSetService.obj.purpose;
          this.getSetService.obj.file[i].version = this.getSetService.obj.version;
          this.getSetService.obj.file[i].date = new Date();
          this.getSetService.obj.file[i].username = this.logedInUserFullName;
        }
        this.showDocuments(this.getSetService.obj.file);
      }
    });
  }


  loadProposalData() {
    let proposalId = this.activatedRouter.snapshot.queryParams["proposalId"];
    let url = PathConfig.BASE_URL_API + PathConfig.ORDER + proposalId;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.newOrder = data;
     //     this.auditObj = data.audits;
       //   this.currentStatusName = _.includes(data.status.name, "Review") ? "Under " + data.status.name : data.status.name;
          this.proposalName = data.name;
     //     this.previousStatus = data.versions[0].previousStatus != null ? data.versions[0].previousStatus.name : '';
      //    this.remainingCost = this.newProposal.budget - this.newProposal.proposalCost;
          //this.previousStatus = data.versions[0].previousStatus.name;
        //  this.assignedBy = data.salesPerson.firstName + " " + data.salesPerson.firstName;
          // this.loadAssignToWithDropdownData(data);
          this.loadDropdownSaveData(data);
          // this.versionOptions = [{ 'id': '1', 'name': '0' }];
          // this.versionOptionsModel[0] = '1';
          // if (this.newProposal.advertiserDiscount == null) {
          //   this.newProposal.advertiserDiscount = 0;
          // }
        }
      });
    return data;
  }
}
