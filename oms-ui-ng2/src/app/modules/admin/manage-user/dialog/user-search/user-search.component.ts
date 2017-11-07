import { Component, Input, OnInit } from "@angular/core";
import { MdDialogRef } from "@angular/material";
import { PathConfig } from "../../../../../common/config/path.config";
import { objectSharingService } from '../../../../../common//services/objectSharingService';
@Component({
  selector: 'user-search-dialog',
  templateUrl: './user-search.component.html',
  styleUrls: ['./user-search.component.scss'],
})
export class UserSearchDialog implements OnInit {



  userListDTConfig1: Object = {};
  userListRefresh1: Boolean = false;
  userListSearchMOdel1: any = {};
  value: string;
  type: string;
  typeApi: string;

  constructor(public dialogRef: MdDialogRef<UserSearchDialog>, public getSetService: objectSharingService) { }
  ngOnInit() {
    if (this.type == 'firstName') {
      this.typeApi = PathConfig.USER_SEARCH_FIRST_NAME_FROM_LADP;
    } else if (this.type == 'lastName') {
      this.typeApi = PathConfig.USER_SEARCH_LAST_NAME_FROM_LADP;
    }

    this.userListDTConfig1 = {
      "bSort": false,
      "processing": true,
      "serverSide": true,
      "paging": false,
      "columnDefs": [
        {
          "targets": 0,
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
          "targets": 2,
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
          "targets": 3,
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
          "targets": 4,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              template = '<span>Active</span>';
            } else {
              template = '<span>Deactive</span>';
            }
            return template;
          }
        }],
      "columns": [
        { "title": 'Login Name', "data": "username" },
        { "title": 'First Name', "data": "firstName" },
        { "title": 'Last Name', "data": "lastName" },
        { "title": 'Email', "data": "emailId" },
        { "title": "Active", "data": "enabled" }

      ],
      "apiURL": PathConfig.BASE_URL_API + this.typeApi + this.value,
      dataSrc: function (JSON) {
        JSON.data = JSON;
        return JSON.data;
      }
    }
  }

  //search dropdown list
  userSearchTableDD1: Array<Object> = [
    { "id": 0, "labelName": "Login Name" },
    { "id": 1, "labelName": "First Name" },
    { "id": 2, "labelName": "Last Name" },
    { "id": 3, "labelName": "Email" },
    { "id": 4, "labelName": "Role" },
    { "id": 5, "labelName": "Active" }
  ];
  //user dropdown Event
  userSearchEvent1(data) {
    console.log(data);
    this.userListSearchMOdel1['searchTxt'] = data['text'];
    this.userListSearchMOdel1['colIndex'] = data['dd-id'];
  }
  onUserTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'edit') {
      //edit code here
      //this.editUser(data['value']);
      console.log(data['value']);
    } else if (data['clickedOn'] == 'delete') {
      //delete code here
      //confirm box
      //let dialogRef = this.dialog.open(ConfirmDialog);
      // dialogRef.afterClosed().subscribe(result => {
      // this.deleteUserOnConfirm(JSON.parse(data['value']));
      //  });

      console.log(data['value']);
    }
    //console.log(data);
  }
  rowClickEvent(data: any) {
    if (data != undefined && data.value != undefined) {
      this.getSetService.setValue(data.value);
      this.dialogRef.close();
    }
  }

}
