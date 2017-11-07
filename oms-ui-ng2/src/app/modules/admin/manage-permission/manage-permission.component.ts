import {
  Component, Input, EventEmitter, OnChanges, OnInit, ViewChild, AfterViewInit,
  AfterViewChecked
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

import * as _ from 'lodash';

@Component({
  templateUrl: './manage-permission.component.html',
  styleUrls: ['./manage-permission.component.scss'],
})
export class ManagePermissionComponent implements OnInit {

  userList: Array<Object> = [];
  filterUserList: Array<Object> = [];
  searchUserList: Array<User> = [];
  roles: Array<Object> = [];
  newUser: User = new User();
  newRole: Role = new Role();
  editMode: Boolean = new Boolean();
  isActiveUser: Boolean = new Boolean();
  showingAddSection: Boolean = new Boolean();

  isUserSearchDataLoaded: boolean = false;
  isPermissionDataLoaded: boolean = false;

  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService,
  ) { }

  ngOnInit() {
    this.isActiveUser = false;
    this.showingAddSection = false;
    // Load Lookup List
    this.loadLookupList();
    this.loadRole();
    this.editMode = false;
  }



  //data table search with drop down functionality
  userTableSearch: string = '';
  userSearchTableDD: Array<Object> = [
    { "id": 0, "labelName": "Name" },
    { "id": 1, "labelName": "Link" },
    { "id": 2, "labelName": "Group" }
  ];
  userSearchSelectedDD: Object = this.userSearchTableDD[0];




  //load admin manage-user with information
  loadLookupList() {
    if (this.authService.isLoggedIn) {
      this.isPermissionDataLoaded = false;
      var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let url = PathConfig.BASE_URL_API + PathConfig.USER_LIST;
      var data = this.httpService.get(url, auth_token);
      let self = this;
      data.subscribe(
        data => {
          if (data) {
            this.userList = data;
            this.filterUserList = _.filter(this.userList, { active: true });
          }
        });
      return data;
    }
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
          this.newRole = data[0];
        }
      });
    return data;
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
    _.extend(this.newUser, selectedUser);
  }

  resetForm() {
    //this.newUser =;
  }
  editUser(id) {
    this.showingAddSection = true;
    this.editMode = true;
    let url = PathConfig.BASE_URL_API + PathConfig.USER_WITH_ID + id;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.newUser = data;
        }
      });
    return data;
  }

  // save the form
  saveForm() {
    let url = PathConfig.BASE_URL_API + PathConfig.USER_ADD;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    var data;
    this.newUser.role = this.newRole;
    if (this.editMode) {
      data = this.httpService.put(url, this.newUser, auth_token);
    } else {
      data = this.httpService.post(url, this.newUser, auth_token);
    }
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          self.loadLookupList();
        }
      });
    this.newUser = new User();
    this.editMode = false;
    //this.toggleActiveUser();
    this.showingAddSection = false;
    return data;

  }

  showAddNewUserForm() {
    this.newUser = new User();
    this.newUser['active'] = true;
    this.showingAddSection = true;
  }

}
