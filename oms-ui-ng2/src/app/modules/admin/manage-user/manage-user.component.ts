import {
  Component, Input, OnInit, AfterViewInit
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
import { UserSearchDialog } from "./dialog/user-search/user-search.component";
import { ConfirmDialogComponent } from "../../../common/modules/dialog/confirm/confirm.component";
import { IMultiSelectOption } from 'angular-2-dropdown-multiselect';
import { objectSharingService } from '../../../common/services/objectSharingService';
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { IMultiSelectTexts } from "angular-2-dropdown-multiselect";
import { DropDownDataFilter } from '../../../common/filter/dropdown.filter';
import { DropDownSetting } from '../../../common/utils/dropdown.settings';

declare var $: any;

@Component({
  templateUrl: './manage-user.component.html',
  styleUrls: ['./manage-user.component.scss'],
})
export class ManageUserComponent implements OnInit {

  userAddressHeight = 215;
  showForm: Boolean = false;
  errorMessage: string;
  userListDTConfig: Object = {};
  userListRefresh: Boolean = false;
  userListSearchModel: any = {};
  searchUserList: Array<User> = [];
  roles: Array<Object> = [];
  newUser: User = new User();
  editMode: Boolean = false;
  isUserSearchDataLoaded: boolean = false;
  selectedRole: Array<Number>;
  disabledFormElements: Boolean = false;
  roleOptions: IMultiSelectOption[];
  roleOptionsModel: number[];
  singleSelectSettings: IMultiSelectSettings;
  multiSelectSelectSettings: IMultiSelectSettings;
  textSetting: IMultiSelectTexts;
  showInactiveUser: Boolean = false;
  isOpenUserAddress = true;

  permissions = null;
  isUserCreatePermission: boolean = false;
  isUserViewPermission: boolean = false;
  isUserEditPermission: boolean = false;
  isUserDeletePermission: boolean = false;

  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService,
    private dialog: MdDialog,
    public getSetService: objectSharingService
  ) { }

   getEditTemp(data, full){
    let tempStr = ''

    if(this.isUserEditPermission){
      tempStr = '<a href="javascript:void(0);" data-name="edit" data-custom="' + data + '"><span class="fa fa-pencil" aria-hidden="true"></span>Edit</a>';
    }else{
      tempStr = '<div><span class="fa fa-pencil" aria-hidden="true"></span>Edit</div>';
    }

    return tempStr;
  }

  getDeleteTemp(data, full){
    let tempStr = ''

    if(this.isUserDeletePermission){
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

      this.isUserCreatePermission = this.permissions.indexOf('edit_users') > -1;
      this.isUserEditPermission = this.permissions.indexOf('edit_users') > -1;
      this.isUserDeletePermission = this.permissions.indexOf('delete_users') > -1;
    }

    this.multiSelectSelectSettings = DropDownSetting.multiSelectSelectSettings;
    this.singleSelectSettings = DropDownSetting.singleSelectSettings;
    this.textSetting = DropDownSetting.textSetting;
    this.loadRole();
    this.userListDTConfig = {
      "columnDefs": [
                {
          "targets": 4,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              template = data;
            } else {
              template = '<span> </span>';
            }
            return template;
          }
        },
        {
          "targets": 5,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              template = '<span>Active</span>';
            } else {
              template = '<span>Inactive</span>';
            }
            return template;
          }
        }, {
          "className": "text-right all",
          "orderable": false,
          "targets": 6,
          "render": function (data, type, full, meta) {
            var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
               self.getEditTemp(data, full) +
               self.getDeleteTemp(data, full) +
              '</div>';
            return template;
          }
        }],
      "columns": [
        { "title": 'Login Name', "data": "username" },
        { "title": 'First Name', "data": "firstName" },
        { "title": 'Last Name', "data": "lastName" },
        { "title": 'Email', "data": "emailId" },
        { "title": "Role", "data": "role.roleName" },
        { "title": "Status", "data": "enabled" },
        { "title": '', "data": "id" }
      ],
      "apiURL": PathConfig.BASE_URL_API + PathConfig.USER_LIST,

    }

  }
  // on Menu Selection
  onUserTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'edit') {
      //edit code here
      this.editUser(data['value']);
      console.log(data['value']);
    } else if (data['clickedOn'] == 'delete') {
      //delete code here
      //confirm box
      let dialogRef = this.dialog.open(ConfirmDialogComponent);
      this.userListRefresh = false;
      dialogRef.afterClosed().subscribe(result => {
        if (result == "OK") {
          this.deleteUserOnConfirm(JSON.parse(data['value']));
        }
      });

      console.log(data['value']);
    }
    //console.log(data);
  }

  //back to table button
  backToTable() {
    this.showForm = false;
    this.userListRefresh = true;
    this.editMode = false;
    this.showInactiveUser = false;
    this.removeMessage();
    this.userListSearchModel = {};
  }

  openShowForm() {
    this.showForm = true;
    this.disabledFormElements = false;
    this.newUser = new User();
    this.selectedRole = [];
    this.roleOptionsModel = [];
    this.newUser.enabled = true;
  }
  //search dropdown list
  userSearchTableDD: Array<Object> = [
    { "id": 0, "labelName": "Login Name" },
    { "id": 1, "labelName": "First Name" },
    { "id": 2, "labelName": "Last Name" },
    { "id": 3, "labelName": "Email" },
    { "id": 4, "labelName": "Role" }
  ];
  //user dropdown Event
  userSearchEvent(data) {
    console.log(data);
    this.userListSearchModel['searchTxt'] = data['text'];
    this.userListSearchModel['colIndex'] = data['dd-id'];
  }

  //open dialog
  openDialog(val, _type) {
    if (val !== undefined && val !== "") {
      let dialogRef = this.dialog.open(UserSearchDialog);
      dialogRef.componentInstance.value = val;
      dialogRef.componentInstance.type = _type;
      dialogRef.afterClosed().subscribe(result => {
        if (this.getSetService.obj != undefined) {
          this.newUser = this.getSetService.obj;

        }
        this.getSetService.setValue({});
      });
    }
  }

  toggleForm() {
    this.showForm = !this.showForm;

  }

  // load roles
  loadRole() {
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let url = PathConfig.BASE_URL_API + PathConfig.ROLE_LIST;
    var data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.roles = data;
          this.roleOptions = DropDownDataFilter.convertDataIntoDropdown(data, 'roleId', 'roleName');
          let selectedValue = data.filter(function (value) { if (value['isSelected']) { return value; } });
          if (selectedValue.length > 0) {
            this.roleOptionsModel = selectedValue.map(function (value) { if (value['isSelected'] == true) return value['roleId'] });
          } else {
            this.roleOptionsModel = [];
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

  //search user from LDAP
  searchUserByNameFromLDAP(firstName) {
    this.isUserSearchDataLoaded = false;
    this.searchUserList = [];
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let url = PathConfig.BASE_URL_API + PathConfig.USER_SEARCH_FIRST_NAME_FROM_LADP + firstName;
    var data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          self.searchUserList = data;
        }
      });
    return data;
  }


  selectedUser(selectedUser) {
    Object.assign(this.newUser, selectedUser);
  }



  editUser(id) {
    this.openShowForm();
    let url = PathConfig.BASE_URL_API + PathConfig.USER_WITH_ID + id;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.newUser = data;
          if (data.role != undefined) {
            this.selectedRole = data.role.roleId;
            this.roleOptionsModel[0] = data.role.roleId;
          }
          this.showForm = true;
          this.disabledFormElements = true;
          this.editMode = true;
        }
      });
    return data;
  }

  //removeMessage
  removeMessage() {
    this.errorMessage = undefined;

  }

  // save the form
  saveForm() {
    let url = PathConfig.BASE_URL_API + PathConfig.USER_ADD;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    var data;
    let selectedValue = this.roleOptionsModel[0];
    let roleValue: any = this.roles.filter(function (val) { if (val['roleId'] == selectedValue) { return val; } })[0];
    this.newUser.role = roleValue;
    if (this.editMode) {
      data = this.httpService.put(url, this.newUser, auth_token);
    } else {
      data = this.httpService.post(url, this.newUser, auth_token);
    }
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.backToTable();

        }
      },
      error => {
        if (error) {
          this.errorMessage = error;
        }
      }
    );
    this.newUser = new User();
    this.newUser.enabled = true;
    return data;

  }


  // delete user
  userObjToDelete: any;
  deleteUserOnConfirm(obj) {
    let url = PathConfig.BASE_URL_API + PathConfig.USER_DELETE + obj.id;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.delete(url, {}, auth_token);
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
    let firstName = this.newUser.firstName;
    let lastName = this.newUser.lastName;
    let userName = this.newUser.username;
    let emailId = this.newUser.emailId;
    this.newUser = new User();
    this.newUser.firstName = firstName;
    this.newUser.lastName = lastName;
    this.newUser.username = userName;
    this.newUser.emailId = emailId;
    this.selectedRole = [];
    //this.editMode = false;
    this.roleOptionsModel = [];
    this.newUser.enabled = false;
    this.removeMessage();
  }

  showInactiveUsers() {
    var activeData = {
      "dd-id": 5,
      "text": false
    }
    if (this.showInactiveUser) {
      this.userSearchEvent(activeData);
    } else {
      this.showActiveUser();
    }


  }

  showActiveUser() {

    var activeData = {
      "dd-id": 5,
      "text": true
    }
    if (!this.showInactiveUser) {
      this.userSearchEvent(activeData);
    }
  }

}
