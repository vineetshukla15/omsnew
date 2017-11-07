
import { Injectable } from '@angular/core';
import * as _ from 'lodash';

@Injectable()
export class CalendarService {

    clickOnDatepicker(thisReff){
    	let thisRef = thisReff;

    	return function(event, type){
    		_.each(thisRef.calendarArr, function(value, key){
		      if(key == type){
		        thisRef.calendarArr[key] = true;
		      }else{
		        thisRef.calendarArr[key] = false;
		      }
		    });

		    event.stopPropagation();
    	}
	}

	calendarBtnClick(thisReff){
    	let thisRef = thisReff;

    	return function(event, type){
    		_.each(this.calendarArr, function(value, key){
		      if(key != type){
		        thisRef.calendarArr[key] = false;
		      }
		    });

		    this.calendarArr[type] = !this.calendarArr[type];

		    event.stopPropagation();
    	}
	}

	resetAllCalendar(thisReff){
    	let thisRef = thisReff;

    	return function(thisRef){
    		 _.each(thisRef.calendarArr, function(value, key){
		        thisRef.calendarArr[key] = false;
		    });
    	}
	}
}