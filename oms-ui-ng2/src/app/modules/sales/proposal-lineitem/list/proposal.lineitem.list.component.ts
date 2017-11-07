import {
  Component, OnInit, AfterViewInit, ElementRef, HostListener
} from '@angular/core';
import { HttpService } from '../../../../common/services/http.service';
import { PathConfig } from '../../../../common/config/path.config';
import { ConstantConfig } from '../../../../common/config/constant.config';
import { LocalStorageService } from "../../../../common/services/local-storage.service";
import { Broadcaster } from '../../../../common/services/broadcaster.service';
import { AuthService } from '../../../../common/services/auth.service';
import { Proposal } from '../../../../common/models/proposal';
import { MdDialog, MdDialogRef } from '@angular/material';
import { DateFormat } from "../../../../common/modules/date.format";
import { IMultiSelectOption } from "angular-2-dropdown-multiselect";
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { IMultiSelectTexts } from "angular-2-dropdown-multiselect";
import { AdUnit } from '../../../../common/models/ad.unit';
import { ProposalLineItem } from '../../../../common/models/proposal.lineitem';
import { AudienceTargetType } from '../../../../common/models/audience.target.type';
import { AudienceTargetValues } from '../../../../common/models/audience.target.values';
import { Router, ActivatedRoute, Params } from "@angular/router";
import { DropDownDataFilter } from '../../../../common/filter/dropdown.filter';
import { ConfirmDialogComponent } from "../../../../common/modules/dialog/confirm/confirm.component";
import { DateFormatterComponent } from "../../../../common/utils/date-format.component";

declare var $: any;

@Component({
  templateUrl: './proposal.lineitem.list.component.html',
  styleUrls: ['./proposal.lineitem.list.component.scss'],
})
export class ProposalLineitemListComponent implements OnInit {


  showForm: Boolean = false;
  showLineItemForm: Boolean = false;

  proposalListDTConfig: Object = {};
  proposalListRefresh: Boolean = false;
  proposalListSearchModel: any = {};
  newProposal: Proposal = new Proposal();
  editMode: Boolean = false;
  //audienceTargetValues: Array<Object> = [{ "id": 1, "label": 'Lorem Ipsum' }, { "id": 1, "label": 'Lorem Ipsum', "isSelected": true }, { "id": 1, "label": 'Lorem Ipsum' }, { "id": 1, "label": 'Lorem Ipsum' }, { "id": 1, "label": 'Lorem Ipsum' }]
  audienceTargetValues: Array<Object>;
  selectedTargetTypeList = [];
  targetTypeList: Array<Object> = [];
  myModelListConfig: Object;
  myModelListRefresh: Boolean;
  productOptionsModel: number[];
  productList: Array<Object> = [];
  productOptions: IMultiSelectOption[];
  searchSettings: IMultiSelectSettings;
  singleSelectSettings: IMultiSelectSettings;
  myTexts: IMultiSelectTexts;
  adUnitList: Array<AdUnit> = [];
  disabledFormElements: Boolean = true;
  adUnitOptions: IMultiSelectOption[];
  adUnitOptionsModel: number[];
  newProposalLineItem: ProposalLineItem = new ProposalLineItem();
  newAudienceTargetType: AudienceTargetType = new AudienceTargetType();
  audienceTargetValuesList: Array<AudienceTargetValues> = [];
  newAudienceTargetValues: AudienceTargetValues = new AudienceTargetValues();
  targetTypeConfig: Object;

  showAudienceTargetValuesForm: Boolean = false;
  seletedAudienceTargetValuesArr: Array<Object>

  specTypeList: Array<Object> = [];

  permissions = null;
  isLineItemEditPermission: boolean = false;
  isLineItemDeletePermission: boolean = false;

  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private router: Router,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService,
    private dialog: MdDialog,
    private elementRef: ElementRef,
    private activatedRouter: ActivatedRoute,
  ) { }

  getEditNameColumnTemp(data, full){
    let tempStr = ''

    if(this.isLineItemEditPermission){
      tempStr = "<a href='javascript:void(0);' class='proposal-name' data-name='edit' data-custom='" + full.proposalId + "'>" + full.proposalName + "</a>";
    }else{
      tempStr = "<div>" + full.proposalName + "</div>";
    }

    return tempStr;
  }

  getDeleteTemp(data, full){
    let tempStr = ''

    if(this.isLineItemDeletePermission){
      tempStr = "<a href='javascript:void(0);' data-name='delete' data-custom='" + JSON.stringify(full) + "'><span class='fa fa-trash' aria-hidden='true'></span>Delete</a>";
    }else{
      tempStr = "<div><span class='fa fa-trash' aria-hidden='true'></span>Delete</div>"
    }

    return tempStr;
  }

  ngOnInit() {

    let self = this;

    this.permissions = this.localStorage.get('user_permissions');

    if(this.permissions && this.permissions.length){
        this.isLineItemEditPermission = this.permissions.indexOf('edit_proposal_line_items') > -1;
        this.isLineItemDeletePermission = this.permissions.indexOf('delete_proposal_line_items') > -1;
    }

    this.searchSettings = {
      enableSearch: true,
      dynamicTitleMaxItems: 3,
      displayAllSelectedText: true
    };

    this.singleSelectSettings = {
      selectionLimit: 1,
      autoUnselect: true,
      dynamicTitleMaxItems: 1,
      displayAllSelectedText: true,
      closeOnSelect: true
    };

    this.myTexts = {
      checkAll: 'Select all',
      uncheckAll: 'Unselect all',
      checked: 'item selected',
      checkedPlural: 'items selected',
      searchPlaceholder: 'Find',
      defaultTitle: 'Select',
      allSelected: 'All selected',
    };
    this.proposalListDTConfig = {
      "columnDefs": [
        {
          "className": "table-inline-a",
          "targets": 0,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              template = "<div class='col-breadcrumb'><div>"+ self.getEditNameColumnTemp(data, full) +"</div>" +
                "<div class='sub-heading'>" + full.lineItemName + "</div>" +
                "<div class='sub-heading'>(" + full.lineItemId + ")</div></div>";
              //template = '<span><a href="javascript:void(0);" data-name="edit" data-custom="' + full.proposal.proposalId + '">' + full.proposal.name + "</a> > " + full.name + "(" + full.lineItemId + ")" + '</span>';
            }
            return template;
          }
        },
        {
          "orderable": false,
          "targets": 4,
          "render": function (data, type, full, meta) {
            var startDate = new DateFormatterComponent().changeDateFormatTo_dd_mm_yy(full.startDate, 'dd-MMM-yyyy');
            var endDate = new DateFormatterComponent().changeDateFormatTo_dd_mm_yy(full.endDate, 'dd-MMM-yyyy');

            var template = '<div>' + startDate + ' to ' + endDate + '</div>';
            return template;
          }
        }, {
          "orderable": false,
          "targets": 6,
          "render": function (data, type, full, meta) {
            var template = full.lineItemCost;
            return template;
          }
        }, {
          "className": "text-center",
          "orderable": false,
          "targets": 7,
          "render": function (data, type, full, meta) {
            var template = '<span class="allICons dots"></span><div class="tooltipDel">' +
               self.getDeleteTemp(data, full) +
              '</div>';
            return template;
          }
        }],
      "columns": [
        { "title": 'Name', "data": "proposalName" },
        { "title": 'Proposal Status', "data": "status" },
        { "title": 'Product', "data": "productName" },
        { "title": "Type", "data": "type" },
        { "title": "Flight", "data": "flightStartdate" },
        { "title": 'Impression', "data": "impressions" },
        { "title": "Cost", "data": "lineItemCost" },
        { "title": '', "data": "endDate" }
      ],
      "apiURL": PathConfig.BASE_URL_API + PathConfig.PROPOSAL_LINEITEM_LIST
    }

  }

  newRowsAdded = [];

  // on Menu Selection
  onProposalTableMenuSelect(data: any) {
    if (data['clickedOn'] == 'edit') {
      //edit code here
      this.editProposal(data['value']);

    } else if (data['clickedOn'] == 'delete') {
      //delete code here
      //confirm box
      let dialogRef = this.dialog.open(ConfirmDialogComponent);
      this.proposalListRefresh = false;
      dialogRef.afterClosed().subscribe(result => {
        if (result == "OK") {
          this.deleteproposalOnConfirm(JSON.parse(data['value']));
        }
      });
    }

  }

  editProposal(id) {
    this.router.navigate(['dashboard/sales/proposal/edit'], { queryParams: { callback: 'editProposalWithLineitem', proposalId: id } });
  }

  //search dropdown list
  proposalSearchTableDD: Array<Object> = [
    { "id": 0, "labelName": "Name" },
    { "id": 1, "labelName": "Base Price" },
    { "id": 2, "labelName": "Proposal Status" },
    { "id": 3, "labelName": "Product" },
    { "id": 4, "labelName": "Start Date" },
    { "id": 5, "labelName": "End Date" },
    { "id": 6, "labelName": "Quantity" },
    { "id": 7, "labelName": "Type" },
    { "id": 8, "labelName": "Targeting" },
    { "id": 9, "labelName": "Cost" },
  ];
  //proposal dropdown Event
  proposalSearchEvent(data) {
    this.proposalListSearchModel['searchTxt'] = data['text'];
    this.proposalListSearchModel['colIndex'] = data['dd-id'];
  }

  // delete proposal lineitem
  deleteproposalOnConfirm(obj) {
    let url = PathConfig.BASE_URL_API + PathConfig.PROPOSAL_LINEITEM_DELETE + obj['lineItemId'];
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let data = this.httpService.deleteWithId(url, auth_token);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          this.proposalListRefresh = true;
        }
      });
    return data;
  }








}
