import { Component, Input, EventEmitter, OnChanges, OnInit, ViewChild, ElementRef, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { HttpService } from '../../../common/services/http.service';
import { PathConfig } from '../../../common/config/path.config';
import { ConstantConfig } from '../../../common/config/constant.config';
import { LocalStorageService } from "../../../common/services/local-storage.service";
import { Broadcaster } from '../../../common/services/broadcaster.service';
import { AuthService } from '../../../common/services/auth.service';
import { Creative } from '../../../common/models/creative';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { DropDownSetting } from '../../../common/utils/dropdown.settings';
import { Router, ActivatedRoute } from '@angular/router';

import * as $ from 'jquery';

import * as _ from 'lodash';
import { MdDialog, MdDialogRef } from '@angular/material';
import { ConfirmDialogComponent } from "../../../common/modules/dialog/confirm/confirm.component";

@Component({
  templateUrl: './manage-creative.component.html',
  styleUrls: ['./manage-creative.component.scss']
})
export class ManageCreativeComponent implements OnInit {

  //ReactiveFormsModule 
  creativeForm: FormGroup;
  //End
  newCreative: Creative = new Creative();
  showingAddSection: Boolean = new Boolean();
  showForm: Boolean = false;
  editMode: Boolean = false;
  creativeListDTConfig: Object = {};
  creativeListRefresh: Boolean = false;
  creativeListData: Array<Object> = [];
  creativeListSearchModel: any = {};
  errorMessage: string;
  successMessage: string;

  permissions = null;
  isCreativeCreatePermission: boolean = false;
  isCreativeViewPermission: boolean = false;
  isCreativeEditPermission: boolean = false;
  isCreativeDeletePermission: boolean = false;

  faleToUpload = null;
  defaultSelectedTypeModel = ["Standard"];
  defaultSelectedInsertionPointModel = ["Preroll"];

  creativeTypeOptions: Array<Object> = [{
    "id": "Standard",
    "name": "Standard"
  }, {
    "id": "Thirdparty",
    "name": "Third Party"
  }, {
    "id": "Placeholder",
    "name": "Place Holder"
  }, {
    "id": "SpotPlaceholder",
    "name": "Spot Place Holder"
  }];

  insertionPointOptions: Array<Object> = [{
    "id": "Preroll",
    "name": "Preroll"
  }, {
    "id": "Midroll",
    "name": "Midroll"
  }, {
    "id": "Postroll",
    "name": "Postroll"
  }];

  singleSelectSettings: IMultiSelectSettings;

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
    private router: Router,
    private activatedRouter: ActivatedRoute,
  ) {
    this.creativeForm = fb.group({
      "creativeName": "",
      "creativeType": "",
      "insertionPoint": "",
      "destFile": "",
      "vpaidStrict": "",
      "vpaidCountdown": "",
      "insertionPointHidden": ""
    });
  }


  backToTable() {
    this.showForm = false;
    this.creativeListRefresh = true;

  }

  editCreative(creativeId) {
    let url = PathConfig.BASE_URL_API + PathConfig.CREATIVE_ADD + creativeId;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.newCreative = data;
          this.showForm = true;
          this.editMode = true;

        }
      });
    return data;

  }

  getCreativeList() {
    let url = PathConfig.BASE_URL_API + PathConfig.CREATIVE_LIST_GET;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.creativeListData = data;
        }
      });
  }

  getEditTemp(data, full){
    let tempStr = ''

    if(this.isCreativeEditPermission){
      tempStr = '<a href="javascript:void(0);" data-name="edit" data-custom="' + data + '"><span class="fa fa-pencil" aria-hidden="true"></span>Edit</a>';
    }else{
      tempStr = '<div><span class="fa fa-pencil" aria-hidden="true"></span>Edit</div>';
    }

    return tempStr;
  }

  getDeleteTemp(data, full){
    let tempStr = ''

    if(this.isCreativeDeletePermission){
      tempStr = "<a href='javascript:void(0);' data-name='delete' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>"  ;
    }else{
      tempStr = "<div><span class='fa fa-trash' aria-hidden='true'></span>Delete</div>"  ;
    }

    return tempStr;
  }

  ngOnInit() {

    let self = this;

    this.permissions = this.localStorage.get('user_permissions');

    if(this.permissions && this.permissions.length){

      this.isCreativeCreatePermission = this.permissions.indexOf('create_creatives') > -1;
      this.isCreativeEditPermission = this.permissions.indexOf('edit_creatives') > -1;
      this.isCreativeDeletePermission = this.permissions.indexOf('delete_creatives') > -1;
    }

    this.singleSelectSettings = DropDownSetting.singleSelectSettings;

    this.getCreativeList();

    this.creativeListDTConfig = {
      "columns": [
        { "title": 'Name', "data": "name" },
        { "title": 'Type', "data": "type" },
        { "title": 'Insertion Point', "data": "insertionpoint" },
        { "title": 'Click Destination Uri', "data": "clickDestinationUri" },
        { "title": 'VPA Id Strict', "data": "vpaidStrict" },
        { "title": 'VPA Id Countdown', "data": "vpaidCountdown" }
      ]
    }

  }

  // on Menu Selection
  onCreativeTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'edit') {
      //edit code here
      this.resetForm();
      this.editCreative(data['value']);
      console.log(data['value']);
    } else if (data['clickedOn'] == 'delete') {
      //delete code here
      //confirm box
      let dialogRef = this.dialog.open(ConfirmDialogComponent);
      this.creativeListRefresh = false;
      dialogRef.afterClosed().subscribe(result => {
        if (result == "OK") {
          this.deleteCreativeOnConfirm(JSON.parse(data['value']));

        }
      });
    }
  }




  ngAfterViewInit() {
    let selfAngular = this;

  }


  //search dropdown list
  creativeSearchTableDD: Array<Object> = [
    { "id": 0, "labelName": "Name" },
    { "id": 1, "labelName": "Width 1" },
    { "id": 2, "labelName": "Height 1" },
    { "id": 3, "labelName": "Width 2" },
    { "id": 4, "labelName": "Height 2" }
  ];
  //company dropdown Event
  creativeSearchEvent(data) {
    console.log(data);
    this.creativeListSearchModel['searchTxt'] = data['text'];
    this.creativeListSearchModel['colIndex'] = data['dd-id'];
  }





  toggleForm() {
    this.resetForm();
    this.showForm = !this.showForm;
  }



  // delete creative
  deleteCreativeOnConfirm(obj) {
    let url = PathConfig.BASE_URL_API + PathConfig.CREATIVE_DELETE + obj['creativeId'];
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.deleteWithId(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        this.backToTable();
      });
    return data;
  }




  // showing cretive form when user click on add new or edit the cretive
  showAddNewCretiveForm() {
    this.showingAddSection = true;
    this.newCreative = new Creative();
  }

  // reset the form
  resetForm() {
    this.newCreative = new Creative();
    this.errorMessage = "";
    for (var i in this.creativeForm.controls) {
      this.removeValidators(this.creativeForm.controls[i], true);
    }
  }

  //Form Validators Form

  ngAfterViewChecked() {
    //explicit change detection to avoid "expression-has-changed-after-it-was-checked-error"
    this.cdr.detectChanges();
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

  saveObj = {};
  saveForm(formData: any) {

    if(formData.value.creativeName && formData.value.creativeType.length){
        if (this.isStandarAndFileuploaded(formData.value)) {
          this.uploadFile(formData);
        }else if(this.isFormValid(formData.value)){
          this.saveFormData(formData, '');
        }
    }else{
      this.errorMessage = "Provide requied fields";
      return;
    }
  }

  isFormValid(obj){
    let flag = false;

    if((obj.creativeType.length && obj.creativeType[0] == 'Standard') || (obj.creativeType.length && obj.creativeType[0] == 'SpotPlaceholder')){
      if(obj.insertionPoint && obj.insertionPoint.length){
        flag = true;
      }else{
        this.errorMessage = "Provide requied fields";
      }
      
    }else{
      flag = true;
    }

    return flag;
  }

  isStandarAndFileuploaded(obj){
    let flag = false;

    if(this.faleToUpload && obj.creativeType.length && obj.creativeType[0] == 'Standard'){
      flag = true;
    }else{
      flag = false;
      this.errorMessage = "Provide requied fields";
    }

    return flag;
  }

  uploadFile(formData){
    if (formData) {
      let url = PathConfig.BASE_URL_API + PathConfig.UPLOAD_CREATIVE_ASSET;
      let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);

      let fileDescriptor: FormData = new FormData();

      fileDescriptor.append('file', this.faleToUpload);

      var fileUpload = this.httpService.postFileUpload(url, fileDescriptor, auth_token);
      fileUpload.subscribe(
        fileUpload => {
          this.saveFormData(formData, fileUpload.assetId)
        }, error => {
          if (error) {
            this.errorMessage = error;
          }
        });
    }
  }

  saveFormData(formData, assetid){
    this.errorMessage = "";
      let url = PathConfig.BASE_URL_API + PathConfig.CREATE_CREATIVE;
      let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);

      
      this.saveObj['name'] = formData.value.creativeName;
      this.saveObj['type'] = formData.value.creativeType && formData.value.creativeType.length ? formData.value.creativeType[0] : '';
      this.saveObj['insertionpoint'] = formData.value.insertionPoint && formData.value.insertionPoint.length ? formData.value.insertionPoint[0] : '';
      this.saveObj['clickDestinationUri'] = '';
      this.saveObj['vpaidStrict'] = formData.value['vpaidStrict'] ? true : false;
      this.saveObj['vpaidCountdown'] = formData.value['vpaidCountdown'] ? true : false;
      this.saveObj['assetid'] = assetid ? assetid : '';


      var data = this.httpService.post(url, this.saveObj, auth_token);
      let self = this;
      data.subscribe(
        data => {
          if (data) {
            let savedObj = _.clone(this.saveObj, true);
            this.saveObj = {};
            this.errorMessage = "";
            this.successMessage = "Creative saved successfully";

            this.creativeListData.push(savedObj);

            let self = this;

            setTimeout(function(){
              self.successMessage = "";
              self.showForm = false;
            }, 3000);
            console.log("Success result: ", data);
          }
        }, error => {
          this.errorMessage = error;
          console.log("Error result: ", error);
        });
      return data;
  }

  fileChange(event) {
    let fileList: FileList = event.target.files;
    this.faleToUpload = fileList[0];
  }

  OnlyAlphabets(event) {
    if ((event.keyCode > 64 && event.keyCode < 91) || (event.keyCode > 96 && event.keyCode < 123) || event.keyCode == 8 || event.keyCode == 32)
      return true;
    else
      return false;
  }
}
