import {
  ViewChild, Component, OnInit, AfterViewInit, ElementRef, HostListener, EventEmitter, Output, Input
} from '@angular/core';
import { HttpService } from '../../../../common/services/http.service';
import { PathConfig } from '../../../../common/config/path.config';
import { ApplicationMessages } from '../../../../common/config/application.messages';
import { ConstantConfig } from '../../../../common/config/constant.config';
import { LocalStorageService } from "../../../../common/services/local-storage.service";
import { Broadcaster } from '../../../../common/services/broadcaster.service';
import { EmitterService } from '../../../../common/services/emitter.service';
import { AuthService } from '../../../../common/services/auth.service';
import { Proposal } from '../../../../common/models/proposal';
import { version } from '../../../../common/models/version';
import { workflowProposal } from '../../../../common/models/workflow.proposal';
import { workflowTask } from '../../../../common/models/workflow.task';
import { MdDialog, MdDialogRef } from '@angular/material';
import { ConfirmDialogComponent } from "../../../../common/modules/dialog/confirm/confirm.component";
import { ConfirmReviewDialogComponent } from "../../../../common/modules/dialog/confirmReview/confirm.component";
import { ProposalCommentDialog } from "../../../../common/modules/dialog/proposal-comment/proposal-comment.component";
import { DateFormat } from "../../../../common/modules/date.format";
import { IMultiSelectOption } from "angular-2-dropdown-multiselect";
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { IMultiSelectTexts } from "angular-2-dropdown-multiselect";
import { AdUnit } from '../../../../common/models/ad.unit';
import { ProposalLineItem } from '../../../../common/models/proposal.lineitem';
import { AudienceTargetType } from '../../../../common/models/audience.target.type';
import { AudienceTargetValues } from '../../../../common/models/audience.target.values';
import { Router, ActivatedRoute } from '@angular/router';
import { DropDownDataFilter } from '../../../../common/filter/dropdown.filter';
import { UploadDocumentDialog } from "../../upload-document/upload-document.component";
import { objectSharingService } from '../../../../common/services/objectSharingService';
import { DropDownSetting } from '../../../../common/utils/dropdown.settings';
import * as FileSaver from 'file-saver';
import { CalendarService } from "../../../../common/services/calendar.service";
import { DateFormatterComponent } from "../../../../common/utils/date-format.component";
import * as _ from 'lodash';

declare var $: any;

@Component({
  templateUrl: './proposal.edit.component.html',
  styleUrls: ['./../proposal.component.scss', './proposal.edit.component.scss'],
})
export class ProposalEditComponent implements OnInit {
  //Calendar settings starts
  //Caledar array. It is declared in constructor.
  calendarArr = null;
  clickOnDatepicker = null;
  calendarBtnClick = null;
  resetAllCalendar = null;
  keysGetter = Object.keys;
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
  disabledVersion: Boolean = false;
  selectedVersion: number = 0;
  viewOnlyMode: boolean = true;
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
  pricingApproved: boolean = false;
  legalApproved: boolean = false;
  needAdminApproval: boolean = false;
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
  assignToDisplayName = null;
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
  newVersion: boolean = false;
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
  auditObj: Array<Object> = [];
  isAlertMessageBoxVisible: Boolean = true;
  remainingCost: number = 0;
  proposalName: string = "";
  proposalNewVersion: version = new version();
  requiredDropDownModelChangeCounter = 0;

  specTypeList: Array<Object> = [];
  errorObj = {
    'nameError': '',
    'MediaPlannerError': '',
    'traffickerModelError': ''
  };
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
                                  this.loadProposalDataAfterLineitem();
                                }
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
      this.loadFileDocuments(this.newProposal.proposalId);
      /*this.newProposal['saleCategoryModel'];
      this.newProposal['saleManagerModel'];
      this.newProposal['traffickerModel'];
      this.newProposal['statusModel'];
      this.newProposal['agencyModel'];
      this.newProposal['advertiserModel'];*/
      this.loadDropdownSaveData(this.newProposal);
    } else {
      let proposalId = this.activatedRouter.snapshot.queryParams["proposalId"];
      this.newVersion = this.activatedRouter.snapshot.queryParams["newVersion"];
      this.clearState();
      this.loadProposalData(proposalId);
      this.loadFileDocuments(proposalId);
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
    this.loadAssignToWithDropdownData(data);
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
    if (data.salesCategory != null) {
      this.saleCategoryModel[0] = data.salesCategory.salesCatagoryId;
    }
    this.pricingModel[0] = data.pricingModel;
    this.currencyModel[0] = data.currency;
    if (data.status != undefined || data.status != null) {
      this.statusModel[0] = data.status.statusId;
      this.currentStatusName = _.includes(data.status.name, "Review") ? "Under " + data.status.name : data.status.name;
      this.currentStatus = data.status.statusId;
    }
    this.onChange();
    this.auditObj = data.audits;
    this.proposalName = data.name;
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
        this[objKey] = val;
        if (val.length > 0) {
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

  iisFormValid() {
    if (this.newProposal.name && this.mediaPlannerOptionsModel.length && this.traffickerModel && this.newProposal.created && this.newProposal.dueDate && this.newProposal.startDate && this.newProposal.endDate && this.pricingModel && this.newProposal.budget && this.currencyModel && this.newProposal.percentageOfClose) {
      return true;
    } else {
      return false;
    }
  }

  saveForm() {
    if (this.isFormValid()) {
      let dialogRef = this.dialog.open(ProposalCommentDialog, { data: { title: 'Comments:' }, });
      dialogRef.afterClosed().subscribe(result => {
        console.log("comment dialog box closed");
        if (this.getSetService.obj != undefined) {
          this.newProposal.action = this.getSetService.obj;
          if (this.newVersion) {
            this.addNewVersion();
          } else {
            this.saveProposal();
          }
        }
        this.getSetService.setValue({});
      });
    } else {
      //this.errorMessage = "Some required fields are missing"
    }

  }
  updateLineItem() {
    for (var i in this.newProposal.lineItems) {
      delete this.newProposal.lineItems[i]['tempId'];
      delete this.newProposal.lineItems[i]['proposal'];
    }
  }

  addNewVersion() {
    console.log("Adding new Version....." + this.newVersionNumber);
    this.updateVersion();
    this.newProposal.versions.push(this.proposalNewVersion);
    this.updateProposal();
  }

  updateVersion() {
    this.proposalNewVersion.advertiserDiscount = this.newProposal.advertiserDiscount;
    this.proposalNewVersion.budget = this.newProposal.budget;
    this.proposalNewVersion.currency = this.newProposal.currency;
    this.proposalNewVersion.description = this.newProposal.description;
    this.proposalNewVersion.dueDate = this.newProposal.dueDate;
    this.proposalNewVersion.endDate = this.newProposal.endDate;
    this.proposalNewVersion.notes = this.newProposal.notes;
    this.proposalNewVersion.opportunity = this.newProposal.opportunityId;
    this.proposalNewVersion.percentageOfClose = this.newProposal.percentageOfClose;
    this.proposalNewVersion.pricingModel = this.newProposal.pricingModel;
    //this.proposalNewVersion.proposal = this.newProposal;
    this.proposalNewVersion.salesCategory = this.newProposal.salesCategory;
    this.proposalNewVersion.startDate = this.newProposal.startDate;
    this.proposalNewVersion.status = this.newProposal.status;
    this.proposalNewVersion.submitted = false;
    this.proposalNewVersion.terms = this.newProposal.term;
    this.proposalNewVersion.version = this.newVersionNumber
  }
  clearState() {
    this.disableFlow = false;
    this.disableApprovalRejection = false;
    this.pricingApproved = false;
    this.legalApproved = false;
    this.needAdminApproval = false;
  }

  // save the form
  saveProposal() {
    this.updateLineItem();
    this.updateProposalData();
    this.updateProposalWithVersionDetails(this.proposalBckup);
    if (!this.newProposal.submitted && this.selectedVersion != this.newProposal.versions.length - 1) {
      this.loadVersionDetails(this.proposalBckup);
    }

    if (this.newProposal.submitted) {
      if (this.currentStatus == this.statusModel[0]) {
        this.updateProposal();
      } else {
        this.executeWorkflow();
      }
    } else if (this.currentStatus == "1" && this.statusModel[0] == "6") {
      console.log("Initiating workflow.")
      this.newProposal.submitted = true;
      this.newProposal.versions[this.selectedVersion].submitted = true;
      this.loadVersionDetails(this.newProposal.versions[this.selectedVersion]);
      this.initiateWorkFlow();
      console.log("workflow started");
    } else if (this.statusModel[0] == "1") {
      this.updateProposal();
    }
  }

  updateProposal() {
    let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_ADD;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    var data = this.httpService.put(url, this.newProposal, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          console.log("updated version");
          this.clearState();
          this.loadProposalData(this.newProposal.proposalId);
          this.successMessage = "Proposal is updated successfully.";
          this.isAlertMessageBoxVisible = false;
          this.showMessage(this);
          this.errorMessage = "";

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
                this.clearState();
                this.newVersion = false;
                this.loadProposalData(this.newProposal.proposalId);
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

  saveAssignedUser() {

    let sel = this.assignToOptionsModel[0];
    this.newProposal.assignTo = this.assignToList.length > 0 && this.assignToList.filter(function (value) {
      if (value['id'] == sel) {
        return value;
      }
    })[0];

    if (this.newProposal.assignTo != undefined) {
      if (this.newProposal.assignTo.role != undefined && this.newProposal.assignTo.role.roleId == 2) {
        this.newProposal.mediaPlanner = this.newProposal.assignTo;
      }
      if (this.newProposal.submitted) {
        this.assignTask();
      } else {
        /* this.newProposal.submitted = true;
         this.statusModel[0] = "6";
         let selStatus = this.statusModel[0];
         this.newProposal.status = this.temp4.length > 0 && this.temp4.filter(function (value) {
           if (value.statusId == selStatus) {
             return value;
           }
         })[0];
         
         this.initiateWorkFlow(); */
        this.updateProposal();
      }
    }
  }


  resetForm() {
    this.newProposal = new Proposal();
    this.editMode = false;
    this.selectedVersion = 0;
    this.errorMessage = "";
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
    //var blob = data.blob(); // new Blob([data], { type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8' });    
    //var url = window.URL.createObjectURL(blob);
    //window.open(url);
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

  convertNumericDataIntoDropdown(array, propId, propLabel) {
    let newArr = array.map(function (val, index) {
      return { 'id': val[propId], 'name': val[propLabel] + "" };
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
    this.newProposal.dueDate = new Date(newDate); //this.getFormatDate(newDate);
  }

  addNewLineitem() {
    if (this.isFormValid()) {
      this.updateProposalData();
      this.errorMessage = "";
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
    } else {
      this.errorMessage = "Some required fields of Proposal is missing";
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


    /*if (param === 'editProposalWithLineitem') {
      this.router.navigate(['dashboard/sales/proposal-lineitem/edit'], { queryParams: { callback: 'editProposalWithLineitem', proposalItem: JSON.stringify(this.newProposal), 'lineitemId': id } });
    } else if (param === 'editProposal') {
      this.router.navigate(['dashboard/sales/proposal-lineitem/edit'], { queryParams: { callback: 'editProposal', proposalItem: JSON.stringify(this.newProposal), 'lineitemId': id } });
    }*/
  }

  updateProposalData() {
    let selSC = this.saleCategoryModel[0];
    this.newProposal.salesCategory = this.temp1.length > 0 && this.temp1.filter(function (value) {
      if (value.salesCatagoryId == selSC) {
        return value;
      }
    })[0];
    /*  let selSP = this.saleManagerModel[0];
      this.newProposal.salesPerson = this.temp2.length > 0 && this.temp2.filter(function (value) {
        if (value.id == selSP) {
          return value;
        }
      })[0];*/

    let selTR = this.traffickerModel[0];
    this.newProposal.trafficker = this.temp3.length > 0 && this.temp3.filter(function (value) {
      if (value.id == selTR) {
        return value;
      }
    })[0];


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

    /* var _selectedPriceId = this.pricingModel[0];
     let _selectedPrice = this.pricingModelOptions.filter(function (value) {
       if (value['id'] === _selectedPriceId) {
         return value;
       }
     })[0];
 
     var _selectedCurrencyId = this.currencyModel[0];
     let _selectedCurrency = this.currencyOptions.filter(function (value) {
       if (value['id'] === _selectedCurrencyId) {
         return value;
       }
     })[0];*/

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
  loadProposalData(proposalId) {
    console.log("in load proposal function");
    let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_GET_BY_ID + proposalId;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    this.proposalId = proposalId;
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.newProposal = data;
          this.proposalBckup = JSON.parse(JSON.stringify(data));
          this.currentStatus = data.status.statusId;
          this.currentStatusName = _.includes(data.status.name, "Review") ? "Under" + data.status.name : data.status.name;
          this.proposalName = this.newProposal.name;
          this.auditObj = data.audits;
          this.remainingCost = this.newProposal.budget - this.newProposal.proposalCost;
          this.assignedBy = data.salesPerson.firstName + " " + data.salesPerson.firstName;
          if (data.assignTo == undefined || data.assignTo == null || data.assignTo.id != this.logedInUserId || this.currentStatus == 3 || this.currentStatus == 2) {
            this.router.navigate(['dashboard/sales/non-approved-proposal/edit'], { queryParams: { callback: 'loadProposalData', proposalId: proposalId } });
          }
          this.versions = data.versions;
          if (this.newProposal.submitted) {
            //this.disabledVersion = true;
            var proposalVersion = this.newProposal.versions.find(version => version.submitted == true);
            if (proposalVersion != null) {
              this.selectedVersion = proposalVersion.version;
            }
          } else {
            this.selectedVersion = data.versions.length - 1;
          }
          this.loadVersionDetails(data.versions[this.selectedVersion]);
          this.newProposal.previousStatus = data.versions[this.selectedVersion].previousStatus;
          this.previousStatus = this.newProposal.previousStatus != null ? this.newProposal.previousStatus.name : '';
          this.versionOptions = this.convertNumericDataIntoDropdown(data.versions, 'version', 'version');
          this.versionOptionsModel[0] = this.selectedVersion;

          if (this.newVersion) {
            this.disabledVersion = true;
            this.newVersionNumber = data.versions.length;
            this.versionOptions.push({ "id": this.newVersionNumber, "name": this.newVersionNumber + "" });
            this.versionOptionsModel[0] = this.newVersionNumber;
          }

          //this.loadAssignToWithDropdownData(data);

          //if (this.selectedVersion) {
          //this.newProposal = data.versions[this.selectedVersion];
          this.loadDropdownSaveData(this.newProposal);

          /* } else {            
             this.newProposal = data;
             if (data.versions.length) {
               this.versionOptions = this.convertNumericDataIntoDropdown(data.versions, 'version', 'version');
             }
             else {
               this.versionOptions = [{ 'id': '1', 'name': '0' }];
               this.versionOptionsModel[0] = '1';
             }
           }*/
          for (var audit in this.newProposal.audits) {
            if (this.newProposal.audits[audit].action == "Pricing Approved") {
              this.pricingApproved = true;
            } else if (this.newProposal.audits[audit].action == "Legal Approved") {
              this.legalApproved = true;
            }
          }
          if (this.pricingApproved && this.legalApproved) {
            this.needAdminApproval = true;
          }
          if (this.newProposal.advertiserDiscount == null) {
            this.newProposal.advertiserDiscount = 0;
          }
          if (this.currentStatus != "6") {
            this.disableFlow = true;
            if (data.assignTo) {
              var roleId = data.assignTo.role.roleId;
              if (roleId == "2") {
                this.disableApprovalRejection = true;
              }
            }
          } else {
            if (data.assignTo) {
              var roleId = data.assignTo.role.roleId;
              if (roleId != "2") {
                this.disableFlow = true;
                this.disableApprovalRejection = true;
              }
            }
          }
        }
      });
    return data;
  }

  onChange() {
    if (this.pricingModel[0] == "Gross" && this.agencyModel.length) {
      this.disabledAgencyMargin = false;
    }
    else {
      this.disabledAgencyMargin = true;
    }
  }

  onVersionSelect(data: any) {
    console.log("version select..............");
    this.selectedVersion = data[0];
    this.versionMode = true;
    this.loadVersionDetails(this.versions[this.selectedVersion]);
    this.loadDropdownSaveData(this.newProposal);
    //this.loadProposalData(this.proposalId);
    //this.loadDropdownSaveData(this.versions[this.selectedVersion]);
    //this.newProposal = this.versions[this.selectedVersion];
    //this.loadVersionDetails(this.newProposal.versions[this.selectedVersion]);
  }

  loadVersionDetails(data: any) {
    this.newProposal.description = data.description;
    this.newProposal.status = data.status;
    this.newProposal.startDate = data.startDate;
    this.newProposal.endDate = data.endDate;
    this.newProposal.dueDate = data.dueDate;
    this.newProposal.currency = data.currency;
    this.newProposal.pricingModel = data.pricingModel;
    this.newProposal.advertiserDiscount = data.advertiserDiscount;
    this.newProposal.budget = data.budget;
    this.newProposal.term = data.term;
    this.newProposal.percentageOfClose = data.percentageOfClose;
    this.newProposal.salesCategory = data.salesCategory;
    this.newProposal.submitted = data.submitted;
  }

  updateProposalWithVersionDetails(data: any) {
    //this.newProposal.versions[this.selectedVersion].name = this.newProposal.name;
    this.newProposal.versions[this.selectedVersion].description = this.newProposal.description;
    this.newProposal.versions[this.selectedVersion].status = this.newProposal.status;
    this.newProposal.versions[this.selectedVersion].startDate = this.newProposal.startDate;
    this.newProposal.versions[this.selectedVersion].endDate = this.newProposal.endDate;
    this.newProposal.versions[this.selectedVersion].dueDate = this.newProposal.dueDate;
    this.newProposal.versions[this.selectedVersion].currency = this.newProposal.currency;
    this.newProposal.versions[this.selectedVersion].pricingModel = this.newProposal.pricingModel;
    this.newProposal.versions[this.selectedVersion].advertiserDiscount = this.newProposal.advertiserDiscount;
    this.newProposal.versions[this.selectedVersion].budget = this.newProposal.budget;
    this.newProposal.versions[this.selectedVersion].terms = this.newProposal.term;
    this.newProposal.versions[this.selectedVersion].percentageOfClose = this.newProposal.percentageOfClose;
    this.newProposal.versions[this.selectedVersion].salesCategory = this.newProposal.salesCategory;
    this.newProposal.versions[this.selectedVersion].submitted = this.newProposal.submitted;

    /*  if (this.selectedVersion != this.newProposal.versions.length - 1){  //|| this.newProposal.submitted) {
        this.loadVersionDetails(data);
      }*/
  }

  initiateWorkFlow() {
    this.newProposal.action = "In progress";
    console.log("Initiating workflow...");
    let workflowUrl = PathConfig.BASE_URL_API + PathConfig.WORKFLOW_START;
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    this.workflowTask.proposal = this.newProposal;
    this.workflowTask.version = this.selectedVersion;
    this.workflowTask.comment = this.newProposal.action;
    this.workflowTask.proposalStatus = this.newProposal.status.statusId;
    this.workflowTask.userId = this.logedInUserId;
    this.workflowTask.action = true;

    var workflowData = this.httpService.post(workflowUrl, this.workflowTask, auth_token);
    let self = this;

    workflowData.subscribe(
      workflowData => {
        if (workflowData) {
          let processId = workflowData.id;
          console.log("Process Id" + processId);
          this.clearState();
          this.loadProposalData(this.newProposal.proposalId);
          this.isAlertMessageBoxVisible = false;
          this.showMessage(this);
          /*     let findTaskUrl = PathConfig.BASE_URL_API + PathConfig.FIND_TASK_ID + processId + "/task/candidateuser/" + this.logedInUserId;
               var processData = this.httpService.get(findTaskUrl, auth_token);
               processData.subscribe(
                 processData => {
                   if (processData) {
                     for (var i in processData) {
                       if (processData[i].executionId == processId) {
                         this.taskId = processData[i].id;
                         console.log("Task Id" + this.taskId);
                       }
                     }
                     let assignTaskUrl = PathConfig.BASE_URL_API + PathConfig.ASSIGN_TASK + processId + "/assignTask/" + this.taskId + "/" + this.logedInUserId;
                     var AssignedTaskData = this.httpService.post(assignTaskUrl, "", auth_token);
                     AssignedTaskData.subscribe(
                       AssignedTaskData => {
                         if (AssignedTaskData) {
                           console.log("Assignee:" + AssignedTaskData.assignee);
                         }
                       });
                   }
                 }); */
        }
      }, error => {
        if (error) {
          console.log("Error in initiating workflow..");
          this.errorMessage = error;
        }
      });
  }

  executeWorkflow() {
    /*   if (this.statusModel.length && this.statusModel[0] != "1") {
         if (this.currentStatus == "1" && this.statusModel[0] == "6") {
           this.initiateWorkFlow();
         } else {*/
    let findTaskUrl = PathConfig.BASE_URL_API + PathConfig.TASK_GET_ID + this.newProposal.proposalId + "/" + this.logedInUserId;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(findTaskUrl, auth_token);

    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.taskId = data.id;
          this.processId = data.executionId;

          let assignTaskUrl = PathConfig.BASE_URL_API + PathConfig.ASSIGN_TASK + this.processId + "/assignTask/" + this.taskId + "/" + this.logedInUserId;
          var AssignedTaskData = this.httpService.post(assignTaskUrl, "", auth_token);
          AssignedTaskData.subscribe(
            AssignedTaskData => {
              if (AssignedTaskData) {
                console.log("Assignee:" + AssignedTaskData.assignee);

                let taskExecutionUrl = PathConfig.BASE_URL_API + PathConfig.ASSIGN_TASK + this.processId + "/executeTask/" + this.taskId;
                this.workflowTask.proposal = this.newProposal;
                this.workflowTask.version = this.selectedVersion;
                this.workflowTask.comment = this.newProposal.action;
                this.workflowTask.proposalStatus = this.newProposal.status.statusId;
                this.workflowTask.userId = this.logedInUserId;
                this.workflowTask.action = true;

                this.workflowTask.proposalStatus = this.statusModel[0];

                if (this.statusModel[0] == "7") {
                  this.workflowTask.action = false;
                } else {
                  this.workflowTask.action = true;
                }

                if (this.currentStatus = "7" && this.statusModel[0] == "6") {
                  this.workflowTask.action = false;
                }
                else if (this.currentStatus = "7" && this.statusModel[0] == "3") {
                  this.workflowTask.action = true;
                }

                let data1 = this.httpService.post(taskExecutionUrl, this.workflowTask, auth_token);

                data1.subscribe(
                  data1 => {
                    if (data1) {
                      console.log("task executed");
                      //   this.backToTable();
                      this.clearState();
                      this.loadProposalData(this.newProposal.proposalId);
                      this.isAlertMessageBoxVisible = false;
                      this.showMessage(this);
                    }
                  },
                  () => {
                    this.clearState();
                    this.loadProposalData(this.newProposal.proposalId);
                    console.log("task executed");
                    this.isAlertMessageBoxVisible = false;
                    this.showMessage(this);
                  });
              }
            }, error => {
              if (error) {
                console.log("Error in assigning a task");
                this.errorMessage = error;
              }
            });
        }
      });
    /*      }
        } else {
          console.log("Status Model" + this.statusModel[0])
          this.currentStatus = this.statusModel[0];
        }*/

  }

  loadAssignToWithDropdownData(data: any) {
    this.assignToOptionsModel = [];
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);

    let assignToUrl = PathConfig.BASE_URL_API + PathConfig.USER_GET_LIST
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
          this.assignToList = assignToData;

          if (data.assignTo != null) {
            this.assignToOptionsModel[0] = data.assignTo.id;

            this.assignToDisplayName = data.assignTo.firstName + ' ' + data.assignTo.lastName;
            this.selectedId = data.id;
            /*console.log("assignTo OBJ : ", data.assignTo.role.roleName);
            let roleName = data.assignTo.role.roleName;
            var roleBasedUserList = dataBaseOnRoleBy[roleName];
            let userObj = _.find(roleBasedUserList, function(obj, index){

            });*/
          }

          let self = this;
          setTimeout(function () {
            if (data && data.assignTo && data.assignTo.id) {
              self.selectedId = data.assignTo.id;
            }
            //self.selectedId = data.assignTo.id;
          }, 100);
        }
      });
  }

  assignTask() {
    console.log("assigning task...");
    let findTaskUrl = PathConfig.BASE_URL_API + PathConfig.TASK_GET_ID + this.proposalId + "/" + this.newProposal.assignTo.id;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(findTaskUrl, auth_token);

    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.taskId = data.id;
          this.processId = data.executionId;

          let assignTaskUrl = PathConfig.BASE_URL_API + PathConfig.ASSIGN_TASK + this.processId + "/assignTask/" + this.taskId + "/" + this.newProposal.assignTo.id;
          var AssignedTaskData = this.httpService.post(assignTaskUrl, "", auth_token);
          AssignedTaskData.subscribe(
            AssignedTaskData => {
              if (AssignedTaskData) {
                console.log("Assignee:" + AssignedTaskData.assignee);
                this.clearState();
                this.loadProposalData(this.newProposal.proposalId);

                /*  let assignTaskUrl = PathConfig.BASE_URL_API + PathConfig.ASSIGN_TASK + processId + "/assignTask/" + this.taskId + "/" + this.logedInUser.id;
                  var AssignedTaskData = this.httpService.post(assignTaskUrl, "", auth_token);
                  AssignedTaskData.subscribe(
                    AssignedTaskData => {
                      if (AssignedTaskData) {
                        console.log("Assignee:" + AssignedTaskData.assignee);
                      }
                    }, error => {
                      if (error) {
                        console.log("Error in assigning a task");
                      }
                    });*/
              }
            }, error => {
              if (error) {
                console.log("Error in assigning a task");
                this.errorMessage = error;
              }
            });
        }
      });
  }

  sendToPricingReview() {
    let dialogRef = this.dialog.open(ConfirmReviewDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result == "OK") {
        this.statusModel[0] = "4";
        this.newProposal.action = "Pricing Review";
        this.successMessage = ApplicationMessages.SEND_PRICING;
        this.saveProposal();
      }
    });
  }

  sendToLegalReview() {
    let dialogRef = this.dialog.open(ConfirmReviewDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result == "OK") {
        this.statusModel[0] = "5";
        this.newProposal.action = "Legal Review";
        this.successMessage = ApplicationMessages.SEND_LEGAL;
        this.saveProposal();
      }
    });
  }

  sendToAdminReview() {
    let dialogRef = this.dialog.open(ConfirmReviewDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result == "OK") {
        this.statusModel[0] = "7";
        this.newProposal.action = "Admin Review";
        this.successMessage = ApplicationMessages.SEND_ADMIN;
        this.saveProposal();
      }
    });
  }

  sendToWorkflowRejection() {
    let dialogRef = this.dialog.open(ProposalCommentDialog, { data: { title: 'Reason for rejection:' }, });
    dialogRef.afterClosed().subscribe(result => {
      console.log("comment dialog box closed");
      if (this.getSetService.obj != undefined) {
        this.newProposal.action = this.getSetService.obj;
        if (this.statusModel[0] == "4") {
          this.newProposal.action = "Pricing Rejected : " + this.newProposal.action;
        } else if (this.statusModel[0] == "5") {
          this.newProposal.action = "Legal Rejected : " + this.newProposal.action;
        } else if (this.statusModel[0] == "7") {
          this.newProposal.action = "Admin Rejected : " + this.newProposal.action;
        }
        this.statusModel[0] = "6";
        this.successMessage = ApplicationMessages.PROPOSAL_REJECT;
        this.saveProposal();
      }
      this.getSetService.setValue({});
    });
  }

  sendToWorkflow() {
    let dialogRef = this.dialog.open(ConfirmReviewDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result == "OK") {
        this.statusModel[0] = "6";
        this.newProposal.action = "In progress";
        this.successMessage = ApplicationMessages.MARK_INPROGRESS;
        this.saveProposal();
      }
    });
  }

  sendApproval() {
    let dialogRef = this.dialog.open(ConfirmReviewDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result == "OK") {
        if (this.statusModel[0] == "7") {
          this.statusModel[0] = "3";
          this.newProposal.action = "Sold";
          this.successMessage = ApplicationMessages.PROPOSAL_SOLD;
        } else {
          this.newProposal.action = "Approved";
          if (this.statusModel[0] == "4") {
            this.newProposal.action = "Pricing Approved";
          } else if (this.statusModel[0] == "5") {
            this.newProposal.action = "Legal Approved";
          }
          this.statusModel[0] = "6";
          this.successMessage = ApplicationMessages.PROPOSAL_APPROVE;
        }
        this.showMessage(this);
        this.saveProposal();
      }
    });
  }

  copyProposal() {
    let self = this;
    let dialogRef = this.dialog.open(ConfirmReviewDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result == "OK") {
        this.successMessage = "Successfully copy";
        this.isAlertMessageBoxVisible = false;
        this.showMessage(this);
        // this.saveProposal();
      }
    });
  }



  showMessage(self) {
    setTimeout(function () {
      self.isAlertMessageBoxVisible = true;
    }, 3000);
  }
}
