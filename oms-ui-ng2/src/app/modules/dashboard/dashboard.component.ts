import { Component, AfterViewInit } from '@angular/core';
import { LocalStorageService } from "../../common/services/local-storage.service";
import { Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { HttpService } from '../../common/services/http.service';
import { AuthService } from '../../common/services/auth.service';
import { ConstantConfig } from "../../common/config/constant.config";
import { PathConfig } from '../../common/config/path.config';
import * as $ from 'jquery';
import * as _ from 'lodash';

@Component({
  styleUrls: ['./dashboard.component.scss'],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements AfterViewInit {

  logedInUserFullName: string;
  logedInUserrRoleName: string;
  isUserInfoDDOpen: boolean = false;
  activeSiteSection: string = '';
  iconHamburgerExpand: Boolean = false;
  isOpen: Boolean = false;


  sideNavigation: Array<Object> = ConstantConfig.SIDE_NAV;
  scrollbarOptions = { axis: 'y', theme: 'minimal-dark', scrollButtons: { enable: true } };
  isOffCanvasToggle: boolean = false;
  permArr = null;

  constructor(private localStorage: LocalStorageService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private httpService: HttpService
  ) {

    let events = router.events;
    events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.SiteURLActiveCheck(event);
      }
    });
  }

  private SiteURLActiveCheck(event: NavigationEnd): void {
    let self = this;
    _.forEach(this.sideNavigation, function (value, key) {
     _.forEach(value.subMenu, function (v, k) {
         let url = event.url.split('?')[0];
        //console.log(url.match(/proposal/g));
         if(self.getExactString(url,"proposal")) {
          // v.isSelected = true;
         }else if(self.getExactString(url,"proposal-lineitem")) {
           //v.isSelected = true;
         }else{
           //v.isSelected = false;
         }
       });
     });
  }

  private getExactString(str:string,find:string):Boolean{
    let pattern = new RegExp("\\b" + find + "\\b", "g");
    let $location = str.match(pattern) || [];

    if($location.length > 0){
      return true;
    }
    return false;
  }

  //log out button handler
  logOut() {
    if (this.localStorage.get(ConstantConfig.AUTH_TOKEN) != undefined) {
      this.localStorage.remove(ConstantConfig.AUTH_TOKEN);
    }
    window.location.replace("#/login");
    window.location.reload();
    // this.router.navigate(['login']);
  }
  ngOnInit() {
    if( ConstantConfig.MOBILE_CHECK ) {
       console.log('user agent', navigator.userAgent);
          this.isOpen = true;
     }
    this.validateUser();
  }

 /* toggleClass(){
    this.isOpen = !this.isOpen;
  }
*/
  ngAfterViewInit() {
    var that =  this;
    //Sidebar toggle
    /*this.toggleClass();*/


    $('#offCanvas').on('click', function () {
      if ($('#wrapperMenu, #contentWrapper').hasClass('is-open')) {
        $('#wrapperMenu').removeClass('is-open');
        $('#contentWrapper').removeClass('is-open');
        $('.iconHamBurgerExpand').show();
        $('.iconHamBurgerCollapse').hide();
        //that.iconHamburgerExpand = true;
      }else{
        $('#wrapperMenu').addClass('is-open');
        $('#contentWrapper').addClass('is-open');
        $('.left-submenu').attr('style', '');
        $('.leftArrowImg').removeClass('active');
        $('.iconHamBurgerExpand').hide();
        $('.iconHamBurgerCollapse').show();
        //that.iconHamburgerExpand = false;
      }
    });
    $('.navLis').on('click', function () {
      if ($('#wrapperMenu, #contentWrapper').hasClass('is-open')) {
        $('#wrapperMenu').removeClass('is-open');
        $('#contentWrapper').removeClass('is-open');
        that.iconHamburgerExpand = true;
         $('.iconHamBurgerExpand').show();
        $('.iconHamBurgerCollapse').hide();
      }
    });
  }

  //toggle side Navigation
  toggleSection(event, item) {
    var panel = event.currentTarget['nextElementSibling'];
    $('.left-submenu').not(panel).removeClass('active').attr('style', '');
    _.forEach(this.sideNavigation, function (v, k) {
      if (v.menu != item.menu) {
        v.isSelected = false;
      }
    });

    item['isSelected'] = !item['isSelected'];
    panel.style.maxHeight = (panel.style.maxHeight) ? null : panel.scrollHeight + "px";
  }

  setActiveSubMenu(event, item) {
    _.forEach(this.sideNavigation, function (value, key) {
      _.forEach(value.subMenu, function (v, k) {
        v.isSelected = false;
      });
    });
    if( /Android|webOS|iPhone|Opera Mini/i.test(navigator.userAgent) ) {
          this.isOpen = !this.isOpen;
          setTimeout(()=> {
            this.isOpen = true
            this.iconHamburgerExpand =  false;
          }, 500);
          this.toggleSection(event, item);
          // setTimeout(function(){
          //   $("#wrapperMenu").addClass('is-open');
          // } , 500);

      //  this.iconHamburgerExpand =  true;
     }
  }


  validateUser() {
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    if (auth_token === undefined) {
      this.router.navigate(['login']);
    } else {
      this.isLogedInUserDetails();
    }
  }

  //load admin manage-user with information
  isLogedInUserDetails() {
    if (this.authService.isLoggedIn) {
      var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let url = PathConfig.BASE_URL_API + PathConfig.LOGED_IN_USER;
      var data = this.httpService.get(url, auth_token);
      let self = this;

      data.subscribe(
        data => {
          if (data) {
            this.logedInUserrRoleName = data.role.roleName;
            this.logedInUserFullName = data.firstName + " " + data.lastName;
            this.permArr = this.getPermissionsArr(data.role.permissions);
            this.permArr = ["create_creatives","view_campaign_report","view_ad_size", "create_ad_size", "view_campaign", "edit_rate_cards", "create_rate_cards", "view_users","edit_users","view_roles","edit_roles","view_products","edit_products","view_rate_cards","edit_rate_cards","view_creatives","edit_creatives","view_ad_units","edit_ad_units","create_opportunity","view_opportunity","create_proposal","view_proposal","edit_proposal","delete_proposal","new_version_proposal","copy_proposal","edit_proposal_line_items","delete_proposal_line_items","view_order","delete_order","edit_order_line_items","delete_order_line_items","delivery","admin","sales"];
            this.localStorage.set('user_permissions', this.permArr);
            console.log("Permission List: ", data.role.permission);
            console.log("Permission List2: ", this.localStorage.get('user_permissions'));

          }
        },
        err => {
         this.router.navigate(['login']);
        });
      return data;
    }
  }

isMobile(){
  return;
}
  getPermissionsArr(perms){

    let permArr = _.map(perms, 'key');
    return permArr;
  }

  isSubMenuVisible(key){
    //let permArr = this.localStorage.get('user_permissions');
    let index = -1;

    if(this.permArr && this.permArr.length > 0){
        index = this.permArr.indexOf(key);
    }

    return index > -1;
  }
}
