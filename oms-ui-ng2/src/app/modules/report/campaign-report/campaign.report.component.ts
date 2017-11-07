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
import { CampaignReportService } from './campaign.report.service';


@Component({
  templateUrl: './campaign.report.component.html',
  //styleUrls: ['./manage-company.component.scss'],
})
export class CampaignReportComponent implements OnInit {

  //companyForm: FormGroup;
  errorMessage: string;
  showForm: Boolean = false;
  editMode: Boolean = false;
 
  singleSelectSettings: IMultiSelectSettings;
  multiSelectSelectSettings: IMultiSelectSettings;
  textSetting: IMultiSelectTexts;

  campaignReportListDTConfig = null;
  campaignReportListData: Array<Object> = [];
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
    private cdr: ChangeDetectorRef,
    private campaignReportService: CampaignReportService
  ) {}

  ngOnInit() {

    let self = this;


    this.createGrig();
    this.getCampaignReportListData();
  }

  getCampaignReportListData(){
    let url = PathConfig.BASE_URL_API + PathConfig.CAMPAIGN_REPORT_LIST;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.campaignReportListData = this.campaignReportService.getReportList(data.rows);
        }
      });
  }

  /*getGoalGridData(advertiserId){
    let url = PathConfig.BASE_URL_API + PathConfig.GOAL_LIST + '/' + '1768e997-a079-443d-9508-c7303c5ec5d5';
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.get(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.campaignReportListData = data.goalBean;
        }
      });
  }*/

  /*// on Menu Selection
  onCampaignDetailsTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'details') {

    }
  }*/

  getCommaSeparatedValue(num){
    var numWithComma = parseFloat(num).toLocaleString('en-IN');
            return numWithComma;
  }

  createGrig(){

    let self = this;

    this.campaignReportListDTConfig = {
      "processing": false,
      "serverSide": false,
      "columnDefs": [
        {
          "targets": 1,
          "render": function (data, type, full, meta) {
            return self.getCommaSeparatedValue(data);
          }
        },
        {
          "targets": 2,
          "render": function (data, type, full, meta) {
            return self.getCommaSeparatedValue(data);
          }
        },
        {
          "targets": 3,
          "render": function (data, type, full, meta) {
            var template = data.toFixed(2);
            return template;
          }
        },
        {
          "targets": 4,
          "render": function (data, type, full, meta) {
            return self.getCommaSeparatedValue(data);
          }
        },
      ],
      "columns": [
        //{ "title": 'Campaign', "data": "campaign" },
        { "title": 'Campaign name', "data": "campaign_name" },
        { "title": 'Impression', "data": "impression" },
        { "title": "Unique Impressions", "data": "unique_impressions" },
        { "title": "CTR", "data": "ctr" },
        { "title": "Click Through", "data": "click_through" },
        { "title": "Revenue", "data": "revenue" },
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
