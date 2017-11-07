import { Component, Input } from '@angular/core';


@Component({
    selector: 'loader-component',
    template: `
        <div  [ngClass]="{'loader-container':true, 'showModal': (showLoader == 'N')}">
            <div class="loader">
                <img src="./assets/images/loader.gif"/>
                <div class="loaderTxt">LOADING..</div>
            </div>
        </div>
    `,
    styles: [`
     .loader-container{
	position: fixed;
	z-index: 1000;
	background: rgba(255,255,255,0.9);
	width: 100%;
	height: 100%;
	padding: 0px;
	margin: 0px;
	top: 0;
	left: 0;
	display: block;
 
}
 .loader-container.showModal{
  display: none;
 }
.loader {
	text-align: center;
	margin-top: 20%;
}
img{
   width: 60px;
}
.loaderTxt{
	margin:0px 0px 0px 15px;
	text-transform: uppercase;
	font-size: 12px;
	font-weight: 500;
}
  `],
})
export class LoaderComponent {
    @Input() showLoader;
    // Constructor
    constructor() { }
}
