import {Component, Input, EventEmitter, Output, OnInit, HostListener, ElementRef} from '@angular/core';
import { DtSearch } from '../../models/dtSearch';


@Component({
    selector: 'dt-search',
    template: `
         <div class="searchAnimate" [ngClass]="{'is-open':isToggled, 'hideOverflow':!overFlowBool,'showOverflow':overFlowBool}">
              <span class="searchBox">
                  <input type="text" placeholder="Search..." name="searchtext" [(ngModel)]="searchTxt" (keyup)="onChange();" (click)="overFlowBool = false;"/>
                  <!--<select [(ngModel)]="selectedDDValue" (change)="onChange()">
                    <option  *ngFor="let item of ddArray" 
                    [ngValue]="item.id">{{item.labelName}}</option>
                  </select> -->
                  <div class="btn-group" dropdown (click)=" this.overFlowBool = !this.overFlowBool;">
                    <button dropdownToggle type="button" class="btn btn-primary dropdown-toggle">
                       
                        {{selectedItem.labelName}}<span class="caret"></span>
                    </button>
                    <ul *dropdownMenu class="dropdown-menu" role="menu">
                        <li role="menuitem" *ngFor="let item of ddArray">
                            <a class="dropdown-item" href="javascript:void(0);" (click)="selectDropDown(item)">{{item.labelName}}</a>
                        </li>                        
                    </ul>
                  </div>
              </span>
        </div>
        <span class="action-icon search toggleClass" (click)="overFlowBool = false;" ></span>
    `
})
export class DTSearchComponent implements OnInit {

  @Input() ddArray: Array<DtSearch>;
  @Output() onDTSearch: EventEmitter<any> = new EventEmitter();
  selectedDDValue: any;
  searchTxt: string;
  isToggled: Boolean = false;
  selectedItem:DtSearch =new DtSearch();
  overFlowBool = false;
  //Constructor
  constructor(private _elementRef: ElementRef) {}
   
  @HostListener('document:click', ['$event', '$event.target'])
  public onClick(event: MouseEvent, targetElement: HTMLElement): void {

    let isSearchElement = this.getClosest(targetElement);

    if(targetElement.classList.contains("search")){
      this.isToggled = !this.isToggled;
    }

    if(!isSearchElement && !targetElement.classList.contains("search")){
      this.isToggled = false;
    }

    if (!targetElement) {
      return;
    }


    const clickedInside = this._elementRef.nativeElement.contains(targetElement);
    if (!clickedInside) {
      this.overFlowBool = false;
    }
  }

  getClosest = function (elem) {
    for ( ; elem && elem !== document; elem = elem.parentNode ) {
        if ( elem.matches( 'div.searchAnimate' ) ) return elem;
    }
    return null;
  };
    //on component init
    ngOnInit() {
        //selecting default value
        this.selectedDDValue = (this.ddArray.length > 0) ? this.ddArray[0]['id'] : '';
      this.selectedItem = this.ddArray[0];
    }
    selectDropDown(item){
        this.selectedItem=item;
        this.selectedDDValue=item.id;
        this.onDTSearch.emit({"text": this.searchTxt, "dd-id":this.selectedDDValue});

    }
    onDropDownHide(){

    }
   //on drop down menu/ search text change event
   onChange():void{
      //call external function with selected value and drop down value
      this.onDTSearch.emit({"text": this.searchTxt, "dd-id":this.selectedDDValue});

    }
    overflowFunc(){


    }

}
