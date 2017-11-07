import { Component, Input, EventEmitter, OnChanges, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectorRef } from '@angular/core';
import { HttpService } from '../../../common/services/http.service';
import { PathConfig } from '../../../common/config/path.config';
import { ConstantConfig } from '../../../common/config/constant.config';
import { LocalStorageService } from "../../../common/services/local-storage.service";
import { Broadcaster } from '../../../common/services/broadcaster.service';
import { AuthService } from '../../../common/services/auth.service';
import { Contact } from '../../../common/models/contact';
import { Company } from '../../../common/models/company';

import * as _ from 'lodash';
import { MdDialog, MdDialogRef } from '@angular/material';
import { ConfirmDialogComponent } from "../../../common/modules/dialog/confirm/confirm.component";
import { IMultiSelectOption } from "angular-2-dropdown-multiselect";
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { IMultiSelectTexts } from "angular-2-dropdown-multiselect";
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { DropDownDataFilter } from '../../../common/filter/dropdown.filter';
import { DropDownSetting } from '../../../common/utils/dropdown.settings';

@Component({
  templateUrl: './manage-contact.component.html',
  styleUrls: ['./manage-contact.component.scss'],
})
export class ManageContactComponent implements OnInit {

  contactForm: FormGroup;
  errorMessage: string;
  companyList: Array<Object> = [];
  newContact: Contact = new Contact();
  showingAddSection: Boolean = new Boolean();
  showForm: Boolean = false;
  editMode: Boolean = false;
  selectedCompany: any;
  contactListDTConfig: Object = {};
  contactListRefresh: Boolean = false;
  comapanyOptions: IMultiSelectOption[];
  companyOptionsModel: number[];
  singleSelectSettings: IMultiSelectSettings;
  multiSelectSelectSettings: IMultiSelectSettings;
  textSetting: IMultiSelectTexts;
  contactListSearchModel: any = {};

  permissions = null;
  isContactCreatePermission: boolean = false;
  isContactViewPermission: boolean = false;
  isContactEditPermission: boolean = false;
  isContactDeletePermission: boolean = false;

  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService,
    private elementRef: ElementRef,
    private dialog: MdDialog,
    private contactFr: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {
    this.contactForm = contactFr.group({
      "contactName": ["", Validators.required],
      "contactEmail": ["", Validators.required],
      "company": [this.companyOptionsModel, Validators.required],
      "contactAddress": [],
      "contactMobile": [],
      "contactPhone": [],
    });
  }

  backToTable() {
    this.showForm = false;
    this.editMode = false;
    this.contactListRefresh = true;
    this.selectedCompany = [];
    this.companyOptionsModel = [];
    this.newContact = new Contact();
  }

  getEditTemp(data, full){
    let tempStr = ''

    if(this.isContactEditPermission){
      tempStr = '<a href="javascript:void(0);" data-name="edit" data-custom="' + data + '"><span class="fa fa-pencil" aria-hidden="true"></span>Edit</a>';
    }else{
      tempStr = '<div><span class="fa fa-pencil" aria-hidden="true"></span>Edit</div>';
    }

    return tempStr;
  }

  getDeleteTemp(data, full){
    let tempStr = ''

    if(this.isContactDeletePermission){
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

      this.isContactCreatePermission = this.permissions.indexOf('create_contacts') > -1;
      this.isContactEditPermission = this.permissions.indexOf('edit_contacts') > -1;
      this.isContactDeletePermission = this.permissions.indexOf('delete_contacts') > -1;
    }

    this.loadCompanyList();
    this.multiSelectSelectSettings = DropDownSetting.multiSelectSelectSettings;
    this.singleSelectSettings = DropDownSetting.singleSelectSettings;

    this.textSetting = DropDownSetting.textSetting;
    this.contactListDTConfig = {
      "columnDefs": [
        {
          "className": "text-right",
          "orderable": false,
          "targets": 5,
          "render": function (data, type, full, meta) {
            var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
               self.getEditTemp(data, full) +
               self.getDeleteTemp(data, full)+
              '</div>';
            return template;
          }
        }],
      "columns": [
        { "title": 'Name', "data": "contactName" },
        { "title": 'Email', "data": "contactEmail" },
        { "title": 'Address', "data": "contactAddress" },
        { "title": 'Mobile', "data": "contactMobile" },
        { "title": 'Company', "data": "company.name" },
        { "title": '', "data": "contactId" }
      ],
      "apiURL": PathConfig.BASE_URL_API + PathConfig.CONTACT_LIST
    }
  }

  // on Menu Selection
  onContactTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'edit') {
      //edit code here
      this.editContact(data['value']);

    } else if (data['clickedOn'] == 'delete') {
      //delete code here
      //confirm box
      let dialogRef = this.dialog.open(ConfirmDialogComponent);
      this.contactListRefresh = false;
      dialogRef.afterClosed().subscribe(result => {
        if (result == "OK") {
          this.deleteContactOnConfirm(JSON.parse(data['value']));

        }
      });
    }
  }

  ngAfterViewInit() {
    let selfAngular = this;

  }



  //data table search with drop down functionality
  contactSearchTableDD: Array<Object> = [
    { "id": 0, "labelName": "Name" },
    { "id": 1, "labelName": "Email" },
    { "id": 2, "labelName": "Address" },
    { "id": 3, "labelName": "Mobile" },
    { "id": 4, "labelName": "Company" }

  ];


  //company dropdown Event
  contactSearchEvent(data) {
    this.contactListSearchModel['searchTxt'] = data['text'];
    this.contactListSearchModel['colIndex'] = data['dd-id'];
  }


  contactSearchSelectedDD: Object = this.contactSearchTableDD[0];





  toggleForm() {
    this.showForm = !this.showForm;
    this.editMode = false;
    this.companyOptionsModel = [];
  }


  //load company
  loadCompanyList() {
    if (this.authService.isLoggedIn) {
      var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let url = PathConfig.BASE_URL_API + PathConfig.COMPANY_LIST;
      var data = this.httpService.get(url, auth_token);
      let self = this;
      data.subscribe(
        data => {
          if (data) {
            this.companyList = data;
            this.comapanyOptions = DropDownDataFilter.convertDataIntoDropdown(data, 'companyId', 'name');
            let selectedValue = data.filter(function (value) { if (value['isSelected']) { return value; } });
            if (selectedValue.length > 0) {
              this.companyOptionsModel = selectedValue.map(function (value) { if (value['isSelected'] == true) return value['companyId'] });
            } else {
              this.companyOptionsModel = [];
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

  //to edit contact
  editContact(contactId) {
    let url = PathConfig.BASE_URL_API + PathConfig.CONTACT_ADD + contactId;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.newContact = data;
          this.selectedCompany = data.company.companyId;
          this.companyOptionsModel[0] = data.company.companyId;
          this.showForm = true;
          this.editMode = true;

        }
      });
    return data;
  }


  // delete contact

  deleteContactOnConfirm(obj) {
    let url = PathConfig.BASE_URL_API + PathConfig.CONTACT_DELETE + obj['contactId'];
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.deleteWithId(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        self.backToTable();
      });
    return data;
  }



  // showing cretive form when user click on add new or edit the cretive
  showAddNewCretiveForm() {
    this.showingAddSection = true;
    this.newContact = new Contact();
  }

  // reset the form
  resetForm() {
    this.newContact = new Contact();
    this.selectedCompany = [];
    this.companyOptionsModel = [];
    this.errorMessage = "";
    for (var i in this.contactForm.controls) {
      this.removeValidators(this.contactForm.controls[i], true);
    }
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
  saveForm(formData: any) {
    this.addRequiredValidatorRunTime(formData);
    if (formData.valid) {
      let url = PathConfig.BASE_URL_API + PathConfig.CONTACT_ADD;
      let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      var _selected = this.companyOptionsModel;
      let _data: any = this.companyList.filter(function (value) {
        if (value['companyId'] == _selected) {
          return value;
        }
      })[0];
      this.newContact.company = _data;
      if (this.editMode) {
        var data = this.httpService.put(url, this.newContact, auth_token);
      } else {
        var data = this.httpService.post(url, this.newContact, auth_token);
      }
      let self = this;
      data.subscribe(
        data => {
          this.backToTable();
        }, error => {
          this.errorMessage = error;
        });
      this.newContact = new Contact();
      return data;
    } else {
      this.errorMessage = "Please fill in required fields";
    }
  }




}
