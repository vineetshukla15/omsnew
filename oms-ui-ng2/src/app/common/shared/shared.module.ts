import { NgModule } from '@angular/core';
import { OnlyNumber } from '../directives/onlynumber.directive';

@NgModule({
  imports: [ ],
  declarations: [ 
    OnlyNumber
  ],
  exports: [OnlyNumber]
})
export class SharedModule {

}
