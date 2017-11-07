import {
  Component, OnInit, AfterViewInit, ChangeDetectorRef
} from '@angular/core';
import { HttpService } from '../../../common/services/http.service';
import { PathConfig } from '../../../common/config/path.config';
import { ConstantConfig } from '../../../common/config/constant.config';
import { LocalStorageService } from "../../../common/services/local-storage.service";
import { Broadcaster } from '../../../common/services/broadcaster.service';
import { AuthService } from '../../../common/services/auth.service';
import { User } from '../../../common/models/user';
import { Role } from '../../../common/models/role';
import { Segment } from '../../../common/models/segment';
import { MdDialog, MdDialogRef } from '@angular/material';
import { ConfirmDialogComponent } from "../../../common/modules/dialog/confirm/confirm.component";
import { UserSearchDialog } from "../manage-user/dialog/user-search/user-search.component";
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

declare var $: any;

@Component({
  templateUrl: './manage-role.component.html',
  styleUrls: ['./manage-role.component.scss'],
})
export class ManageRoleComponent implements OnInit {

  //ReactiveFormsModule 
  roleForm: FormGroup;

  showForm: Boolean = false;
  roleListDTConfig: Object = {};
  roleListRefresh: Boolean = false;
  roleListSearchModel: any = {};
  editMode: Boolean = false;
  showingAddSection: Boolean = new Boolean();
  newRole: Role = new Role();
  highLevelWithLowLevelPermissionList: Array<Role> = [];
  showButton: Boolean = false;
  errorMessage: string;
  showInactiveRole: Boolean = false;

  permissions = null;
  isRoleCreatePermission: boolean = false;
  isRoleViewPermission: boolean = false;
  isRoleEditPermission: boolean = false;
  isRoleDeletePermission: boolean = false;

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
    this.roleForm = fb.group({
      'roleName': ""
    });
  }

  getEditTemp(data, full){
    let tempStr = ''

    if(this.isRoleEditPermission){
      tempStr = '<a href="javascript:void(0);" data-name="edit" data-custom="' + data + '"><span class="fa fa-pencil" aria-hidden="true"></span>Edit</a>';
    }else{
      tempStr = '<div><span class="fa fa-pencil" aria-hidden="true"></span>Edit</div>';
    }

    return tempStr;
  }

  getDeleteTemp(data, full){
    let tempStr = ''

    if(this.isRoleDeletePermission){
      tempStr = "<a href='javascript:void(0);' data-name='delete' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>" ;
    }else{
      tempStr = "<div><span class='fa fa-trash' aria-hidden='true'></span>Delete</div>" ;
    }

    return tempStr;
  }

  ngOnInit() {

    let self = this;

    this.permissions = this.localStorage.get('user_permissions');

    if(this.permissions && this.permissions.length){

      this.isRoleCreatePermission = this.permissions.indexOf('create_roles') > -1;
      this.isRoleEditPermission = this.permissions.indexOf('edit_roles') > -1;
      this.isRoleDeletePermission = this.permissions.indexOf('delete_roles') > -1;
    }

    this.roleListDTConfig = {
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
          "targets": 3,
          "render": function (data, type, full, meta) {
            var value = "";
            for (var i in data) {


              if (data.length === 1) {
                value += data[i].permissionName;
              } else {
                value += data[i].permissionName + ", ";
              }

              if (value.length > 25) {
                value += " ...";
                break;
              }
            }
            var template = '<div>' + value + '</div>';
            return template;
          }
        },
        {
          "className": "text-right",
          "orderable": false,
          "targets": 4,
          "render": function (data, type, full, meta) {
            let fulldata = JSON.stringify(full);
            var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
              self.getEditTemp(data, full) +
              self.getDeleteTemp(data, full) +
              '</div>';
            return template;
          }
        }],
      "columns": [
        { "title": 'Name', "data": "roleName" },
        { "title": 'Description', "data": "roleDesc" },
        { "title": 'Status', "data": "active" },
        { "title": 'Permissions', "data": "permissions" },
        { "title": '', "data": "roleId" }
      ],
      "apiURL": PathConfig.BASE_URL_API + PathConfig.ROLE_LIST
    }

    this.loadAllPermission([]);
  }
  // on Menu Selection
  onRoleTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'edit') {
      this.resetForm();
      this.editRole(data['value']);
    } else if (data['clickedOn'] == 'delete') {
      //delete code here
      //confirm box
      let dialogRef = this.dialog.open(ConfirmDialogComponent);
      this.roleListRefresh = false;
      dialogRef.afterClosed().subscribe(result => {
        if (result == "OK") {
          this.deleteRoleOnConfirm(JSON.parse(data['value']));
          console.log(result);
        }
      });

      console.log(data['value']);
    }
  }

  //back to table button
  backToTable() {
    this.showForm = false;
    this.roleListRefresh = true;
    //this.newRole = new Role();
    this.showInactiveRole = false;
    this.roleListSearchModel = {};
  }


  //role dropdown list
  roleSearchTableDD: Array<Object> = [
    { "id": 0, "labelName": "Name" },
    { "id": 1, "labelName": "Description" },
    { "id": 3, "labelName": "Permissions" }
  ];
  //role dropdown Event
  roleSearchEvent(data) {
    console.log(data);
    this.roleListSearchModel['searchTxt'] = data['text'];
    this.roleListSearchModel['colIndex'] = data['dd-id'];
  }

  //open dialog
  openDialog() {
    let dialogRef = this.dialog.open(UserSearchDialog);
    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
    });
  }


  resetForm() {
    this.newRole = new Role();
    this.loadAllPermission([]);
    this.newRole.active = false;
    this.errorMessage = "";
    for (var i in this.roleForm.controls) {
      this.removeValidators(this.roleForm.controls[i], true);
    }
  }
  editRole(id) {
    this.showingAddSection = true;
    this.editMode = true;
    this.showButton = true;
    let url = PathConfig.BASE_URL_API + PathConfig.ROLE_GET_BY_ID + id;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          var tempArr = [];
          this.newRole = data;
          for (var i in data.permissions) {
            tempArr.push({ 'permissionId': data.permissions[i].permissionId });
          }
          this.loadAllPermission(tempArr);
          this.showForm = true;
        }
      });
    return data;
  }

  changeSelect(obj) {
    obj['isChecked'] = !obj['isChecked'];
  }
  toggleForm() {
    this.newRole = new Role();
    this.newRole.active = true;
    this.showForm = true;
    this.editMode = false;
    this.showButton = false;
    this.loadAllPermission("");
    this.errorMessage = "";
    for (var i in this.roleForm.controls) {
      this.removeValidators(this.roleForm.controls[i], true);
    }
  }
  loadAllPermission(value) {
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let url = PathConfig.BASE_URL_API + PathConfig.PERMISSION_LIST;
    var data = this.httpService.get(url, auth_token);
    let self = this;
    var found;
    data.subscribe(
      data => {
        if (data) {
          if (value.length != 0) {
            for (let i in data) {

              for (let j in value) {
                found = data[i].permissions.some(function (res) { return res.permissionId == value[j].permissionId; });
                if (found) {
                  for (var k in data[i].permissions) {
                    if (data[i].permissions[k].permissionId === value[j].permissionId) {
                      data[i].permissions[k]['isChecked'] = true;
                    }
                  }
                }
              }

              for (let j in value) {
                found = data[i].permissions.some(function (res) { return res.permissionId == value[j].permissionId; });
                if (found) {
                  for (var k in data[i].permissions) {
                    if (data[i].permissions[k].isChecked === undefined || !data[i].permissions[k].isChecked) {
                      data[i].permissions[k]['isChecked'] = false;
                    }
                  }
                }
              }

            }
          }
          if (value.length == 0) {
            for (let i in data) {
              for (let k in data[i].permissions) {
                data[i].permissions[k].isChecked = false;
              }
            }
          }
          this.highLevelWithLowLevelPermissionList = data;
          this.highLevelWithLowLevelPermissionList[0]['isOpen'] = true;
        }
      });
    return data;
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

  // save the form
  saveForm(formData: any) {
    this.addRequiredValidatorRunTime(formData);

    if (formData.valid) {
      this.errorMessage = "";
      var resultedArray: Array<any> = [];
      var tempData = this.highLevelWithLowLevelPermissionList;
      var permissionDataArr = this.highLevelWithLowLevelPermissionList;

      for (var i in tempData) {
        tempData[i].permissions = tempData[i].permissions.filter(function (value) { return value['isChecked'] == true; });
        for (var j in tempData[i].permissions) {
          delete tempData[i].permissions[j]['isChecked'];
          resultedArray.push(tempData[i].permissions[j]);
        }
      }


      console.log(resultedArray);
      this.newRole.permissions = resultedArray;

      let url = PathConfig.BASE_URL_API + PathConfig.ROLE_ADD;
      let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      var data;
      this.roleListRefresh = false;

      if (this.editMode) {
        data = this.httpService.put(url, this.newRole, auth_token);
      } else {
        data = this.httpService.post(url, this.newRole, auth_token);
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
      this.newRole = new Role();
      this.newRole.active = true;
      return data;
    } else {      
      this.errorMessage = "Please fill in required fields";
    }

  }

  selectAllPermission(seletedPermission, event) {

    for (var i in seletedPermission) {
      if (seletedPermission.checked) {
        typeof seletedPermission[i] == 'object' && (seletedPermission[i].isChecked = false);
      } else {
        typeof seletedPermission[i] == 'object' && (seletedPermission[i].isChecked = true);
      }
    }
    seletedPermission.checked = (seletedPermission.checked == undefined) ? true : (seletedPermission.checked == true) ? false : true;

  }


  // delete role

  deleteRoleOnConfirm(obj) {
    let url = PathConfig.BASE_URL_API + PathConfig.ROLE_DELETE+ obj['roleId'];
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

  showInactiveRoles() {
    var activeData = {
      "dd-id": 2,
      "text": false
    }
    if (this.showInactiveRole) {
      this.roleSearchEvent(activeData);
    } else {
      this.showActiveAdUnit();
    }
  }
  showActiveAdUnit() {
    var activeData = {
      "dd-id": 0,
      "text": ''
    }
    if (!this.showInactiveRole) {
      this.roleSearchEvent(activeData);
    }
  }


}
