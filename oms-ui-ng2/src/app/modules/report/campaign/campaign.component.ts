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
import { Router,NavigationExtras } from "@angular/router";


@Component({
  templateUrl: './campaign.component.html',
  //styleUrls: ['./manage-company.component.scss'],
})
export class CampaignComponent implements OnInit {

  //companyForm: FormGroup;
  errorMessage: string;
  showForm: Boolean = false;
  editMode: Boolean = false;
 
  singleSelectSettings: IMultiSelectSettings;
  multiSelectSelectSettings: IMultiSelectSettings;
  textSetting: IMultiSelectTexts;

  campaignListDTConfig = {};
  campaignData: Array<Object> = [];

  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService,
    private dialog: MdDialog,
    private router: Router,
    // private companyFr: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {

    let self = this;

   /* this.permissions = this.localStorage.get('user_permissions');

    if(this.permissions && this.permissions.length){

      this.isCompanyCreatePermission = this.permissions.indexOf('create_companies') > -1;
      this.isCompanyEditPermission = this.permissions.indexOf('edit_companies') > -1;
      this.isCompanyDeletePermission = this.permissions.indexOf('delete_companies') > -1;
    }*/

    //camoaign name, goal name, ad name, impresiion, click, ctr (clik throuhgh put) 

    this.createGrig();
    this.getCampaignGridData()
  }

  getCampaignGridData(){
    let url = PathConfig.BASE_URL_API + PathConfig.CAMPAIGN_LIST;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data.campaignBean.length) {
          this.campaignData = data.campaignBean;
        }
      });
  }

  // on Menu Selection
  onCampaignTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'details') {
      //details code here
      this.goToCampaignDetails(data);

    }
  }

  goToCampaignDetails(data){
     let navigationExtras: NavigationExtras = {
            queryParams: {
                "mycustom":data.value
            }
        };
     this.router.navigate(['dashboard/report/campaign-details'], navigationExtras );
  
  }

  createGrig(){

    this.campaignListDTConfig = {
      "processing": false,
      "serverSide": false,
      "dom": 'tr',
       "columnDefs": [
        {
          "className": "table-inline-a",
          "targets": 0,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              template = "<div class='col-breadcrumb'><div><a href='javascript:void(0);' class='opportunity-name' data-name='details' data-custom='" + JSON.stringify(full) + "'>" + full.name + "</a></div>" +
                "<div class='sub-heading'>(" + full.impression + ")</div></div>";
            }
            return template;
          }
        }
      ],
      "columns": [
        { "title": 'Goal Name', "data": "goalType" },
        { "title": 'Ad Name', "data": "goalType" },
        { "title": "Status", "data": "status" },
        { "title": "cpmHigh", "data": "cpmHigh.value" },
        { "title": "cpmLow", "data": "cpmLow.value" }
      ]
    }
  }
}
