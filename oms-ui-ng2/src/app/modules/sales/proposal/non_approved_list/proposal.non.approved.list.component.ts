import {
  Component, OnInit, AfterViewInit, ElementRef, HostListener
} from '@angular/core';
import { HttpService } from '../../../../common/services/http.service';
import { PathConfig } from '../../../../common/config/path.config';
import { ConstantConfig } from '../../../../common/config/constant.config';
import { LocalStorageService } from "../../../../common/services/local-storage.service";
import { Broadcaster } from '../../../../common/services/broadcaster.service';
import { AuthService } from '../../../../common/services/auth.service';
import { Proposal } from '../../../../common/models/proposal';
import { MdDialog, MdDialogRef } from '@angular/material';
import { ConfirmDialogComponent } from "../../../../common/modules/dialog/confirm/confirm.component";
import { DateFormat } from "../../../../common/modules/date.format";
import { IMultiSelectOption } from "angular-2-dropdown-multiselect";
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { IMultiSelectTexts } from "angular-2-dropdown-multiselect";
import { AdUnit } from '../../../../common/models/ad.unit';
import { ProposalLineItem } from '../../../../common/models/proposal.lineitem';
import { AudienceTargetType } from '../../../../common/models/audience.target.type';
import { AudienceTargetValues } from '../../../../common/models/audience.target.values';
import { Router, ActivatedRoute, Params } from "@angular/router";

import { DropDownDataFilter } from '../../../../common/filter/dropdown.filter';

declare var $: any;

@Component({
  templateUrl: './proposal.non.approved.list.component.html',
  styleUrls: ['./../proposal.component.scss'],
})
export class ProposalNonApprovedListComponent implements OnInit {
  @HostListener('document:click', ['$event'])
  clickout(event) {
    if (this.elementRef.nativeElement.contains(event.target)) {
      if (event.target.getAttribute('class') != 'mat-datepicker-toggle') {
        if (event.target.offsetParent.getAttribute('class') !== 'date-picker-container' && event.target.offsetParent.offsetParent.getAttribute('class') !== 'date-picker-container') {
          if (this.newProposalLineItem['isNewEndDateOpen'] == true) {
            this.newProposalLineItem['isNewEndDateOpen'] = !this.newProposalLineItem['isNewEndDateOpen'];
          }
          if (this.newProposalLineItem['isNewStartDateOpen'] == true) {
            this.newProposalLineItem['isNewStartDateOpen'] = !this.newProposalLineItem['isNewStartDateOpen'];
          }
        }
      }


    }
  }

  showForm: Boolean = false;
  showLineItemForm: Boolean = false;

  proposalListDTConfig: Object = {};
  proposalListRefresh: Boolean = false;
  proposalListSearchModel: any = {};
  newProposal: Proposal = new Proposal();
  editMode: Boolean = false;
  //audienceTargetValues: Array<Object> = [{ "id": 1, "label": 'Lorem Ipsum' }, { "id": 1, "label": 'Lorem Ipsum', "isSelected": true }, { "id": 1, "label": 'Lorem Ipsum' }, { "id": 1, "label": 'Lorem Ipsum' }, { "id": 1, "label": 'Lorem Ipsum' }]
  audienceTargetValues: Array<Object>;
  selectedTargetTypeList = [];
  targetTypeList: Array<Object> = [];
  myModelListConfig: Object;
  myModelListRefresh: Boolean;
  productOptionsModel: number[];
  productList: Array<Object> = [];
  productOptions: IMultiSelectOption[];
  searchSettings: IMultiSelectSettings;
  singleSelectSettings: IMultiSelectSettings;
  myTexts: IMultiSelectTexts;
  adUnitList: Array<AdUnit> = [];
  disabledFormElements: Boolean = true;
  adUnitOptions: IMultiSelectOption[];
  adUnitOptionsModel: number[];
  newProposalLineItem: ProposalLineItem = new ProposalLineItem();
  newAudienceTargetType: AudienceTargetType = new AudienceTargetType();
  audienceTargetValuesList: Array<AudienceTargetValues> = [];
  newAudienceTargetValues: AudienceTargetValues = new AudienceTargetValues();
  targetTypeConfig: Object;

  showAudienceTargetValuesForm: Boolean = false;
  seletedAudienceTargetValuesArr: Array<Object>

  specTypeList: Array<Object> = [];
  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private router: Router,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService,
    private dialog: MdDialog,
    private elementRef: ElementRef,
    private activatedRouter: ActivatedRoute,

  ) { }

  ngOnInit() {
    this.loadAllProducts();
    this.searchSettings = {
      enableSearch: true,
      dynamicTitleMaxItems: 3,
      displayAllSelectedText: true
    };

    this.singleSelectSettings = {
      selectionLimit: 1,
      autoUnselect: true,
      dynamicTitleMaxItems: 1,
      displayAllSelectedText: true,
      closeOnSelect: true
    };

    this.myTexts = {
      checkAll: 'Select all',
      uncheckAll: 'Unselect all',
      checked: 'item selected',
      checkedPlural: 'items selected',
      searchPlaceholder: 'Find',
      defaultTitle: 'Select',
      allSelected: 'All selected',
    };
    this.proposalListDTConfig = {
      "columnDefs": [
        {
          "className": "table-inline-a",
          "targets": 0,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              template = "<div class='col-breadcrumb'><div><a href='javascript:void(0);' class='proposal-name' data-name='edit' data-custom='" + full.proposalId + "'>" + full.name + "</a></div>" +
                "<div class='sub-heading'>(" + full.proposalId + ")</div></div>";
              //template = '<span><a href="javascript:void(0);" data-name="edit" data-custom="' + full.proposalId + '">' + full.name + "</a> >(" + full.proposalId + ")" + '</span>';
            }
            return template;
          }
        },
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
          "targets": 8,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              template = '<span>Yes</span>';
            } else {
              template = '<span>No</span>';
            }
            return template;
          }
        },
        {
          "className": "text-center",
          "orderable": false,
          "targets": 11,
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
        { "title": 'Status', "data": "status.name" },
        { "title": 'Salesperson', "data": "salesPerson" },
        { "title": 'Sales Planner', "data": "salesPerson" },
        { "title": "Start Date", "data": "startDate" },
        { "title": "End Date", "data": "endDate" },
        { "title": "Due Date", "data": "dueDate" },
        { "title": 'Budget', "data": "budget" },
        { "title": "Advertiser", "data": "opportunity.advertiser.name" },
        { "title": "Advertiser Discount", "data": "advertiserDiscount" },
        { "title": "Submitted", "data": "submitted" },
        { "title": '', "data": "proposalId" }
      ],
      "apiURL": PathConfig.BASE_URL_API + PathConfig.PROPOSAL_LIST
    }


    this.myModelListConfig = {
      "processing": false,
      "serverSide": false,
      "dom": 'tr',
      "columnDefs": [
        {
          "orderable": false,
          "targets": 2,
          "render": function (data, type, full, meta) {
            var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
              '<a href="javascript:void(0);" data-name="edit" data-custom="' + data + '"><span class="fa fa-pencil" aria-hidden="true"></span>Edit</a>' +
              "<a href='javascript:void(0);' data-name='delete' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>" +
              '</div>';
            return template;
          }
        }],
      "columns": [
        { "title": 'Target Type', "data": "name" },
        { "title": 'Elements', "data": "opportunity" },
        { "title": '', "data": "id" }
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
  }

  newRowsAdded = [];

  // on Menu Selection
  onProposalTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'edit') {
      //edit code here
      this.editProposal(data['value']);

    } else if (data['clickedOn'] == 'delete') {
      //delete code here
      //confirm box
      let dialogRef = this.dialog.open(ConfirmDialogComponent);
      this.proposalListRefresh = false;
      dialogRef.afterClosed().subscribe(result => {
        if (result == "OK") {
          this.deleteproposalOnConfirm(JSON.parse(data['value']));
          this.proposalListRefresh = true;
        }
      });
    }

  }

  gotoAddForm() {
    this.router.navigate(['proposals/crud']);
  }
  //back to table button
  backToTable() {
    this.showForm = false;
    this.proposalListRefresh = true;
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
    { "id": 6, "labelName": "Submitted" }
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
          this.newProposal = new Proposal();
        }
      });
    return data;
  }





  // save the form
  saveForm() {
    let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_ADD;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    if (this.editMode) {
      var data = this.httpService.put(url, this.newProposal, auth_token);
    } else {
      var data = this.httpService.post(url, this.newProposal, auth_token);
    }
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.backToTable();
          this.proposalListRefresh = true;
        }
      });
    this.newProposal = new Proposal();
    return data;

  }



  resetForm() {
    this.newProposal = new Proposal();
    this.editMode = false;
  }


  // line item code by vikas parashar

  loadAllProducts() {
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let url = PathConfig.BASE_URL_API + PathConfig.PRODUCT_GET_LIST;
    var data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.productList = data;
          this.productOptions = DropDownDataFilter.convertDataIntoDropdown(this.productList, 'productId', 'name');
          let selectedValue = this.productList.filter(function (value) { if (value['isSelected']) { return value; } });
          if (selectedValue.length > 0) {
            this.productOptionsModel = selectedValue.map(function (value) { if (value['isSelected'] == true) return value['productId'] });
          } else {
            this.productOptionsModel = [];
          }
        }
      });
    return data;
  }



  onProductChange() {
    var _selectedProductTypeId = this.productOptionsModel[0];
    let _selectedProductTypeList = this.productList.filter(function (value) {
      if (value['productId'] == _selectedProductTypeId) {
        return value;
      }
    })[0];
    this.adUnitList = _selectedProductTypeList['adUnits'];
    if (this.adUnitList.length > 0) {
      this.disabledFormElements = false;
    } else {
      this.disabledFormElements = true;

    }
    this.adUnitOptions = DropDownDataFilter.convertDataIntoDropdown(this.adUnitList, 'adUnitId', 'name');
    let selectedValue = this.adUnitList.filter(function (value) { if (value['isSelected']) { return value; } });
    if (selectedValue.length > 0) {
      this.adUnitOptionsModel = selectedValue.map(function (value) { if (value['isSelected'] == true) return value['adUnitId'] });
    } else {
      this.adUnitOptionsModel = [];
    }
  }







  convertDataIntoDropdown(array, propId, propLabel) {
    let newArr = array.map(function (val, index) {
      return { 'id': val[propId], 'name': val[propLabel] };
    });
    return newArr;

  }





  convertDataTargetTypeValuesDropdown(array, propId, propLabel, isSelected) {
    let newArr = array.map(function (val, index) {
      return { 'id': val[propId], 'label': val[propLabel], 'isSelected': isSelected };
    });
    return newArr;

  }


  // select Audience Target Values
  onAudienceTargetValuesSelect(seletedAudienceTargetValuesArr: Array<Object>) {
    this.seletedAudienceTargetValuesArr = seletedAudienceTargetValuesArr;


  }

  addTargetType() {
    if (this.seletedAudienceTargetValuesArr.length > 0) {
      let _selectedAudienceTargetValuesId = this.seletedAudienceTargetValuesArr[0]['id'];
      let seletedTargetTypeName = "";

      // find targettype name, for showin in grid
      for (var i in this.targetTypeList) {
        for (var j in this.targetTypeList[i]['audienceTargetValues']) {
          if (this.targetTypeList[i]['audienceTargetValues'][j]['id'] === _selectedAudienceTargetValuesId) {
            seletedTargetTypeName = this.targetTypeList[i]['name'];
            this.newAudienceTargetType.targetTypeId = this.targetTypeList[i]['targetTypeId']
            break;
          }
        }
      }

      let targetTypeAudienceValues = "";
      this.audienceTargetValuesList = [];
      for (var i in this.seletedAudienceTargetValuesArr) {
        // push values because we need id for edit.
        this.newAudienceTargetValues = new AudienceTargetValues();
        this.newAudienceTargetValues.id = this.seletedAudienceTargetValuesArr[i]['id'];
        this.newAudienceTargetValues.value = this.seletedAudienceTargetValuesArr[i]['label'];
        this.audienceTargetValuesList.push(this.newAudienceTargetValues);

        // find target type audience values , for showin in grid with ...(dots)
        if (this.seletedAudienceTargetValuesArr.length === 1) {
          targetTypeAudienceValues += this.seletedAudienceTargetValuesArr[i]['label'];
        } else {
          targetTypeAudienceValues += this.seletedAudienceTargetValuesArr[i]['label'] + ", ";
        }
        if (targetTypeAudienceValues.length > 25) {
          targetTypeAudienceValues += " ...";
          break;
        }
      }
      this.newAudienceTargetType.tempId = this.selectedTargetTypeList.length;
      // remove, if user crud same data for targettype
      for (var i in this.selectedTargetTypeList) {
        if (this.selectedTargetTypeList[i]['name'] === seletedTargetTypeName) {
          this.newAudienceTargetType.tempId = this.selectedTargetTypeList[i]['tempId'];
          this.selectedTargetTypeList.splice(this.selectedTargetTypeList[i]['tempId'], 1);
        }
      }


      this.newAudienceTargetType.name = seletedTargetTypeName;
      this.newAudienceTargetType.targetTypeAudienceName = targetTypeAudienceValues;
      this.newAudienceTargetType.audienceTargetValues = this.audienceTargetValuesList;
      this.selectedTargetTypeList.push(this.newAudienceTargetType);
      this.newAudienceTargetType = new AudienceTargetType();
      this.newRowsAdded = [];
      let self = this;
      setTimeout(function () {
        self.newRowsAdded = self.selectedTargetTypeList;
      }, 10);
    }
  }

  onTargetTypeMenuSelect(data) {
    if (data['clickedOn'] == 'edit') {
      //edit code here
      // this.editTargetType(JSON.parse(data['value']));

    } else if (data['clickedOn'] == 'delete') {
      //delete code here
      //confirm box
      let dialogRef = this.dialog.open(ConfirmDialogComponent);
      //this.proposalListRefresh = false;
      dialogRef.afterClosed().subscribe(result => {
        if (result == "OK") {
          this.deleteTargetType(JSON.parse(data['value']));
          //this.proposalListRefresh = true;
        }
      });
    }
  }

  /*editTargetType(targetType) {
    this.newProposalLineItem.targetTypeOptionsModel[0] = targetType.targetTypeId;
    var _selectedTargetTypeId = this.newProposalLineItem.targetTypeOptionsModel[0];
    let _selectedTargetType = this.targetTypeList.filter(function (value) {
      if (value['targetTypeId'] == _selectedTargetTypeId) {
        return value;
      }
    })[0];
    this.audienceTargetValues = DropDownDataFilter.convertDataTargetTypeValuesDropdown(_selectedTargetType['audienceTargetValues'], 'id', 'value', false);
    for (var i in this.audienceTargetValues) {
      for (var j in targetType.audienceTargetValues) {
        if (this.audienceTargetValues[i]['id'] === targetType.audienceTargetValues[j]['id']) {
          this.audienceTargetValues[i]['isSelected'] = true;
        }
      }
    }
    //this.audienceTargetValues = DropDownDataFilter.convertDataTargetTypeValuesDropdown(targetType.audienceTargetValues, 'id', 'value', true);
  }*/

  deleteTargetType(targetType) {
    for (var i in this.selectedTargetTypeList) {
      if (this.selectedTargetTypeList[i]['tempId'] === targetType.tempId) {
        this.selectedTargetTypeList.splice(this.selectedTargetTypeList[i]['tempId'], 1);
      }
      this.selectedTargetTypeList[i]['tempId'] = parseInt(i);
    }
    this.newRowsAdded = [];
    let self = this;
    setTimeout(function () {
      self.newRowsAdded = self.selectedTargetTypeList;
    }, 10);
  }

  private startDateChanged(newDate) {
    this.newProposalLineItem.flightStartdate = new Date(newDate); //this.getFormatDate(newDate);
  }

  private endDateChanged(newDate) {
    this.newProposalLineItem.flightEndDate = new Date(newDate); //this.getFormatDate(newDate);
  }

  /*addNewProposal() {
    this.router.navigate(['dashboard/sales/proposal/add'], { queryParams: { callback: 'addProposal' } });
  }*/


  editProposal(id) {
    this.router.navigate(['dashboard/sales/non-approved-proposal/edit'], { queryParams: { callback: 'addProposal', proposalId: id } });
  }

}
