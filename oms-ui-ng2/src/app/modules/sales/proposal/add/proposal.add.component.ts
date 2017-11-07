import {
  ViewChild, Component, OnInit, AfterViewInit, ElementRef, HostListener, EventEmitter, Output, Input
} from '@angular/core';
import { HttpService } from '../../../../common/services/http.service';
import { PathConfig } from '../../../../common/config/path.config';
import { ConstantConfig } from '../../../../common/config/constant.config';
import { LocalStorageService } from "../../../../common/services/local-storage.service";
import { Broadcaster } from '../../../../common/services/broadcaster.service';
import { EmitterService } from '../../../../common/services/emitter.service';
import { AuthService } from '../../../../common/services/auth.service';
import { Proposal } from '../../../../common/models/proposal';
import { workflowProposal } from '../../../../common/models/workflow.proposal';
import { workflowTask } from '../../../../common/models/workflow.task';
import { MdDialog, MdDialogRef } from '@angular/material';
import { ConfirmDialogComponent } from "../../../../common/modules/dialog/confirm/confirm.component";
import { ConfirmReviewDialogComponent } from "../../../../common/modules/dialog/confirmReview/confirm.component";
import { DateFormat } from "../../../../common/modules/date.format";
import { IMultiSelectOption } from "angular-2-dropdown-multiselect";
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { IMultiSelectTexts } from "angular-2-dropdown-multiselect";
import { AdUnit } from '../../../../common/models/ad.unit';
import { ProposalLineItem } from '../../../../common/models/proposal.lineitem';
import { User } from '../../../../common/models/user';
import { AudienceTargetType } from '../../../../common/models/audience.target.type';
import { AudienceTargetValues } from '../../../../common/models/audience.target.values';
import { Router, ActivatedRoute } from '@angular/router';
import { DropDownDataFilter } from '../../../../common/filter/dropdown.filter';
import { UploadDocumentDialog } from "../../upload-document/upload-document.component";
import { objectSharingService } from '../../../../common/services/objectSharingService';
import { DropDownSetting } from '../../../../common/utils/dropdown.settings';
import * as FileSaver from 'file-saver';
import { CalendarService } from "../../../../common/services/calendar.service";
import * as _ from 'lodash';

declare var $: any;

@Component({
  templateUrl: './proposal.add.component.html',
  styleUrls: ['./../proposal.component.scss', 'proposal.add.component.scss'],
})
export class ProposalAddComponent implements OnInit {
  //Calendar settings starts
  //Caledar array. It is declared in constructor.
  calendarArr = null;
  clickOnDatepicker = null;
  calendarBtnClick = null;
  resetAllCalendar = null;
  keysGetter = Object.keys;
  currentDate: Date;
  //Calendar settings end
  @HostListener('document:click', ['$event'])
  clickout(event) {
    this.resetAllCalendar(this);
  }

  @Input() disable: boolean = true;
  showForm: Boolean = false;
  showLineItemForm: Boolean = false;
  @ViewChild('inputFile')
  documentValue: any;
  proposalListDTConfig: Object = {};
  proposalListRefresh: Boolean = false;
  proposalListSearchModel: any = {};
  newProposal: Proposal = new Proposal();
  proposalBckup: Proposal = new Proposal();
  disabledAgencyMargin: Boolean = true;
  selectedVersion: number = 0;
  viewOnlyMode: boolean = true;
  initialCost: number = 0;
  showSchedule: boolean = false;
  showFinancialDetails: boolean = false;

  //workflowProposal: workflowProposal = new workflowProposal();
  workflowTask: workflowTask = new workflowTask();
  newProposalData: any;
  processId: number;
  taskId: number;
  AssignedTask: any;
  currentStatus: any;
  proposalSold: boolean = false;
  disableFlow: boolean = false;
  disableApprovalRejection: boolean = false;
  currentStatusName: string;
  previousStatus: string;

  proposalId: number;
  versions: any;
  documentType: string;
  errorMessage: string;
  successMessage: string;
  editMode: Boolean = false;
  versionMode: boolean = false;
  //audienceTargetValues: Array<Object> = [{ "id": 1, "label": 'Lorem Ipsum' }, { "id": 1, "label": 'Lorem Ipsum', "isSelected": true }, { "id": 1, "label": 'Lorem Ipsum' }, { "id": 1, "label": 'Lorem Ipsum' }, { "id": 1, "label": 'Lorem Ipsum' }]
  audienceTargetValues: Array<Object>;
  selectedTargetTypeList = [];
  targetTypeList: Array<Object> = [];
  mediaPlannerList: Array<Object> = [];
  assignToList: Array<Object> = [];
  lineItemConfig: Object;
  lineItemRefresh: Boolean = false;
  productOptionsModel: number[];
  productList: Array<Object> = [];
  productOptions: IMultiSelectOption[];
  singleSelectSettings: IMultiSelectSettings;
  multiSelectSelectSettings: IMultiSelectSettings;
  textSetting: IMultiSelectTexts;
  proposalDocumentTable: boolean = true;
  proposalDocument1: any = [];
  mediaPlannerOptions: IMultiSelectOption[];
  mediaPlannerOptionsModel: Array<Object> = [];
  assignToOptions: IMultiSelectOption[];
  assignToOptionsModel: Array<Object> = [];
  assignToDisplayName = "Select";
  saleCategoryOptions: IMultiSelectOption[];
  saleCategoryModel = [];
  saleManagerOptions: IMultiSelectOption[];
  saleManagerModel = [];
  versionOptions: IMultiSelectOption[];
  versionOptionsModel: Array<Object> = [];
  traffickerOptions: IMultiSelectOption[];
  traffickerModel = [];
  agencyOptions: IMultiSelectOption[];
  agencyModel = [];
  advertiserOptions: IMultiSelectOption[];
  advertiserModel = [];
  newVersionNumber: number = 0;
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
  logedInUser: any;
  assignedBy: string;
  typeOptions: Array<Object> = [{
    "id": 1,
    "name": "Agency"
  }, {
    "id": 2,
    "name": "Advertiser"
  }];
  typeModel = [];
  statusModel = [];

  statusOptions: Array<Object> = [];
  currencyModel = [];
  currencyOptions: Array<Object> = [{
    "id": "USD",
    "name": "USD"
  }, {
    "id": "INR",
    "name": "INR"
  }, {
    "id": "EUR",
    "name": "EUR"
  }];

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
  proposalDocument: any = [];
  showAudienceTargetValuesForm: Boolean = false;
  seletedAudienceTargetValuesArr: Array<Object>
  selectedId: number = 0;
  selectedMediaPlanner: any;
  auditObj: Array<Object> = [];
  isAlertMessageBoxVisible: Boolean = true;
  requiredDropDownModelChangeCounter = 0;

  errorObj = {
    'nameError': '',
    'MediaPlannerError': '',
    'traffickerModelError': ''
  };


  specTypeList: Array<Object> = [];
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
    public getSetService: objectSharingService,
    private calendarService: CalendarService,
  ) {
    this.calendarArr = {
      'isStartDateOpen': false,
      'isEndDateOpen': false,
      'isNewCreatedDateOpen': false,
      'isDueDateOpen': false
    }
  }

  typeOptionsChange(data) {
    if (!_.isEmpty(data) && data.id) {
      this.assignToDisplayName = data.firstName + ' ' + data.lastName;
      this.assignToOptionsModel[0] = data.id;
      this.selectedId = data.id;
    }
  }

  ngOnInit() {
    this.currentDate = new Date();

    this.clickOnDatepicker = this.calendarService.clickOnDatepicker(this);
    this.calendarBtnClick = this.calendarService.calendarBtnClick(this);
    this.resetAllCalendar = this.calendarService.resetAllCalendar(this);

    this.loadAllDropdowns();

    this.multiSelectSelectSettings = DropDownSetting.multiSelectSelectSettings;
    this.singleSelectSettings = DropDownSetting.singleSelectSettings;
    this.textSetting = DropDownSetting.textSetting;

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
          "targets": 5,
          "render": function (data, type, full, meta) {
            var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
              "<a href='javascript:void(0);' data-name='edit' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-pencil' aria-hidden='true'></span>Edit</a>" +
              "<a href='javascript:void(0);' data-name='delete' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>" +
              '</div>';
            return template;
          }
        }, {
          "orderable": false,
          "targets": 4,
          "render": function (data, type, full, meta) {
            var template = full.proposedCost * full.quantity;
            return template;
          }
        }, {
          "orderable": false,
          "targets": 3,
          "render": function (data, type, full, meta) {
            var startDate = new Date(full.flightStartdate);
            var startYear = startDate.getFullYear();
            /// Add 1 because JavaScript months start at 0
            var startMonth = (1 + startDate.getMonth()).toString();
            startMonth = startMonth.length > 1 ? startMonth : '0' + startMonth;
            var startDay = startDate.getDate().toString();
            startDay = startDay.length > 1 ? startDay : '0' + startDay;

            var endDate = new Date(full.flightEndDate);
            var endYear = endDate.getFullYear();
            /// Add 1 because JavaScript months start at 0
            var endMonth = (1 + endDate.getMonth()).toString();
            endMonth = endMonth.length > 1 ? endMonth : '0' + endMonth;
            var endDay = endDate.getDate().toString();
            endDay = endDay.length > 1 ? endDay : '0' + endDay;

            var template = '<div>' + startMonth + '-' + startDay + '-' + startYear + ' to ' + endMonth + '-' + endDay + '-' + endYear; +'</div>';
            return template;
          }
        }
      ],
      "columns": [
        { "title": 'Name', "data": "name" },
        { "title": 'CPM', "data": "proposedCost" },
        { "title": 'Impression', "data": "quantity" },
        { "title": 'Flight', "data": "flightStartdate" },
        { "title": 'Cost', "data": "proposedCost" },
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
          "targets": 1,
          "render": function (data, type, full, meta) {
            return full.name.split(".").pop();
          }
        },
        {
          "orderable": false,
          "targets": 3,
          "render": function (data, type, full, meta) {
            var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
              "<a href='javascript:void(0);' data-name='download' data-custom='" + meta.row + "'><span class='fa fa-download' aria-hidden='true'></span>Download</a>" +
              "<a href='javascript:void(0);' data-name='delete' data-custom='" + meta.row + "'><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>" +
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
    };
  }

  newRowsAdded = [];


  loadAllDropdowns() {
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let url1 = PathConfig.BASE_URL_API + PathConfig.SALESCATEGORY_GET_LIST;
    let url2 = PathConfig.BASE_URL_API + PathConfig.SALESMANAGER_GET_LIST;
    let url3 = PathConfig.BASE_URL_API + PathConfig.TRAFFICKER_GET_LIST;
    let url4 = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_STATUS;
    let url5 = PathConfig.BASE_URL_API + PathConfig.COMPANY_LIST;
    let url6 = PathConfig.BASE_URL_API + PathConfig.MEDIAPLANNER_GET_LIST;
    let assignToUrl = PathConfig.BASE_URL_API + PathConfig.USER_GET_LIST;

    var data1 = this.httpService.get(url1, auth_token);
    var data2 = this.httpService.get(url2, auth_token);
    var data3 = this.httpService.get(url3, auth_token);
    var data4 = this.httpService.get(url4, auth_token);
    var data5 = this.httpService.get(url5, auth_token);
    var data6 = this.httpService.get(url6, auth_token);
    var assignToData = this.httpService.get(assignToUrl, auth_token);

    let self = this;
    data1.subscribe(
      data1 => {
        if (data1) {
          this.saleCategoryOptions = this.convertDataIntoDropdown(data1, 'salesCatagoryId', 'name');
          this.temp1 = [];
          this.temp1 = data1;

          data2.subscribe(
            data2 => {
              if (data2) {
                this.saleManagerOptions = this.convertDataIntoDropdownForUser(data2, 'id', 'firstName', 'lastName');
                this.temp2 = [];
                this.temp2 = data2;
              }
              data3.subscribe(
                data3 => {
                  if (data3) {
                    this.traffickerOptions = this.convertDataIntoDropdownForUser(data3, 'id', 'firstName', 'lastName');
                    this.temp3 = [];
                    this.temp3 = data3;
                  }
                  data4.subscribe(
                    data4 => {
                      if (data4) {
                        this.statusOptions = this.convertDataIntoDropdown(data4, 'statusId', 'name');
                        this.temp4 = [];
                        this.temp4 = data4;
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
                            data6.subscribe(
                              data6 => {
                                if (data6) {
                                  this.mediaPlannerOptions = this.convertDataIntoDropdownForUser(data6, 'id', 'firstName', 'lastName');
                                  this.mediaPlannerList = data6;
                                }

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
                                      this.assignToOptions = dataBaseOnRoleBy ? dataBaseOnRoleBy : [];
                                      this.assignToList = assignToData;

                                      this.loadProposalDataAfterLineitem();
                                      this.versionOptions = [{ 'id': '1', 'name': 0 + "" }];
                                      this.versionOptionsModel[0] = "1";
                                    }
                                  });
                              });
                          });
                      }
                    });
                });
            });
        }
      });
  }

  loadProposalDataAfterLineitem() {
    let param = this.activatedRouter.snapshot.queryParams["proposalItem"];
    this.loggedInUserDetails();
    if (param !== undefined) {
      this.newProposal = new Proposal();
      this.newProposal = JSON.parse(param);
      //     this.loadFileDocuments(this.newProposal.proposalId);
      /*this.newProposal['saleCategoryModel'];
      this.newProposal['saleManagerModel'];
      this.newProposal['traffickerModel'];
      this.newProposal['statusModel'];
      this.newProposal['agencyModel'];
      this.newProposal['advertiserModel'];*/
      this.loadDropdownSaveData(this.newProposal);
    }
  }
  // on Menu Selection
  onProposalTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'edit') {
      //edit code here
      this.editLineitem(JSON.parse(data['value']));

    } else if (data['clickedOn'] == 'delete') {
      //delete code here
      //confirm box
      let dialogRef = this.dialog.open(ConfirmDialogComponent);
      this.proposalListRefresh = false;
      dialogRef.afterClosed().subscribe(result => {
        if (result == "OK") {
          this.deleteproposalOnConfirm(JSON.parse(data['value']));
          // this.proposalListRefresh = true;
        }
      });
    }

  }

  onDocumentTableChange(data: any) {
    if (data['clickedOn'] == 'delete') {
      this.actionOnUploadFile(true, Number(data['value']), true, false);
      let self = this;
      self.proposalDocument1 = [];
      this.documentValue.nativeElement.value = "";
      setTimeout(function () {
        self.proposalDocument1 = self.proposalDocument;

      }, 5);
      //this.editLineitem(JSON.parse(data['value']));
    } else if (data['clickedOn'] == "download") {
      this.downloadDocument(data);
      /*     $http({ method: 'GET', url: '/someUrl' }).
            success(function (data, status, headers, config) {
              var anchor = angular.element('<a/>');
              anchor.attr({
                href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
                target: '_blank',
                download: 'filename.csv'
              })[0].click();
    
            }).
            error(function (data, status, headers, config) {
              // handle error
            }); */
    }
  }
  //back to table button
  backToTable() {
    this.showForm = false;
    this.proposalListRefresh = true;
    this.versionMode = false;
    this.disableApprovalRejection = false;
    this.errorMessage = "";
    let param = this.activatedRouter.snapshot.queryParams["callback"];
    setTimeout(() => {
      if (param === 'editProposal' || param === 'addProposal') {
        this.router.navigate(['dashboard/sales/proposal/list']);
      } else {
        this.router.navigate(['dashboard/sales/proposal-lineitem/list']);
      }
    }, 2000);
    // if (param === 'editProposal' || param === 'addProposal') {
    //   this.router.navigate(['dashboard/sales/proposal/list']);
    // } else {
    //   this.router.navigate(['dashboard/sales/proposal-lineitem/list']);
    // }
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
  deleteproposalOnConfirm(obj) {
    let proposalLineItem: ProposalLineItem = this.newProposal.lineItems.find(lineItem => lineItem.lineItemId == obj.lineItemId);
    let index = this.newProposal.lineItems.indexOf(proposalLineItem);
    if (index !== -1) {
      this.newProposal.lineItems.splice(index, 1);
    }
    this.lineItemRefresh = true;
    /* let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_LINEITEM_DELETE + obj['lineItemId'];
     let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
     let data = this.httpService.delete(url, obj, auth_token);
     let self = this;
     data.subscribe(
       data => {
         if (data) {
           this.backToForm();
         }
       });
     return data;*/
  }

  editproposal(id) {
    console.log("in edit proposal function");
    let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_GET_BY_ID + id;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    this.proposalId = id;
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.newProposal = data;
          this.onChange();
          this.showForm = true;
          this.editMode = true;
        }
      });
    return data;
  }

  loadDropdownSaveData(data) {
    this.saleManagerModel[0] = data.salesPerson.id;
    this.assignedBy = data.salesPerson.firstName + " " + data.salesPerson.lastName;
    this.traffickerModel[0] = data.trafficker.id;
    if (data.agency != null) {
      this.agencyModel[0] = data.agency.companyId;
    }
    if (data.advertiser != null) {
      this.advertiserModel[0] = data.advertiser.companyId;
    }
    if (data.mediaPlanner != null) {
      this.mediaPlannerOptionsModel[0] = data.mediaPlanner.id;
    }
    if (data.assignTo != null) {
      this.selectedId = data.assignTo.id;
      this.assignToOptionsModel[0] = data.assignTo.id;
      this.assignToDisplayName = data.assignTo.firstName + " " + data.assignTo.lastName;
    }
    if (data.salesCategory != null) {
      this.saleCategoryModel[0] = data.salesCategory.salesCatagoryId;
    }
    this.pricingModel[0] = data.pricingModel;
    this.currencyModel[0] = data.currency;
    if (data.status != undefined || data.status != null) {
      this.statusModel[0] = data.status.statusId;
      this.currentStatusName = data.status.name;
      this.currentStatus = data.status.statusId;
    }
    this.onChange();
  }

  /*saveForm() {
    let dialogRef = this.dialog.open(ProposalCommentDialog);
    dialogRef.afterClosed().subscribe(result => {
      console.log("comment dialog box closed");
      if (this.getSetService.obj != undefined) {
        this.newProposal.action = this.getSetService.obj;
        this.saveProposal();
      }
      this.getSetService.setValue({});
    });
  }*/

  updateLineItem() {
    for (var i in this.newProposal.lineItems) {
      delete this.newProposal.lineItems[i]['tempId'];
      delete this.newProposal.lineItems[i]['proposal'];
    }
  }

  onchangeFun(val, errorKey, objKey) {
    val = val + '';
    if (val && ('' + val).length > 0) {
      this.errorObj[errorKey] = '';
      this.newProposal[objKey] = val;
    } else {
      this.newProposal[objKey] = '';
    }
  }

  onDropDownchangeFun(val, errorKey, objKey) {
    this.requiredDropDownModelChangeCounter++;

    if(this.requiredDropDownModelChangeCounter > 2){
      if (val[0]) {
        this.errorObj[errorKey] = '';
      } else {
        this.errorObj[errorKey] = ConstantConfig.PROPOSAL_ERROR_MESSAGES[errorKey];
      }
    }

  }

  isFormValid() {
    if (!this.newProposal.name) {
      this.errorObj["nameError"] = ConstantConfig.PROPOSAL_ERROR_MESSAGES["nameError"];
      return false;
    } else if (!this.mediaPlannerOptionsModel.length) {
      //this.errorMessage = "'Media planner' is required field";
      this.errorObj["MediaPlannerError"] = ConstantConfig.PROPOSAL_ERROR_MESSAGES["MediaPlannerError"];
      return false;
    } else if (!this.traffickerModel.length) {
      //this.errorMessage = "'Trafficker' is required field";
      this.errorObj["traffickerModelError"] = ConstantConfig.PROPOSAL_ERROR_MESSAGES["traffickerModelError"];
      return false;
    } else if (!this.newProposal.created) {
      //this.errorMessage = "'Requested on' is required field";
      this.errorObj['createdOnError'] = ConstantConfig.PROPOSAL_ERROR_MESSAGES["createdOnError"];
      this.showSchedule = true;
      return false;
    } else if (!this.newProposal.dueDate) {
      //this.errorMessage = "'Due on' is required field";
      this.errorObj['dueDateError'] = ConstantConfig.PROPOSAL_ERROR_MESSAGES["dueDateError"];
      this.showSchedule = true;
      return false;
    } else if (!this.newProposal.startDate) {
      //this.errorMessage = "'Start date' is required field";
      this.errorObj['startDateError'] = ConstantConfig.PROPOSAL_ERROR_MESSAGES["startDateError"];
      this.showSchedule = true;
      return false;
    } else if (!this.newProposal.endDate) {
      //this.errorMessage = "'End date' is required field";
      this.errorObj['endDateError'] = ConstantConfig.PROPOSAL_ERROR_MESSAGES["endDateError"];
      this.showSchedule = true;
      return false;
    } else if (!this.pricingModel.length) {
      //this.errorMessage = "'Pricing model' is required field";
      this.errorObj['pricingModelError'] = ConstantConfig.PROPOSAL_ERROR_MESSAGES["pricingModelError"];
      this.showFinancialDetails = true;
      return false;
    } else if (!this.newProposal.budget) {
      //this.errorMessage = "'Budget' is required field";
      this.errorObj['budgetError'] = ConstantConfig.PROPOSAL_ERROR_MESSAGES["budgetError"];
      this.showFinancialDetails = true;
      return false;
    } else if (!this.currencyModel.length) {
      //this.errorMessage = "'Currency' is required field";
      this.errorObj['currencyModelError'] = ConstantConfig.PROPOSAL_ERROR_MESSAGES["currencyModelError"];
      this.showFinancialDetails = true;
      return false;
    } else if (!this.newProposal.percentageOfClose) {
      //this.errorMessage = "'Probability of close' is required field";
      this.errorObj['percentageOfCloseError'] = ConstantConfig.PROPOSAL_ERROR_MESSAGES["percentageOfCloseError"];
      this.showFinancialDetails = true;
      return false;
    } else {
      return true;
    }
  }

  // save the form
  saveForm() {
    if (this.isFormValid()) {
      this.errorMessage = '';
      this.updateLineItem();
      this.updateProposalData();
      this.updateProposal();
    }
  }

  updateProposal() {
    console.log("inside update proposal");
    let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_ADD;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    var data = this.httpService.post(url, this.newProposal, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          console.log("updated version");
          //    this.showMessage(this);

          let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_FILEUPLOAD;
          let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
          var proId = data.proposalId;
          let formData: FormData = new FormData();
          var fileObj = {
            proposalId: proId,
            documentDetails: []
          };
          for (var i in this.proposalDocument) {
            formData.append('docs', this.proposalDocument[i], this.proposalDocument[i]['name']);
            this.documentType = this.proposalDocument[i]['name'];
            fileObj.documentDetails.push({ "name": this.proposalDocument[i]['name'], "type": this.documentType.split('.').pop() });
          }

          formData.append('doc_details', new Blob([JSON.stringify(fileObj)], { type: "application/json" }));

          var fileUpload = this.httpService.putFileUpload(url, formData, auth_token);
          fileUpload.subscribe(
            data => {
              if (data) {
                this.successMessage = "Proposal is created successfully.";
                this.isAlertMessageBoxVisible = false;
                this.showMessage(this);
                this.router.navigate(['dashboard/sales/proposal/edit'], { queryParams: { callback: 'editProposal', proposalId: proId } });
              }
            }, error => {
              if (error) {
                this.errorMessage = error;
                this.proposalDocument = [];
              }
            });
        }
      }, error => {
        if (error) {
          this.errorMessage = error;
          this.loadAllDropdowns();
          this.proposalDocument = [];
        }
      });

    return data;

  }

  showMessage(self) {
    setTimeout(function () {
      self.isAlertMessageBoxVisible = true;
    }, 3000);
  }

  ClearState() {
    this.versionMode = false;
    this.disableApprovalRejection = false;
    this.errorMessage = "";
  }


  resetForm() {
    this.newProposal = new Proposal();
    this.editMode = false;
    this.selectedVersion = 0;
    this.errorMessage = "";
    this.proposalDocument1 = [];
    this.proposalDocument = [];
  }

  actionOnUploadFile(file, index, remove, add) {
    if (add) {
      file.username = this.logedInUserFullName;
      this.proposalDocument.push(file);
      //this.proposalDocument.s(file);
    }
    if (remove) {
      if (index == 0) {
        this.proposalDocument.shift();
      } else {
        var temp = this.proposalDocument.splice(0, index);
        this.proposalDocument = temp;
      }
    }
  }

  downloadDocument(data) {

    let url = PathConfig.BASE_URL_API + "proposal/" + this.proposalId + "/documents/" + this.proposalDocument1[Number(data['value'])]['proposalDocId'];
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data1 = this.httpService.getFile(url, auth_token);
    let fileName = this.proposalDocument1[Number(data['value'])]['name'];
    console.log(data1);
    let self = this;
    data1.subscribe(data1 => this.downloadFile(data1, fileName),
      error => console.log(error),
      () => console.info("OK")
    );
    return data1;
  }

  downloadFile(data: Response, fileName: string) {
    var blob = data.blob();
    FileSaver.saveAs(blob, fileName);
  }

  fileChange(event) {
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      let file: File = fileList[0];
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

  toggleForm() {
    this.showForm = !this.showForm;
    this.resetForm();

    this, this.audienceTargetValues = [];
  }

  dueDateChanged(newDate) {
    this.newProposal.dueDate = new Date(newDate); //this.getFormatDate(newDate);
  }

  addNewLineitem() {
    this.updateProposalData();
    let param = this.activatedRouter.snapshot.queryParams["callback"];
    if (param === 'addProposal') {
      this.newProposal['saleCategoryModel'] = this.saleCategoryModel[0];
      this.newProposal['saleManagerModel'] = this.saleManagerModel[0];
      this.newProposal['traffickerModel'] = this.traffickerModel[0];
      this.newProposal['statusModel'] = this.statusModel[0];
      this.newProposal['agencyModel'] = this.agencyModel[0];
      this.newProposal['advertiserModel'] = this.advertiserModel[0];
      this.newProposal['pricingModel'] = this.pricingModel[0];
      this.newProposal['currencyModel'] = this.currencyModel[0];
      this.newProposal['assignToOptionsModel'] = this.assignToOptionsModel[0];

      this.router.navigate(['dashboard/sales/proposal-lineitem/add'], { queryParams: { callback: param, proposalItem: JSON.stringify(this.newProposal) } });
    } else {
      this.router.navigate(['dashboard/sales/proposal-lineitem/add'], { queryParams: { callback: param, proposalItem: JSON.stringify(this.newProposal) } });
    }
  }

  editLineitem(lineitem) {
    this.updateProposalData();
    let id;
    let param = this.activatedRouter.snapshot.queryParams["callback"];
    if (lineitem['lineItemId'] !== undefined) {
      id = lineitem['lineItemId'];
      this.router.navigate(['dashboard/sales/proposal-lineitem/add'], { queryParams: { callback: param, proposalItem: JSON.stringify(this.newProposal), 'lineitemId': id } });
    } else {
      id = lineitem['tempId'];
      this.router.navigate(['dashboard/sales/proposal-lineitem/add'], { queryParams: { callback: param, proposalItem: JSON.stringify(this.newProposal), 'tempId': id } });
    }
  }


  updateProposalData() {
    let selSC = this.saleCategoryModel[0];
    this.newProposal.salesCategory = this.temp1.length > 0 && this.temp1.filter(function (value) {
      if (value.salesCatagoryId == selSC) {
        return value;
      }
    })[0];
    let selSP = this.saleManagerModel[0];
    this.newProposal.salesPerson = this.logedInUser;

    let selTR = this.traffickerModel[0];
    this.newProposal.trafficker = this.temp3.length > 0 && this.temp3.filter(function (value) {
      if (value.id == selTR) {
        return value;
      }
    })[0];

    this.statusModel[0] = "1";
    let selStatus = this.statusModel[0];
    this.newProposal.status = this.temp4.length > 0 && this.temp4.filter(function (value) {
      if (value.statusId == selStatus) {
        return value;
      }
    })[0];

    let selAgency = this.agencyModel[0];
    this.newProposal.agency = this.temp5.length > 0 && this.temp5.filter(function (value) {
      if (value.companyId == selAgency) {
        return value;
      }
    })[0];


    let selAdvertiser = this.advertiserModel[0];
    this.newProposal.advertiser = this.temp6.length > 0 && this.temp6.filter(function (value) {
      if (value.companyId == selAdvertiser) {
        return value;
      }
    })[0];

    var _selectedMediaPlannerId = this.mediaPlannerOptionsModel[0];
    this.newProposal.mediaPlanner = this.mediaPlannerList.length > 0 && this.mediaPlannerList.filter(function (value) {
      if (value['id'] === _selectedMediaPlannerId) {
        return value;
      }
    })[0];

    var _selectedAssignToId = this.assignToOptionsModel[0];
    this.newProposal.assignTo = this.assignToList.length > 0 && this.assignToList.filter(function (value) {
      if (value['id'] === _selectedAssignToId) {
        return value;
      }
    })[0];

    this.newProposal.pricingModel = this.pricingModel[0];//_selectedPrice['name'];
    this.newProposal.currency = this.currencyModel[0];//_selectedCurrency['name'];
    this.newProposal.dueDate = new Date(this.newProposal.dueDate).getTime();
    this.newProposal.startDate = new Date(this.newProposal.startDate).getTime();
    this.newProposal.endDate = new Date(this.newProposal.endDate).getTime();
    this.newProposal.created = new Date(this.newProposal.created).getTime();

    delete this.newProposal['@id'];
    delete this.newProposal['traffickerModel'];
    delete this.newProposal['saleCategoryModel'];
    delete this.newProposal['saleManagerModel'];
    delete this.newProposal['statusModel'];
    delete this.newProposal['advertiserModel'];
    delete this.newProposal['agencyModel'];
    delete this.newProposal['assignToOptionsModel'];
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
            this.logedInUser = data;
            this.assignedBy = this.logedInUserFullName;
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

  loadFileDocuments(proposalId) {
    console.log("inside load file documents");
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

  onChange() {
    if (this.pricingModel[0] == "Gross" && this.agencyModel.length) {
      this.disabledAgencyMargin = false;
    }
    else {
      this.disabledAgencyMargin = true;
    }
  }

  onMediaPlannerChange(data: any) {
    console.log("mediaPlannerChanged.............");
    if (data.length) {
      this.selectedId = data[0];
      this.assignToOptionsModel[0] = data[0];
      var _selectedAssignToId = this.assignToOptionsModel[0];
      this.selectedMediaPlanner = this.assignToList.length > 0 && this.assignToList.filter(function (value) {
        if (value['id'] === _selectedAssignToId) {
          return value;
        }
      })[0];
      this.assignToDisplayName = this.selectedMediaPlanner.firstName + ' ' + this.selectedMediaPlanner.lastName;
    } else {
      this.assignToDisplayName = "Select";
      this.selectedId = 0;
      this.assignToOptionsModel = [];
    }
  }
}
