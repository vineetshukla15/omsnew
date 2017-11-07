
import { Injectable } from '@angular/core';
import * as _ from 'lodash';

@Injectable()
export class SalesService {
    setTargetTypesInLineItem(lineItem, targettypesaddedlist){ 
    	let targetArr = [];
    	let sampleTargetObj = {
    		audienceTargetType: '',
    		audienceTargetValues: '',
    		id: ''
    	}
    	_.each(targettypesaddedlist, function(obj, index){
    		sampleTargetObj.audienceTargetType = obj;
    		sampleTargetObj.audienceTargetValues = obj.audienceTargetValues;
    		sampleTargetObj.id = lineItem.target.id ? lineItem.target.id: '';
    		targetArr.push(_.clone(sampleTargetObj, true));
    	});

    	lineItem.target = targetArr;
	}

    convertToTargetGridData(targetObj){
        let targetArr = [];
        let sampleObjRef = {
            "targetTypeId": '',
            "tempId": '',
            "name": '',
            "targetTypeAudienceName": '',
            "audienceTargetValues": null
        }
           let self = this;
        _.each(targetObj, function(ele, index){
            let sampleObj = null;
            sampleObj = _.clone(sampleObjRef, true);
            sampleObj.targetTypeId = ele.audienceTargetType.targetTypeId;
            sampleObj.name = ele.audienceTargetType.name;
            sampleObj.targetTypeAudienceName = self.concatenateNames(ele.audienceTargetType.audienceTargetValues);
            sampleObj.audienceTargetValues = ele.audienceTargetType.audienceTargetValues;

            targetArr.push(_.clone(sampleObj, true));
        });

        return targetArr;
    }

    concatenateNames(audienceTargetValues){
        let name = '';

        _.each(audienceTargetValues, function(elmnt, indx){
            name = name + elmnt.value + ', ';
        })

        let lastIndexOfComma = name.lastIndexOf(',');

        if (lastIndexOfComma > 0) {
        name = name.substr(0, name.lastIndexOf(','));
        }

        return name;
    }
}