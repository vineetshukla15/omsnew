import { Component, OnInit, AfterViewInit, ElementRef, HostListener, EventEmitter, Output, Input } from '@angular/core';
import { PathConfig } from '../../../common/config/path.config';
import { Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { HttpService } from '../../../common/services/http.service';
import { AuthService } from '../../../common/services/auth.service';
import { LocalStorageService } from "../../../common/services/local-storage.service";
import { ConstantConfig } from "../../../common/config/constant.config";
import { DateFormatterComponent } from "../../../common/utils/date-format.component";
import { DashboardServices } from "../services/dashboard.services";
import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { DropDownSetting } from '../../../common/utils/dropdown.settings';
import { MdDialog, MdDialogRef } from '@angular/material';
import { CalendarService } from "../../../common/services/calendar.service";

@Component({
  styleUrls: ['./dashboard-proposal.component.scss'],
  templateUrl: './dashboard-proposal.component.html',
  providers: [DateFormatterComponent, DashboardServices]
})
export class DashboardProposalComponent implements OnInit {

  tableConfig: Object = {};
  pieChartConfig: Object = {};
  barChartConfig: Object = {};
  tableData: Array<Object> = [];
  tableDataRefresh: boolean = true;
  pieChartUpdatedData = [];
  barChartUpdatedData = [];
  barChartGraphs = [];
  graphConfig = [];
  fromDateModel: Date = new Date();
  fromDate: boolean = false;
  toDateModel = null;
  piChartData = [];
  barChartData = [];
  singleSelectSettings: IMultiSelectSettings;
  userListOptions = [];
  userModel;
  previousDate = '';
  previousUserId = '0';
  selectedDate = '';
  isAdminLogin = false;
  isShowInactiveProposals = false;
  currentUserId = null;
  auth_token = null;
  permissions = null;

  //Calendar settings starts
  //Caledar array. It is declared in constructor.
  calendarArr = null;
  clickOnDatepicker = null;
  calendarBtnClick = null;
  resetAllCalendar = null;

  constructor(
    private router: Router,
    private authService: AuthService,
    private localStorage: LocalStorageService,
    private httpService: HttpService,
    private dateFormatterComponent: DateFormatterComponent,
    private dashboardServices: DashboardServices,
    private dialog: MdDialog,
    private calendarService: CalendarService
   ) {
    this.calendarArr = {
      'isFromDateOpen': false
    }
  }

  //Calendar settings end
  @HostListener('document:click', ['$event'])
  clickout(event) {
    this.resetAllCalendar(this);
  }

  onDataChange(type, data) {
    var isUserChanged = false;

    if (type == 'user' && this.previousUserId != data[0]) {
      this.previousUserId = data[0];
      isUserChanged = true;
    }

    if (type == 'fromDate') {
      this.setTodate(this.fromDateModel);
      this.selectedDate = this.getRequiredDateFormat(this.fromDateModel);
    }

    if (this.previousDate != this.selectedDate || isUserChanged) {
      this.chartAndTableCreation(this.previousUserId, this.selectedDate);
      this.previousDate = this.selectedDate
    }
  }

  setTodate(fromDate) {
    var newdate = new Date(fromDate);
    newdate.setDate(newdate.getDate() + 5);
    //this.toDateModel = this.prefixWithZero(newdate.getDate()) + '-' + this.prefixWithZero((newdate.getMonth() + 1)) + '-' + newdate.getFullYear();
    //var month= $filter('date')(newdate, 'DD-MMM-YYYY');
    this.toDateModel = newdate;
  }

  getRequiredDateFormat(date) {
    return date.getFullYear() + '-' + this.prefixWithZero((date.getMonth() + 1)) + '-' + this.prefixWithZero(date.getDate());
  }

  prefixWithZero(number) {
    return (number < 10 ? '0' : '') + number
  }

  ngOnInit() {
    let userId = '';
    let selectedDate = this.getRequiredDateFormat(this.fromDateModel);
    this.setTodate(this.fromDateModel);
    this.chartAndTableCreation(userId, selectedDate);

    this.clickOnDatepicker = this.calendarService.clickOnDatepicker(this);
    this.calendarBtnClick = this.calendarService.calendarBtnClick(this);
    this.resetAllCalendar = this.calendarService.resetAllCalendar(this);
  }

  chartAndTableCreation(userId, selectedDate) {
    var formatterOfDate = this.dateFormatterComponent;

    this.singleSelectSettings = DropDownSetting.singleSelectSettings;
    this.loggedInUserDetails(this, userId, selectedDate);

    this.createPieChart();
    this.createBarChart();
    this.createTable(formatterOfDate);
  }

  convertDataIntoDropdownForUser(array, propId, propLabel1, propLabel2) {
    let newArr = array.map(function (val, index) {
      return { 'id': val[propId], 'name': val[propLabel1] + " " + val[propLabel2] };
    });
    return newArr;
  }

  loggedInUserDetails(context, userId, selectedDate) {
    if (this.authService.isLoggedIn) {
      this.auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let url = PathConfig.BASE_URL_API + PathConfig.LOGED_IN_USER;
      var data = this.httpService.get(url, this.auth_token);

      data.subscribe(
        data => {
          if (data) {

            if (data.role.roleName == 'Administrator') {
              this.isAdminLogin = true;
            } else {
              this.isAdminLogin = false;
            }

            if (!userId) {
              //Get Users list for admin
              this.getUserList();
            }

            this.currentUserId = userId ? userId : data.id;

            setTimeout(() =>{
              //Data for Pi-chart
              this.getPieChartData(context);

              //Data for Bar-chart
              this.getBarChartData(context, selectedDate);

              //Get data for proposal list
              this.getUserSpecificProposals();
            }, 100);

          }
        });

      return data;
    }
  }

  getBarChartData(context, selectedDate) {
    //Data for Bar-chart
    var BarChartResponse = this.httpService.get(PathConfig.BASE_URL_API + PathConfig.DASHBOARD_STATISTICS + selectedDate + '/' + this.currentUserId, this.auth_token);

    BarChartResponse.subscribe(
      data => {
        if (data) {
          let returnedObj = this.dashboardServices.getBarChartRequiredData(data);
          context.barChartUpdatedData = returnedObj.preparedData;
          context.barChartGraphs = returnedObj.graphConfig;
        }
      });
  }

  getPieChartData(context) {
    var piChartResponse = this.httpService.get(PathConfig.BASE_URL_API + PathConfig.DASHBOARD_PROPOSAL_COUNTS + this.currentUserId, this.auth_token);
    piChartResponse.subscribe(
      data => {
        if (data) {
          var piChartData = this.dashboardServices.getPieChartRequiredData(data);
          context.pieChartUpdatedData = piChartData;
        }
      });
  }

  getUserList() {
    //Get Users list for admin
    var userList = this.httpService.get(PathConfig.BASE_URL_API + PathConfig.DASHBOARD_ADMIN_USER_LIST, this.auth_token);

    userList.subscribe(
      data => {
        if (data) {
          this.userListOptions = this.convertDataIntoDropdownForUser(data, 'id', 'firstName', 'lastName');
        }
      });
  }

  getUserSpecificProposals() {
    //Data for proposal tabular list

    if (this.isShowInactiveProposals) {
      var response = this.httpService.get(PathConfig.BASE_URL_API + PathConfig.DASHBOARD_PROPOSAL_INACTIVE_LIST + this.currentUserId, this.auth_token);
      response.subscribe(
        data => {
          if (data) {
            this.tableData = data;
          }
        }
      );
    } else {
      var response = this.httpService.get(PathConfig.BASE_URL_API + PathConfig.DASHBOARD_PROPOSAL_LIST + this.currentUserId, this.auth_token);
      response.subscribe(
        data => {
          if (data) {
            this.tableData = data;
          }
        }
      );
    }
  }

  //On click of proposal name in proposal list table
  onDashboardProposalTableMenuSelect(data) {
    if (data.value) {
      this.router.navigate(['dashboard/sales/proposal/edit'], { queryParams: { callback: 'editProposal', proposalId: data.value } });
    }
  }

  //create pie chart
  createPieChart() {
    this.pieChartConfig = this.dashboardServices.getPieChartConfig();
  }

  createBarChart() {

    this.barChartConfig = this.dashboardServices.getBarChartConfig();
  }

  createTable(dateFormatterComponent) {
    this.tableConfig = this.dashboardServices.getTableConfig(dateFormatterComponent)
  }

  showInactiveProposals() {
    this.getUserSpecificProposals();
  }

}
