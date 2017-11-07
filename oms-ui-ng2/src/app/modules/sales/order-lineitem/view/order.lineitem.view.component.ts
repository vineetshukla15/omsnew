import {
  Component, OnInit, AfterViewInit, ElementRef, HostListener, ChangeDetectorRef, Input
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
import { Product } from '../../../../common/models/product';
import { SpecType } from '../../../../common/models/spec.type';
import { ProposalLineItem } from '../../../../common/models/proposal.lineitem';
import { AudienceTargetType } from '../../../../common/models/audience.target.type';
import { AudienceTargetValues } from '../../../../common/models/audience.target.values';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { DropDownDataFilter } from '../../../../common/filter/dropdown.filter';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import * as _ from 'lodash';
declare var $: any;

@Component({
  templateUrl: './order.lineitem.view.component.html',
  styleUrls: ['./../order.lineitem.component.scss'],
})
export class OrderLineitemViewComponent implements OnInit {
  @Input() disable: boolean = true;
  showForm: Boolean = false;
  newProposal: Proposal = new Proposal();
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

  targetTypeList: Array<Object> = [];
  selectedTargetTypeList = [];
  deliveryList: Array<Object> = [];
  displayCreativeList: Array<Object> = [];
  rotateCreativeList: Array<Object> = [];
  audienceTargetValues: Array<AudienceTargetValues>;
  newAudienceTargetType: AudienceTargetType = new AudienceTargetType();
  audienceTargetValuesList: Array<AudienceTargetValues> = [];
  newAudienceTargetValues: AudienceTargetValues = new AudienceTargetValues();
  targetTypeConfig: Object;
  showAudienceTargetValuesForm: Boolean = false;
  seletedAudienceTargetValuesArr: Array<Object>
  targetTypeOptions: IMultiSelectOption[];
  targetTypeOptionsModel: number[];
  deliveryOptions: IMultiSelectOption[];
  deliveryOptionsModel = [];
  displayCreativeOptions: IMultiSelectOption[];
  displayCreativeOptionsModel = [];
  rotateCreativeOptions: IMultiSelectOption[];
  rotateCreativeOptionsModel = [];
  priorityList: IMultiSelectOption[];
  priorityValues: Array<Object> = [];
  priorityOptionsModel = [];

  newRowsAdded = [];
  priceTypeOptions: IMultiSelectOption[];
  priceTypeOptionsModel = [];
  priceTypeTypeList: Array<Object> = [{
    "id": 1,
    "name": "STANDARD"
  }, {
    "id": 2,
    "name": "SPONSORSHIP"
  }];

  specTypeOptions: IMultiSelectOption[];
  specTypeOptionsModel: number[];
  audienceTargetType: AudienceTargetType = new AudienceTargetType();
  rateCardPrice: number;
  specTypeList: Array<Object> = [];
  errorMessage: string;
  disabledTargetType = true;

  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService,
    private dialog: MdDialog,
    private router: Router,
    private activatedRouter: ActivatedRoute,
    private elementRef: ElementRef,
    private cdr: ChangeDetectorRef

  ) {


  }

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

  loadProposalData() {
    let param = this.activatedRouter.snapshot.queryParams["proposalItem"];
    this.newProposal = JSON.parse(param);
    let lienItemId = this.activatedRouter.snapshot.queryParams["lineitemId"];
    if (lienItemId !== undefined) {
      let _selectedLineitem = this.newProposal.lineItems.filter(function (value) {
        if (value['lineItemId'] == lienItemId) {
          return value;
        }
      })[0];
      this.newProposalLineItem = _selectedLineitem;
      this.populateLineitemData();
    } else {
      let tempId = this.activatedRouter.snapshot.queryParams["tempId"];
      this.newProposalLineItem.name = "";
      if (tempId !== undefined) {
        let _selectedLineitem = this.newProposal.lineItems.filter(function (value) {
          if (value['tempId'] == tempId) {
            return value;
          }
        })[0];
        this.newProposalLineItem = _selectedLineitem;
        this.populateLineitemData();
      }

    }
  }



  //back to table button
  backToTable() {
    this.showForm = false;
    let param = this.activatedRouter.snapshot.queryParams["callback"];
    if (this.newProposalLineItem.lineItemId === undefined)
      if (param === 'addProposal') {
        this.router.navigate(['dashboard/sales/non-approved-proposal/add'], { queryParams: { callback: 'addProposal', proposalItem: JSON.stringify(this.newProposal) } });
      } else {
        this.router.navigate(['dashboard/sales/non-approved-proposal/add'], { queryParams: { proposalItem: JSON.stringify(this.newProposal) } });
      } else {
      if (param === 'addProposal') {
        this.router.navigate(['dashboard/sales/order/view'], { queryParams: { callback: 'addProposal', proposalItem: JSON.stringify(this.newProposal) } });
      } else {
        this.router.navigate(['dashboard/sales/order/view'], { queryParams: { proposalItem: JSON.stringify(this.newProposal) } });
      }
    }
  }



  // save the form
  saveForm(formData: any) {
    this.addRequiredValidatorRunTime(formData);
    if (this.specTypeOptionsModel !== undefined && this.specTypeOptionsModel.length > 0) {
      let specType = new SpecType();
      specType.spec_Id = this.specTypeOptionsModel[0];
      this.newProposalLineItem.specType = specType;
    }
    if (this.priceTypeOptionsModel.length > 0) {
      var _selectedPriceTypeId = this.priceTypeOptionsModel[0];
      let _selectedPriceType = this.priceTypeOptions.filter(function (value) {
        if (value['id'] == _selectedPriceTypeId) {
          return value;
        }
      })[0];
      this.newProposalLineItem.type = _selectedPriceType['name'];
    }
    if (formData.valid) {
      this.updateProductObject(null);
      if (this.newProposalLineItem.lineItemId === undefined) {
        if (this.newProposalLineItem.tempId === undefined) {
          this.newProposalLineItem.tempId = this.newProposal.lineItems.length;
          this.newProposal.lineItems.push(this.newProposalLineItem);
        }
      }
      this.backToTable();
    } else {
      this.errorMessage = "Please fill in required fields";
    }
  }

  updateProductObject(type) {
    if (this.productOptionsModel.length > 0) {
      if (type === null) {
        let product = new Product();
        product.productId = this.productOptionsModel[0];
        this.newProposalLineItem.product = product;
        if (this.adUnitOptionsModel.length > 0) {
          this.newProposalLineItem.product.adUnits = this.adUnitOptionsModel;
        }
        delete this.newProposalLineItem.product.creatives;
      } else {
        var _selectedProductId = this.productOptionsModel[0];
        let _selectedProduct = this.productList.filter(function (value) {
          if (value['productId'] == _selectedProductId) {
            return value;
          }
        })[0];
        this.newProposalLineItem.product = _selectedProduct;
        //if (this.adUnitOptionsModel.length > 0) {
        //   this.newProposalLineItem.product.adUnits = this.adUnitOptionsModel;
        //}
      }
    }
  }

  updateLineitem() {
    this.newProposalLineItem.target = [];
    this.rateCardPrice = this.newProposalLineItem.proposedCost;
    var startDate = new Date(this.newProposalLineItem.flightStartdate);
    var startDateMilliseconds = startDate.getTime();

    var endDate = new Date(this.newProposalLineItem.flightEndDate);
    var endDateMilliseconds = endDate.getTime();

    this.newProposalLineItem.flightStartdate = startDateMilliseconds;
    this.newProposalLineItem.flightEndDate = endDateMilliseconds;


    for (var i in this.newRowsAdded) {
      let targetTypeId = this.newRowsAdded[i].targetTypeId;
      let audienceTargetType = { 'targetTypeId': targetTypeId };
      let audienceTargetValues = this.newRowsAdded[i].audienceTargetValues;
      for (let j in audienceTargetValues) {
        delete audienceTargetValues[j]['value'];
      }
      var targetTypeValue = { 'audienceTargetType': audienceTargetType, 'audienceTargetValues': audienceTargetValues };
      this.newProposalLineItem.target.push(targetTypeValue);
    }

    //delete this.newProposalLineItem['lineItemCost'];
    delete this.newProposalLineItem['price'];
    delete this.newProposalLineItem['proposal'];
    delete this.newProposalLineItem['isNewProposalLineItemEndDateOpen'];
    delete this.newProposalLineItem['isNewProposalLineItemStartDateOpen'];

  }

  resetForm() {
    this.newProposalLineItem = new ProposalLineItem();
    this.targetTypeOptionsModel = [];
    this.productOptionsModel = [];
  }



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
          this.loadAllSpecType();

        }
      });
    return data;
  }



  onProductChange(event) {
    if (this.productOptionsModel !== undefined && this.productOptionsModel.length > 0) {
      var _selectedProductTypeId = this.productOptionsModel[0];
      let _selectedProductTypeList = this.productList.filter(function (value) {
        if (value['productId'] == _selectedProductTypeId) {
          return value;
        }
      })[0];
      if (_selectedProductTypeList !== undefined) {
        this.adUnitList = _selectedProductTypeList['adUnits'];
        this.adUnitOptions = DropDownDataFilter.convertDataIntoDropdown(this.adUnitList, 'adUnitId', 'name');
        let selectedValue = this.adUnitList.filter(function (value) { if (value['isSelected']) { return value; } });
        this.adUnitOptionsModel = [];
        for (var i in this.adUnitOptions) {
          this.adUnitOptionsModel[i] = this.adUnitOptions[i]['id'];
        }

        /*if (selectedValue.length > 0) {
          this.adUnitOptionsModel = selectedValue.map(function (value) { if (value['isSelected'] == true) return value['adUnitId'] });
        } else if (this.newProposalLineItem.product !== undefined && this.newProposalLineItem.product.adUnits !== undefined) {
          if (this.newProposalLineItem.product.adUnits[0].name === undefined) {
            this.adUnitOptionsModel = this.newProposalLineItem.product.adUnits;
          } else {
            this.adUnitOptionsModel = [];
            for (var i in this.newProposalLineItem.product.adUnits) {
              this.adUnitOptionsModel[i] = this.newProposalLineItem.product.adUnits[i]['adUnitId'];
            }
          }
        } else {
          this.adUnitOptionsModel = [];
        }*/
      }
    }
  }

  loadAllTargetType() {
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let url = PathConfig.BASE_URL_API + PathConfig.TARGET_TYPE_LIST;
    var data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.targetTypeList = data;
          this.targetTypeOptions = DropDownDataFilter.convertDataIntoDropdown(data, 'targetTypeId', 'name');
          let selectedValue = data.filter(function (value) { if (value['isSelected']) { return value; } });
          if (selectedValue.length > 0) {
            this.targetTypeOptionsModel = selectedValue.map(function (value) { if (value['isSelected'] == true) return value['targetTypeId'] });
          } else {
            this.targetTypeOptionsModel = [];
          }
        }
        this.loadAllDeliveryInfo();
      });
    return data;
  }

  loadAllDeliveryInfo() {
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let deliveryImpressionUrl = PathConfig.BASE_URL_API + PathConfig.DELIVERY_IMPRESSIONS_GET;
    let displayCreativeUrl = PathConfig.BASE_URL_API + PathConfig.DISPLAY_CREATIVES_GET;
    let rotateCreativeUrl = PathConfig.BASE_URL_API + PathConfig.ROTATE_CREATIVES_GET;
    let priorityUrl = PathConfig.BASE_URL_API + PathConfig.PRIORITY_GET_LIST;

    var deliveryImpressionData = this.httpService.get(deliveryImpressionUrl, auth_token);
    var displayCreativeData = this.httpService.get(displayCreativeUrl, auth_token);
    var rotateCreativeData = this.httpService.get(rotateCreativeUrl, auth_token);
    var priorityData = this.httpService.get(priorityUrl, auth_token);

    let self = this;
    deliveryImpressionData.subscribe(
      deliveryImpressionData => {
        if (deliveryImpressionData) {
          this.deliveryList = deliveryImpressionData;
          this.deliveryOptions = this.convertArrayIntoDropdown(deliveryImpressionData);
          this.deliveryOptionsModel[0] = this.deliveryList[0];
        }

        displayCreativeData.subscribe(
          displayCreativeData => {
            if (displayCreativeData) {
              this.displayCreativeList = displayCreativeData;
              this.displayCreativeOptions = this.convertArrayIntoDropdown(displayCreativeData);
            }

            rotateCreativeData.subscribe(
              rotateCreativeData => {
                if (rotateCreativeData) {
                  this.rotateCreativeList = rotateCreativeData;
                  this.rotateCreativeOptions = this.convertArrayIntoDropdown(rotateCreativeData);
                }
                priorityData.subscribe(
                  priorityData => {
                    if (priorityData) {
                      this.priorityValues = priorityData;
                      this.priorityList = this.convertArrayIntoDropdown(priorityData);
                    }
                    this.loadProposalData();
                  });
              });
          });
      });
  }

  loadAllSpecType() {
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let url = PathConfig.BASE_URL_API + PathConfig.SPEC_TYPE_LIST;
    var data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.specTypeList = data;
          this.specTypeOptions = DropDownDataFilter.convertDataIntoDropdown(data, 'spec_Id', 'name');
          let selectedValue = data.filter(function (value) { if (value['isSelected']) { return value; } });
          if (selectedValue.length > 0) {
            this.specTypeOptionsModel = selectedValue.map(function (value) { if (value['isSelected'] == true) return value['spec_Id'] });
          } else {
            this.specTypeOptionsModel = [];
          }
          this.loadRateTypeList();
          this.loadAllTargetType();
        }
      });
    return data;
  }



  toggleForm() {
    this.showForm = !this.showForm;
    this.resetForm();
    this.loadAllTargetType();
    this.loadAllSpecType();
    this, this.audienceTargetValues = [];
  }

  onTargetTypeChange(event) {
    if (this.targetTypeOptionsModel.length > 0) {
      this.showAudienceTargetValuesForm = true
      var _selectedTargetTypeId = this.targetTypeOptionsModel[0];
      let _selectedTargetType = this.targetTypeList.filter(function (value) {
        if (value['targetTypeId'] == _selectedTargetTypeId) {
          return value;
        }
      })[0];
      this.audienceTargetValues = DropDownDataFilter.convertDataTargetTypeValuesDropdown(_selectedTargetType['audienceTargetValues'], 'id', 'value', false);
    } else {
      this.audienceTargetValues = [];
      this.showAudienceTargetValuesForm = false;
    }
    this.disabledTargetType = true;
  }




  // select Audience Target Values
  onAudienceTargetValuesSelect(seletedAudienceTargetValuesArr: Array<Object>) {
    if (seletedAudienceTargetValuesArr.length > 0) {
      this.disabledTargetType = false;
    } else {
      this.disabledTargetType = true;
    }
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
      this.selectedTargetTypeList.sort(function (a, b) {
        return a.tempId - b.tempId;
      });
      this.newAudienceTargetType = new AudienceTargetType();
      this.disabledTargetType = true;
      this.newRowsAdded = [];
      let self = this;
      this.onTargetTypeChange(null);
      setTimeout(function () {
        self.newRowsAdded = self.selectedTargetTypeList;
      }, 10);
    }
  }

  onTargetTypeMenuSelect(data) {
    if (data['clickedOn'] == 'edit') {
      this.editTargetType(JSON.parse(data['value']));

    } else if (data['clickedOn'] == 'delete') {
      let dialogRef = this.dialog.open(ConfirmDialogComponent);
      dialogRef.afterClosed().subscribe(result => {
        if (result == "OK") {
          this.deleteTargetType(JSON.parse(data['value']));
        }
      });
    }
  }

  editTargetType(targetType) {
    this.targetTypeOptionsModel[0] = targetType.targetTypeId;
    var _selectedTargetTypeId = this.targetTypeOptionsModel[0];
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
          this.disabledTargetType = false;
        }
      }
    }

  }

  deleteTargetType(targetType) {
    for (var i in this.selectedTargetTypeList) {
      if (this.selectedTargetTypeList[i]['tempId'] === targetType.tempId) {
        this.selectedTargetTypeList.splice(this.selectedTargetTypeList[i]['tempId'], 1);
      }
      if (this.selectedTargetTypeList.length > 0) {
        this.selectedTargetTypeList[i]['tempId'] = parseInt(i);
      }
    }
    this.newAudienceTargetType = new AudienceTargetType();
    this.onTargetTypeChange(null);
    this.newRowsAdded = [];
    let self = this;
    setTimeout(function () {
      self.newRowsAdded = self.selectedTargetTypeList;
    }, 10);
  }

  startDateChanged(newDate) {
    this.newProposalLineItem.flightStartdate = new Date(newDate);
  }

  endDateChanged(newDate) {
    this.newProposalLineItem.flightEndDate = new Date(newDate);
  }


  //load  Rate type with information
  loadRateTypeList() {
    if (this.authService.isLoggedIn) {
      this.priceTypeOptions = DropDownDataFilter.convertDataIntoDropdown(this.priceTypeTypeList, 'id', 'name');
    }
  }

  checkPriceType(formData: any) {
    this.addRequiredValidatorRunTime(formData);
    if (formData.valid) {
      let startDate = this.newProposalLineItem.flightStartdate;
      let endDate = this.newProposalLineItem.flightEndDate;
      this.updateProductObject("checkPrice");
      this.updateLineitem();
      let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_LINEITEM_GET_PRICE;
      let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let data = this.httpService.post(url, this.newProposalLineItem, auth_token);
      data.subscribe(
        data => {
          this.newProposalLineItem.proposedCost = data;
          this.newProposalLineItem.flightStartdate = startDate;
          this.newProposalLineItem.flightEndDate = endDate;
        }, error => {
          this.errorMessage = error;
        });
      return data;
    } else {
      this.errorMessage = "Please fill in required fields";
    }
  }



  //Form Validators Form

  ngAfterViewChecked() {
    //explicit change detection to avoid "expression-has-changed-after-it-was-checked-error"
    this.cdr.detectChanges();
  }

  addRequiredValidatorRunTime(obj) {
    for (let item in obj.controls) {
      if (item != "not") {
        obj.controls[item].setValidators([Validators.required]);
        if ((obj.controls[item].value != undefined && obj.controls[item].value.length == 0) || obj.controls[item].value == null) {
          obj.controls[item].setValue(undefined);
        } else {
          obj.controls[item].updateValueAndValidity(obj.controls[item].value);
        }
      }
    }
  }



  populateLineitemData() {
    this.productOptionsModel = [];
    this.specTypeOptionsModel = [];
    this.priceTypeOptionsModel = []
    this.productOptionsModel[0] = this.newProposalLineItem.product.productId;
    this.specTypeOptionsModel[0] = this.newProposalLineItem.specType.spec_Id;
    if (this.newProposalLineItem.deliveryImpressions != null) {
      this.deliveryOptionsModel[0] = this.newProposalLineItem.deliveryImpressions;      
    }
    if (this.newProposalLineItem.displayCreatives != null) {
      this.displayCreativeOptionsModel[0] = this.newProposalLineItem.displayCreatives;
    }
    if (this.newProposalLineItem.rotateCreatives != null) {
      this.rotateCreativeOptionsModel[0] = this.newProposalLineItem.rotateCreatives;
    }
    if(this.newProposalLineItem.priority){
      let self = this;
      _.each(this.priorityList, function(obj, key){
        if(obj.name == self.newProposalLineItem.priority){
            self.priorityOptionsModel[0] = obj.id;
        }
      });
    }else{
      this.priorityOptionsModel[0] = 1;
    }
    this.editTargetTypes();
    this.onProductChange(event);
  }

  editTargetTypes() {
    for (let i in this.newProposalLineItem.target) {
      let selectedTargetTypeId = this.newProposalLineItem.target[i]['audienceTargetType']['targetTypeId'];
      let seletedTargetTypeName = "";
      let _selectedTargetType = this.targetTypeList.filter(function (value) {
        if (value['targetTypeId'] == selectedTargetTypeId) {
          return value;
        }
      })[0];

      let allTargetTypeValues = [];
      allTargetTypeValues = _selectedTargetType['audienceTargetValues'];

      let selectedTargetTypeValuesArr = [];
      selectedTargetTypeValuesArr = this.newProposalLineItem.target[i]['audienceTargetValues'];


      seletedTargetTypeName = _selectedTargetType['name'];
      this.newAudienceTargetType.targetTypeId = selectedTargetTypeId;
      let targetTypeAudienceValues = "";
      this.audienceTargetValuesList = [];

      for (let j in selectedTargetTypeValuesArr) {
        let _selectedTargetTypeValues = allTargetTypeValues.filter(function (value) {
          if (value['id'] == selectedTargetTypeValuesArr[j]['id']) {
            return value;
          }
        })[0];
        // push values because we need id for edit.
        this.newAudienceTargetValues = new AudienceTargetValues();
        this.newAudienceTargetValues.id = _selectedTargetTypeValues['id'];
        this.newAudienceTargetValues.value = _selectedTargetTypeValues['value'];
        this.audienceTargetValuesList.push(this.newAudienceTargetValues);

        // find target type audience values , for showin in grid with ...(dots)
        if (selectedTargetTypeValuesArr.length === 1) {
          targetTypeAudienceValues += _selectedTargetTypeValues['value'];
        } else {
          targetTypeAudienceValues += _selectedTargetTypeValues['value'] + ", ";
        }
        if (targetTypeAudienceValues.length > 25) {
          targetTypeAudienceValues += " ...";
          break;
        }
      }


      this.newAudienceTargetType.tempId = this.selectedTargetTypeList.length;
      // remove, if user crud same data for targettype
      for (let i in this.selectedTargetTypeList) {
        if (this.selectedTargetTypeList[i]['name'] === seletedTargetTypeName) {
          this.newAudienceTargetType.tempId = this.selectedTargetTypeList[i]['tempId'];
          this.selectedTargetTypeList.splice(this.selectedTargetTypeList[i]['tempId'], 1);
        }
      }
      this.newAudienceTargetType.name = seletedTargetTypeName;
      this.newAudienceTargetType.targetTypeAudienceName = targetTypeAudienceValues;
      this.newAudienceTargetType.audienceTargetValues = this.audienceTargetValuesList;
      this.selectedTargetTypeList.push(this.newAudienceTargetType);
      this.selectedTargetTypeList.sort(function (a, b) {
        return a.tempId - b.tempId;
      });
      this.newAudienceTargetType = new AudienceTargetType();
      this.disabledTargetType = true;
      this.newRowsAdded = [];
      let self = this;
      this.onTargetTypeChange(null);
      setTimeout(function () {
        self.newRowsAdded = self.selectedTargetTypeList;
      }, 10);
    }
  }
  convertArrayIntoDropdown(array) {
    let newArr = array.map(function (val, index) {
      return { 'id': val, 'name': val };
    });
    return newArr;
  }
}
