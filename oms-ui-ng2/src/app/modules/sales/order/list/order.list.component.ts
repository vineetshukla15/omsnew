import {
  Component, OnInit, AfterViewInit, HostListener, ElementRef, ChangeDetectorRef, ViewChild
} from '@angular/core';
import { HttpService } from '../../../../common/services/http.service';
import { PathConfig } from '../../../../common/config/path.config';
import { ConstantConfig } from '../../../../common/config/constant.config';
import { LocalStorageService } from "../../../../common/services/local-storage.service";
import { Order } from '../../../../common/models/order';
import { MdDialog, MdDialogRef } from '@angular/material';
import { ConfirmDialogComponent } from "../../../../common/modules/dialog/confirm/confirm.component";
import { Router } from "@angular/router";
import { DateFormatterComponent } from "../../../../common/utils/date-format.component";
import { CalendarService } from "../../../../common/services/calendar.service";
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { IMultiSelectOption } from "angular-2-dropdown-multiselect";
import { DropDownSetting } from '../../../../common/utils/dropdown.settings';
import * as _ from 'lodash';
declare var $: any;

@Component({
  templateUrl: './order.list.component.html',
  styleUrls: ['./../order.component.scss'],
})
export class OrderListComponent implements OnInit {

  orderListDTConfig: Object = {};
  orderListRefresh: Boolean = false;
  orderListSearchModel: any = {};
  newOrder: Order = new Order();
  filterHeight: number = 0;
  filters: Boolean = false;

  permissions = null;
  isOrderViewPermission: boolean = false;
  isOrderDeletePermission: boolean = false;
  singleSelectSettings: IMultiSelectSettings;

  advanceForm: FormGroup;
  isRegenerate: boolean = true;

  //Calendar settings starts
  //Caledar array. It is declared in constructor.
  calendarArr = null;
  clickOnDatepicker = null;
  calendarBtnClick = null;
  resetAllCalendar = null;
  //Calendar settings end

  advanceSearchModelOBj = {
    searchCreatedDate: null,
    searchEndDate: null,
    searchDueDate: null,
  };

  advertiserOptions: IMultiSelectOption[];
  agencyOptions: IMultiSelectOption[];
  salesCategoryOptions: IMultiSelectOption[];

  @HostListener('document:click', ['$event'])
  clickout(event) {
    this.resetAllCalendar(this);
  }

  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private router: Router,
    private localStorage: LocalStorageService,
    private dialog: MdDialog,
    private fb: FormBuilder,
    private calendarService: CalendarService,
  ) {
    this.createAdvanceForm();

    // You have to declare how many calendar want to use in this component
    this.calendarArr = {
      'isStartDateOpen': false,
      'isEndDateOpen': false,
      'isNewCreatedDateOpen': false,
      'isDueDateOpen': false
    };
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

  getNameColumnTemp(data, full) {
    let tempStr = ''

    if (this.isOrderViewPermission) {
      tempStr = "<a href='javascript:void(0);' class='proposal-name' data-name='view' data-custom='" + full.orderId + "'>" + full.name + "</a>";
    } else {
      tempStr = "<div>" + full.name + "</div>";
    }

    return tempStr;
  }

  getViewTemp(data, full) {
    let tempStr = ''

    if (this.isOrderViewPermission) {
      tempStr = '<a href="javascript:void(0);" data-name="view" data-custom="' + data + '"><span class="fa fa-pencil" aria-hidden="true"></span>Edit</a>';
    } else {
      tempStr = '<div><span class="fa fa-pencil" aria-hidden="true"></span>Edit</div>';
    }

    return tempStr;
  }

  getDeleteTemp(data, full) {
    let tempStr = ''

    if (this.isOrderDeletePermission) {
      tempStr = "<a href='javascript:void(0);' data-name='delete' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>";
    } else {
      tempStr = "<div><span class='fa fa-trash' aria-hidden='true'></span>Delete</div>";
    }

    return tempStr;
  }



  ngOnInit() {

    let self = this;

    this.loadAllDropdowns();

    this.permissions = this.localStorage.get('user_permissions');

    if (this.permissions && this.permissions.length) {
      this.isOrderViewPermission = this.permissions.indexOf('view_order') > -1;
      this.isOrderDeletePermission = this.permissions.indexOf('delete_order') > -1;
    }

    this.clickOnDatepicker = this.calendarService.clickOnDatepicker(this);
    this.calendarBtnClick = this.calendarService.calendarBtnClick(this);
    this.resetAllCalendar = this.calendarService.resetAllCalendar(this);

    this.singleSelectSettings = DropDownSetting.singleSelectSettings;

    this.createTable();
  }

  createTable() {
    if (!this.isRegenerate) {
      this.isRegenerate = true;
    }

    let self = this;

    this.orderListDTConfig = {
      "columnDefs": [
        {
          "className": "table-inline-a",
          "targets": 0,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              template = "<div class='col-breadcrumb'><div>" + self.getNameColumnTemp(data, full) + "</div>" +
                "<div class='sub-heading'>(" + full.orderId + ")</div></div>";              
            }
            return template;
          }
        },
        {
          "targets": 1,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              var startDate = new DateFormatterComponent().changeDateFormatTo_dd_mm_yy(data, 'dd-MMM-yyyy');
              var endDate = new DateFormatterComponent().changeDateFormatTo_dd_mm_yy(full.endDate, 'dd-MMM-yyyy');
              template = '<span>' + startDate + " to " + endDate + '</span>';
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
          "className": "text-center",
          "orderable": false,
          "targets": 4,
          "render": function (data, type, full, meta) {
            var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
              self.getViewTemp(data, full) +
              self.getDeleteTemp(data, full) +
              '</div>';
            return template;
          }
        }],
      "columns": [
        { "title": 'Name', "data": "name" },
        { "title": "Flight", "data": "startDate" },        
        { "title": "Advertiser", "data": "advertiser.name" },        
        { "title": "VPZ Campaign Id", "data": "vpzCampaignId" },
        { "title": '', "data": "orderId" }
      ],      

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
        url: PathConfig.BASE_URL_API + PathConfig.ORDER_LIST
      }
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
  onOrderTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'view') {
      //edit code here
      this.viewOrder(data['value']);

    } else if (data['clickedOn'] == 'delete') {
      //delete code here
      //confirm box
      let dialogRef = this.dialog.open(ConfirmDialogComponent);
      this.orderListRefresh = false;
      dialogRef.afterClosed().subscribe(result => {
        if (result == "OK") {
          this.deleteOrderOnConfirm(JSON.parse(data['value']));
          this.orderListRefresh = true;
        }
      });
    }
  }

  //search dropdown list
  orderSearchTableDD: Array<Object> = [
    { "id": 0, "labelName": "Name" },
    { "id": 1, "labelName": "Status" },
    { "id": 2, "labelName": "Sales Planner" },
    { "id": 3, "labelName": "Start Date" },
    { "id": 4, "labelName": "End Date" },
    { "id": 5, "labelName": "Due Date" },
    { "id": 6, "labelName": "Budget" },
    { "id": 7, "labelName": "Advertiser" },
    { "id": 8, "labelName": "Agency" }
  ];
  //order dropdown Event
  orderSearchEvent(data) {
    this.advanceForm.reset();
    this.filterHeight = 0;
    this.orderListSearchModel['searchTxt'] = data['text'];
    this.orderListSearchModel['colIndex'] = data['dd-id'];
  }

  // delete order
  orderObjToDelete: any;
  deleteOrderOnConfirm(obj) {
    let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_DELETE + obj.orderId;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.delete(url, obj, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.orderObjToDelete = null;
          this.newOrder = new Order();
          this.orderListRefresh = true;
        }
      });
    return data;
  }

  //Load drop down for advance serach filter
  loadAllDropdowns() {
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let url1 = PathConfig.BASE_URL_API + PathConfig.SALESCATEGORY_GET_LIST;
    let url5 = PathConfig.BASE_URL_API + PathConfig.COMPANY_LIST;

    var data1 = this.httpService.get(url1, auth_token);
    var data5 = this.httpService.get(url5, auth_token);

    let self = this;
    data1.subscribe(
      data1 => {
        if (data1) {
          this.salesCategoryOptions = this.convertDataIntoDropdown(data1, 'salesCatagoryId', 'name')
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
          this.advertiserOptions = this.convertDataIntoDropdown(advertiserData, 'companyId', 'name');
        }
      });
  }

  convertDataIntoDropdown(array, propId, propLabel) {
    let newArr = array.map(function (val, index) {
      return { 'id': val[propId], 'name': val[propLabel] };
    });
    return newArr;
  }
  //End of drop down for advance serach filter

  viewOrder(id) {
    this.router.navigate(['dashboard/sales/order/view'], { queryParams: { callback: 'editProposal', proposalId: id } });
  }

  copyProposal(proposalId) {
    let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_GET_BY_ID + proposalId;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.newOrder = data;
          this.saveCopiedProposal();
        }
      });
    return data;
  }

  saveCopiedProposal() {
    let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_ADD;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);

    delete this.newOrder['proposalId'];
    delete this.newOrder['proposalAudits'];
    delete this.newOrder['documents'];

    var data = this.httpService.post(url, this.newOrder, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.orderListRefresh = true;
        }
      }, error => {
        if (error) {
          console.log(error);
        }
      });
    return data;
  }

  addProposalVersion(proposalId) {
    console.log("New version....");
    this.router.navigate(['dashboard/sales/proposal/edit'], { queryParams: { callback: 'editProposal', proposalId: proposalId, newVersion: 'yes' } });
  }
}
