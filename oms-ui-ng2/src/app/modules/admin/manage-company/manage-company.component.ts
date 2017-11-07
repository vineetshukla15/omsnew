import { Component, Input, EventEmitter, OnChanges, OnInit, ViewChild, ElementRef, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { HttpService } from '../../../common/services/http.service';
import { PathConfig } from '../../../common/config/path.config';
import { ConstantConfig } from '../../../common/config/constant.config';
import { LocalStorageService } from "../../../common/services/local-storage.service";
import { Broadcaster } from '../../../common/services/broadcaster.service';
import { AuthService } from '../../../common/services/auth.service';
import { AdUnit } from '../../../common/models/ad.unit';
import { Company } from '../../../common/models/company';
import * as $ from 'jquery';
import { MdDialog, MdDialogRef } from '@angular/material';
import * as _ from 'lodash';
import { ConfirmDialogComponent } from "../../../common/modules/dialog/confirm/confirm.component";
import { IMultiSelectOption } from "angular-2-dropdown-multiselect";
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { IMultiSelectTexts } from "angular-2-dropdown-multiselect";
//import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { DropDownDataFilter } from '../../../common/filter/dropdown.filter';
import { DropDownSetting } from '../../../common/utils/dropdown.settings';


@Component({
  templateUrl: './manage-company.component.html',
  styleUrls: ['./manage-company.component.scss'],
})
export class ManageCompanyComponent implements OnInit {

  //companyForm: FormGroup;
  errorMessage: string;
  showForm: Boolean = false;
  editMode: Boolean = false;
  companyListDTConfig: Object = {};
  companyListRefresh: Boolean = false;
  companyListSearchModel: any = {};
  newCompany: Company = new Company();
  companyStatusList: Array<Object> = [];
  selectedCompanyStatus: Array<Number>;
  comapanyOptions: IMultiSelectOption[];
  companyOptionsModel: number[];
  comapanyTypeOptions: IMultiSelectOption[];
  companyTypeOptionsModel: number[];
  singleSelectSettings: IMultiSelectSettings;
  multiSelectSelectSettings: IMultiSelectSettings;
  textSetting: IMultiSelectTexts;
  companyType: Array<Object> = [{
    "id": 1,
    "name": "Agency"
  }, {
    "id": 2,
    "name": "Advertiser"
  }];

  customer = {
    "updated": null,
    "customerID": 3,
    "customerName": "hotstar",
    "customerEmail": "ritesh.nailwal@tavant.cim",
    "customerPhone": "9818871522",
    "status": 0,
    "customerAddress": null,

  }

  permissions = null;
  isCompanyCreatePermission: boolean = false;
  isCompanyViewPermission: boolean = false;
  isCompanyEditPermission: boolean = false;
  isCompanyDeletePermission: boolean = false;

  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService,
    private dialog: MdDialog,
    // private companyFr: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {/*
    this.companyForm = companyFr.group({
      "name": ["", Validators.required],
      "type": [this.companyTypeOptionsModel, Validators.required],
      "status": [this.companyOptionsModel, Validators.required],
      "address": "",
    });*/
  }

  getEditTemp(data, full){
    let tempStr = ''

    if(this.isCompanyEditPermission){
      tempStr = '<a href="javascript:void(0);" data-name="edit" data-custom="' + data + '"><span class="fa fa-pencil" aria-hidden="true"></span>Edit</a>';
    }else{
      tempStr = '<div><span class="fa fa-pencil" aria-hidden="true"></span>Edit</div>';
    }

    return tempStr;
  }

  getDeleteTemp(data, full){
    let tempStr = ''

    if(this.isCompanyDeletePermission){
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

      this.isCompanyCreatePermission = this.permissions.indexOf('create_companies') > -1;
      this.isCompanyEditPermission = this.permissions.indexOf('edit_companies') > -1;
      this.isCompanyDeletePermission = this.permissions.indexOf('delete_companies') > -1;
    }

    this.loadCompanyStatusList();
    this.multiSelectSelectSettings = DropDownSetting.multiSelectSelectSettings;
    this.singleSelectSettings = DropDownSetting.singleSelectSettings;

    this.textSetting = DropDownSetting.textSetting;
    this.companyListDTConfig = {
      "columnDefs": [{
        "className": "text-right",
        "orderable": false,
        "targets": 4,
        "render": function (data, type, full, meta) {
          var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
             self.getEditTemp(data, full)+
             self.getDeleteTemp(data, full)+
            '</div>';
          return template;
        }
      }],
      "columns": [
        { "title": 'Name', "data": "name" },
        { "title": 'Type', "data": "type" },
        { "title": 'Status', "data": "companyStatus.name" },
        { "title": 'Address', "data": "address" },
        { "title": '', "data": "companyId" }
      ],
      "apiURL": PathConfig.BASE_URL_API + PathConfig.COMPANY_LIST
    }
  }
  // on Menu Selection
  onCompanyTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'edit') {
      //this.loadCompanyStatusList();
      this.resetForm();
      this.editCompany(data['value']);

    } else if (data['clickedOn'] == 'delete') {
      //delete code here
      //confirm box
      let dialogRef = this.dialog.open(ConfirmDialogComponent);
      this.companyListRefresh = false;
      dialogRef.afterClosed().subscribe(result => {
        if (result == "OK") {
          this.deleteCompanyOnConfirm(JSON.parse(data['value']));

        }
      });


    }

  }

  //back to table button
  backToTable() {
    this.showForm = false;
    this.editMode = false;
    this.companyListRefresh = true;
    this.selectedCompanyStatus = [];
    this.companyOptionsModel = [];
    this.companyTypeOptionsModel = [];
  }


  //search dropdown list
  comapanySearchTableDD: Array<Object> = [
    { "id": 0, "labelName": "Name" },
    { "id": 1, "labelName": "Type" },
    { "id": 2, "labelName": "Status" },
    { "id": 3, "labelName": "Address" }
  ];
  //company dropdown Event
  companySearchEvent(data) {
    this.companyListSearchModel['searchTxt'] = data['text'];
    this.companyListSearchModel['colIndex'] = data['dd-id'];
  }







  editCompany(companyId) {
    let url = PathConfig.BASE_URL_API + PathConfig.COMPANY_ADD + companyId;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.newCompany = data;
          this.selectedCompanyStatus = data.companyStatus.companyStatusId;
          this.companyOptionsModel[0] = data.companyStatus.companyStatusId;
          if (data.type === 'Agency') {
            this.companyTypeOptionsModel[0] = 1;
          } else {
            this.companyTypeOptionsModel[0] = 2;
          }
          this.showForm = true;
          this.editMode = true;
        }
      });
    return data;
  }




  // delete company

  deleteCompanyOnConfirm(obj) {
    let url = PathConfig.BASE_URL_API + PathConfig.COMPANY_DELETE + obj['companyId'];
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


  resetForm() {
    this.newCompany = new Company();
    this.selectedCompanyStatus = [];
    this.companyOptionsModel = [];
    this.companyTypeOptionsModel = [];

    this.errorMessage = "";
    /*for (var i in this.companyForm.controls) {
      this.removeValidators(this.companyForm.controls[i], true);
    }*/

    //  this.selectedRoleNameDD = { 'roleName': '-- SELECT -- ' };
  }

  toggleForm() {
    this.resetForm();
    this.showForm = !this.showForm;
    this.newCompany = new Company();
    this.editMode = false;
    //this.loadCompanyStatusList();
  }

  //load Company Status
  loadCompanyStatusList() {
    if (this.authService.isLoggedIn) {
      var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let url = PathConfig.BASE_URL_API + PathConfig.COMPANY_STATUS;
      var data = this.httpService.get(url, auth_token);
      let self = this;
      data.subscribe(
        data => {
          if (data) {
            this.companyStatusList = data;
            this.comapanyOptions = DropDownDataFilter.convertDataIntoDropdown(data, 'companyStatusId', 'name');
            let selectedValue = data.filter(function (value) { if (value['isSelected']) { return value; } });
            if (selectedValue.length > 0) {
              this.companyOptionsModel = selectedValue.map(function (value) { if (value['isSelected'] == true) return value['companyStatusId'] });
            } else {
              this.companyOptionsModel = [];
            }


            this.comapanyTypeOptions = DropDownDataFilter.convertDataIntoDropdown(this.companyType, 'id', 'name');
            let companyTypeSelectedValue = data.filter(function (value) { if (value['isSelected']) { return value; } });
            if (companyTypeSelectedValue.length > 0) {
              this.companyTypeOptionsModel = companyTypeSelectedValue.map(function (value) { if (value['isSelected'] == true) return value['id'] });
            } else {
              this.companyTypeOptionsModel = [];
            }

          }
        });
      return data;
    }
  }

  convertDataIntoDropdown(array, propId, propLabel) {
    let newArr = array.map(function (val, index) {
      return { 'id': val[propId], 'name': val[propLabel] };
    });
    return newArr;

  }

  //Form Validators Form

  ngAfterViewChecked() {
    //explicit change detection to avoid "expression-has-changed-after-it-was-checked-error"
    this.cdr.detectChanges();
  }

  addRequiredValidatorRunTime(obj) {
    for (let item in obj.controls) {
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
  saveForm() {
    //this.addRequiredValidatorRunTime(formData);

    let url = PathConfig.BASE_URL_API + PathConfig.COMPANY_ADD;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    var _selected = this.companyOptionsModel;
    let _status = this.companyStatusList.filter(function (value) {
      if (value['companyStatusId'] == _selected) {
        return value;
      }
    })[0];

    this.newCompany.companyStatus = _status;

    var _selectedCompanyType = this.companyTypeOptionsModel;
    let _companyType = this.companyType.filter(function (value) {
      if (value['id'] == _selectedCompanyType) {
        return value;
      }
    })[0];

    this.newCompany.type = _companyType['name'];
    this.newCompany.customer = this.customer;
    if (this.editMode) {
        var data = this.httpService.put(url, this.newCompany, auth_token);
      } else {
        var data = this.httpService.post(url, this.newCompany, auth_token);
    }
    let self = this;
    data.subscribe(
      data => {
        this.backToTable();
      }, error => {
        this.errorMessage = error;
      });
    this.newCompany = new Company();
    return data;
  }

  keypressEventHandler(event){
    if ((event.keyCode > 64 && event.keyCode < 91) || (event.keyCode > 96 && event.keyCode < 123) || event.keyCode == 8 || event.keyCode == 32)
       return true;
    else
       {
           this.errorMessage = "Only characters are allowed";
           var ref = this;
           setTimeout(function(){
             ref.errorMessage = "";
           }, 3000);

           return false;
       }
  }
}
