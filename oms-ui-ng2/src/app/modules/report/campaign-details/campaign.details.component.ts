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
import { AlertDialogComponent } from "../../../common/modules/dialog/alertbox/alert.dialog.component";
import { IMultiSelectOption } from "angular-2-dropdown-multiselect";
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { IMultiSelectTexts } from "angular-2-dropdown-multiselect";
//import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { DropDownDataFilter } from '../../../common/filter/dropdown.filter';
import { AdsListDialogComponent } from '../ad-dialog/ads.list.dialog.component';
import { DropDownSetting } from '../../../common/utils/dropdown.settings';
import { Router,  ActivatedRoute} from "@angular/router";


@Component({
  templateUrl: './campaign.details.component.html',
  //styleUrls: ['./manage-company.component.scss'],
})
export class CampaignDetailsComponent implements OnInit {

  //companyForm: FormGroup;
  errorMessage: string;
  showForm: Boolean = false;
  editMode: Boolean = false;
 
  singleSelectSettings: IMultiSelectSettings;
  multiSelectSelectSettings: IMultiSelectSettings;
  textSetting: IMultiSelectTexts;

  campaignGoalListDTConfig = null;
  campaignGoalListData: Array<Object> = [];
  paramObj: any;
  campaignObj:any;

  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService,
    private dialog: MdDialog,
    private router: Router,
    private activatedRouter: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {

    let self = this;

    this.paramObj = this.activatedRouter.snapshot.queryParams["mycustom"];
    this.campaignObj = JSON.parse(this.paramObj);
    console.log(this.campaignObj);


    this.createGrig();
    this.getGoalGridData(this.campaignObj.advertiserId)
  }

  getGoalGridData(advertiserId){
    let url = PathConfig.BASE_URL_API + PathConfig.GOAL_LIST + '/' + '1768e997-a079-443d-9508-c7303c5ec5d5';
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.campaignGoalListData = data.goalBean;
        }
      });
  }

  /*// on Menu Selection
  onCampaignDetailsTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'details') {

    }
  }*/

  createGrig(){

    let self = this;

    this.campaignGoalListDTConfig = {
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
              template = "<div class='col-breadcrumb'><div><a href='javascript:void(0);' class='opportunity-name' data-name='goalDetails' data-custom='" + JSON.stringify(full) + "'>" + full.name + "</a></div>";
            }
            return template;
          }
        }
      ],

      "columns": [
        { "title": 'NAME', "data": "name" },
        { "title": 'TYPE', "data": "type" },
        { "title": 'VARANT', "data": "variant" },
        { "title": "GOALVALUE", "data": "goalValue" },
        { "title": "FORMAT TYPE", "data": "formatType" },
        { "title": "BUDGET", "data": "budget.value" },
        { "title": "CPM", "data": "cpm.value" },
        { "title": "START", "data": "start" },
        { "title": "END", "data": "end" },
      ]
    }
  }

  // on Menu Selection
  onCampaignGoalTableMenuSelect(data: any) {
    let self = this;
    if (data['clickedOn'] == 'goalDetails') {
      //details code here
      //this.goToCampaignDetails(data);

      let url = PathConfig.BASE_URL_API + PathConfig.GOAL_AD_LIST;
      let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let data = this.httpService.get(url, auth_token);
      data.subscribe(
        data => {
          if (data) {
            let dialogRef = self.dialog.open(AdsListDialogComponent, {
              data: data,
              height: '500px',
              width: '60%'
            });
            //this.proposalListRefresh = false;
            dialogRef.afterClosed().subscribe(result => {
              if (result == "OK") {
                //this.deleteproposalOnConfirm(JSON.parse(data['value']));
                // this.proposalListRefresh = true;
              }
            });
          }
        });  

    }
  }
}
