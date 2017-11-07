import {
  Component, OnInit, AfterViewInit, ElementRef, HostListener, ChangeDetectorRef
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
import { ProposalCommentDialog } from "../../../../common/modules/dialog/proposal-comment/proposal-comment.component";
import { DateFormat } from "../../../../common/modules/date.format";
import { IMultiSelectOption } from "angular-2-dropdown-multiselect";
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { IMultiSelectTexts } from "angular-2-dropdown-multiselect";
import { AdUnit } from '../../../../common/models/ad.unit';
import { Product } from '../../../../common/models/product';
import { SpecType } from '../../../../common/models/spec.type';
import { RateType } from '../../../../common/models/rate.type';
import { ProposalLineItem, LineItemProposal } from '../../../../common/models/proposal.lineitem';
import { AudienceTargetType } from '../../../../common/models/audience.target.type';
import { AudienceTargetValues } from '../../../../common/models/audience.target.values';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { DropDownDataFilter } from '../../../../common/filter/dropdown.filter';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { objectSharingService } from '../../../../common/services/objectSharingService';

declare var $: any;
import { DropDownSetting } from '../../../../common/utils/dropdown.settings';
import { SalesService } from '../../sales.service';
import * as _ from 'lodash';


@Component({
  templateUrl: './proposal.lineitem.add.component.html',
  styleUrls: ['./proposal.lineitem.add.component.scss'],
  providers: [SalesService],
})
export class ProposalLineitemAddComponent implements OnInit {

  showForm: Boolean = false;
  newProposal: Proposal = new Proposal();
  productOptionsModel: number[];
  productList: Array<Object> = [];
  deliveryList: Array<Object> = [];
  displayCreativeList: Array<Object> = [];
  rotateCreativeList: Array<Object> = [];
  productOptions: IMultiSelectOption[];
  singleSelectSettings: IMultiSelectSettings;
  multiSelectSelectSettings: IMultiSelectSettings;
  textSetting: IMultiSelectTexts
  adUnitList: Array<AdUnit> = [];
  disabledFormElements: Boolean = true;
  adUnitOptions: IMultiSelectOption[];
  adUnitOptionsModel: number[];
  currentLineItem: ProposalLineItem = new ProposalLineItem();

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
  currentDate: Date;
  lineItemTargetId: number;
  newRowsAdded = [];
  priceTypeOptions: IMultiSelectOption[];
  priceTypeOptionsModel = [];
  priorityOptionsModel = [];
  editingLineItemId = null;
  editingLineItemObject: null;
  priceTypeTypeList: Array<Object> = [{
    "id": 1,
    "name": "STANDARD"
  }, {
    "id": 2,
    "name": "SPONSORSHIP"
  }];

  c: IMultiSelectOption[];
  rateOptions: IMultiSelectOption[];
  rateOptionsModel = [];

  specTypeOptions: IMultiSelectOption[];
  specTypeOptionsModel: number[];
  deliveryOptions: IMultiSelectOption[];
  deliveryOptionsModel = [];
  displayCreativeOptions: IMultiSelectOption[];
  displayCreativeOptionsModel = [];
  rotateCreativeOptions: IMultiSelectOption[];
  rotateCreativeOptionsModel = [];
  audienceTargetType: AudienceTargetType = new AudienceTargetType();
  rateCardPrice: number;
  specTypeList: Array<Object> = [];
  errorMessage: string;
  lineitemForm: FormGroup;
  disabledTargetType = true;
  deliveryFlag: boolean = true;
  priorityList: IMultiSelectOption[];
  priorityValues: Array<Object> = [];
  isMessageBoxHidden = true;
  successMessage = "";
  lineItemName = "";

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
    public getSetService: objectSharingService,
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef,
    private salesService: SalesService,

  ) {
    this.lineitemForm = fb.group({
      "product": this.productOptionsModel,
      "adUnits": this.adUnitOptionsModel,
      "name": "",
      "startDate": this.currentLineItem.flightStartdate,
      "endDate": this.currentLineItem.flightEndDate,
    });

  }

  ngOnInit() {
    this.currentDate = new Date();
    this.loadAllProducts();
    this.loadRateTypeList();
    this.multiSelectSelectSettings = DropDownSetting.multiSelectSelectSettings;
    this.multiSelectSelectSettings['dynamicTitleMaxItems'] = 5;

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

  loadPriorityList() {
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let url = PathConfig.BASE_URL_API + PathConfig.PRIORITY_GET_LIST;
    var data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {

          this.priorityList = _.map(data, function (ele, index) {
            return {
              'id': index,
              'name': ele
            }
          });

          /*this.productOptions = DropDownDataFilter.convertDataIntoDropdown(this.productList, 'productId', 'name');
          let selectedValue = this.productList.filter(function (value) { if (value['isSelected']) { return value; } });
          if (selectedValue.length > 0) {
            this.productOptionsModel = selectedValue.map(function (value) { if (value['isSelected'] == true) return value['productId'] });
          } else {
            this.productOptionsModel = [];
          }
          this.loadAllSpecType();*/

        }
      });
    return data;
  }

  loadProposalData() {
    let param = this.activatedRouter.snapshot.queryParams["proposalItem"];
    this.newProposal = JSON.parse(param);
    let lienItemId = this.activatedRouter.snapshot.queryParams["lineitemId"];
    if (lienItemId !== undefined) {
      this.editingLineItemId = lienItemId;
      let _selectedLineitem = this.newProposal.lineItems.filter(function (value) {
        if (value['lineItemId'] == lienItemId) {
          return value;
        }
      })[0];
      this.currentLineItem = _selectedLineitem;
      this.editingLineItemObject = _.clone(_selectedLineitem, true);
      this.lineItemName = _.clone(this.currentLineItem.name);
      this.populateLineitemData();
    } else {
      let tempId = this.activatedRouter.snapshot.queryParams["tempId"];
      this.currentLineItem.name = "";
      this.lineItemName = ""
      if (tempId !== undefined) {
        let _selectedLineitem = this.newProposal.lineItems.filter(function (value) {
          if (value['tempId'] == tempId) {
            return value;
          }
        })[0];
        this.currentLineItem = _selectedLineitem;
        this.populateLineitemData();
      }

    }
  }

  showMessage(self) {
    setTimeout(function () {
      self.isMessageBoxHidden = true;
    }, 3000);
  }

  /*//back to table button
  backToTableClick() {
    this.showForm = false;
    let param = this.activatedRouter.snapshot.queryParams["callback"];
    if (param === 'addProposal') {
      this.router.navigate(['dashboard/sales/proposal/add'], { queryParams: { callback: param, proposalItem: JSON.stringify(this.newProposal) } });
    }
    else if (param === 'editProposal' || param === 'editProposalWithLineitem') {
      this.router.navigate(['dashboard/sales/proposal/edit'], { queryParams: { callback: param, proposalItem: JSON.stringify(this.newProposal), isDirectSave: true } });
   }
  }*/

  //back to table button
  backToTable() {
    this.showForm = false;
    let param = this.activatedRouter.snapshot.queryParams["callback"];
    if (param === 'addProposal') {
      this.router.navigate(['dashboard/sales/proposal/add'], { queryParams: { callback: param, proposalItem: JSON.stringify(this.newProposal) } });
    }
    else if (param === 'editProposal' || param === 'editProposalWithLineitem') {
      this.router.navigate(['dashboard/sales/proposal/edit'], { queryParams: { callback: param, proposalItem: JSON.stringify(this.newProposal), isDirectSave: true } });

    }


    /*else if (param === 'editProposalWithLineitem') {
      this.router.navigate(['dashboard/sales/proposal/edit'], { queryParams: { callback: 'editProposalWithLineitem', proposalItem: JSON.stringify(this.newProposal) } });
    }*/
    /*if (param === 'addProposal' && this.newProposalLineItem.lineItemId === undefined) {
      this.router.navigate(['dashboard/sales/proposal/add'], { queryParams: { callback: 'addProposal', proposalItem: JSON.stringify(this.newProposal) } });
    } else {
      this.router.navigate(['dashboard/sales/proposal/add'], { queryParams: { proposalItem: JSON.stringify(this.newProposal) } });
    }

    if (param === 'editProposal' && this.newProposalLineItem.lineItemId !== undefined) {
      this.router.navigate(['dashboard/sales/proposal/edit'], { queryParams: { callback: 'editProposal', proposalItem: JSON.stringify(this.newProposal) } });
    } else {
      this.router.navigate(['dashboard/sales/proposal/edit'], { queryParams: { proposalItem: JSON.stringify(this.newProposal) } });
    }*/
  }

  saveProposal() {
    this.isMessageBoxHidden = true;
    this.updateLineitem();
    let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_ADD;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    var data = this.httpService.put(url, this.newProposal, auth_token);
    let self = this;
    data.subscribe(
      data => {
        let getUrl = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_GET_BY_ID + this.newProposal.proposalId;
        let proposalData = this.httpService.get(getUrl, auth_token);

        proposalData.subscribe(
          proposalData => {
            if (proposalData) {
              this.newProposal = proposalData;
              this.isMessageBoxHidden = false;
              this.lineItemName = _.clone(this.currentLineItem.name);
              this.successMessage = "Proposal is saved successfully.";
              this.showMessage(this);
            }
          });
      })
  }
  // save the form
  saveForm(formData: any) {
    let dialogRef = this.dialog.open(ProposalCommentDialog, { data: { title: 'Comments:' }, });
    dialogRef.afterClosed().subscribe(result => {
      console.log("comment dialog box closed");
      if (this.getSetService.obj != undefined) {
        this.newProposal.action = this.getSetService.obj;
        this.saveProposalData(formData);
      }
      this.getSetService.setValue({});
    });
  }

  saveProposalData(formData: any) {
    if (this.currentLineItem.name && this.productOptionsModel && this.currentLineItem.flightStartdate && this.currentLineItem.flightEndDate && this.deliveryOptionsModel && this.displayCreativeOptionsModel && this.rotateCreativeOptionsModel && this.rateOptionsModel && this.currentLineItem.proposedCost && this.specTypeOptionsModel && this.priorityOptionsModel && this.currentLineItem.cpms && this.currentLineItem.quantity && this.currentLineItem.proposedCost) {
      this.addRequiredValidatorRunTime(formData);
      if (this.specTypeOptionsModel !== undefined && this.specTypeOptionsModel.length > 0) {
        let specType = new SpecType();
        specType.spec_Id = this.specTypeOptionsModel[0];
        this.currentLineItem.specType = specType;
      }
      if (this.priorityOptionsModel.length > 0) {
        var _selectedPriceTypeId = this.priorityOptionsModel[0];
        let _selectedPriceType = this.priorityList.filter(function (value) {
          if (value['id'] == _selectedPriceTypeId) {
            return value;
          }
        })[0];

        this.currentLineItem.priority = _selectedPriceType['name'];
      }
      if (this.deliveryOptionsModel != null) {
        this.currentLineItem.deliveryImpressions = this.deliveryOptionsModel[0];
      }
      if (this.displayCreativeOptionsModel != null) {
        this.currentLineItem.displayCreatives = this.displayCreativeOptionsModel[0];
      }
      if (this.rotateCreativeOptionsModel != null) {
        this.currentLineItem.rotateCreatives = this.rotateCreativeOptionsModel[0];
      }
      if (this.rateOptionsModel != null) {
        let rateType = new RateType();
        rateType.rateTypeId = this.rateOptionsModel[0];
        //this.newProposalLineItem.specType = specType;
        this.currentLineItem.rateType = rateType;
      }
      if (formData.valid) {
        this.updateProductObject(null);
        if (this.currentLineItem.lineItemId === undefined) {
          if (this.currentLineItem.tempId === undefined) {
            this.currentLineItem.tempId = this.newProposal.lineItems.length;
            this.newProposal.lineItems.push(this.currentLineItem);
          }
        }
        //self.newRowsAdded
        this.salesService.setTargetTypesInLineItem(this.currentLineItem, this.newRowsAdded);
        this.saveProposal();
        //this.backToTable();
        // this.detectValidator();
      } else {
        this.errorMessage = "Please fill in required fields";
      }
    } else {
      this.errorMessage = "Please fill in required fields";
    }
  }

  updateProductObject(type) {
    if (this.productOptionsModel.length > 0) {
      if (type === null) {
        this.currentLineItem.product.adUnits = [];
        let product = new Product();
        product.productId = this.productOptionsModel[0];
        this.currentLineItem.product = product;
        if (this.adUnitOptionsModel.length > 0) {
          for (var i in this.adUnitOptionsModel) {
            let adUnitId = { 'adUnitId': this.adUnitOptionsModel[i] };
            this.currentLineItem.product.adUnits.push(adUnitId);
          }

        }
        delete this.currentLineItem.product.creatives;
      } else {
        var _selectedProductId = this.productOptionsModel[0];
        let _selectedProduct = this.productList.filter(function (value) {
          if (value['productId'] == _selectedProductId) {
            return value;
          }
        })[0];
        this.currentLineItem.product = _selectedProduct;
        //if (this.adUnitOptionsModel.length > 0) {
        //   this.newProposalLineItem.product.adUnits = this.adUnitOptionsModel;
        //}
      }
    }
  }

  updateLineitem() {
    this.currentLineItem.target = [];
    // var startDate = new Date(this.newProposalLineItem.flightStartdate);
    // var startDateMilliseconds = startDate.getTime();

    // var endDate = new Date(this.newProposalLineItem.flightEndDate);
    // var endDateMilliseconds = endDate.getTime();

    // this.newProposalLineItem.flightStartdate = startDateMilliseconds;
    // this.newProposalLineItem.flightEndDate = endDateMilliseconds;


    for (var i in this.newRowsAdded) {
      let targetTypeId = this.newRowsAdded[i].targetTypeId;
      let audienceTargetType = { 'targetTypeId': targetTypeId };
      let audienceTargetValues = this.newRowsAdded[i].audienceTargetValues;
      for (let j in audienceTargetValues) {
        delete audienceTargetValues[j]['value'];
      }
      var targetTypeValue = { 'audienceTargetType': audienceTargetType, 'audienceTargetValues': audienceTargetValues };
      this.currentLineItem.target.push(targetTypeValue);
    }

    //delete this.newProposalLineItem['proposedCost'];
    let lineitemProposal = new LineItemProposal;
    lineitemProposal.name = this.newProposal.name;
    lineitemProposal.proposalId = this.newProposal.proposalId;

    //this.currentLineItem['proposal'] = lineitemProposal;
    if (this.currentLineItem.proposal) {
      delete this.currentLineItem.proposal;
    }

    delete this.currentLineItem['isNewProposalLineItemEndDateOpen'];
    delete this.currentLineItem['isNewProposalLineItemStartDateOpen'];

  }

  resetForm() {
    if (this.editingLineItemId) {
      this.currentLineItem = this.editingLineItemObject;
      this.populateLineitemData();

    } else {
      this.currentLineItem = new ProposalLineItem();
      this.targetTypeOptionsModel = [];
      this.productOptionsModel = [];
      this.specTypeOptionsModel = [];
      this.priceTypeOptionsModel = [];
      this.newRowsAdded = [];
      this.priorityOptionsModel = [];
      this.detectValidator();
    }
  }

  detectValidator() {
    for (var i in this.lineitemForm.controls) {
      this.removeValidators(this.lineitemForm.controls[i], true);
    }

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

  assignTargetArray(targetArr) {
    if (targetArr.length > 0) {
      this.newRowsAdded = targetArr;
      this.selectedTargetTypeList = targetArr;
    } else {
      this.newRowsAdded = [];
    }
  };

  onProductChange(event) {
    this.errorMessage = "";
    if (this.productOptionsModel !== undefined && this.productOptionsModel.length > 0) {

      var _selectedProductTypeId = this.productOptionsModel[0];
      let _selectedProductTypeList = this.productList.filter(function (value) {
        if (value['productId'] == _selectedProductTypeId) {
          return value;
        }
      })[0];

      if (_selectedProductTypeList !== undefined) {
        this.currentLineItem.product = _selectedProductTypeList;
        //if (_selectedProductTypeList['rateCard'] != null) {

        this.adUnitList = _selectedProductTypeList['adUnits'];
        this.adUnitOptions = DropDownDataFilter.convertDataIntoDropdown(this.adUnitList, 'adUnitId', 'name');
        let selectedValue = this.adUnitList.filter(function (value) { if (value['isSelected']) { return value; } });
        this.adUnitOptionsModel = [];
        for (var i in this.adUnitOptions) {
          this.adUnitOptionsModel[i] = this.adUnitOptions[i]['id'];
        }

        if (this.deliveryFlag) {

          let targetArr = this.salesService.convertToTargetGridData(_selectedProductTypeList['target']);
          this.assignTargetArray(targetArr);

          if (_selectedProductTypeList['deliveryImpressions'] != null) {
            this.deliveryOptionsModel[0] = _selectedProductTypeList['deliveryImpressions'];
          }
          if (_selectedProductTypeList['displayCreatives'] != null) {
            this.displayCreativeOptionsModel[0] = _selectedProductTypeList['displayCreatives'];
          }
          if (_selectedProductTypeList['rotateCreatives'] != null) {
            this.rotateCreativeOptionsModel[0] = _selectedProductTypeList['rotateCreatives'];
          }
          if (_selectedProductTypeList['priority'] != null) {
            this.priceTypeOptionsModel[0] = _selectedProductTypeList['priority'];
          }
          if (_selectedProductTypeList['rateType'] != null) {
            this.rateOptionsModel[0] = _selectedProductTypeList['rateType']['rateTypeId'];
          }
          if (_selectedProductTypeList['priority']) {
            let self = this;
            _.each(this.priorityList, function (obj, key) {
              if (obj.name == _selectedProductTypeList['priority']) {
                self.priorityOptionsModel[0] = obj.id;
              }
            });
          } else {
            this.priorityOptionsModel[0] = 'Standard Low';
          }
          if (_selectedProductTypeList['priorityValues']) {
            // parse return value in string
            this.currentLineItem.cpms = '' + _selectedProductTypeList['priorityValues'];
          } else {
            this.currentLineItem.cpms = '0';
          }
        }
        this.deliveryFlag = true;
      }
    } else{
      this.deliveryOptionsModel=[];
      this.displayCreativeOptionsModel=[];
      this.rotateCreativeOptionsModel=[];
      this.priceTypeOptionsModel=[];
      this.rateOptionsModel=[];
      this.priorityOptionsModel=[];
      this.adUnitOptionsModel=[];
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

      // remove comma from last in string 'targetTypeAudienceValues'
      let lastIndexOfComma = targetTypeAudienceValues.lastIndexOf(',');

      if (lastIndexOfComma > 0) {
        targetTypeAudienceValues = targetTypeAudienceValues.substr(0, targetTypeAudienceValues.lastIndexOf(','));
      }

      this.newAudienceTargetType.tempId = this.selectedTargetTypeList.length;
      // remove, if user crud same data for targettype
      for (let i = 0; i < this.selectedTargetTypeList.length; i++) {
        if (this.selectedTargetTypeList[i]['name'] === seletedTargetTypeName) {
          this.newAudienceTargetType.tempId = this.selectedTargetTypeList[i]['tempId'];
          this.selectedTargetTypeList.splice(i, 1);
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

    for (let i = 0; i < this.selectedTargetTypeList.length; i++) {
      if (this.selectedTargetTypeList[i]['targetTypeId'] === targetType.targetTypeId) {
        this.selectedTargetTypeList.splice(i, 1);
      }
      /*if (this.selectedTargetTypeList.length > 0) {
        this.selectedTargetTypeList[i]['tempId'] = i;
      }*/
    }

    this.newAudienceTargetType = new AudienceTargetType();
    this.onTargetTypeChange(null);
    this.newRowsAdded = [];

    let self = this;
    setTimeout(function () {
      self.newRowsAdded = self.selectedTargetTypeList;
      self.cdr.detectChanges();
    }, 100);
  }

  startDateChanged(newDate) {
    this.currentLineItem.flightStartdate = new Date(newDate);
  }

  endDateChanged(newDate) {
    this.currentLineItem.flightEndDate = new Date(newDate);
  }


  //load  Rate type with information
  loadRateTypeList() {
    if (this.authService.isLoggedIn) {
      var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let loadRatesURL = PathConfig.BASE_URL_API + PathConfig.RATE_TYPE_LIST;
      var loadRateTypes = this.httpService.get(loadRatesURL, auth_token);
      loadRateTypes.subscribe(
        loadRateTypes => {
          if (loadRateTypes) {
            this.rateOptions = DropDownDataFilter.convertDataIntoDropdown(loadRateTypes, 'rateTypeId', 'name');
          }
        }
      );
    }
  }

  checkPriceType(formData: any) {
    this.addRequiredValidatorRunTime(formData);
    if (formData.valid) {
      let startDate = this.currentLineItem.flightStartdate;
      let endDate = this.currentLineItem.flightEndDate;
      this.updateProductObject("checkPrice");
      this.updateLineitem();
      let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_LINEITEM_GET_PRICE;
      let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let data = this.httpService.post(url, this.currentLineItem, auth_token);
      data.subscribe(
        data => {

          this.currentLineItem.proposedCost = data;
          //this.newProposalLineItem.flightStartdate = startDate;
          //this.newProposalLineItem.flightEndDate = endDate;
        }, error => {
          this.errorMessage = error;
        });
      return data;
    } else {
      this.errorMessage = "Please fill in required fields";
    }
  }

  viewPrice() {

    this.currentLineItem.proposedCostView = "BASE PRICE +SUM( SUM(BASE PRICE % SEASONAL DISCOUNTS) % PREMIUM)";

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
    this.priceTypeOptionsModel = [];
    this.priorityOptionsModel = [];
    this.productOptionsModel[0] = this.currentLineItem.product.productId;
    this.specTypeOptionsModel[0] = this.currentLineItem.specType.spec_Id;
    if (this.currentLineItem.deliveryImpressions != null) {
      this.deliveryOptionsModel[0] = this.currentLineItem.deliveryImpressions;
      this.deliveryFlag = false;
    }
    if (this.currentLineItem.displayCreatives != null) {
      this.displayCreativeOptionsModel[0] = this.currentLineItem.displayCreatives;
    }
    if (this.currentLineItem.rotateCreatives != null) {
      this.rotateCreativeOptionsModel[0] = this.currentLineItem.rotateCreatives;
    }
    if (this.currentLineItem.rateType != null) {
      this.rateOptionsModel[0] = this.currentLineItem.rateType.rateTypeId;
    }
    if (this.currentLineItem.priority) {
      let self = this;
      _.each(this.priorityList, function (obj, key) {
        if (obj.name == self.currentLineItem.priority) {
          self.priorityOptionsModel[0] = obj.id;
        }
      });
    } else {
      this.priorityOptionsModel[0] = 1;
    }

    this.editTargetTypes();

  }

  editTargetTypes() {
    for (let i in this.currentLineItem.target) {
      let selectedTargetTypeId = this.currentLineItem.target[i]['audienceTargetType']['targetTypeId'];
      let seletedTargetTypeName = "";

      let _selectedTargetType = this.targetTypeList.filter(function (value) {
        if (value['targetTypeId'] == selectedTargetTypeId) {
          return value;
        }
      })[0];

      let allTargetTypeValues = [];
      allTargetTypeValues = _selectedTargetType['audienceTargetValues'];

      let selectedTargetTypeValuesArr = [];
      selectedTargetTypeValuesArr = this.currentLineItem.target[i]['audienceTargetValues'];

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

  convertArrayIntoDropdown(array) {
    let newArr = array.map(function (val, index) {
      return { 'id': val, 'name': val };
    });
    return newArr;
  }
}
