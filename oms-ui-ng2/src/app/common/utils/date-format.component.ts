import { Injectable } from '@angular/core';

@Injectable()
export class DateFormatterComponent {

    changeDateFormatTo_dd_mm_yy(milliSec, format) {
        var t = new Date(milliSec);

        var tf = function (i) { return (i < 10 ? '0' : '') + i };
        return format.replace(/yyyy|MMM|MM|dd|HH|mm|ss/g, function (a) {
            switch (a) {
                case 'yyyy':
                    return tf(t.getFullYear());
                case 'MMM':
                    var month = new Array();
                    month[0] = "Jan";
                    month[1] = "Feb";
                    month[2] = "Mar";
                    month[3] = "Apr";
                    month[4] = "May";
                    month[5] = "Jun";
                    month[6] = "Jul";
                    month[7] = "Aug";
                    month[8] = "Sep";
                    month[9] = "Oct";
                    month[10] = "Nov";
                    month[11] = "Dec";

                    return tf(month[t.getMonth()]);
                case 'MM':
                    return tf(t.getMonth() + 1);
                case 'mm':
                    return tf(t.getMinutes());
                case 'dd':
                    return tf(t.getDate());
                case 'HH':
                    return tf(t.getHours());
                case 'ss':
                    return tf(t.getSeconds());
            }
        })
    }

    constructor() {

    }
}