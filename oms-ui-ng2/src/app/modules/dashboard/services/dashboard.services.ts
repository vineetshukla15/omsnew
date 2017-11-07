import { Injectable } from '@angular/core';
import * as _ from 'lodash';
import { LocalStorageService } from "../../../common/services/local-storage.service";

const COLOR_MAP: Object = {
    "Approved": "#00AF48",
    "Rejected": "#FF4133",
    "In progress": "#FF9801",
    "Draft": "#ff8080",
    "Sold": "#660000",
    "Pending": "#708090",
    "Admin Review": "6699FF",
    "Legal Review": "#669900",
    "Pending approval": "#00ffcc",
    "Pricing Review": "#9e619e",
    "Pricing approved": "#ff8000",
    "Pricing rejected": "#ffcc99",
    "Submitted": "#00ff00"
}

@Injectable()
export class DashboardServices {
    permissions = null;
    constructor(private localStorage: LocalStorageService,) {
      this.permissions = this.localStorage.get('user_permissions');
    }



    getPieChartRequiredData(data){

        let requiredData = this.generatePieChartData(data);

         return requiredData;
    }

    generatePieChartData(data){
        let obj = [];
        _.each(data, function(value, key){
            var sampleObj = {
              "label": "",
              "color": "",
              "value": ""
            }

            sampleObj.label = key;
            sampleObj.color = COLOR_MAP[key];
            sampleObj.value = value;

            obj.push(sampleObj);
        });

        return obj;

    }

    getBarChartRequiredData(data){
        
        let preparedData = [];
        let graphConfigdData = [];
        let isFirstTime = true;

        let thisRef = this;

        _.each(data, function(obj, key){

              let sampleData = {};

              sampleData['label'] = key;

              _.each(obj, function(value, inKey){
                  // Only consider those graph to be displayed which have value > 0
                  if(value > 0){
                      sampleData[inKey] = value;
                  }

                  //Graphs Config only need once 
                  if(isFirstTime){
                      let graphArr = thisRef.setGraphColorAndId(inKey);
                      graphConfigdData.push(graphArr);
                  }
              });

              isFirstTime = false;

              preparedData.push(sampleData);
        })
        return {'preparedData': preparedData, 'graphConfig': graphConfigdData}
    }

    setGraphColorAndId(key){
        var obj = {
            "balloonText": "[[title]] ([[value]])",
            "fillAlphas": 1,
            "fillColors": COLOR_MAP[key],
            "lineThickness": 0,
            "id": key.replace(/\s+/g, ''),
            "title": key,
            "type": "column",
            "valueField": key,
            "fixedColumnWidth": 60
          }

          return obj;
    }

    getBarChartConfig(){
         return {
          "hideCredits":true,
          "type": "serial",
          "categoryField": "label",
          "borderColor": "#FFFFFF",
          "categoryAxis": {
              "gridPosition": "start",
              "axisAlpha": 0,
              "axisColor": "#FFFFFF",
              "gridColor": "#FFFFFF",
              "gridThickness": 0,
              "titleColor": "#000000"
            },
              "chartCursor": {
              "enabled": true
            },
          "graphs": [],
          "valueAxes": [
            {
              "id": "ValueAxis-1",
              "stackType": "regular",
              "axisColor": "#DEDEDE",
              "axisThickness": 0.2,
              "gridColor": "#DEDEDE",
              "tickLength": 0,
              "title": ""
            }
          ],
          "dataProvider": []
        }
    }

    getPieChartConfig(){
        return {
          "hideCredits":true,
          "type": "pie",
          "startDuration":0,
          "balloonText": "<span style='font-size:14px'>([[percents]]%)</span>",
          "colorField": "color",
          "pullOutOnlyOne": true,
          "innerRadius": "50%",
          "minRadius": 9,
          "hideLabelsPercent": 100,
          "labelsEnabled": false,
          "titleField": "label",
          "valueField": "value",
          "fontSize": 12,
          "theme": "chalk",
          "allLabels": [],
          "balloon": {},
          "titles": [],
          "legend": {
            "enabled": true,
            "align": "center",
            "autoMargins": false,
            "left": 0,
            "position": "right",
          },
          "dataProvider": []
        }
    }

    getTemplateLink(full){
      let tempStr = '';
      if(this.permissions && this.permissions.length && this.permissions.indexOf('edit_proposal')){
        tempStr = "<a href='javascript:void(0);' class='proposal-name' data-name='edit' data-custom='" + full.proposalId + "'>" + full.proposalName + "</a>";
      }else{
          tempStr = "<div>" + full.proposalName + "</div>";
      }

      return tempStr;
    }

    getTableConfig(dateFormatterComponent){
      let self = this;
        return {
      "columnDefs": [
        {
          "className": "table-inline-a",
          "targets": 0,
          "render": function (data, type, full, meta) {
            var template;
            if (data) {
              template = "<div class='col-breadcrumb'><div>" + self.getTemplateLink(full) + "</div>" +
                "<div class='sub-heading'>(" + full.proposalId + ")</div></div>";
            }
            return template;
          }
        },        
        {
          "orderable": false,
          "targets": 4,
          "render": function (data, type, full, meta) {
            let fullDate = dateFormatterComponent.changeDateFormatTo_dd_mm_yy(data, 'dd-MMM-yyyy');
            let template = '<div>' + fullDate + '</div>';
            return template;
          }
       }],
      "columns": [
        { "title": 'Name', "data": "proposalName" },
        { "title": 'Media Planner', "data": "planner" },
        { "title": 'Advertiser', "data": "advertiser" },
        { "title": 'Sales Category', "data": "salesCategory" },
        { "title": 'Due Date', "data": "dueDate" },
      ],
    }
    }
}