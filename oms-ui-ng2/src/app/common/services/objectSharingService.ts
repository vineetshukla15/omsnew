
import { Injectable } from '@angular/core';

@Injectable()
export class objectSharingService {
    obj;
    setValue(val): any {
        this.obj = val;
    }

    getValue(val): Object {
        return this.obj;

    }

}
