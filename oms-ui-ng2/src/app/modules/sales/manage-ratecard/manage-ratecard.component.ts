import { Component, Input, EventEmitter, OnChanges, OnInit, ViewChild, AfterViewInit, ElementRef, Output, HostListener, ChangeDetectorRef } from '@angular/core';
import { HttpService } from '../../../common/services/http.service';
import { PathConfig } from '../../../common/config/path.config';
import { ConstantConfig } from '../../../common/config/constant.config';
import { LocalStorageService } from "../../../common/services/local-storage.service";
import { Broadcaster } from '../../../common/services/broadcaster.service';
import { AuthService } from '../../../common/services/auth.service';
import { RateCard } from '../../../common/models/ratecard';
import { Product } from '../../../common/models/product';
import { AdUnit } from '../../../common/models/ad.unit';
import { Premium } from '../../../common/models/premium';
import { SeasonalDiscount } from '../../../common/models/seasonal.discount';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

import { MdDialogRef } from '@angular/material';

import * as _ from 'lodash';
import { IMultiSelectOption } from "angular-2-dropdown-multiselect";
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { IMultiSelectTexts } from "angular-2-dropdown-multiselect";
import { ConfirmDialogComponent } from "../../../common/modules/dialog/confirm/confirm.component";
import { MdDialog } from '@angular/material';
import { DropDownDataFilter } from '../../../common/filter/dropdown.filter';
import { DropDownSetting } from '../../../common/utils/dropdown.settings';
import { CalendarService } from "../../../common/services/calendar.service";


@Component({
  templateUrl: './manage-ratecard.component.html',
  styleUrls: ['./manage-ratecard.component.scss'],
})
export class ManageRateCardComponent implements OnInit, AfterViewInit {
  @HostListener('document:click', ['$event'])
  clickout(event) {
    this.resetAllCalendar(this);
  }

  findAutomateContainer(target, className, typeName) {
    if (target.getAttribute('class') != undefined && target.getAttribute('class').indexOf(className) == -1) {
      if (target.getAttribute('class').indexOf(className) != -1) {
        typeName['type'] = false;
      } else {
        this.findAutomateContainer(target.parentNode, className, typeName);
      }
    } else {
      typeName['type'] = true;
    }
  }

  autoMateClick = false;
  clickSeasonalStatus: Object = {
    type: false
  };
  clickPremiumStatus: Object = {
    type: false
  }
  rateDTConfig: Object = {};
  rateListRefresh: Boolean = false;
  rateListSearchMOdel: any = {};
  newRateCard: RateCard = new RateCard();
  selectedProduct: any;
  newProduct: any;
  seasonalDiscountList = [];
  showSeasonalList = true;
  editSeasonalIndex = null;
  editPremiumIndex = null;
  premiumList = [];
  newPremium = new Premium();
  newSeasonalDiscount = new SeasonalDiscount();
  productList: Array<Object> = [];
  targetTypeList: Array<Object> = [];
  adUnitList: Array<AdUnit> = [];
  showForm: Boolean = false;
  editMode: Boolean = false;
  showSeasonalDiscountAddForm: Boolean = false;
  showSeasonalDiscountAddButton: Boolean = true;
  showPremiumAddForm: Boolean = false;
  showPremiumAddButton: Boolean = true;
  adUnitOptions: IMultiSelectOption[];
  adUnitOptionsModel: number[];
  productOptions: IMultiSelectOption[];
  targetTypeOptions: IMultiSelectOption[];
  productOptionsModel: number[];
  targetTypeOptionsModel: number[];
  singleSelectSettings: IMultiSelectSettings;
  multiSelectSelectSettings: IMultiSelectSettings;
  textSetting: IMultiSelectTexts;
  myTexts: IMultiSelectTexts;
  disabledFormElements: Boolean = true;
  minDateValue: Date;
  errorMessage: string;
  premiumErrorMessage: String;
  seasonalDiscountErrorMessage: String;
  //ReactiveFormsModule 
  ratecardForm: FormGroup;
  ratecardPremiumForm: FormGroup;
  ratecardSeasonalForm: FormGroup;

  permissions = null;
  isRateCardCreatePermission: boolean = false;
  isRateCardViewPermission: boolean = false;
  isRateCardEditPermission: boolean = false;
  isRateCardDeletePermission: boolean = false;
  //Calendar settings starts
  //Caledar array. It is declared in constructor.
  calendarArr = null;
  clickOnDatepicker = null;
  calendarBtnClick = null;
  resetAllCalendar = null;
  //Calendar settings end

  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService,
    private elementRef: ElementRef,
    private dialog: MdDialog,
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef,
    private calendarService: CalendarService,
  ) {
    this.ratecardForm = fb.group({
      "product": this.productOptionsModel,
      "basePrice": "",
    });
    this.ratecardPremiumForm = fb.group({
      "targetTypeOptions": this.targetTypeOptionsModel,
      "premiumPercentage": this.newPremium.premiumPercentage,
    });
    this.ratecardSeasonalForm = fb.group({
      "ruleName": "",
      "not": "",
      "startDate": this.newSeasonalDiscount.startDate,
      "endDate": this.newSeasonalDiscount.endDate,
      "percent": ""
    });

    // You have to declare how many calendar want to use in this component
    this.calendarArr = {
      'isNewSeasonalStartDateOpen': false,
      'isNewSeasonalEndDateOpen': false,
    };
  }

  backToTable() {
    this.showForm = false;
    this.editMode = false;
    this.adUnitList = [];
    this.adUnitOptionsModel = [];
    this.productOptionsModel = [];
    this.newRateCard = new RateCard();
    this.selectedProduct = null;
    this.rateListRefresh = true;

  }

  addSeasonalDiscount(formData: any) {
    this.addRequiredValidatorRunTime(formData);
    if (formData.valid) {
      this.seasonalDiscountErrorMessage = "";
      if (this.newSeasonalDiscount.ruleName !== undefined && this.newSeasonalDiscount.startDate !== undefined && this.newSeasonalDiscount.endDate !== undefined) {
        this.newSeasonalDiscount.tempId = this.seasonalDiscountList.length;
        this.seasonalDiscountList.push(this.newSeasonalDiscount);
        this.newSeasonalDiscount = new SeasonalDiscount();
        this.showSeasonalDiscountAddForm = false;
        this.showSeasonalDiscountAddButton = true;
      }
    } else {
      this.seasonalDiscountErrorMessage = "Please fill in required fields";
    }
  }

  editSeasonalDiscount(seasonalDiscount, index) {
    this.editSeasonalIndex = index;
    this.showSeasonalDiscountAddForm = false;
  }

  saveSeasonalDiscount() {
    this.editSeasonalIndex = null;
    this.showSeasonalDiscountAddForm = false;
  }

  deleteSeasonalDiscount(index) {
    let dialogRef = this.dialog.open(ConfirmDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result == "OK") {
        this.seasonalDiscountList.splice(index, 1);
      }
    });
  }


  addPremium(formData: any) {
    this.addRequiredValidatorRunTime(formData);
    if (formData.valid) {
      this.premiumErrorMessage = "";
      if (this.newPremium.targetTypeOptionsModel.length > 0) {
        var _selectedTargetTypeId = this.newPremium.targetTypeOptionsModel[0];
        let _selectedTargetType = this.newPremium.targetTypeOptions.filter(function (value) {
          if (value['id'] == _selectedTargetTypeId) {
            return value;
          }
        })[0];
        this.newPremium.targetTypeName = _selectedTargetType['name'];
        this.newPremium.tempId = this.premiumList.length;
        this.newPremium.targetTypeId = _selectedTargetTypeId;
        this.premiumList.push(this.newPremium);
        var tempTargetTypeArr = this.newPremium.targetTypeOptions
        this.newPremium = new Premium();
        this.newPremium.targetTypeOptions = tempTargetTypeArr;
        this.showPremiumAddForm = false;
        this.showPremiumAddButton = true;
      }
    } else {
      this.premiumErrorMessage = "Please fill in required fields";
    }
  }

  editPremium(premium, index) {
    this.showPremiumAddForm = false;
    this.newPremium = premium;
    this.editPremiumIndex = index;
  }

  savePremium() {
    var _selectedTargetTypeId = this.newPremium.targetTypeOptionsModel[0];
    let _selectedTargetType = this.newPremium.targetTypeOptions.filter(function (value) {
      if (value['id'] == _selectedTargetTypeId) {
        return value;
      }
    })[0];
    for (var i in this.premiumList) {
      if (this.premiumList[i].targetTypeOptionsModel[0] === _selectedTargetTypeId) {
        this.premiumList[i]['targetTypeId'] = _selectedTargetTypeId;
      }
    }
    this.newPremium.targetTypeName = _selectedTargetType['name'];
    this.editPremiumIndex = null;
    var tempTargetTypeArr = this.newPremium.targetTypeOptions
    this.newPremium = new Premium();
    this.newPremium.targetTypeOptions = tempTargetTypeArr;
    this.showPremiumAddForm = false;
  }

  showAddSeasonalDiscountForm() {
    this.seasonalDiscountErrorMessage = "";
    for (var i in this.ratecardSeasonalForm.controls) {
      this.removeValidators(this.ratecardSeasonalForm.controls[i], true);
    }
    if (this.showSeasonalDiscountAddForm) {
      this.showSeasonalDiscountAddForm = false;
      this.showSeasonalDiscountAddButton = false;
      this.newSeasonalDiscount = new SeasonalDiscount();
    } else {
      this.showSeasonalDiscountAddForm = true;
      this.showSeasonalDiscountAddButton = false;
    }

  }

  showAddPremiumForm() {
    this.premiumErrorMessage = "";
    for (var i in this.ratecardPremiumForm.controls) {
      this.removeValidators(this.ratecardPremiumForm.controls[i], true);
    }
    if (this.showPremiumAddForm) {
      this.showPremiumAddForm = false;
      this.showPremiumAddButton = false;
      this.newPremium = new Premium();
    } else {
      this.showPremiumAddForm = true;
      this.showPremiumAddButton = false;
    }
  }

  deletePremium(index) {
    let dialogRef = this.dialog.open(ConfirmDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result == "OK") {
        this.premiumList.splice(index, 1);
      }
    });
  }


  editRateCard(rateCardId) {
    this.toggleForm();
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let url = PathConfig.BASE_URL_API + PathConfig.RATE_CARD_ADD + rateCardId;
    var data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.newRateCard = data;
          this.newProduct = this.newRateCard.product;
          this.productOptionsModel[0] = this.newProduct['productId'];
          this.onProductChange();
          for (var i in this.newRateCard.adUnits) {
            this.adUnitOptionsModel.push(this.newRateCard.adUnits[i]['adUnitId']);
          }
          this.seasonalDiscountList = this.newRateCard.seasonalDiscounts;
          for (var i in this.seasonalDiscountList) {
            this.seasonalDiscountList[i]['tempId'] = i;
            var startDate = new Date(this.seasonalDiscountList[i].startDate).getMonth() + 1 + "-" + new Date(this.seasonalDiscountList[i].startDate).getDate() + "-" + new Date(this.seasonalDiscountList[i].startDate).getFullYear();
            var endDate = new Date(this.seasonalDiscountList[i].endDate).getMonth() + 1 + "-" + new Date(this.seasonalDiscountList[i].endDate).getDate() + "-" + new Date(this.seasonalDiscountList[i].endDate).getFullYear();
            this.seasonalDiscountList[i]['startDate'] = startDate;
            this.seasonalDiscountList[i]['endDate'] = endDate;
          }

          this.premiumList = this.newRateCard.premiums;
          for (var i in this.premiumList) {
            var _selectedTargetTypeId = this.premiumList[i]['targetTypeId'];
            let _selectedTargetType = this.targetTypeList.filter(function (value) {
              if (value['targetTypeId'] == _selectedTargetTypeId) {
                return value;
              }
            })[0];
            this.premiumList[i]['targetTypeName'] = _selectedTargetType['name'];
            this.premiumList[i]['tempId'] = i;
            var tempArr = [];
            tempArr[0] = _selectedTargetTypeId;
            this.premiumList[i]['targetTypeOptionsModel'] = tempArr;
            this.premiumList[i]['targetTypeOptions'] = DropDownDataFilter.convertDataIntoDropdown(this.targetTypeList, 'targetTypeId', 'name');
          }
          this.showForm = true;
          this.editMode = true;
        }
      });
  }

  getEditTemp(data, full){
    let tempStr = ''

    if(this.isRateCardEditPermission){
      tempStr = '<a href="javascript:void(0);"  data-name="editRateCard" data-custom="' + data + '"><span class="fa fa-pencil" aria-hidden="true"></span>Edit</a>';
    }else{
      tempStr = '<div><span class="fa fa-pencil" aria-hidden="true"></span>Edit</div>';
    }

    return tempStr;
  }

  getDeleteTemp(data, full){
    let tempStr = ''

    if(this.isRateCardDeletePermission){
      tempStr = "<a href='javascript:void(0);'  data-name='deleteRateCard' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>";
    }else{
      tempStr = "<div><span class='fa fa-trash' aria-hidden='true'></span>Delete</div>";
    }

    return tempStr;
  }

  ngOnInit() {

    this.clickOnDatepicker = this.calendarService.clickOnDatepicker(this);
    this.calendarBtnClick = this.calendarService.calendarBtnClick(this);
    this.resetAllCalendar = this.calendarService.resetAllCalendar(this);

    this.minDateValue = new Date();
    this.loadAllProducts();
    this.multiSelectSelectSettings = DropDownSetting.multiSelectSelectSettings;
    this.singleSelectSettings = DropDownSetting.singleSelectSettings;
    this.textSetting = DropDownSetting.textSetting;

    let self = this;

    this.permissions = this.localStorage.get('user_permissions');

    if(this.permissions && this.permissions.length){

      this.isRateCardCreatePermission = this.permissions.indexOf('create_rate_cards') > -1;
      this.isRateCardViewPermission = this.permissions.indexOf('view_rate_cards') > -1;
      this.isRateCardEditPermission = this.permissions.indexOf('edit_rate_cards') > -1;
      this.isRateCardDeletePermission = this.permissions.indexOf('delete_rate_cards') > -1;
    }

    this.myTexts = {
      checkAll: 'Select all',
      uncheckAll: 'Unselect all',
      checked: 'item selected',
      checkedPlural: 'items selected',
      searchPlaceholder: 'Find',
      defaultTitle: 'Select',
      allSelected: 'All selected',
    };
    this.rateDTConfig = {
      "columnDefs": [
        {
          "className": "text-right",
          "orderable": false,
          "targets": 2,
          "render": function (data, type, full, meta) {
            var fullObj = JSON.stringify(full);
            var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
              self.getEditTemp(data, full) +
              self.getDeleteTemp(data, full) +
              '</div>';
            return template;
          }
        }],
      "columns": [
        { "title": 'Product', "data": "product.name" },        
        { "title": 'Base Price', "data": "basePrice" },
        { "title": '', "data": "rateCardId" }
      ],
      "apiURL": PathConfig.BASE_URL_API + PathConfig.RATE_CARD_LIST
    }
  }

  onRateTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'editRateCard') {
      //edit code here
      this.editRateCard(data['value']);
      console.log(data['value']);
    } else if (data['clickedOn'] == 'deleteRateCard') {
      //delete code here
      //confirm box
      let dialogRef = this.dialog.open(ConfirmDialogComponent);
      this.rateListRefresh = false;
      dialogRef.afterClosed().subscribe(result => {
        if (result == "OK") {
          this.deleteRateCardOnConfirm(JSON.parse(data['value']));
        }
      });

      console.log(data['value']);
    }
    //console.log(data);
  }


  ngAfterViewInit() {

  }



  //data table search with drop down functionality
  rateCardTableSearch: string = '';
  rateCardSearchTableDD: Array<Object> = [
    { "id": 0, "labelName": "Product" },    
    { "id": 1, "labelName": "Base Price" },

  ];
  rateCardSearchSelectedDD: Object = this.rateCardSearchTableDD[0];



  //user dropdown Event
  rateSearchEvent(data) {
    console.log(data);
    this.rateListSearchMOdel['searchTxt'] = data['text'];
    this.rateListSearchMOdel['colIndex'] = data['dd-id'];
  }

  onProductChange() {
    if (this.productOptionsModel != undefined && this.productOptionsModel.length > 0) {
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
  }

  toggleForm() {
    this.showForm = !this.showForm;
    this.editMode = false;
    this.resetForm();
    this.loadAllTargetType();
    this.showPremiumAddForm = false;
    this.showSeasonalDiscountAddForm = false;
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
        }
      });
    return data;
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
          this.newPremium.targetTypeOptions = DropDownDataFilter.convertDataIntoDropdown(data, 'targetTypeId', 'name');
          let selectedValue = data.filter(function (value) { if (value['isSelected']) { return value; } });
          if (selectedValue.length > 0) {
            this.newPremium.targetTypeOptionsModel = selectedValue.map(function (value) { if (value['isSelected'] == true) return value['targetTypeId'] });
          } else {
            this.newPremium.targetTypeOptionsModel = [];
          }

        }
      });
    return data;
  }

  convertDataIntoDropdown(array, propId, propLabel) {
    let newArr = array.map(function (val, index) {
      return { 'id': val[propId], 'name': val[propLabel] };
    });
    return newArr;

  }



  //adUnit drop down
  selectedAdUnitNameDD: any = { 'name': '-- SELECT -- ' };
  adUnitOptionsChange(list) {
    let selectedArray = _.filter(list, { isSelected: true });
    if (selectedArray.length > 0) {
      this.selectedAdUnitNameDD = { 'name': _.map(selectedArray, "name").join(',') };
    } else {
      this.selectedAdUnitNameDD['name'] = '-- SELECT -- ';
    }
  }


  // delete rateCard
  rateCardObjToDelete: any;
  deleteRateCardOnConfirm(obj) {

    let url = PathConfig.BASE_URL_API + PathConfig.RATE_CARD_DELETE + obj['rateCardId'];
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.deleteWithId(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        this.backToTable();
      });
    return data;
  }






  // reset the form
  resetForm() {
    this.newRateCard = new RateCard();
    this.seasonalDiscountList = [];
    this.premiumList = [];
    var tempTargetTypeArr = this.newPremium.targetTypeOptions
    this.newPremium = new Premium();
    this.newPremium.targetTypeOptions = tempTargetTypeArr;
    this.newSeasonalDiscount = new SeasonalDiscount();
    this.targetTypeOptionsModel = [];
    this.productOptionsModel = [];
    this.adUnitOptionsModel = [];
    this.disabledFormElements = true;
    this.errorMessage = "";
    this.premiumErrorMessage = "";
    this.seasonalDiscountErrorMessage = "";
    for (var i in this.ratecardForm.controls) {
      this.removeValidators(this.ratecardForm.controls[i], true);
    }
  }


  //Form Validators Form

  ngAfterViewChecked() {
    //explicit change detection to avoid "expression-has-changed-after-it-was-checked-error"
    this.cdr.detectChanges();
  }

  removeValidators(controlFormObj, reset) {
    this.errorMessage = "";
    this.premiumErrorMessage = "";
    this.seasonalDiscountErrorMessage = "";
    if (controlFormObj != undefined) {
      controlFormObj.setValidators([]);
      if (reset) {
        controlFormObj.setValue(undefined);
      } else {
        controlFormObj.setValue(controlFormObj.value);
      }
    }
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

  // save the form
  saveForm(formData: any) {
    this.addRequiredValidatorRunTime(formData);
    if (formData.valid) {
      this.errorMessage = "";
      this.premiumErrorMessage = "";
      this.seasonalDiscountErrorMessage = "";
      let self = this;
      var _selectedProductTypeId = this.productOptionsModel[0];
      let _selectedProduct = this.productList.filter(function (value) {
        if (value['productId'] == _selectedProductTypeId) {
          return value;
        }
      })[0];

      this.newProduct = _selectedProduct;
      this.newRateCard.adUnits = [];
      for (var i in this.adUnitOptionsModel) {
        var _selectedAdUnitId = this.adUnitOptionsModel[i];
        let _selectedAdUnit = this.adUnitList.filter(function (value) {
          if (value['adUnitId'] == _selectedAdUnitId) {
            return value;
          }
        })[0];
        this.newRateCard.adUnits.push(_selectedAdUnit);
      }
      this.newRateCard.product = this.newProduct;
      for (var i in this.seasonalDiscountList) {
        delete this.seasonalDiscountList[i].tempId;
        delete this.seasonalDiscountList[i].targetTypeName;

        var startDate = new Date(this.seasonalDiscountList[i].startDate);
        var startDateMilliseconds = startDate.getTime();

        var endDate = new Date(this.seasonalDiscountList[i].endDate);
        var endDateMilliseconds = endDate.getTime();

        this.seasonalDiscountList[i].startDate = startDateMilliseconds;
        this.seasonalDiscountList[i].endDate = endDateMilliseconds;
        if (this.seasonalDiscountList[i].isNot) {
          this.seasonalDiscountList[i].isNot = 1
        } else {
          this.seasonalDiscountList[i].isNot = 0;
        }
      }
      for (var i in this.premiumList) {
        delete this.premiumList[i].tempId;
        delete this.premiumList[i].targetTypeName;
        delete this.premiumList[i].targetTypeOptions;
        delete this.premiumList[i].targetTypeOptionsModel;
      }
      this.newRateCard.seasonalDiscounts = this.seasonalDiscountList;
      this.newRateCard.premiums = this.premiumList;

      let url = PathConfig.BASE_URL_API + PathConfig.RATE_CARD_ADD;
      let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      if (this.editMode) {
        var data = this.httpService.put(url, this.newRateCard, auth_token);
      } else {
        var data = this.httpService.post(url, this.newRateCard, auth_token);
      }
      data.subscribe(
        data => {
          this.backToTable();
        }, error => {
          this.errorMessage = error;
        });
      this.newRateCard = new RateCard();
      return data;
    } else {
      this.errorMessage = "Please fill in required fields";
    }
  }

  onChange() {
    console.log(this.adUnitOptions);
    console.log(this.adUnitOptionsModel);
  }


  getFormatDate(date) {
    var m_names = new Array("Jan", "Feb", "Mar",
      "Apr", "May", "Jun", "Jul", "Aug", "Sep",
      "Oct", "Nov", "Dec");

    var d = new Date(date);
    var curr_date = d.getDate();
    var curr_month = d.getMonth();
    var curr_year = d.getFullYear();
    return curr_date + "-" + m_names[curr_month]
      + "-" + curr_year;
  }

  private startDateChanged(newDate) {
    this.newSeasonalDiscount.startDate = new Date(newDate); //this.getFormatDate(newDate);   
  }

  private endDateChanged(newDate) {
    this.newSeasonalDiscount.endDate = new Date(newDate); //this.getFormatDate(newDate);  
  }
  // public closePopover(event: Date,obj,type): void {
  //   obj[type] = false;
  // }

}
