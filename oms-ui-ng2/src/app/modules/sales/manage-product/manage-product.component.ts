import { Component, Input, EventEmitter, OnChanges, OnInit, ViewChild, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { HttpService } from '../../../common/services/http.service';
import { PathConfig } from '../../../common/config/path.config';
import { ConstantConfig } from '../../../common/config/constant.config';
import { LocalStorageService } from "../../../common/services/local-storage.service";
import { Broadcaster } from '../../../common/services/broadcaster.service';
import { AuthService } from '../../../common/services/auth.service';
import { Product } from '../../../common/models/product';
import { RateType } from '../../../common/models/rate.type';
import { ProductType } from '../../../common/models/product.type';
import { Creative } from '../../../common/models/creative';
import { AdUnit } from '../../../common/models/ad.unit';

import * as _ from 'lodash';
import { IMultiSelectOption } from "angular-2-dropdown-multiselect";
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { IMultiSelectTexts } from "angular-2-dropdown-multiselect";
import { ConfirmDialogComponent } from "../../../common/modules/dialog/confirm/confirm.component";
import { MdDialog } from '@angular/material';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { DropDownDataFilter } from '../../../common/filter/dropdown.filter';
import { DropDownSetting } from '../../../common/utils/dropdown.settings';

import { AudienceTargetType } from '../../../common/models/audience.target.type';
import { AudienceTargetValues } from '../../../common/models/audience.target.values';


@Component({
  templateUrl: './manage-product.component.html',
  styleUrls: ['./manage-product.component.scss'],
})
export class ManageProductComponent implements OnInit {

  productForm: FormGroup;
  errorMessage: string;
  showForm: Boolean = false;
  selectedProductType: any;
  selectedRateType: any;
  selectedCreative: any;
  selectedAdUnit: any;
  newProduct: Product = new Product();
  creativeList: Array<Object> = [];
  productTypeList: Array<Object> = [];
  rateTypeList: Array<Object> = [];
  adUnitList: Array<Object> = [];
  deliveryList: Array<Object> = [];
  displayCreativeList: Array<Object> = [];
  rotateCreativeList: Array<Object> = [];
  priorityList: Array<Object> = [];
  productListDTConfig: Object = {};
  productListRefresh: Boolean = false;
  productListSearchModel: any = {};
  rateOptions: IMultiSelectOption[];
  rateOptionsModel = [];
  creativeOptions: IMultiSelectOption[];
  creativeOptionsModel: number[];
  adUnitOptions: IMultiSelectOption[];
  adUnitOptionsModel: number[];
  deliveryOptions: IMultiSelectOption[];
  deliveryOptionsModel = [];
  displayCreativeOptions: IMultiSelectOption[];
  displayCreativeOptionsModel = [];
  rotateCreativeOptions: IMultiSelectOption[];
  rotateCreativeOptionsModel = [];
  priorityOptions: IMultiSelectOption[];
  priorityOptionsModel = [];
  searchSettings: IMultiSelectSettings;
  singleSelectSettings: IMultiSelectSettings;
  multiSelectSelectSettings: IMultiSelectSettings;
  textSetting: IMultiSelectTexts;

  newProductType: ProductType = new ProductType();

  targetTypeList: Array<Object> = [];
  selectedTargetTypeList = [];
  audienceTargetValues: Array<AudienceTargetValues>;
  newAudienceTargetType: AudienceTargetType = new AudienceTargetType();
  audienceTargetValuesList: Array<AudienceTargetValues> = [];
  newAudienceTargetValues: AudienceTargetValues = new AudienceTargetValues();
  targetTypeConfig: Object;
  showAudienceTargetValuesForm: Boolean = false;
  seletedAudienceTargetValuesArr: Array<Object>
  targetTypeOptions: IMultiSelectOption[];
  targetTypeOptionsModel: number[];

  disabledTargetType = true;
  newRowsAdded = [];

  permissions = null;
  isProductCreatePermission: boolean = false;
  isProductEditPermission: boolean = false;
  isProductDeletePermission: boolean = false;

  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService,
    private dialog: MdDialog,
    private productFr: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {
    this.productForm = productFr.group({
      "name": "",
      "adUnit": this.adUnitOptionsModel,
      "rate": this.rateOptionsModel,
      "creative": this.creativeOptionsModel
    });
  }


  //back to table button
  backToTable() {
    this.showForm = false;
    this.productListRefresh = true;
    this.newProduct = new Product();
    this.rateOptionsModel = [];

  }

  // showing cretive form when user click on add new or edit the product
  loadAllDropdowns() {
    this.loadCretiveList();
    this.loadRateTypeList();
    this.loadProductTypeList(null);
    this.loadAdUnitList();
    this.loadDeliveryInfo();
    this.loadAllTargetType();
    this.newProduct = new Product();
    this.selectedProductType = "";
    this.selectedRateType = "";
    this.selectedCreative = "";
    this.selectedAdUnit = "";

  }

  editProduct(prodId) {
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let url = PathConfig.BASE_URL_API + PathConfig.PRODUCT_ADD + '/' + prodId;
    var data = this.httpService.get(url, auth_token);
    let self = this;
  
    data.subscribe(
      data => {
        if (data) {
          Object.assign(this.newProduct, data);
          self.selectedProductType = data.productType.typeId;
          self.rateOptionsModel.push(data.rateType.rateTypeId);
          for (var i in data.creatives) {
            this.creativeOptionsModel.push(data.creatives[i].creativeId);
          }
          for (var i in data.adUnits) {
            this.adUnitOptionsModel.push(data.adUnits[i].adUnitId);
          }
          this.deliveryOptionsModel[0] = data.deliveryImpressions;
          this.displayCreativeOptionsModel[0] = data.displayCreatives;
          this.rotateCreativeOptionsModel[0] = data.rotateCreatives;
          this.priorityOptionsModel[0]= data.priority;
          this.selectedCreative = data.creatives[0].creativeId;
          this.selectedAdUnit = data.adUnits[0].adUnitId;
          this.showForm = true;
          this.newProductType = data.productType;
          this.editTargetTypes();
          this.loadProductTypeList(this.newProductType);
        }
      });
  }

  getEditTepm(data, full){
    let tempStr = ''

    if(this.isProductEditPermission){  
      tempStr = '<a href="javascript:void(0);" data-name="edit" data-custom="' + data + '"><span class="fa fa-pencil" aria-hidden="true"></span>Edit</a>';
    }else{
      tempStr = '<div><span class="fa fa-pencil" aria-hidden="true"></span>Edit</div>'
    }

    return tempStr;
  }

  getDeleteTemp(data, full){
    let tempStr = ''

    if(this.isProductDeletePermission){
      tempStr = "<a href='javascript:void(0);' data-name='delete' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>";
    }else{
      tempStr = "<div><span class='fa fa-trash' aria-hidden='true'></span>Delete</div>";
    }

    return tempStr;
  }


  ngOnInit() {

    let self = this;

    this.permissions = this.localStorage.get('user_permissions');

    if(this.permissions && this.permissions.length){

      this.isProductCreatePermission = this.permissions.indexOf('edit_products') > -1;
      this.isProductEditPermission = this.permissions.indexOf('edit_products') > -1;
      this.isProductDeletePermission = this.permissions.indexOf('delete_products') > -1;
    }

    this.multiSelectSelectSettings = DropDownSetting.multiSelectSelectSettings;
    this.singleSelectSettings = DropDownSetting.singleSelectSettings;
    this.textSetting = DropDownSetting.textSetting;
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
              "<a href='javascript:void(0);' data-name='edit' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-pencil' aria-hidden='true'></span>Edit</a>"+
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
    };
    this.productListDTConfig = {
      "columnDefs": [
        {

          "orderable": false,
          "targets": 2,
          "render": function (data, type, full, meta) {
            var value = "";
            var data = full.adUnits;
            for (var i in data) {
              value += data[i].name + ", ";

              if (value.length > 25) {
                value += " ...";
                break;
              }
            }
            value = value.substr(0, value.lastIndexOf(','));
            var template = '<div">' + value + '</div>';
            return template;
          }
        },
        {

          "orderable": false,
          "targets": 3,
          "render": function (data, type, full, meta) {
            var value = "";
            var data = full.creatives;
            for (var i in data) {
              value += data[i].name + ", ";

              if (value.length > 25) {
                value += " ...";
                break;
              }
            }
            value = value.substr(0, value.lastIndexOf(','));
            var template = '<div">' + value + '</div>';
            return template;
          }
        },
        {
          "className": "text-right",
          "orderable": false,
          "targets": 5,
          "render": function (data, type, full, meta) {
            var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
              self.getEditTepm(data, full) +
              self.getDeleteTemp(data, full) +
              '</div>';
            return template;
          }
        }],
      "columns": [
        { "title": 'Name', "data": "name" },
        { "title": 'Type', "data": "productType.subTypeName" },
        { "title": 'Ad Unit', "data": "adUnits" },
        { "title": 'Creative', "data": "creatives" },
        { "title": "Rate Type", "data": "rateType.name" },
        { "title": '', "data": "productId" }
      ],
      "apiURL": PathConfig.BASE_URL_API + PathConfig.PRODUCT_LIST
    }
  }

  //data table search with drop down functionality
  productTableSearch: string = '';
  productSearchTableDD: Array<Object> = [
    { "id": 0, "labelName": "Name" },
    { "id": 1, "labelName": "Type" },
    { "id": 2, "labelName": "Ad Unit" },
    { "id": 3, "labelName": "Creative" },
    { "id": 4, "labelName": "Rate Type" }
  ];



  //user dropdown Event
  productSearchEvent(data) {
    console.log(data);
    this.productListSearchModel['searchTxt'] = data['text'];
    this.productListSearchModel['colIndex'] = data['dd-id'];
  }


  //load  manage-creative with information
  loadCretiveList() {
    if (this.authService.isLoggedIn) {
      var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let url = PathConfig.BASE_URL_API + PathConfig.CREATIVE_LIST;
      var data = this.httpService.get(url, auth_token);
      let self = this;
      data.subscribe(
        data => {
          if (data) {
            this.creativeList = data;
            this.creativeOptions = DropDownDataFilter.convertDataIntoDropdown(data, 'creativeId', 'name');
            let selectedValue = data.filter(function (value) { if (value.isSelected) { return value; } });
            if (selectedValue.length > 0) {
              this.creativeOptionsModel = selectedValue.map(function (value) { if (value.isSelected == true) return value.creativeID });
            } else {
              this.creativeOptionsModel = [];
            }
          }

        });
      return data;
    }
  }


  //load  Rate type with information
  loadRateTypeList() {
    if (this.authService.isLoggedIn) {
      var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let url = PathConfig.BASE_URL_API + PathConfig.RATE_TYPE_LIST;
      var data = this.httpService.get(url, auth_token);
      let self = this;
      data.subscribe(
        data => {
          if (data) {
            this.rateOptions = DropDownDataFilter.convertDataIntoDropdown(data, 'rateTypeId', 'name');
            this.rateTypeList = data;
          }
        });
      return data;
    }
  }

  //load  product type with information
  loadProductTypeList(editVal) {
    if (this.authService.isLoggedIn) {
      var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let url = PathConfig.BASE_URL_API + PathConfig.PRODUCT_TYPE_LIST;
      var data = this.httpService.get(url, auth_token);
      let self = this;
      data.subscribe(
        data => {
          if (data) {
            if (editVal != null) {
              //this.newProductType = data.filter(function (value) { return value.typeId == editVal.typeId; })[0];
              for (var i in data) {
                if (data[i].typeId == editVal.typeId) {
                  data[i].isSelected = true;
                }
              }
              this.productTypeList = data;
              this.productTypeDD = _.groupBy(data, function (b) { return b.typeName });
              let selectedValue = data.filter(function (value) { return value.isSelected == true; });
              this.typeOptionsChange(this.productTypeDD, selectedValue[0]);
            } else {
              this.productTypeList = data;
              this.productTypeDD = _.groupBy(data, function (b) { return b.typeName });
              this.typeOptionsChange(this.productTypeDD, this.productTypeList);
            }
          }
        });
      return data;
    }
  }

  //load  AdUnit with information
  loadAdUnitList() {
    if (this.authService.isLoggedIn) {
      var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let url = PathConfig.BASE_URL_API + PathConfig.AD_UNIT_LIST;
      var data = this.httpService.get(url, auth_token);
      let self = this;
      data.subscribe(
        data => {
          if (data) {
            this.adUnitList = data;
            this.adUnitOptions = DropDownDataFilter.convertDataIntoDropdown(data, 'adUnitId', 'name');
            let selectedValue = data.filter(function (value) { if (value.isSelected) { return value; } });
            if (selectedValue.length > 0) {
              this.adUnitOptionsModel = selectedValue.map(function (value) { if (value.isSelected == true) return value.creativeId });
            } else {
              this.adUnitOptionsModel = [];
            }
          }
        });
      return data;
    }
  }

  loadDeliveryInfo() {
    if (this.authService.isLoggedIn) {
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
        });
      displayCreativeData.subscribe(
        displayCreativeData => {
          if (displayCreativeData) {
            this.displayCreativeList = displayCreativeData;
            this.displayCreativeOptions = this.convertArrayIntoDropdown(displayCreativeData);
            this.displayCreativeOptionsModel[0] = this.displayCreativeList[1];
          }
        });
      rotateCreativeData.subscribe(
        rotateCreativeData => {
          if (rotateCreativeData) {
            this.rotateCreativeList = rotateCreativeData;
            this.rotateCreativeOptions = this.convertArrayIntoDropdown(rotateCreativeData);
            this.rotateCreativeOptionsModel[0] = this.rotateCreativeList[0];
          }
        });
      priorityData.subscribe(
        priorityData => {
          if (priorityData) {
            this.priorityList = priorityData;
            this.priorityOptions = this.convertArrayIntoDropdown(priorityData);
            this.priorityOptionsModel[0] = this.priorityList[0];
          }
        });
    }
  }

  convertArrayIntoDropdown(array) {
    let newArr = array.map(function (val, index) {
      return { 'id': val, 'name': val };
    });
    return newArr;
  }

  // reset the form
  resetForm() {
    this.rateOptionsModel = [];
    this.loadAllDropdowns();
    this.targetTypeOptionsModel = [];
    this.errorMessage = "";
    for (var i in this.productForm.controls) {
      this.removeValidators(this.productForm.controls[i], true);
    }
  }

  //Form Validators Form

  ngAfterViewChecked() {
    //explicit change detection to avoid "expression-has-changed-after-it-was-checked-error"
    this.cdr.detectChanges();
  }

  addRequiredValidatorRunTime(obj) {
    for (let item in obj.controls) {
      obj.controls[item].setValidators([Validators.required]);
      if ((obj.controls[item].value != undefined && obj.controls[item].value.length == 0) || obj.controls[item].value == null) {
        obj.controls[item].setValue(undefined);
      } else {
        obj.controls[item].updateValueAndValidity(obj.controls[item].value);
      }
    }
  }

  removeValidators(controlFormObj, reset) {
    this.errorMessage = "";
    if (controlFormObj != undefined) {
      controlFormObj.setValidators([]);
      if (reset) {
        controlFormObj.setValue(undefined);
      } else {
        controlFormObj.setValue(controlFormObj.value);
      }
    }
  }

  // save the form
  saveForm(formData: any) {
    this.addRequiredValidatorRunTime(formData);
    if (formData.valid) {
      let url = PathConfig.BASE_URL_API + PathConfig.PRODUCT_ADD;
      let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      this.newProduct.adUnits = [];
      this.newProduct.target = [];
      for (var i in this.newRowsAdded) {

        let targetTypeId = this.newRowsAdded[i].targetTypeId;
        let audienceTargetType = { 'targetTypeId': targetTypeId };
        let audienceTargetValues = this.newRowsAdded[i].audienceTargetValues;
        for (let j in audienceTargetValues) {
          delete audienceTargetValues[j]['value'];
        }
        var targetTypeValue = { 'audienceTargetType': audienceTargetType, 'audienceTargetValues': audienceTargetValues };
        this.newProduct.target.push(targetTypeValue);
      }

      for (var i in this.adUnitOptionsModel) {
        var _selectedAdUnitId = this.adUnitOptionsModel[i];
        let _selectedAdUnit = this.adUnitList.filter(function (value) {
          if (value['adUnitId'] == _selectedAdUnitId) {
            return value;
          }
        })[0];
        this.newProduct.adUnits.push(_selectedAdUnit);
      }

      this.newProduct.creatives = [];
      for (var i in this.creativeOptionsModel) {
        var _selectedCreativeId = this.creativeOptionsModel[i];
        let _selectedCreative = this.creativeList.filter(function (value) {
          if (value['creativeId'] == _selectedCreativeId) {
            return value;
          }
        })[0];
        this.newProduct.creatives.push(_selectedCreative);
      }



      var _selectedProductTypeId = this.selectedProductType;
      let _selectedProductTypeList = this.productTypeList.filter(function (value) {
        if (value['typeId'] == _selectedProductTypeId) {
          return value;
        }
      })[0];

      var _selectedRateTypeId = this.rateOptionsModel[0];
      let _selectedRateTypeList = this.rateTypeList.filter(function (value) {
        if (value['rateTypeId'] == _selectedRateTypeId) {
          return value;
        }
      })[0];

      if (this.deliveryOptionsModel != null) {
        this.newProduct.deliveryImpressions = this.deliveryOptionsModel[0];
      }
      if (this.displayCreativeOptionsModel != null) {
        this.newProduct.displayCreatives = this.displayCreativeOptionsModel[0];
      }
      if (this.rotateCreativeOptionsModel != null) {
        this.newProduct.rotateCreatives = this.rotateCreativeOptionsModel[0];
      }

      if (this.priorityOptionsModel != null) {
        this.newProduct.priority = this.priorityOptionsModel[0];
      }

      //this.newProduct.productType = _selectedProductTypeList; used when we use md-select drop down
      // used when we are use coustom dropdown
      delete this.newProductType['isSelected'];
      this.newProduct.productType = this.newProductType;

      this.newProduct.rateType = _selectedRateTypeList;
      this.newProduct.status = true;
      let data;
      if (this.newProduct.productId === undefined) {
        data = this.httpService.post(url, this.newProduct, auth_token);
      } else {
        data = this.httpService.put(url, this.newProduct, auth_token);
      }
      let self = this;
      data.subscribe(
        data => {
          this.rateOptionsModel = [];
          this.backToTable();
        }, error => {
          this.errorMessage = error;
        });
      this.newProduct = new Product();

      return data;
    } else {
      this.errorMessage = "Please fill in required fields";
    }
  }



  toggleForm() {
    this.showForm = !this.showForm;
    this.newProduct = new Product();
    this.newRowsAdded = [];
    this.loadAllDropdowns();
    this.errorMessage = "";
    for (var i in this.productForm.controls) {
      this.removeValidators(this.productForm.controls[i], true);
    }

  }


  // on Menu Selection
  onProductTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'edit') {
      this.resetForm();
      this.editProduct(data['value']);
      console.log(data['value']);
    } else if (data['clickedOn'] == 'delete') {
      let dialogRef = this.dialog.open(ConfirmDialogComponent);
      this.productListRefresh = false;
      dialogRef.afterClosed().subscribe(result => {
        if (result == "OK") {
          this.deleteProductOnConfirm(JSON.parse(data['value']));
        }
      });

      console.log(data['value']);

      console.log(data['value']);
    }
  }

  onChange() {
    console.log(this.adUnitOptions);
    console.log(this.adUnitOptionsModel);
  }


  //type dropdown
  productTypeDD: any;
  keysGetter = Object.keys;
  selectedTypeDD = 'Select';
  typeOptionsChange(list, data) {
    let arr = [];
    _.forEach(list, function (value, key) {
      _.forEach(value, function (v, k) {
        v.typeName = key;
        if (v.typeId === data.typeId) {
          v.isSelected = true;
        } else {
          v.isSelected = false;
        }
        arr.push(v);
      });
    });
    //delete data.isSelected;
    this.newProductType = data
    let selectedArray = _.filter(arr, { isSelected: true });
    if (selectedArray.length > 0) {
      this.selectedTypeDD = _.map(selectedArray, "subTypeName").join(',');
    } else {
      this.selectedTypeDD = 'Select';
    }

    console.log(arr);
  }

  onchangeProductType(selectedProductType) {
    this.newProductType = selectedProductType;
  }


  // delete product
  deleteProductOnConfirm(obj) {
    let url = PathConfig.BASE_URL_API + PathConfig.PRODUCT_DELETE + obj['productId'];
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.deleteWithId(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          self.backToTable();
        }
      });
    return data;
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

      // remove comma from last in string 'targetTypeAudienceValues'
      let lastIndexOfComma = targetTypeAudienceValues.lastIndexOf(',');

      if (lastIndexOfComma > 0) {
        targetTypeAudienceValues = targetTypeAudienceValues.substr(0, targetTypeAudienceValues.lastIndexOf(','));
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
      console.log("self.selectedTargetTypeList :: " + self.selectedTargetTypeList);
    }, 10);
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
      });
    return data;
  }
  editTargetTypes() {
    for (let i in this.newProduct.target) {
      let selectedTargetTypeId = this.newProduct.target[i]['audienceTargetType']['targetTypeId'];
      let seletedTargetTypeName = "";

      let _selectedTargetType = this.targetTypeList.filter(function (value) {
        if (value['targetTypeId'] == selectedTargetTypeId) {
          return value;
        }
      })[0];

      let allTargetTypeValues = [];
      allTargetTypeValues = _selectedTargetType['audienceTargetValues'];

      let selectedTargetTypeValuesArr = [];
      selectedTargetTypeValuesArr = this.newProduct.target[i]['audienceTargetValues'];

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

      // remove comma from last in string 'targetTypeAudienceValues'
      let lastIndexOfComma = targetTypeAudienceValues.lastIndexOf(',');

      if (lastIndexOfComma > 0) {
        targetTypeAudienceValues = targetTypeAudienceValues.substr(0, targetTypeAudienceValues.lastIndexOf(','));
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


}

