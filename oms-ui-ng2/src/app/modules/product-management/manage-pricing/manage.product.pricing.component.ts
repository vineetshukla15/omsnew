import { Component, Input, EventEmitter, OnChanges, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { HttpService } from '../../../common/services/http.service';
import { PathConfig } from '../../../common/config/path.config';
import { ConstantConfig } from '../../../common/config/constant.config';
import { LocalStorageService } from "../../../common/services/local-storage.service";
import { Broadcaster } from '../../../common/services/broadcaster.service';
import { AuthService } from '../../../common/services/auth.service';
import { Product } from '../../../common/models/product';
import { RateType } from '../../../common/models/rate.type';
import { ProductType } from '../../../common/models/product.type';
import { Creative } from '../../../common/models/creative';
import { AdUnit } from '../../../common/models/ad.unit';

import * as _ from 'lodash';


@Component({
  templateUrl: './manage-product.component.html',
  styleUrls: ['./manage-product.component.scss'],
})
export class ManageProductPricingComponent implements OnInit {


  productList: Array<Object> = [];
  pList: Array<Object> = [];
  newProduct: Product = new Product();
  showingAddSection: Boolean = new Boolean();
  creativeList: Array<Object> = [];
  productTypeList: Array<Object> = [];
  rateTypeList: Array<Object> = [];
  adUnitList: Array<Object> = [];
  newAdUnit: AdUnit = new AdUnit();
  newProductType: ProductType = new ProductType();
  newRateType: RateType = new RateType();
  newCreative: Creative = new Creative();
  isProductDataLoaded: boolean = false;


  // Constructor with injected service
  constructor(
    private httpService: HttpService,
    private broadcaster: Broadcaster,
    private localStorage: LocalStorageService,
    private authService: AuthService
  ) { }


  ngOnInit() {
    // Load Lookup List
    this.loadLookupList();
    this.showingAddSection = false;
  }



  //data table search with drop down functionality
  productTableSearch: string = '';
  productSearchTableDD: Array<Object> = [
    { "colIndex": 0, "labelName": "Name" },
    { "colIndex": 1, "labelName": "Type" },
    { "colIndex": 2, "labelName": "adUnit" },
    { "colIndex": 3, "labelName": "Rate Type" }
  ];
  productSearchSelectedDD: Object = this.productSearchTableDD[0];



  //load admin manage-product with information
  loadLookupList() {
    if (this.authService.isLoggedIn) {
      this.isProductDataLoaded = false;
      var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let url = PathConfig.BASE_URL_API + PathConfig.PRODUCT_LIST;
      var data = this.httpService.get(url, auth_token);
      let self = this;
      data.subscribe(
        data => {
          if (data) {
            this.pList = data;
            this.productList = data;

          }
        });
      return data;
    }
  }


  //load  manage-creative with information
  loadCretiveList() {
    if (this.authService.isLoggedIn) {
      var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let url = PathConfig.BASE_URL_API + PathConfig.CREATIVE_LIST;
      var data = this.httpService.get(url, auth_token);
      let self = this;
      data.subscribe(
        data => {
          if (data) {
            this.creativeList = data;
            setTimeout(function () {

            }, 10);
          }
        });
      return data;
    }
  }

  //load  Rate type with information
  loadRateTypeList() {
    if (this.authService.isLoggedIn) {
      var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let url = PathConfig.BASE_URL_API + PathConfig.RATE_TYPE_LIST;
      var data = this.httpService.get(url, auth_token);
      let self = this;
      data.subscribe(
        data => {
          if (data) {
            this.rateTypeList = data;
            setTimeout(function () {

            }, 10);
          }
        });
      return data;
    }
  }

  //load  product type with information
  loadProductTypeList() {
    if (this.authService.isLoggedIn) {
      var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let url = PathConfig.BASE_URL_API + PathConfig.PRODUCT_TYPE_LIST;
      var data = this.httpService.get(url, auth_token);
      let self = this;
      data.subscribe(
        data => {
          if (data) {
            this.productList = data;
            setTimeout(function () {

            }, 10);
          }
        });
      return data;
    }
  }

  //load  AdUnit with information
  loadAdUnitList() {
    if (this.authService.isLoggedIn) {
      var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
      let url = PathConfig.BASE_URL_API + PathConfig.AD_UNIT_LIST;
      var data = this.httpService.get(url, auth_token);
      let self = this;
      data.subscribe(
        data => {
          if (data) {
            this.adUnitList = data;
            setTimeout(function () {

            }, 10);
          }
        });
      return data;
    }
  }


  // showing cretive form when user click on add new or edit the product
  showAddNewProductForm() {
    this.loadCretiveList();
    this.loadRateTypeList();
    this.loadProductTypeList();
    this.loadAdUnitList();
    this.showingAddSection = true;
  }


  // reset the form
  resetForm() {
  }

  // save the form
  saveForm() {
    let url = PathConfig.BASE_URL_API + PathConfig.PRODUCT_ADD;
    let auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    this.newProduct.adUnits.push(this.newAdUnit);
    this.newProduct.productType = this.newProductType;
    this.newProduct.rateType = this.newRateType;
    this.newProduct.creatives.push(this.newCreative);
    let data = this.httpService.post(url, this.newProduct, auth_token);
    let self = this;
    data.subscribe(
      data => {

      });
    this.newProduct = new Product();
    this.newAdUnit = new AdUnit();
    this.newProductType = new ProductType();
    this.newRateType = new RateType();
    this.newCreative = new Creative();
    this.showingAddSection = false;
    return data;
  }

}
