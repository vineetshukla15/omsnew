import {
  Component, Input, HostListener, EventEmitter, Output
} from '@angular/core';


@Component({
  selector: 'select-list',
  template: `
       <div class="custom-select-list">
          <ul>
             <li class="customCheckbox" [ngClass]="{'active':item['isSelected']}"*ngFor="let item of dataList">
               <span class="mat-checkbox-layout checkbox-active">
                  <md-checkbox [ngModel]="item['isSelected']" (change)="onOptionSelect(item)">{{item['label']}}</md-checkbox>
                </span> 
              </li>
          </ul>
        </div>
    `
})
export class SelectListComponent {

  @Input() dataList;
  @Input() type;
  @Output() onSelect: EventEmitter<any> = new EventEmitter();

  scrollbarOptions = { axis: 'y', theme: 'minimal-dark', scrollButtons: { enable: true } };

  //hiding tooltip on component leave
  @HostListener('mouseleave') mouseleave() {

  }

  //Constructor
  constructor() { }

  //on checkbox select menu event bind
  onOptionSelect(item): void {
    if (this.type != 'multi-select') {
      this.dataList.forEach(function (v, k) {
        v['isSelected'] = false;
      });
    }
    item['isSelected'] = !item['isSelected'];
    let result = [];
    this.dataList.forEach(function (v, k) {
      if (v['isSelected']) {
        result.push({ "id": v['id'], "label": v['label'] });
      }
    });
    this.onSelect.emit(result);
  }

}
