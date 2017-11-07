import {
  Component, OnInit, AfterViewInit, HostListener, ElementRef, ChangeDetectorRef, ViewChild
} from '@angular/core';
import { HttpService } from '../../../common/services/http.service';
import { PathConfig } from '../../../common/config/path.config';
import { ConstantConfig } from '../../../common/config/constant.config';
import { LocalStorageService } from "../../../common/services/local-storage.service";
import { Broadcaster } from '../../../common/services/broadcaster.service';
import { AuthService } from '../../../common/services/auth.service';
import { Opportunity } from '../../../common/models/opportunity';
import { workflowProposal } from '../../../common/models/workflow.proposal';
import { MdDialog, MdDialogRef } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { ConfirmDialogComponent } from "../../../common/modules/dialog/confirm/confirm.component";
import { DateFormat } from "../../../common/modules/date.format";
import { IMultiSelectOption } from "angular-2-dropdown-multiselect";
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { DropDownDataFilter } from '../../../common/filter/dropdown.filter';
import { DropDownSetting } from '../../../common/utils/dropdown.settings';
import { IMultiSelectTexts } from "angular-2-dropdown-multiselect";
import { DateFormatterComponent } from "../../../common/utils/date-format.component";
import * as FileSaver from 'file-saver';
import * as _ from 'lodash';
import { CalendarService } from "../../../common/services/calendar.service";


@Component({
  templateUrl: './opportunity.component.html',
  styleUrls: ['./opportunity.component.scss']
})
export class OpportunityComponent implements OnInit {
  filterHeight: number = 0;
  //Calendar settings starts
  //Caledar array. It is declared in constructor.
  calendarArr = null;
  clickOnDatepicker = null;
  calendarBtnClick = null;
  resetAllCalendar = null;
  //Calendar settings end

  @HostListener('document:click', ['$event'])
  clickout(event) {
    this.resetAllCalendar(this);
  }

  showForm: Boolean = false;

  opportunityListDTConfig: Object = {};
  opportunityListRefresh: Boolean = false;
  opportunityListSearchModel: any = {};
  newopportunity: Opportunity = new Opportunity();
  workflowProposal: workflowProposal = new workflowProposal();
  editMode: Boolean = false;
  versionMode: Boolean = false;
  versionUpdateMode: Boolean = false;
  copyMode: Boolean = false;
  disableOpportunity: boolean = false;
  logedInUserFullName: string;
  logedInUser: any = {};
  opportunityDocument: Array<Object> = [];
  opportunityDocumentTable: boolean = true;
  opportunityDocument1 = [];
  documentConfig: Object;
  documentListRefresh: Boolean = false;
  currentDate: Date;
  salesCategoryList: Array<Object> = [];
  salesPersonList: Array<Object> = [];
  mediaPlannerList: Array<Object> = [];
  traffickerList: Array<Object> = [];
  advertiserList: Array<Object> = [];
  agencyList: Array<Object> = [];
  billingSourceList: Array<Object> = [];
  salesCategoryOptions: IMultiSelectOption[];
  salesCategoryOptionsModel: Array<Object> = [];
  traffickerOptions: IMultiSelectOption[];
  traffickerModel: Array<Object> = [];
  salesPersonOptions: IMultiSelectOption[];
  salesPersonOptionsModel: Array<Object> = [];
  mediaPlannerOptions: IMultiSelectOption[];
  mediaPlannerOptionsModel: Array<Object> = [];
  billingSourceOptions: IMultiSelectOption[];
  billingSourceOptionsModel: Array<Object> = [];
  advertiserOptions: IMultiSelectOption[];
  advertiserOptionsModel: Array<Object> = [];
  advertiserOptionsModel2: Array<Object> = [];
  agencyOptions: IMultiSelectOption[];
  agencyOptionsModel: Array<Object> = [];
  versionOptions: IMultiSelectOption[];
  versionOptionsModel: Array<Object> = [];
  singleSelectSettings: IMultiSelectSettings;
  multiSelectSelectSettings: IMultiSelectSettings;
  textSetting: IMultiSelectTexts;
  disabledAgencyMargin: Boolean = true;
  disabledVersion: Boolean = false;
  documentType: string;
  newProposal: any;
  newProposalData: any;
  processData: any;
  taskId: any;
  AssignedTask: any;
  isAlertMessageBoxVisible: Boolean = true;
  successMessage: String = "Opportunity is updated successfully.";

  opportunityForm: FormGroup;
  errorMessage: string;
  opportunityId: any;

  filters: Boolean = false;
  editActions: Boolean = true;
  notes: Array<Object> = [];
  description: string;
  editNoteModel: any = {};
  editNoteIndex = null;
  noteObj: any = {};
  testModel = [];
  newVersionNumber: number = 0;
  selectedVersionSeq: number = 0;

  permissions = null;
  isOpportunityCreatePermission: boolean = false;
  isOpportunityEditPermission: boolean = false;
  isOpportunityDeletePermission: boolean = false;
  isOpportunityCopyPermission: boolean = false;
  isOpportunityNewVersionPermission: boolean = false;

  advanceSearchModelOBj = {
    searchCreatedDate: null,
    searchEndDate: null,
    searchDueDate: null,
  };

  advanceForm: FormGroup;
  isRegenerate: boolean = true;

  @ViewChild('inputFile')
  documentValue: any;
  pricingModelOptions: Array<Object> = [{
    "id": "Net",
    "name": "Net"
  }, {
    "id": "Gross",
    "name": "Gross"
  }];
  pricingModel = [];

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
  currencyModel = [];

  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService,
    private elementRef: ElementRef,
    private dialog: MdDialog,
    private router: Router,
    private opportunityFr: FormBuilder,
    private cdr: ChangeDetectorRef,
    private calendarService: CalendarService,
    private fb: FormBuilder
  ) {
    this.opportunityForm = opportunityFr.group({
      "advertiser": [this.advertiserOptionsModel],
      "agency": [this.agencyOptionsModel],
      "name": [],
    });

    // You have to declare how many calendar want to use in this component
    this.calendarArr = {
      'isStartDateOpen': false,
      'isEndDateOpen': false,
      'isNewCreatedDateOpen': false,
      'isDueDateOpen': false
    };

    this.createAdvanceForm();
  }

  createAdvanceForm() {
    this.advanceForm = this.fb.group({
      name: '',
      advertiser: '',
      agency: '',
      startDate: '',
      endDate: '',
      dueDate: '',
      salesCategory: ''
    });
  }

  applyAdvanceFilter() {
    this.isRegenerate = false;
    let self = this;
    setTimeout(function () {
      self.createTable();
    }, 1000)
  }

  toggleForm() {
    this.showForm = !this.showForm;
    this.resetForm();
    //this.loadAllDropdowns();
  }

  addNotes() {
    this.description = this.newopportunity.description;
    this.noteObj = {
      description: this.newopportunity.description,
      date: new Date()
    }
    this.notes.unshift(this.noteObj);
    this.newopportunity.description = ''
  }

  editNote(index, description) {
    this.editNoteIndex = index;
    this.editNoteModel.description = description;
  }

  saveNote(index) {
    this.noteObj = {
      description: this.editNoteModel.description,
      date: new Date()
    }
    this.notes.splice(index, 1, this.noteObj);
    this.editNoteIndex = null;
  }

  deleteNote(index) {
    this.notes.splice(index, 1);
  }

  getCopyTemp(data, full) {
    let tempStr = ''

    if (this.isOpportunityCopyPermission) {
      tempStr = '<a href="javascript:void(0);" data-name="copy" data-custom="' + data + '"><span class="fa fa-copy" aria-hidden="true"></span>copy</a>';
    } else {
      tempStr = '<div><span class="fa fa-copy" aria-hidden="true"></span>copy</div>'
    }

    return tempStr;
  }

  getEditTepm(data, full) {
    let tempStr = ''

    if (this.isOpportunityEditPermission) {
      tempStr = '<a href="javascript:void(0);" data-name="edit" data-custom="' + data + '" ><span class="fa fa-pencil" aria-hidden="true"></span>Edit</a>';
    } else {
      tempStr = '<div><span class="fa fa-pencil" aria-hidden="true"></span>Edit</div>'
    }

    return tempStr;
  }

  getDeleteTemp(data, full) {
    let tempStr = ''

    if (this.isOpportunityDeletePermission) {
      tempStr = "<a href='javascript:void(0);' data-name='delete' data-custom='" + JSON.stringify(full) + "' ><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>";
    } else {
      tempStr = "<div><span class='fa fa-trash' aria-hidden='true'></span>Delete</div>";
    }

    return tempStr;
  }

  getViewTemp(data, full) {
    let tempStr = ''

    if (this.isOpportunityDeletePermission) {
      tempStr = '<a href="javascript:void(0);" data-name="edit" data-custom="' + data + '" ><span class="fa fa-folder-open" aria-hidden="true"></span>Open</a>';
    } else {
      tempStr = '<div><span class="fa fa-folder-open" aria-hidden="true"></span>Open</div>';
    }

    return tempStr;
  }

  getNewVersionTemp(data, full) {
    let tempStr = ''

    if (this.isOpportunityNewVersionPermission) {
      tempStr = '<a href="javascript:void(0);" data-name="version" data-custom="' + data + '"><span class="fa fa-plus" aria-hidden="true"></span>New Version</a>';
    } else {
      tempStr = '<div><span class="fa fa-plus" aria-hidden="true"></span>New Version</div>';
    }

    return tempStr;
  }

  ngOnInit() {

    this.loadAllDropdowns();

    this.loadTimeSetting();

    this.createTable();

  }

  loadTimeSetting() {
    let self = this;
    this.permissions = this.localStorage.get('user_permissions');

    if (this.permissions && this.permissions.length) {
      this.isOpportunityCreatePermission = this.permissions.indexOf('create_opportunity') > -1;
      this.isOpportunityEditPermission = this.permissions.indexOf('edit_opportunity') > -1;
      this.isOpportunityDeletePermission = this.permissions.indexOf('delete_opportunity') > -1;
      this.isOpportunityCopyPermission = this.permissions.indexOf('copy_opportunity') > -1;
      this.isOpportunityNewVersionPermission = this.permissions.indexOf('new_version_opportunity') > -1;
    }

    this.clickOnDatepicker = this.calendarService.clickOnDatepicker(this);
    this.calendarBtnClick = this.calendarService.calendarBtnClick(this);
    this.resetAllCalendar = this.calendarService.resetAllCalendar(this);

    this.multiSelectSelectSettings = DropDownSetting.multiSelectSelectSettings;
    this.singleSelectSettings = DropDownSetting.singleSelectSettings;
    this.textSetting = DropDownSetting.textSetting;

    this.currentDate = new Date();
  }

  createTable() {
    if (!this.isRegenerate) {
      this.isRegenerate = true;
    }

    let self = this;

    this.opportunityListDTConfig = {
      "columnDefs": [
        {
          "className": "table-inline-a",
          "targets": 0,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              template = "<div class='col-breadcrumb'><div><a href='javascript:void(0);' class='opportunity-name' data-name='edit' data-custom='" + full.opportunityId + "'>" + full.name + "</a></div>" +
                "<div class='sub-heading'>(" + full.opportunityId + ")</div></div>";
            }
            return template;
          }
        },
        {
          "targets": 1,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              template = data.firstName + " " + data.lastName;
            } else {
              template = '<span> </span>';
            }
            return template;
          }
        },
        {
          "targets": 2,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              template = data;
            } else {
              template = '<span> </span>';
            }
            return template;
          }
        },
        {
          "targets": 3,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              template = data;
            } else {
              template = '<span> </span>';
            }
            return template;
          }
        },
        {
          "targets": 4,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              template = data;
            } else {
              template = '<span> </span>';
            }
            return template;
          }
        },
        {
          "targets": 5,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              var date = new DateFormatterComponent().changeDateFormatTo_dd_mm_yy(data, 'dd-MMM-yyyy');
              template = '<span>' + date + '</span>';
            }
            return template;
          }
        },
        {
          "className": "text-center",
          "orderable": false,
          "targets": 7,
          "render": function (data, type, full, meta) {
            var template;
            if (full.submitted) {
              template = '<span class="allICons dots"></span><div class="tooltipDel">' +
                self.getViewTemp(data, full) +
                self.getCopyTemp(data, full) +
                self.getDeleteTemp(data, full) +
                '</div>';
            } else {
              template = '<span class="allICons dots"></span><div class="tooltipDel">' +
                self.getEditTepm(data, full) +
                self.getCopyTemp(data, full) +
                self.getNewVersionTemp(data, full) +
                self.getDeleteTemp(data, full) +
                '</div>';
            }
            return template;
          }
        }],
      "columns": [
        { "title": "Name", "data": "name" },
        { "title": "Sales Manager", "data": "salesPerson" },
        { "title": "Advertiser", "data": "advertiser.name" },
        { "title": "Agency", "data": "agency.name" },
        { "title": "Sales Category", "data": "salesCategory.name" },
        { "title": "Due Date", "data": "dueDate" },
        { "title": 'Budget', "data": "budget" },
        { "title": '', "data": "opportunityId" }
      ],
      //"apiURL": PathConfig.BASE_URL_API + PathConfig.OPPORTUNITY_LIST
      "ajax": {
        type: "POST",
        contentType: "application/json",
        dataType: 'json',
        cache: false,
        data: function (d) {
          d['filters'] = self.getFilterArr(self.advanceForm.value);
          return JSON.stringify(d);
        },
        /*dataSrc: function (json) {
              self.isFilterDataProcessing = false;
              return json.data;
        },*/
        beforeSend: function (xhr) {
          xhr.setRequestHeader("Authorization", 'Bearer ' + self.localStorage.get(ConstantConfig.AUTH_TOKEN));
        },
        url: PathConfig.BASE_URL_API + PathConfig.OPPORTUNITY_LIST
      }
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
            var template;
            if (full.editMode) {
              template = '<span class="allICons dots"></span><div class="tooltipDel">' +
                "<a href='javascript:void(0);' data-name='delete' data-custom='" + meta.row + "'><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>" +
                "<a href='javascript:void(0);' data-name='download' data-custom='" + meta.row + "'><span class='fa fa-download' aria-hidden='true'></span>Download</a>" +
                '</div>';
            } else {
              template = '<span class="allICons dots"></span><div class="tooltipDel">' +
                "<a href='javascript:void(0);' data-name='delete' data-custom='" + meta.row + "'><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>" +
                '</div>';
            }

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

  getFilterArr(data) {
    let filterArr = [];
    _.each(data, function (val, key) {
      if (val) {
        filterArr.push({
          'column': key,
          'value': Array.isArray(val) ? val[0] : val
        });
      }
    });

    return filterArr;
  }
  // on Menu Selection
  onOpportunityTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'edit') {
      //edit code here
      this.toggleForm();
      this.editMode = true;
      this.versionMode = false;
      this.disabledVersion = false;
      this.versionUpdateMode = false;
      this.copyMode = false;
      this.editOpportunity(data['value'], this.editMode);

    } else if (data['clickedOn'] == 'delete') {
      //delete code here
      //confirm box
      let dialogRef = this.dialog.open(ConfirmDialogComponent);
      this.opportunityListRefresh = false;
      dialogRef.afterClosed().subscribe(result => {
        if (result == "OK") {
          this.deleteOpportunityOnConfirm(JSON.parse(data['value']));
        }
      });
    } else if (data['clickedOn'] == 'version') {
      //copy code here
      console.log("New version of opportunity........");
      this.toggleForm();
      this.disabledVersion = true;
      this.versionMode = true;
      this.copyMode = false;
      this.editOpportunity(data['value'], this.editMode);

    } else if (data['clickedOn'] == 'copy') {
      this.copyMode = true;
      this.versionUpdateMode = false;
      this.versionMode = false;
      this.editOpportunity(data['value'], this.editMode);
    }
  }



  //back to table button
  backToTable() {
    this.resetForm();
    this.opportunityListRefresh = true;
    this.showForm = false;
    this.disableOpportunity = false;
  }


  //search dropdown list
  opportunitySearchTableDD: Array<Object> = [
    { "id": 0, "labelName": "Name" },
    { "id": 1, "labelName": "Sales Manager" },
    { "id": 2, "labelName": "Advertiser" },
    { "id": 3, "labelName": "Agency" },
    { "id": 4, "labelName": "Sales Category" },
    { "id": 5, "labelName": "Due Date" },
    { "id": 6, "labelName": "Budget" }
  ];
  //opportunity dropdown Event
  opportunitySearchEvent(data) {
    this.advanceForm.reset();
    this.filterHeight = 0;
    this.opportunityListSearchModel['searchTxt'] = data['text'];
    this.opportunityListSearchModel['colIndex'] = data['dd-id'];
  }

  // delete opportunity
  opportunityObjToDelete: any;
  deleteOpportunityOnConfirm(obj) {
    let url = PathConfig.BASE_URL_API + PathConfig.OPPORTUNITY_DELETE + obj['opportunityId'];;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.deleteWithId(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.opportunityObjToDelete = null;
          this.opportunityListRefresh = true;
        }
      });
    return data;
  }

  editOpportunity(id, edit) {
    let url = PathConfig.BASE_URL_API + PathConfig.OPPORTUNITY_GET_BY_ID + id;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          /*    if(this.versionMode&&edit){
                this.newopportunity=data.versions;
              } */

          this.newopportunity = data;
          if (this.newopportunity.submitted) {
            this.disableOpportunity = true;
          }
          this.opportunityId = data.opportunityId;
          this.logedInUserFullName = data.assignedBy.firstName + " " + data.assignedBy.lastName;
          this.salesPersonOptionsModel[0] = data.salesPerson.id;
          this.traffickerModel[0] = data.trafficker.id;
          if (data.mediaPlanner != null) {
            this.mediaPlannerOptionsModel[0] = data.mediaPlanner.id;
          }

          if (data.versions.length) {
            this.versionOptions = this.convertNumericDataIntoDropdown(data.versions, 'seqNo', 'version');
            this.versionOptionsModel[0] = data.versions.length;
          }
          //else {
          // this.versionOptions = [{ "id": "firstVersion", "name": 0 + "" }];
          // this.versionOptionsModel[0] = "firstVersion";
          //}
          if (this.versionMode) {
            this.newVersionNumber = data.versions.length + 1;
            this.versionOptions.push({ "id": "newVersion", "name": this.newVersionNumber + "" });
            this.versionOptionsModel[0] = "newVersion";
          }


          this.pricingModel[0] = data.pricingModel;
          this.currencyModel[0] = data.currency;

          if (data.salesCategory != undefined) {
            this.salesCategoryOptionsModel[0] = data.salesCategory.salesCatagoryId;
          }
          if (data.agency != undefined) {
            this.agencyOptionsModel[0] = data.agency.companyId;
            if (this.pricingModel[0] == "Gross") {
              this.disabledAgencyMargin = false;
            } else {
              this.disabledAgencyMargin = true;
            }
          }
          if (data.advertiser != undefined) {
            this.advertiserOptionsModel[0] = data.advertiser.companyId;
          }
          if (data.billingSource != undefined && data.billingSource != null) {
            this.billingSourceOptionsModel[0] = data.billingSource.billingSourceId;
          }
          this.pricingModel[0] = data.pricingModel;
          this.currencyModel[0] = data.currency;

          if (data.documents != undefined && data.documents != null) {
            this.opportunityDocument1 = data.documents;
            for (var i in this.opportunityDocument1) {
              let updatedBy = this.opportunityDocument1[i]['updatedBy'];
              let name = updatedBy ? updatedBy.firstName + " " + updatedBy.lastName : '';
              this.opportunityDocument1[i]['username'] = name
              this.opportunityDocument1[i]['editMode'] = 1;
            }
          }
          this.opportunityDocument = this.opportunityDocument1;
          if (edit || this.versionMode) {
            this.showForm = true;
          }
          else {
            this.saveForm();
          }
        }
      });
    console.log("At the end of edit");
    return data;
  }



  // save the form
  saveForm() {
    let url = PathConfig.BASE_URL_API;

    if (this.copyMode) {
      this.editMode = false;
      url = url + PathConfig.OPPORTUNITY_COPY + this.newopportunity.opportunityId + "/" + this.versionMode;
    } else {
      url = url + PathConfig.OPPORTUNITY_ADD;
    }

    if (this.versionUpdateMode || this.versionMode) {
      url = PathConfig.BASE_URL_API + PathConfig.OPPORTUNITY_VERSION_UPDATE + this.newopportunity.opportunityId;
    }

    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);

    this.newopportunity.pricingModel = this.pricingModel[0];

    this.newopportunity.currency = this.currencyModel[0];


    let _selectedTrafficker = this.traffickerModel[0];
    this.newopportunity.trafficker = this.traffickerList.length > 0 && this.traffickerList.filter(function (value) {
      if (value['id'] == _selectedTrafficker) {
        return value;
      }
    })[0];

    /*  var _selectedSalesPerson = this.salesPersonOptionsModel[0];
      this.newopportunity.salesPerson = this.salesPersonList.length > 0 && this.salesPersonList.filter(function (value) {
        if (value['id'] == _selectedSalesPerson) {
          return value;
        }
      })[0];*/
    this.newopportunity.salesPerson = this.logedInUser;

    let _selectedAgency = this.agencyOptionsModel[0];
    this.newopportunity.agency = this.agencyList.length > 0 && this.agencyList.filter(function (value) {
      if (value['companyId'] == _selectedAgency) {
        return value;
      }
    })[0];

    let _selectedAdvertiser = this.advertiserOptionsModel[0];
    this.newopportunity.advertiser = this.advertiserList.length > 0 && this.advertiserList.filter(function (value) {
      if (value['companyId'] == _selectedAdvertiser) {
        return value;
      }
    })[0];

    let _selectedSalesCategory = this.salesCategoryOptionsModel[0];
    this.newopportunity.salesCategory = this.salesCategoryList.length > 0 && this.salesCategoryList.filter(function (value) {
      if (value['salesCatagoryId'] == _selectedSalesCategory) {
        return value;
      }
    })[0];

    let _selectedBillingSource = this.billingSourceOptionsModel[0];
    this.newopportunity.billingSource = this.billingSourceList.length > 0 && this.billingSourceList.filter(function (value) {
      if (value['billingSourceId'] == _selectedBillingSource) {
        return value;
      }
    })[0];

    let _selectedMediaPlanner = this.mediaPlannerOptionsModel[0];
    this.newopportunity.mediaPlanner = this.mediaPlannerList.length > 0 && this.mediaPlannerList.filter(function (value) {
      if (value['id'] == _selectedMediaPlanner) {
        return value;
      }
    })[0];

    //need to fix version conditions and augment success message.
    if (this.newopportunity.opportunityId == undefined) {
      this.successMessage = "Opportunity is created successfully.";
    }
    if (this.versionMode) {
      var data = this.httpService.post(url, this.newopportunity, auth_token);
    } else if (this.editMode || this.versionUpdateMode) {
      this.successMessage = "Opportunity is updated successfully.";
      var data = this.httpService.put(url, this.newopportunity, auth_token);
    } else {
      this.newopportunity.assignedBy = this.logedInUser;
      var data = this.httpService.post(url, this.newopportunity, auth_token);
    }
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          let url = PathConfig.BASE_URL_API + PathConfig.OPPORTUNITY_FILEUPLOAD;
          let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
          var oppId = data.opportunityId;
          let formData: FormData = new FormData();
          var fileObj = {
            opportunityId: oppId,
            documentDetails: []
          };
          for (var i in this.opportunityDocument) {
            formData.append('docs', this.opportunityDocument[i], this.opportunityDocument[i]['name']);
            this.documentType = this.opportunityDocument[i]['name'];
            fileObj.documentDetails.push({ "name": this.opportunityDocument[i]['name'], "type": this.documentType.split('.').pop() });
          }


          formData.append('doc_details', new Blob([JSON.stringify(fileObj)], { type: "application/json" }));

          var fileUpload = this.httpService.putFileUpload(url, formData, auth_token);
          fileUpload.subscribe(
            fileUpload => {
              if (fileUpload) {
                this.editMode = true;
                this.editOpportunity(oppId, true);
                this.isAlertMessageBoxVisible = false;
                this.showMessage(this);
                // this.backToTable();
                //this.opportunityListRefresh = true;
              }
            }, error => {
              if (error) {
                this.errorMessage = error;
                this.opportunityDocument = [];
              }
            });
        }
      }, error => {
        if (error) {
          this.errorMessage = error;
          this.loadAllDropdowns();
          this.opportunityDocument = [];
        }
      });
    return data;
  }

  showMessage(self) {
    setTimeout(function () {
      self.isAlertMessageBoxVisible = true;
    }, 3000);
  }

  resetForm() {
    this.errorMessage = "";
    this.newopportunity = new Opportunity();
    this.editMode = false;
    this.disabledVersion = false;
    this.salesCategoryOptionsModel = [];
    this.salesPersonOptionsModel = [];
    this.agencyOptionsModel = [];
    this.advertiserOptionsModel = [];
    this.billingSourceOptionsModel = [];
    this.mediaPlannerOptionsModel = [];
    this.versionOptionsModel = [];
    this.currencyModel = [];
    this.pricingModel = [];
    this.traffickerModel = [];
    this.versionOptions = [];
    this.versionOptionsModel = [];
    this.mediaPlannerOptionsModel = [];
    this.opportunityDocument = [];
    this.opportunityDocument1 = [];
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

  loadAllDropdowns() {
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let url1 = PathConfig.BASE_URL_API + PathConfig.SALESCATEGORY_GET_LIST;
    let url2 = PathConfig.BASE_URL_API + PathConfig.SALESMANAGER_GET_LIST;
    let url3 = PathConfig.BASE_URL_API + PathConfig.TRAFFICKER_GET_LIST;
    let url4 = PathConfig.BASE_URL_API + PathConfig.BILLINGSOURCE_GET_LIST;
    let url5 = PathConfig.BASE_URL_API + PathConfig.COMPANY_LIST;
    let url6 = PathConfig.BASE_URL_API + PathConfig.LOGED_IN_USER;
    let url7 = PathConfig.BASE_URL_API + PathConfig.MEDIAPLANNER_GET_LIST;

    var data1 = this.httpService.get(url1, auth_token);
    var statusData = this.httpService.get(url2, auth_token);
    var data3 = this.httpService.get(url3, auth_token);
    var data4 = this.httpService.get(url4, auth_token);
    var data5 = this.httpService.get(url5, auth_token);
    var data6 = this.httpService.get(url6, auth_token);
    var data7 = this.httpService.get(url7, auth_token);

    let self = this;
    data1.subscribe(
      data1 => {
        if (data1) {
          this.salesCategoryOptions = this.convertDataIntoDropdown(data1, 'salesCatagoryId', 'name')
          this.salesCategoryList = data1;
        }
      });

    statusData.subscribe(
      statusData => {
        if (statusData) {
          this.salesPersonOptions = this.convertDataIntoDropdownForUser(statusData, 'id', 'firstName', 'lastName');
          this.salesPersonList = statusData;
        }
      });

    data3.subscribe(
      data3 => {
        if (data3) {
          this.traffickerOptions = this.convertDataIntoDropdownForUser(data3, 'id', 'firstName', 'lastName');
          this.traffickerList = data3;
        }
      });

    data4.subscribe(
      data4 => {
        if (data4) {
          this.billingSourceOptions = this.convertDataIntoDropdown(data4, 'billingSourceId', 'name');
          this.billingSourceList = data4;
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
          this.agencyList = agencyData;
          this.advertiserOptions = this.convertDataIntoDropdown(advertiserData, 'companyId', 'name');
          this.advertiserList = advertiserData;
        }
      });

    data6.subscribe(
      data => {
        if (data) {
          this.logedInUserFullName = data.firstName + " " + data.lastName;
          this.logedInUser = data;
        }
      });

    data7.subscribe(
      data7 => {
        if (data7) {
          this.mediaPlannerOptions = this.convertDataIntoDropdownForUser(data7, 'id', 'firstName', 'lastName');
          this.mediaPlannerList = data7;
        }
      });

    this.versionOptions = [{ "id": "firstVersion", "name": 0 + "" }];
    this.versionOptionsModel[0] = "firstVersion";
  }

  onDocumentTableChange(data: any) {
    if (data['clickedOn'] == 'delete') {
      this.documentListRefresh = false;
      this.actionOnUploadFile(true, Number(data['value']), true, false);
      let self = this;
      // this.opportunityDocument1 = [];
      //   this.documentValue.nativeElement.value = "";
      setTimeout(function () {
        self.opportunityDocument1 = self.opportunityDocument;
        self.documentListRefresh = true;
      }, 5);
      //this.editLineitem(JSON.parse(data['value']));
    } else if (data['clickedOn'] == "download") {
      this.downloadDocument(data);
    }
  }

  downloadDocument(data) {

    let url = PathConfig.BASE_URL_API + "opportunity/" + this.opportunityId + "/documents/" + this.opportunityDocument1[Number(data['value'])]['opportinutyDocId'];
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data1 = this.httpService.getFile(url, auth_token);
    let fileName = this.opportunityDocument1[Number(data['value'])]['name'];
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

  actionOnUploadFile(file, index, remove, add) {
    if (add) {
      file.username = this.logedInUserFullName;
      this.opportunityDocument.push(file);
    }
    if (remove) {
      if (index == 0) {
        this.opportunityDocument.shift();
      } else {
        var temp = this.opportunityDocument.splice(index, 1);
        //this.opportunityDocument = temp;
      }
    }
  }


  fileChange(event) {
    let fileList: FileList = event.target.files;
    for (let i = 0; i < fileList.length; i++) {
      this.actionOnUploadFile(fileList[i], null, false, true);
    }
    let self = this;
    //self.opportunityDocument1 = [];
    self.documentListRefresh=false;
    setTimeout(function () {
      self.opportunityDocument1 = self.opportunityDocument;
      self.documentListRefresh=true;
    }, 5);
  }

  onChange($event) {
    if (this.pricingModel[0] == "Gross" && this.agencyOptionsModel.length) {
      this.disabledAgencyMargin = false;
    }
    else {
      this.disabledAgencyMargin = true;
    }
  }

  onVersionSelect($event) {
    console.log(" on version select.........................");
    this.selectedVersionSeq = $event[0];
    let url = PathConfig.BASE_URL_API + PathConfig.OPPORTUNITY_GET_BY_ID + this.newopportunity.opportunityId;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {

        for (var i in data.versions) {
          if (data && data.versions[i].seqNo == this.selectedVersionSeq) {
            this.newopportunity = data;
            this.opportunityId = data.opportunityId;
            this.logedInUserFullName = data.assignedBy.firstName + " " + data.assignedBy.lastName;
            this.newopportunity.name = data.versions[i].name;
            this.newopportunity.percentageOfClose = data.versions[i].percentageOfClose;
            this.newopportunity.startDate = data.versions[i].startDate;
            this.newopportunity.endDate = data.versions[i].endDate;
            this.newopportunity.dueDate = data.versions[i].dueDate;
            this.newopportunity.description = data.versions[i].description;
            this.newopportunity.seqNo = data.versions[i].seqNo;
            this.newopportunity.budget = data.versions[i].budget;
            this.newopportunity.advertiserDiscount = data.versions[i].advertiserDiscount;
            this.newopportunity.version = data.versions[i].version;
            if (data.versions[i].salesPerson != undefined) {
              this.salesPersonOptionsModel[0] = data.versions[i].salesPerson.id;
            }
            if (data.versions[i].trafficker != undefined) {
              this.traffickerModel[0] = data.versions[i].trafficker.id;
            }
            if (data.versions[i].salesCategory != undefined) {
              this.salesCategoryOptionsModel[0] = data.versions[i].salesCategory.salesCatagoryId;
            }
            if (data.versions[i].advertiser != undefined) {
              this.advertiserOptionsModel[0] = data.versions[i].advertiser.companyId;
            }
            if (data.versions[i].billingSource != undefined && data.versions[i].billingSource != null) {
              this.billingSourceOptionsModel[0] = data.versions[i].billingSource.billingSourceId;
            }

            if (data.versions[i].pricingModel == "Gross") {
              this.disabledAgencyMargin = false;
            } else {
              this.disabledAgencyMargin = true;
            }

            this.pricingModel[0] = data.versions[i].pricingModel;
            this.currencyModel[0] = data.versions[i].currency;

            if (data.versions[i].agency != undefined) {
              this.agencyOptionsModel[0] = data.versions[i].agency.companyId;
            }
            this.newopportunity.versions = data.versions[i].version;
          }
          this.versionUpdateMode = true;
        }
      });
    console.log("At the end of version select");
    return data;
  }

  submitOpportunity() {
    this.editMode = false;
    this.successMessage = "Opportunity is submitted successfully.";
    this.newopportunity.submitted = true;
    this.saveForm();
  }

  createNewProposal() {
    let addUrl = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_ADD;
    let statusUrl = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_STATUS;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    var statusData = this.httpService.get(statusUrl, auth_token);

    statusData.subscribe(
      statusData => {
        if (statusData) {
          console.log(statusData);
          for (var i in statusData) {
            if (statusData[i].name == "Draft") {
              this.newopportunity['status'] = statusData[i];
              delete this.newopportunity['versions'];
              this.newopportunity['assignTo'] = this.newopportunity.mediaPlanner;
              var data = this.httpService.post(addUrl, this.newopportunity, auth_token);
              let self = this;
              data.subscribe(
                data => {
                  if (data) {
                    console.log("New Proposal Created");
                    //  this.initiateWorkFlow(data.proposalId);
                  }
                });
              break;
            }
          }
        }
      });
  }

  initiateWorkFlow(proposalId: number) {
    console.log("inside workflow process...");
    let workflowUrl = PathConfig.BASE_URL_API + PathConfig.WORKFLOW_START;
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    this.workflowProposal.proposalId = proposalId;

    var workflowData = this.httpService.post(workflowUrl, this.workflowProposal, auth_token);
    let self = this;

    workflowData.subscribe(
      workflowData => {
        if (workflowData) {
          let processId = workflowData.id;
          console.log("Process Id" + processId);
          let findTaskUrl = PathConfig.BASE_URL_API + PathConfig.FIND_TASK_ID + processId + "/task/candidateuser/" + this.logedInUser.id;
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
                /*        let assignTaskUrl = PathConfig.BASE_URL_API + PathConfig.ASSIGN_TASK + processId + "/assignTask/" + this.taskId + "/" + this.logedInUser.id;
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
                          }); */
              }
            }, error => {
              if (error) {
                console.log("error in task id(candidate user)..");
              }
            });

        }
      }, error => {
        if (error) {
          console.log("Error in workflow creation..");
        }
      });
  }
}