import {
  Component
} from '@angular/core';

@Component({
  templateUrl: './order.component.html'
})
export class OrderComponent {
  constructor() { }

}


// import {
//   Component, OnInit, AfterViewInit, HostListener, ElementRef, ChangeDetectorRef, ViewChild
// } from '@angular/core';
// import { HttpService } from '../../../common/services/http.service';
// import { PathConfig } from '../../../common/config/path.config';
// import { ConstantConfig } from '../../../common/config/constant.config';
// import { LocalStorageService } from '../../../common/services/local-storage.service';
// import { Proposal } from '../../../common/models/proposal';
// import { MdDialog, MdDialogRef } from '@angular/material';
// import { ConfirmDialogComponent } from "../../../common/modules/dialog/confirm/confirm.component";
// import { Router } from "@angular/router";
// import { DateFormatterComponent } from "../../../common/utils/date-format.component";
// import { CalendarService } from "../../../common/services/calendar.service";
// import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
// import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
// import { IMultiSelectOption } from "angular-2-dropdown-multiselect";
// import { DropDownSetting } from '../../../common/utils/dropdown.settings';
// import * as _ from 'lodash';
// declare var $: any;

// @Component({
//   templateUrl: './order.component.html',
//   styleUrls: ['./order.component.scss'],
// })
// export class OrderComponent implements OnInit {

//   proposalListDTConfig: Object = {};
//   proposalListRefresh: Boolean = false;
//   proposalListSearchModel: any = {};
//   newProposal: Proposal = new Proposal();
//   filterHeight: number = 0;
//   filters: Boolean = false;

//   permissions = null;
//   isProposalCreatePermission: boolean = false;
//   isProposalEditPermission: boolean = false;
//   isProposalDeletePermission: boolean = false;
//   isProposalNewVersionPermission: boolean = false;
//   isProposalCopyPermission: boolean = false;
//   singleSelectSettings: IMultiSelectSettings;
  
//   advanceForm: FormGroup;
//   isRegenerate: boolean = true;

//   //Calendar settings starts
//   //Caledar array. It is declared in constructor.
//   calendarArr = null;
//   clickOnDatepicker = null;
//   calendarBtnClick = null;
//   resetAllCalendar = null;
//   //Calendar settings end

//   advanceSearchModelOBj = {
//     searchCreatedDate: null,
//     searchEndDate: null,
//     searchDueDate: null,
//   };

//   advertiserOptions: IMultiSelectOption[];
//   agencyOptions: IMultiSelectOption[];
//   salesCategoryOptions: IMultiSelectOption[];

//   @HostListener('document:click', ['$event'])
//   clickout(event) {
//     this.resetAllCalendar(this);
//   }

//   // Constructor with injected service
//   constructor(
//     private httpService: HttpService,
//     private router: Router,    
//     private localStorage: LocalStorageService,    
//     private dialog: MdDialog,
//     private fb: FormBuilder,
//     private calendarService: CalendarService,
//   ) { 
//     this.createAdvanceForm();

//     // You have to declare how many calendar want to use in this component
//     this.calendarArr = {
//       'isStartDateOpen': false,
//       'isEndDateOpen': false,
//       'isNewCreatedDateOpen': false,
//       'isDueDateOpen': false
//     };
//   }

//   createAdvanceForm() {
//     this.advanceForm = this.fb.group({
//       name: '',
//       advertiser: '',
//       agency: '',
//       startDate: '',
//       endDate: '',
//       dueDate: '',
//       salesCategory: ''
//     });
//   }

//   applyAdvanceFilter(){
//     this.isRegenerate = false;
//     let self = this;
//     setTimeout(function(){
//       self.createTable();
//     }, 1000)
//   }

//   getNameColumnTemp(data, full){
//     let tempStr = ''

//     if(this.isProposalEditPermission){
//       tempStr = "<a href='javascript:void(0);' class='proposal-name' data-name='edit' data-custom='" + full.proposalId + "'>" + full.name + "</a>";
//     }else{
//       tempStr = "<div>" + full.name + "</div>";
//     }

//     return tempStr;
//   }

//   getEditTemp(data, full){
//     let tempStr = ''

//     if(this.isProposalEditPermission){
//       tempStr = '<a href="javascript:void(0);" data-name="edit" data-custom="' + data + '"><span class="fa fa-pencil" aria-hidden="true"></span>Edit</a>';
//     }else{
//       tempStr = '<div><span class="fa fa-pencil" aria-hidden="true"></span>Edit</div>';
//     }

//     return tempStr;
//   }

//   getDeleteTemp(data, full){
//     let tempStr = ''

//     if(this.isProposalDeletePermission){
//       tempStr = "<a href='javascript:void(0);' data-name='delete' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>";
//     }else{
//       tempStr = "<div><span class='fa fa-trash' aria-hidden='true'></span>Delete</div>";
//     }

//     return tempStr;
//   }

//   getCopyTemp(data, full){
//     let tempStr = ''

//     if(this.isProposalCopyPermission){
//       tempStr = '<a href="javascript:void(0);" data-name="copy" data-custom="' + data + '"><span class="fa fa-copy" aria-hidden="true"></span>copy</a>';
//     }else{
//       tempStr = '<div><span class="fa fa-copy" aria-hidden="true"></span>copy</div>';
//     }

//     return tempStr;
//   }

//   getNewVersionTemp(data, full){
//     let tempStr = ''

//     if(this.isProposalNewVersionPermission){
//       tempStr = '<a href="javascript:void(0);" data-name="version" data-custom="' + data + '"><span class="fa fa-plus" aria-hidden="true"></span>New Version</a>';
//     }else{
//       tempStr = '<div><span class="fa fa-plus" aria-hidden="true"></span>New Version</div>';
//     }

//     return tempStr;
//   }

//   ngOnInit() {

//     let self = this;

//     this.loadAllDropdowns();

//     this.permissions = this.localStorage.get('user_permissions');

//     if(this.permissions && this.permissions.length){

//       this.isProposalCreatePermission = this.permissions.indexOf('create_proposal') > -1;
//       this.isProposalCopyPermission = this.permissions.indexOf('copy_proposal') > -1;
//       this.isProposalNewVersionPermission = this.permissions.indexOf('new_version_proposal') > -1;
//       this.isProposalEditPermission = this.permissions.indexOf('edit_proposal') > -1;
//       this.isProposalDeletePermission = this.permissions.indexOf('delete_proposal') > -1;
//     }

//     this.clickOnDatepicker = this.calendarService.clickOnDatepicker(this);
//     this.calendarBtnClick = this.calendarService.calendarBtnClick(this);
//     this.resetAllCalendar = this.calendarService.resetAllCalendar(this);

//     this.singleSelectSettings = DropDownSetting.singleSelectSettings;

//     this.createTable();
//   }

//   createTable(){
//     if(!this.isRegenerate){
//       this.isRegenerate = true;
//     }

//     let self = this;

//     this.proposalListDTConfig = {
//       "columnDefs": [
//         {
//           "className": "table-inline-a",
//           "targets": 0,
//           "render": function (data, type, full, meta) {
//             var template;
//             if (data) {
//               template = "<div class='col-breadcrumb'><div>"+ self.getNameColumnTemp(data, full) +"</div>" +
//                 "<div class='sub-heading'>(" + full.proposalId + ")</div></div>";
//               //template = '<span><a href="javascript:void(0);" data-name="edit" data-custom="' + full.proposalId + '">' + full.name + "</a> >(" + full.proposalId + ")" + '</span>';
//             }
//             return template;
//           }
//         },
//         {
//           "targets": 1,
//           "render": function (data, type, full, meta) {
//             var template;
//             if (_.includes(data,"Review")) {
//               template = "Under " + data;
//             } else {
//               template = data;
//             }
//             return template;
//           }
//         },
//         {
//           "targets": 3,
//           "render": function (data, type, full, meta) {
//             var template;
//             if (data) {
//               var date = new DateFormatterComponent().changeDateFormatTo_dd_mm_yy(data, 'dd-MMM-yyyy');
//               template = '<span>' + date + '</span>';
//             }
//             return template;
//           }
//         },
//         {
//           "targets": 4,
//           "render": function (data, type, full, meta) {
//             var template;
//             if (data) {
//               var date = new DateFormatterComponent().changeDateFormatTo_dd_mm_yy(data, 'dd-MMM-yyyy');
//               template = '<span>' + date + '</span>';
//             }
//             return template;
//           }
//         },
//         {
//           "targets": 5,
//           "render": function (data, type, full, meta) {
//             var template;
//             if (data) {
//               var date = new DateFormatterComponent().changeDateFormatTo_dd_mm_yy(data, 'dd-MMM-yyyy');
//               template = '<span>' + date + '</span>';
//             }
//             return template;
//           }
//         },
//         {
//           "targets": 7,
//           "render": function (data, type, full, meta) {
//             var template;
//             if (data) {
//               template = data;
//             } else {
//               template = '<span> </span>';
//             }
//             return template;
//           }
//         },
//         {
//           "targets": 8,
//           "render": function (data, type, full, meta) {
//             var template;
//             if (data) {
//               template = data;
//             } else {
//               template = '<span> </span>';
//             }
//             return template;
//           }
//         },
//         {
//           "className": "text-center",
//           "orderable": false,
//           "targets": 9,
//           "render": function (data, type, full, meta) {
//             var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
//               self.getEditTemp(data, full) +
//               self.getCopyTemp(data, full) +
//               self.getNewVersionTemp(data, full) +
//               self.getDeleteTemp(data, full) +
//               '</div>';
//             return template;
//           }
//         }],
//       "columns": [
//         { "title": 'Name', "data": "name" },
//         { "title": 'Status', "data": "status.name" },
//         { "title": 'Sales Planner', "data": "salesPerson.firstName" },
//         { "title": "Start Date", "data": "startDate" },
//         { "title": "End Date", "data": "endDate" },
//         { "title": "Due Date", "data": "dueDate" },
//         { "title": 'Budget', "data": "budget" },
//         { "title": "Advertiser", "data": "advertiser.name" },
//         { "title": "Agency", "data": "agency.name" },
//         { "title": '', "data": "proposalId" }
//       ],
//       //"apiURL": PathConfig.BASE_URL_API + PathConfig.PROPOSAL_LIST

//       "ajax": {
//             type: "POST",
//             contentType: "application/json",
//             dataType: 'json',
//             cache: false,
//             data: function (d) {
//               d['filters'] = self.getFilterArr(self.advanceForm.value);
//               return JSON.stringify(d);
//             },
//             /*dataSrc: function (json) {
//                   self.isFilterDataProcessing = false;
//                   return json.data;
//             },*/
//             beforeSend: function (xhr) {
//               xhr.setRequestHeader("Authorization", 'Bearer '+self.localStorage.get(ConstantConfig.AUTH_TOKEN));
//             },
//             url: PathConfig.BASE_URL_API + PathConfig.PROPOSAL_LIST
//       }
//     }
//   }

//   getFilterArr(data){
//     let filterArr = [];
//     _.each(data, function(val, key){
//       if(val){
//         filterArr.push({
//           'column': key,
//           'value': Array.isArray(val) ? val[0] : val
//         });
//       }
//     });

//     return filterArr;
//   }

//   // on Menu Selection
//   onProposalTableMenuSelect(data: any) {
//     if (data['clickedOn'] == 'edit') {
//       //edit code here
//       this.editProposal(data['value']);

//     } else if (data['clickedOn'] == 'delete') {
//       //delete code here
//       //confirm box
//       let dialogRef = this.dialog.open(ConfirmDialogComponent);
//       this.proposalListRefresh = false;
//       dialogRef.afterClosed().subscribe(result => {
//         if (result == "OK") {
//           this.deleteproposalOnConfirm(JSON.parse(data['value']));
//           this.proposalListRefresh = true;
//         }
//       });
//     } else if (data['clickedOn'] == 'copy') {
//       //copy proposal
//       this.copyProposal(data['value']);
//     } if (data['clickedOn'] == 'version') {
//       //create new version
//       this.addProposalVersion(data['value']);
//     }

//   }

//   //search dropdown list
//   proposalSearchTableDD: Array<Object> = [
//     { "id": 0, "labelName": "Name" },
//     { "id": 1, "labelName": "Status" },
//     { "id": 2, "labelName": "Sales Planner" },
//     { "id": 3, "labelName": "Start Date" },
//     { "id": 4, "labelName": "End Date" },
//     { "id": 5, "labelName": "Due Date" },
//     { "id": 6, "labelName": "Budget" },
//     { "id": 7, "labelName": "Advertiser" },
//     { "id": 8, "labelName": "Agency" }
//   ];
//   //proposal dropdown Event
//   proposalSearchEvent(data) {
//     this.advanceForm.reset();
//     this.filterHeight = 0;
//     this.proposalListSearchModel['searchTxt'] = data['text'];
//     this.proposalListSearchModel['colIndex'] = data['dd-id'];
//   }

//   // delete proposal
//   proposalObjToDelete: any;
//   deleteproposalOnConfirm(obj) {
//     let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_DELETE + obj.proposalId;
//     let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
//     let data = this.httpService.delete(url, obj, auth_token);
//     let self = this;
//     data.subscribe(
//       data => {
//         if (data) {
//           this.proposalObjToDelete = null;
//           this.newProposal = new Proposal();
//           this.proposalListRefresh = true;
//         }
//       });
//     return data;
//   }

// //Load drop down for advance serach filter
// loadAllDropdowns() {
//     var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
//     let url1 = PathConfig.BASE_URL_API + PathConfig.SALESCATEGORY_GET_LIST;
//     let url5 = PathConfig.BASE_URL_API + PathConfig.COMPANY_LIST;

//     var data1 = this.httpService.get(url1, auth_token);
//     var data5 = this.httpService.get(url5, auth_token);

//     let self = this;
//     data1.subscribe(
//       data1 => {
//         if (data1) {
//           this.salesCategoryOptions = this.convertDataIntoDropdown(data1, 'salesCatagoryId', 'name')
//         }
//       });

//     data5.subscribe(
//       data5 => {
//         if (data5) {
//           let agencyData = data5.filter(function (value) {
//             if (value.type == 'Agency') {
//               return value;
//             }
//           });
//           let advertiserData = data5.filter(function (value) {
//             if (value.type == 'Advertiser') {
//               return value;
//             }
//           });
//           this.agencyOptions = this.convertDataIntoDropdown(agencyData, 'companyId', 'name');
//           this.advertiserOptions = this.convertDataIntoDropdown(advertiserData, 'companyId', 'name');
//         }
//       });
//   }

//   convertDataIntoDropdown(array, propId, propLabel) {
//     let newArr = array.map(function (val, index) {
//       return { 'id': val[propId], 'name': val[propLabel] };
//     });
//     return newArr;
//   }
//   //End of drop down for advance serach filter

//   editProposal(id) {
//     this.router.navigate(['dashboard/sales/proposal/edit'], { queryParams: { callback: 'editProposal', proposalId: id } });
//   }

//   copyProposal(proposalId) {
//     let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_GET_BY_ID + proposalId;
//     let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
//     let data = this.httpService.get(url, auth_token);
//     let self = this;
//     data.subscribe(
//       data => {
//         if (data) {
//           this.newProposal = data;
//           this.saveCopiedProposal();
//         }
//       });
//     return data;
//   }

//   saveCopiedProposal() {
//     let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_ADD;
//     let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);

//     delete this.newProposal['proposalId'];
//     delete this.newProposal['proposalAudits'];
//     delete this.newProposal['documents'];

//     var data = this.httpService.post(url, this.newProposal, auth_token);
//     let self = this;
//     data.subscribe(
//       data => {
//         if (data) {
//           this.proposalListRefresh = true;
//         }
//       }, error => {
//         if (error) {
//           console.log(error);
//         }
//       });
//     return data;
//   }

//   addProposalVersion(proposalId) {
//     console.log("New version....");
//     this.router.navigate(['dashboard/sales/proposal/edit'], { queryParams: { callback: 'editProposal', proposalId: proposalId, newVersion: 'yes' } });
//   }
// }
