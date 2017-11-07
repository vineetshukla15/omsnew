import {
  Component, OnInit, AfterViewInit, ChangeDetectorRef
} from '@angular/core';
import { HttpService } from '../../../common/services/http.service';
import { PathConfig } from '../../../common/config/path.config';
import { ConstantConfig } from '../../../common/config/constant.config';
import { LocalStorageService } from "../../../common/services/local-storage.service";
import { Broadcaster } from '../../../common/services/broadcaster.service';
import { AuthService } from '../../../common/services/auth.service';
import { AdUnit } from '../../../common/models/ad.unit';
import { Segment } from '../../../common/models/segment';
import { MdDialog, MdDialogRef } from '@angular/material';
import { ConfirmDialogComponent } from "../../../common/modules/dialog/confirm/confirm.component";
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

declare var $: any;

@Component({
  templateUrl: './manage-adunit.component.html',
  styleUrls: ['./manage-adunit.component.scss'],
})
export class ManageAdUnitComponent implements OnInit {

  showForm: Boolean = false;
  adUnitForm: FormGroup;
  adUnitListDTConfig: Object = {};
  adUnitListRefresh: Boolean = false;
  adUnitListSearchMOdel: any = {};
  newadUnit: AdUnit = new AdUnit();
  editMode: Boolean = false;
  errorMessage: string;
  errorMessageRequiredField: boolean = false;
  showInactiveAdUnit: Boolean = false;

  permissions = null;
  isAdUnitCreatePermission: boolean = false;
  isAdUnitViewPermission: boolean = false;
  isAdUnitEditPermission: boolean = false;
  isAdUnitDeletePermission: boolean = false;

  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService,
    private dialog: MdDialog,
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {
    this.adUnitForm = fb.group({
      'name': "",
      'displayName': "",
    });
  }

  getEditTemp(data, full){
    let tempStr = ''

    if(this.isAdUnitEditPermission){
      tempStr = '<a href="javascript:void(0);" data-name="edit" data-custom="' + data + '"><span class="fa fa-pencil" aria-hidden="true"></span>Edit</a>';
    }else{
      tempStr = '<div><span class="fa fa-pencil" aria-hidden="true"></span>Edit</div>';
    }

    return tempStr;
  }

  getDeleteTemp(data, full){
    let tempStr = ''

    if(this.isAdUnitDeletePermission){
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

      this.isAdUnitCreatePermission = this.permissions.indexOf('create_ad_units') > -1;
      this.isAdUnitEditPermission = this.permissions.indexOf('edit_ad_units') > -1;
      this.isAdUnitDeletePermission = this.permissions.indexOf('delete_ad_units') > -1;
    }

    this.adUnitListDTConfig = {
      "columnDefs": [
        {
          "targets": 2,
          "render": function (data, type, full, meta) {
            var value = "";
            if (data == true)
              value = "Active";
            else
              value = "Inactive";
            var template = '<div>' + value + '</div>';
            return template;
          }
        },
        {
          "className": "text-right",
          "orderable": false,
          "targets": 3,
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
        { "title": 'Display Name', "data": "displayName" },
        { "title": "Status", "data": "active" },
        { "title": '', "data": "adUnitId" }
      ],
      "apiURL": PathConfig.BASE_URL_API + PathConfig.AD_UNIT_LIST
    }
  }
  // on Menu Selection
  onadUnitTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'edit') {
      //edit code here
      this.resetForm();
      this.editAdUnit(data['value']);

    } else if (data['clickedOn'] == 'delete') {
      //delete code here
      //confirm box
      let dialogRef = this.dialog.open(ConfirmDialogComponent);
      this.adUnitListRefresh = false;
      dialogRef.afterClosed().subscribe(result => {
        if (result == "OK") {
          this.deleteAdunitOnConfirm(JSON.parse(data['value']));
        }
      });
    }

  }


  toggle() {    
    this.resetForm();    
    this.newadUnit.active = true;
    this.showForm = true;
  }


  //back to table button
  backToTable() {
    this.showForm = false;
    this.adUnitListRefresh = true;
    this.showInactiveAdUnit = false;
    this.adUnitListSearchMOdel = {};
  }


  //search dropdown list
  adUnitSearchTableDD: Array<Object> = [
    { "id": 0, "labelName": "Name" },
    { "id": 1, "labelName": "Display Name" }
  ];
  //adUnit dropdown Event
  adUnitSearchEvent(data) {
    this.adUnitListSearchMOdel['searchTxt'] = data['text'];
    this.adUnitListSearchMOdel['colIndex'] = data['dd-id'];
  }

  // delete adUnit
  deleteAdunitOnConfirm(obj) {
    let url = PathConfig.BASE_URL_API + PathConfig.AD_UNIT_DELETE + obj['adUnitId'];;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.deleteWithId(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.backToTable();
        }
      });
    return data;
  }


  editAdUnit(id) {
    this.errorMessageRequiredField = false;
    let url = PathConfig.BASE_URL_API + PathConfig.AD_UNIT_GET_BY_ID + id;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.newadUnit = data;
          this.showForm = true;
          this.editMode = true;
        }
      });
    return data;
  }

  // save the form
  saveForm(formData: any) {
    this.errorMessageRequiredField = false;
    this.addRequiredValidatorRunTime(formData);

    if (formData.valid) {
      this.errorMessage = "";
      let url = PathConfig.BASE_URL_API + PathConfig.AD_UNIT_ADD;
      let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      if (this.editMode) {
        var data = this.httpService.put(url, this.newadUnit, auth_token);
      } else {
        var data = this.httpService.post(url, this.newadUnit, auth_token);
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
      this.newadUnit = new AdUnit();
      return data;
    } else {
      this.errorMessageRequiredField = false;
      this.errorMessage = "Please fill in required fields";
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

  resetForm() {
    this.newadUnit = new AdUnit();
    this.editMode = false;
    this.newadUnit.active = false;
    this.errorMessage = "";
    for (var i in this.adUnitForm.controls) {
      this.removeValidators(this.adUnitForm.controls[i], true);
    }
  }

  showInactiveAdUnits() {
    var activeData = {
      "dd-id": 2,
      "text": false
    }
    if (this.showInactiveAdUnit) {
      this.adUnitSearchEvent(activeData);
    } else {
      this.showActiveAdUnit();
    }


  }

  showActiveAdUnit() {
    var activeData = {
      "dd-id": 2,
      "text": true
    }
    if (!this.showInactiveAdUnit) {
      this.adUnitSearchEvent(activeData);
    }
  }

  keypressEventHandler(event){
    if ((event.keyCode > 64 && event.keyCode < 91) || (event.keyCode > 96 && event.keyCode < 123) || event.keyCode == 8 || event.keyCode == 32)
       return true;
    else
       {
           this.errorMessage = "Only characters are allowed";
           return false;
       }
  }

}
