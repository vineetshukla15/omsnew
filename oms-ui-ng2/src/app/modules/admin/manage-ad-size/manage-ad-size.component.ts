import { Component, Input, EventEmitter, OnChanges, OnInit, ViewChild, ElementRef, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { HttpService } from '../../../common/services/http.service';
import { PathConfig } from '../../../common/config/path.config';
import { ConstantConfig } from '../../../common/config/constant.config';
import { LocalStorageService } from "../../../common/services/local-storage.service";
import { Broadcaster } from '../../../common/services/broadcaster.service';
import { AuthService } from '../../../common/services/auth.service';
import { Creative } from '../../../common/models/creative';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

import * as $ from 'jquery';

import * as _ from 'lodash';
import { MdDialog, MdDialogRef } from '@angular/material';
import { ConfirmDialogComponent } from "../../../common/modules/dialog/confirm/confirm.component";

@Component({
  templateUrl: './manage-ad-size.component.html',
  styleUrls: ['./manage-ad-size.component.scss']
})
export class ManageAdSizeComponent implements OnInit {

  //ReactiveFormsModule 
  creativeForm: FormGroup;
  //End
  newCreative: Creative = new Creative();
  showingAddSection: Boolean = new Boolean();
  showForm: Boolean = false;
  editMode: Boolean = false;
  creativeListDTConfig: Object = {};
  creativeListRefresh: Boolean = false;
  creativeListSearchModel: any = {};
  errorMessage: string;

  permissions = null;
  isAdSizeCreatePermission: boolean = false;
  isCreativeViewPermission: boolean = false;
  isCreativeEditPermission: boolean = false;
  isCreativeDeletePermission: boolean = false;

  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService,
    private elementRef: ElementRef,
    private dialog: MdDialog,
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {
    this.creativeForm = fb.group({
      'name': "",
      "width1": "",
      "height1": "",
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

      this.isAdSizeCreatePermission = this.permissions.indexOf('create_ad_size') > -1;
      this.isCreativeEditPermission = this.permissions.indexOf('edit_creatives') > -1;
      this.isCreativeDeletePermission = this.permissions.indexOf('delete_creatives') > -1;
    }

    this.creativeListDTConfig = {
      "columnDefs": [
        {
          "className": "text-right",
          "orderable": false,
          "targets": 5,
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
        { "title": 'Width 1', "data": "width1" },
        { "title": 'Height 1', "data": "height1" },
        { "title": 'Width 2', "data": "width2" },
        { "title": 'Height 2', "data": "height2" },
        { "title": '', "data": "creativeId" }
      ],
      "apiURL": PathConfig.BASE_URL_API + PathConfig.CREATIVE_LIST
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

  saveForm(formData: any) {
    this.addRequiredValidatorRunTime(formData);

    if (formData.valid) {
      this.errorMessage = "";
      let url = PathConfig.BASE_URL_API + PathConfig.CREATIVE_ADD;
      let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      if (this.editMode) {
        var data = this.httpService.put(url, this.newCreative, auth_token);
      } else {
        var data = this.httpService.post(url, this.newCreative, auth_token);
      }
      let self = this;
      data.subscribe(
        data => {
          if (data) {
            this.backToTable();
          }
        }, error => {
          this.errorMessage = error;
        });
      return data;
    } else {
      this.errorMessage = "Please fill in required fields";
    }

  }

  OnlyAlphabets(event) {
    if ((event.keyCode > 64 && event.keyCode < 91) || (event.keyCode > 96 && event.keyCode < 123) || event.keyCode == 8 || event.keyCode == 32)
      return true;
    else
      return false;
  }
}
