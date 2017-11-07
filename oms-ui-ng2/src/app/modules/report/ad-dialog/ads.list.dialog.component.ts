import { Component, Inject, OnInit } from "@angular/core";
import { MdDialogRef, MD_DIALOG_DATA } from "@angular/material";


@Component({
  selector: 'goal-ad-list',
  templateUrl: './ads.list.dialog.component.html',
  styles: [`
    .ok-btn{
      margin: auto;
    }
  `]
})
export class AdsListDialogComponent implements OnInit{

	goalAdsListDTConfig = null;
 	goalAdsListData: Array<Object> = [];

  constructor(@Inject(MD_DIALOG_DATA) public data: any, public dialogRef: MdDialogRef<AdsListDialogComponent>) { 
  	this.goalAdsListData = data;
  }

  ngOnInit() {
  	this.goalAdsListDTConfig = this.getGoalAdsConfig();
  }


  getGoalAdsConfig(){
  	let config = {
      "columns": [
        { "title": 'NAME', "data": "name" },
        { "title": 'WEIGHT', "data": "weight" },
        { "title": 'IS ENABLED', "data": "enabled" },
        { "title": "CREATIVE TYPE", "data": "creative.type" },
        { "title": "CREATIVE INSERTION TYPE", "data": "creative.insertionPoint" }
      ]
    }

    return config;
  }
}
